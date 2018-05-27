package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.CourseAdapter;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.HttpUtils;

/**
 * 周边
 */

public class PeripheryFragment extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.course_list)
    RecyclerView courseList;
    Unbinder unbinder;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefer;

    private List<CourseBean> courseBeanList;
    private CourseAdapter courseAdapter;

    private int type = 0  ;

    private int page  =1 ;
    private int pageSize = 20 ;

    private List<RoundBean.LivePreviewBean.DataBean> dataBeanList ;

    private CourseAdapter  liveAdatper ;
    public static void start(Context context , int type){
        Intent intent = new Intent(context ,PeripheryFragment.class);
        intent.putExtra("type" , type);
        context.startActivity(intent);
    }

    public static void start(Context context ){
        Intent intent = new Intent(context ,PeripheryFragment.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_periphery);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent() ;
        if (intent != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        init();
        initData();
        swipeRefer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                swipeRefer.setRefreshing(false);
            }
        });
    }


    public void initData(){
        if (type  != 0) {
            addToCompositeDis(HttpUtil.courseBeanObservable(type)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<List<CourseBean>>() {
                        @Override
                        public void accept(@NonNull final List<CourseBean> courseBeans) throws Exception {
                            dismissLoadDialog();
                            if (courseBeans != null && courseBeans.size() > 0) {
                                courseBeanList.addAll(courseBeans);
                                courseAdapter.notifyDataSetChanged();
                                courseAdapter.setSelectRlClickListener(new SelectRlClickListener() {
                                    @Override
                                    public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                                            ClassDetailActivity.start(PeripheryFragment.this ,courseBeans.get(position));
                                    }
                                });
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            toTast(PeripheryFragment.this, throwable.toString());
                        }
                    }));
        }else{
            addToCompositeDis(HttpUtil.liveListen(page +"" ,pageSize +"")
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(@NonNull Disposable disposable) throws Exception {
                    showLoadDialog();
                }
            }).subscribe(new Consumer<List<RoundBean.LivePreviewBean.DataBean> >() {
                        @Override
                        public void accept(@NonNull List<RoundBean.LivePreviewBean.DataBean> dataBeanLists) throws Exception {
                            dismissLoadDialog();
                            dataBeanList.addAll(dataBeanLists);
                            liveAdatper.notifyDataSetChanged();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            toTast(PeripheryFragment.this ,throwable.toString());
                        }
                    }));
        }
    }

    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }

    public void init() {
        if (type != 0) {
            titleT.setText("课程列表");
        }else{
            titleT.setText("直播列表");
        }
        if (type != 0) {
            courseBeanList = new ArrayList<>();
            courseAdapter = new CourseAdapter(this, courseBeanList);
            courseAdapter.setSelectListener(new SelectListener() {
                @Override
                public void setListener(int position) {
                    CourseBean courseBean = courseBeanList.get(position);
                    if (courseBean != null) {
                        PlayActivity.start(PeripheryFragment.this, courseBean.getContent(), courseBean.getName(), courseBean.getUrl());
                    }
                }
            });
            courseList.setLayoutManager(new LinearLayoutManager(this));
            courseList.setAdapter(courseAdapter);
        }else{
            dataBeanList = new ArrayList<>();
            liveAdatper = new CourseAdapter(this);
            liveAdatper.setData(dataBeanList);
            courseList.setLayoutManager(new LinearLayoutManager(this));
            courseList.setAdapter(liveAdatper);
            liveAdatper.setSelectListener(new SelectListener() {
                @Override
                public void setListener(int position) {
                    RoundBean.LivePreviewBean.DataBean dataBean = dataBeanList.get(position);
                    if (dataBean != null) {
                       ClassDetailActivity.start(PeripheryFragment.this ,dataBean);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
