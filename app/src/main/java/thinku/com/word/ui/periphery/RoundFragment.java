package thinku.com.word.ui.periphery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.EvaAdapter;
import thinku.com.word.ui.periphery.adapter.LiveAdapter;
import thinku.com.word.ui.periphery.adapter.RecentClassAdapter;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.DateUtil;

import static thinku.com.word.http.C.LG_COURSE_GMAT;
import static thinku.com.word.http.C.LG_COURSE_GRE;
import static thinku.com.word.http.C.LG_COURSE_IELTS;
import static thinku.com.word.http.C.LG_COURSE_SCHOOL;
import static thinku.com.word.http.C.LG_COURSE_TOEFL;

/**
 * 周边
 */

public class RoundFragment extends BaseFragment {

    @BindView(R.id.title)
    ImageView title;
    @BindView(R.id.gmat)
    AutoLinearLayout gmat;
    @BindView(R.id.gre)
    AutoLinearLayout gre;
    @BindView(R.id.abroad)
    AutoLinearLayout abroad;
    @BindView(R.id.rollPager)
    RollPagerView rollPager;
    @BindView(R.id.live)
    RecyclerView live;
    Unbinder unbinder;
    @BindView(R.id.toefl)
    AutoLinearLayout toefl;
    @BindView(R.id.IELTS)
    AutoLinearLayout IELTS;
    @BindView(R.id.triangle)
    ImageView triangle;
    @BindView(R.id.eva_list)
    RecyclerView evaList;
    @BindView(R.id.open_class)
    AutoLinearLayout openClass;
    @BindView(R.id.oepn_class_title)
    AutoRelativeLayout oepnClassTitle;
    @BindView(R.id.case_more)
    AutoRelativeLayout caseMore;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.content1)
    TextView content1;
    @BindView(R.id.class1)
    AutoRelativeLayout class1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.content2)
    TextView content2;
    @BindView(R.id.calss2)
    AutoRelativeLayout calss2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.content3)
    TextView content3;
    @BindView(R.id.class3)
    AutoRelativeLayout class3;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.content4)
    TextView content4;
    @BindView(R.id.class4)
    AutoRelativeLayout class4;
    @BindView(R.id.title5)
    TextView title5;
    @BindView(R.id.content5)
    TextView content5;
    @BindView(R.id.class5)
    AutoRelativeLayout class5;
    @BindView(R.id.title6)
    TextView title6;
    @BindView(R.id.content6)
    TextView content6;
    @BindView(R.id.class6)
    AutoRelativeLayout class6;
    @BindView(R.id.title7)
    TextView title7;
    @BindView(R.id.content7)
    TextView content7;
    @BindView(R.id.class7)
    AutoRelativeLayout class7;
    @BindView(R.id.triangle_1)
    ImageView triangle1;

    private RecentClassAdapter recentClassAdapter;
    private List<RoundBean.RecentClassBean> recentClassBeanList;

    private LiveAdapter liveAdapter;
    private List<RoundBean.LivePreviewBean.DataBean> dataBeanList;

    private List<RoundBean.CaseBean> caseBeanList;
    private EvaAdapter evaAdapter;

    private TextView[] titles = new TextView[7];
    private TextView[] contents = new TextView[7];

    private List<RoundBean.ChoicenessBean> choicenessBeanList;

    public static RoundFragment newInstance() {
        RoundFragment peripheryFragment = new RoundFragment();
        return peripheryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_round, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initData();
        return view;
    }


    public void init() {
        dataBeanList = new ArrayList<>();
        liveAdapter = new LiveAdapter(_mActivity, dataBeanList);
        liveAdapter.SelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                ClassDetailActivity.start(_mActivity, dataBeanList.get(position));
            }
        });
        live.setLayoutManager(new LinearLayoutManager(_mActivity));
        live.setAdapter(liveAdapter);
        caseBeanList = new ArrayList<>();
        evaAdapter = new EvaAdapter(_mActivity, caseBeanList);
        evaAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                EvaAllActivity.start(_mActivity, caseBeanList.get(position));
            }
        });
        evaList.setLayoutManager(new LinearLayoutManager(_mActivity));
        evaList.setAdapter(evaAdapter);
        titles[0] = title1;
        titles[1] = title2;
        titles[2] = title3;
        titles[3] = title4;
        titles[4] = title5;
        titles[5] = title6;
        titles[6] = title7;
        contents[0] = content1;
        contents[1] = content2;
        contents[2] = content3;
        contents[3] = content4;
        contents[4] = content5;
        contents[5] = content6;
        contents[6] = content7;
    }

    /**
     * 获取数据
     */
    public void initData() {
        addToCompositeDis(HttpUtil.roundBeanObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                }).subscribe(new Consumer<RoundBean>() {
                    @Override
                    public void accept(@NonNull RoundBean roundBean) throws Exception {
                        if (roundBean != null) {
                            referUi(roundBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(_mActivity, throwable.getMessage());
                    }
                }));
    }

    public void referUi(RoundBean roundBean) {
        recentClassBeanList = roundBean.getRecentClass();
        recentClassAdapter = new RecentClassAdapter(_mActivity, rollPager, recentClassBeanList);
        recentClassAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                ClassDetailActivity.start(_mActivity, recentClassBeanList.get(position));
            }
        });
        rollPager.setAdapter(recentClassAdapter);
        choseLiveList(roundBean.getLivePreview());
        caseBeanList.addAll(roundBean.getCaseX());
        evaAdapter.notifyDataSetChanged();
        setClass(roundBean.getChoiceness());
    }

    public void setClass(List<RoundBean.ChoicenessBean> choicenessBeanList) {
        this.choicenessBeanList = choicenessBeanList;
        for (int i = 0; i < choicenessBeanList.size(); i++) {
            titles[i].setText(getClassType(choicenessBeanList.get(i).getCategoryId()));
            contents[i].setText(choicenessBeanList.get(i).getName());
        }
    }

    public String getClassType(String id) {
        String type = "";
        switch (id) {
            case "1":
                type = "GMAT";
                break;
            case "2":
                type = "托福";
                break;
            case "3":
                type = "雅思";
                break;
            case "4":
                type = "SAT";
                break;
            case "5":
                type = "GRE";
                break;
            case "6":
                type = "留学";
                break;
            default:
                break;
        }
        return type;
    }

    public void choseLiveList(List<RoundBean.LivePreviewBean> livePreviewBeanList) {
        for (RoundBean.LivePreviewBean livePreviewBean : livePreviewBeanList) {
            int i = 0;
            if (DateUtil.compare(DateUtil.dateToString(), livePreviewBean.getDate())) {

                for (int j = 0; j < livePreviewBean.getData().size(); j++) {
                    RoundBean.LivePreviewBean.DataBean dataBean = livePreviewBean.getData().get(j);
                    i++;
                    if (i == 1) {
                        dataBean.setIsTitle(livePreviewBean.getDate());
                    }
                    if (DateUtil.compare(System.currentTimeMillis(), dataBean.getCnName())) {
                        dataBeanList.add(dataBean);
                    } else {
                        dataBeanList.add(dataBean);
                    }
                }
            } else {
                for (int j = 0; j < livePreviewBean.getData().size(); j++) {
                    RoundBean.LivePreviewBean.DataBean dataBean = livePreviewBean.getData().get(j);
                    i++;
                    if (i == 1) {
                        dataBean.setIsTitle(livePreviewBean.getDate());
                    }
                    if (DateUtil.compare(System.currentTimeMillis(), dataBean.getCnName())) {
                        dataBeanList.add(dataBean);
                    } else {
                        dataBeanList.add(dataBean);
                    }
            }
        }
    }
        if(dataBeanList.isEmpty()) {
        openClass.setVisibility(View.GONE);
        oepnClassTitle.setVisibility(View.GONE);
    } else {
        openClass.setVisibility(View.VISIBLE);
        oepnClassTitle.setVisibility(View.VISIBLE);
        liveAdapter.notifyDataSetChanged();
    }

}

    @OnClick({R.id.case_more, R.id.gmat, R.id.gre, R.id.toefl, R.id.IELTS, R.id.abroad,
            R.id.class1, R.id.class3, R.id.class4, R.id.class5, R.id.class6, R.id.class7, R.id.calss2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.case_more:
                AllEvaActivity.start(_mActivity);
                break;
            case R.id.gmat:
                PeripheryFragment.start(_mActivity, LG_COURSE_GMAT);
                break;
            case R.id.gre:
                PeripheryFragment.start(_mActivity, LG_COURSE_GRE);
                break;
            case R.id.toefl:
                PeripheryFragment.start(_mActivity, LG_COURSE_TOEFL);
                break;
            case R.id.IELTS:
                PeripheryFragment.start(_mActivity, LG_COURSE_IELTS);
                break;
            case R.id.abroad:
                PeripheryFragment.start(_mActivity, LG_COURSE_SCHOOL);
                break;
            case R.id.class1:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(0));
                break;
            case R.id.class3:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(2));
                break;
            case R.id.class4:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(3));
                break;
            case R.id.class5:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(4));
                break;
            case R.id.class6:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(5));
                break;
            case R.id.class7:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(6));
                break;
            case R.id.calss2:
                ClassDetailActivity.start(_mActivity, choicenessBeanList.get(1));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
