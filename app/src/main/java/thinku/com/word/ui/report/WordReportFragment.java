package thinku.com.word.ui.report;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.WeekData;
import thinku.com.word.bean.WordReportBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.PieView;

/**
 * 单词报表
 */

public class WordReportFragment extends BaseFragment {

    private static final String TAG = WordReportFragment.class.getSimpleName();
    @BindView(R.id.total_num)
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
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.setting_date)
    ImageView settingDate;
    Unbinder unbinder;
    @BindView(R.id.week)
    PieView week;
    List<WeekData> weekDataList = new ArrayList<>();
    private   DatePicker picker ;
    public static WordReportFragment newInstance() {
        WordReportFragment wordReportFragment = new WordReportFragment();
        return wordReportFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        initDataPicker();
        addNet();
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
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                    }
                }));
    }

    //  刷新UI
    public void referUi(WordReportBeen wordReportBeen) {
        WordReportBeen.WeekBean weekBean = wordReportBeen.getWeek() ;
        familiarNum.setText(weekBean.getKnowWell());
        notKnowNum.setText(weekBean.getNotKnow());
        knowNum.setText(weekBean.getKnow());
        forgetNum.setText(weekBean.getForget());
        vagueNum.setText(weekBean.getDim());
        totalNum.setText( "总量："+weekBean.getAll());
        if (!"0".equals(weekBean.getAll())){
            getPercent("forget",weekBean.getForget(),weekBean.getAll());
            getPercent("notKnow" ,weekBean.getKnow() , weekBean.getAll());
            getPercent("knowWell" , weekBean.getKnowWell() ,weekBean.getAll());
            getPercent("know",weekBean.getKnow() ,weekBean.getAll());
            getPercent("dim" ,weekBean.getDim() ,weekBean.getAll());
            week.setData(weekDataList , Integer.parseInt(weekBean.getAll()));
        }else{
            week.setData(weekDataList ,Integer.parseInt(weekBean.getAll()));
        }

    }

    public void initDataPicker(){
       picker = new DatePicker(_mActivity);
        picker.setCanceledOnTouchOutside(true);
        picker.setTopPadding(ConvertUtils.toPx(_mActivity, 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(2018, 4, 17);
        String nowDate =  DateUtil.getCurrentYearAndMonth() ;
        List<String> dates = StringUtils.spiltInt(nowDate);
        picker.setSelectedItem(Integer.parseInt(dates.get(0)), Integer.parseInt(dates.get(1)), Integer.parseInt(dates.get(2)));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                date.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    @OnClick(R.id.setting_date)
    public void choseDate(){
        picker.show();
    }
    public void getPercent(String name  , String value ,String all){
        WeekData weekData  ;
        try{
            float values = Float.parseFloat(value) / Float.parseFloat(all);
            weekData = new WeekData(name ,values);
            weekDataList.add(weekData);
        }catch (Exception e){
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
