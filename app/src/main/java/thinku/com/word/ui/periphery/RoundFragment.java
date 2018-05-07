package thinku.com.word.ui.periphery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.LiveAdapter;
import thinku.com.word.ui.periphery.adapter.RecentClassAdapter;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.DateUtil;

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

    private RecentClassAdapter recentClassAdapter;
    private List<RoundBean.RecentClassBean> recentClassBeanList;

    private LiveAdapter liveAdapter ;
    private List<RoundBean.LivePreviewBean.DataBean> dataBeanList ;
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

    public void init(){
        dataBeanList = new ArrayList<>();
        liveAdapter = new LiveAdapter(_mActivity,dataBeanList);
        live.setLayoutManager(new LinearLayoutManager(_mActivity));
        live.setAdapter(liveAdapter);
    }
    /**
     * 获取数据
     */
    public void initData(){
        addToCompositeDis(HttpUtil.roundBeanObservable()
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {

            }
        }).subscribe(new Consumer<RoundBean>() {
                    @Override
                    public void accept(@NonNull RoundBean roundBean) throws Exception {
                        if (roundBean != null){
                            referUi(roundBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(_mActivity , throwable.getMessage());
                    }
                }));
    }

    public void referUi(RoundBean roundBean){
        recentClassBeanList = roundBean.getRecentClass();
        recentClassAdapter = new RecentClassAdapter(_mActivity, rollPager, recentClassBeanList);
        rollPager.setAdapter(recentClassAdapter);
        choseLiveList(roundBean.getLivePreview());

    }

    public void choseLiveList(List<RoundBean.LivePreviewBean> livePreviewBeanList ){
       for (RoundBean.LivePreviewBean  livePreviewBean : livePreviewBeanList){
             if (DateUtil.compare(DateUtil.dateToString(),livePreviewBean.getDate())){
                for (RoundBean.LivePreviewBean.DataBean dataBean : livePreviewBean.getData()){
                    if (DateUtil.compare(System.currentTimeMillis() ,dataBean.getCnName())){
                        dataBeanList.add(dataBean);
                    }
                }
           }else{

             }
       }
       if (dataBeanList.isEmpty()){
            live.setVisibility(View.GONE);
       }else {
           live.setVisibility(View.VISIBLE);
           liveAdapter.notifyDataSetChanged();
       }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
