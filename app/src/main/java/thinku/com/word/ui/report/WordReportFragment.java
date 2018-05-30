package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.WeekData;
import thinku.com.word.bean.WordReportBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.C;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.WaitUtils;
import thinku.com.word.view.BarChartView;
import thinku.com.word.view.PieView;

/**
 * 单词报表
 */

public class WordReportFragment extends BaseFragment {

    private static final String TAG = WordReportFragment.class.getSimpleName();
    @BindView(R.id.weekAll)
    TextView totalNum;
    @BindView(R.id.familiar_num)
    TextView familiarNum;
    @BindView(R.id.know_num)
    TextView knowNum;
    @BindView(R.id.not_know_num)
    TextView notKnowNum;
    @BindView(R.id.vague_num)
    TextView vagueNum;
    @BindView(R.id.forget_num)
    TextView forgetNum;
    Unbinder unbinder;
    @BindView(R.id.week)
    PieView week;
    List<WeekData> weekDataList = new ArrayList<>();
    private int poisition = 0;
    //  日报图
    private List<String> xValue = new ArrayList<>();
    private List<Integer> yValue = new ArrayList<>();
    private Map<String, List<Integer>> Value = new HashMap<>();
    @BindView(R.id.date_report)
//    BarChartView dateReport;
    BarChartView dateReport;
    private Observable<Boolean> exitLoginObservable;
    private Observable<Boolean> referUiObservable;

    public static WordReportFragment newInstance() {
        WordReportFragment wordReportFragment = new WordReportFragment();
        return wordReportFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        exitLoginObservable = RxBus.get().register(C.RXBUS_EXLOING, Boolean.class);
        exitLoginObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                addNet();
            }
        });
        referUiObservable = RxBus.get().register(C.RXBUS_REPORT_WORD, Boolean.class);
        referUiObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                addNet();
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    //  网络请求
    public void addNet() {
        addToCompositeDis(HttpUtil.wordReportBeenObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Consumer<WordReportBeen>() {
                    @Override
                    public void accept(WordReportBeen wordReportBeen) throws Exception {
                        if (getHttpResSuc(wordReportBeen.getCode())) {
                            referUi(wordReportBeen);
                        } else if (wordReportBeen.getCode() == 99) {
                            LoginHelper.needLogin(_mActivity, "您还未登陆，请先登陆");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.e(TAG, "onHiddenChanged: " + hidden );
        if (!hidden) addNet();
    }

    //  刷新UI
    public void referUi(WordReportBeen wordReportBeen) {
        WordReportBeen.WeekBean weekBean = wordReportBeen.getWeek();
        familiarNum.setText("(" + weekBean.getKnowWell() +")");
        notKnowNum.setText("(" +weekBean.getNotKnow() +")");
        knowNum.setText("(" +weekBean.getKnow()+")");
        forgetNum.setText("("+weekBean.getForget()+")");
        vagueNum.setText("("+weekBean.getDim()+")");
        totalNum.setText("总量：(" + weekBean.getAll()+")");
        if (!"0".equals(weekBean.getAll())) {
            getPercent("forget", weekBean.getForget(), weekBean.getAll());
            getPercent("notKnow", weekBean.getNotKnow(), weekBean.getAll());
            getPercent("knowWell", weekBean.getKnowWell(), weekBean.getAll());
            getPercent("know", weekBean.getKnow(), weekBean.getAll());
            getPercent("dim", weekBean.getDim(), weekBean.getAll());
            week.setData(weekDataList, Integer.parseInt(weekBean.getAll()));
        } else {
            week.setData(weekDataList, Integer.parseInt(weekBean.getAll()));
        }
        getXValue(wordReportBeen.getData());
        setyValue(wordReportBeen.getData());
    }

    public void getXValue(WordReportBeen.DataBeanX dataBeanX) {
        xValue.clear();
        if (dataBeanX.getRe() != null && dataBeanX.getRe().size() > 0) {
            poisition = dataBeanX.getRe().size() - 1;
            XValueString(dataBeanX.getRe());
        }
        if (dataBeanX.getRe1() != null && dataBeanX.getRe1().size() > 0) {
            XValueString1(dataBeanX.getRe1());

        }
    }

    /**
     * 设Y的值
     *
     * @param dataBeanX
     */
    public void setyValue(WordReportBeen.DataBeanX dataBeanX) {
        if (Value.size() == 0) {
            int max = 0;
            if (dataBeanX.getRe() != null && dataBeanX.getRe().size() > 0) {
                for (WordReportBeen.DataBeanX.ReBean reBean : dataBeanX.getRe()) {
                    int all = StringToInt(reBean.getData().getAll());
                    if (all > max) max = all;
                    Value.put(DateToInt(reBean.getDate()), setXDateListValue(reBean.getData()));
                }
            }
            if (dataBeanX.getRe1() != null && dataBeanX.getRe1().size() > 0) {
                for (WordReportBeen.DataBeanX.Re1Bean re1Bean : dataBeanX.getRe1()) {
                    List<Integer> after = new ArrayList<>();
                    int all = re1Bean.getData();
                    if (all > max) max = all;
                    after.add(all);
                    Value.put(DateToInt(re1Bean.getDate()), after);
                }
            }
            dateReport.setMaxValue(max);
            int percent = max / 7;
            yValue.clear();
            for (int i = 0; i < 7; i++) {
                yValue.add(percent * i);
            }
            dateReport.setData(xValue, Value);
        }
    }

    public List<Integer> setXDateListValue(WordReportBeen.DataBeanX.ReBean.DataBean dataBean) {
        List<Integer> list = new ArrayList<>();
        list.add(StringToInt(dataBean.getNotKnow()));
        list.add(StringToInt(dataBean.getForget()));
        list.add(StringToInt(dataBean.getDim()));
        list.add(StringToInt(dataBean.getKnow()));
        list.add(StringToInt(dataBean.getKnowWell()));
        return list;
    }

    public int setAll(WordReportBeen.DataBeanX.ReBean.DataBean dataBean) {
        int all = 0;
        all = StringToInt(dataBean.getAll()) + StringToInt(dataBean.getNotKnow()) + StringToInt(dataBean.getKnowWell())
                + StringToInt(dataBean.getForget()) + StringToInt(dataBean.getDim());
        return all;
    }

    public int StringToInt(String num) {
        int value = 0;
        try {
            value = Integer.parseInt(num);
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 将日期转化为天数  ， 比如 一天前 ， 一天后
     *
     * @param reBeanList
     */
    public void XValueString(List<WordReportBeen.DataBeanX.ReBean> reBeanList) {
        for (WordReportBeen.DataBeanX.ReBean reBean : reBeanList) {
            int instance = DateUtil.differentDays(reBean.getDate());
            if (instance < 0) {
                xValue.add(Math.abs(instance) + "天后");
            } else if (instance == 0) {
                xValue.add(poisition, "今天");
            } else {
                xValue.add(Math.abs(instance) + "天前");
            }
        }

    }

    /**
     * 将日期转化为天数  ， 比如 一天前 ， 一天后
     *
     * @param reBeanList
     */
    public void XValueString1(List<WordReportBeen.DataBeanX.Re1Bean> reBeanList) {
        for (WordReportBeen.DataBeanX.Re1Bean reBean : reBeanList) {
            int instance = DateUtil.differentDays(reBean.getDate());
            if (instance < 0) {
                xValue.add(Math.abs(instance) + "天后");
            } else if (instance == 0) {
                xValue.add(poisition, "今天");
            } else {
                xValue.add(Math.abs(instance) + "天前");
            }
        }

    }

    /**
     * 从 2018-05-12  转为 一天前
     */
    public String DateToInt(String time) {
        String times = "";
        int instance = DateUtil.differentDays(time);
        if (instance < 0) {
            times = Math.abs(instance) + "天后";
        } else if (instance > 0) {
            times = Math.abs(instance) + "天前";
        } else {
            times = "今天";
        }
        return times;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addNet();
    }


    public void getPercent(String name, String value, String all) {
        WeekData weekData;
        try {
            float values = Float.parseFloat(value) / Float.parseFloat(all);
            weekData = new WeekData(name, values);
            weekDataList.add(weekData);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister(C.RXBUS_EXLOING, exitLoginObservable);
        RxBus.get().unregister(C.RXBUS_REPORT_WORD, referUiObservable);
    }


}
