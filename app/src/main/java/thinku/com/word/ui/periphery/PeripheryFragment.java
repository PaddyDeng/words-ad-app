package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
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

public class PeripheryFragment extends BaseActivity  {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.course_list)
    XRecyclerView pkWordRl;
    Unbinder unbinder;

    private List<CourseBean> courseBeanList;
    private CourseAdapter courseAdapter;

    private int type = 0;

    private int page = 1;
    private int pageSize = 20;

    private List<RoundBean.LivePreviewBean.DataBean> dataBeanList;

    private CourseAdapter liveAdatper;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, PeripheryFragment.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PeripheryFragment.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_periphery);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        init();
        initRecycler();
        initData();
    }

    public void initRecycler(){
        pkWordRl.setRefreshProgressStyle(ProgressStyle.SysProgress);
        pkWordRl.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        pkWordRl.setArrowImageView(R.drawable.iconfont_downgrey);
        pkWordRl
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        pkWordRl.setLoadingMoreEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pkWordRl.setLayoutManager(linearLayoutManager);
        pkWordRl.getDefaultFootView().setLoadingHint("加载中");
        pkWordRl.getDefaultFootView().setNoMoreHint("加载完成");
        pkWordRl.setLimitNumberToCallLoadMore(2);
        pkWordRl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                 initData();
                pkWordRl.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page ++ ;
                initData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void initData() {
        if (type != 0) {
            addToCompositeDis(HttpUtil.courseBeanObservable(type)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
//                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<List<CourseBean>>() {
                        @Override
                        public void accept(@NonNull final List<CourseBean> courseBeans) throws Exception {
//                            dismissLoadDialog();
                            pkWordRl.loadMoreComplete();
                            if (courseBeans != null) {
                                courseBeanList.clear();
                                courseBeanList.addAll(courseBeans);
                                Log.e(TAG, "accept: " + courseBeanList.size() );
                                courseAdapter.notifyDataSetChanged();
                                courseAdapter.setSelectRlClickListener(new SelectRlClickListener() {
                                    @Override
                                    public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                                        ClassDetailActivity.start(PeripheryFragment.this, courseBeans.get(position));
                                    }
                                });
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
//                            dismissLoadDialog();
                            pkWordRl.loadMoreComplete();
                            toTast(PeripheryFragment.this, throwable.toString());
                        }
                    }));
        } else {
            addToCompositeDis(HttpUtil.liveListen(page + "", pageSize + "")
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
//                            showLoadDialog();

                        }
                    }).subscribe(new Consumer<List<RoundBean.LivePreviewBean.DataBean>>() {
                        @Override
                        public void accept(@NonNull List<RoundBean.LivePreviewBean.DataBean> dataBeanLists) throws Exception {
//                           dismissLoadDialog();
                            pkWordRl.loadMoreComplete();
                            if (dataBeanList != null) {
                                dataBeanList.addAll(dataBeanLists);
                                Log.e(TAG, "accept: " + dataBeanLists.size() );
                                liveAdatper.notifyDataSetChanged();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
//                            dismissLoadDialog();
                            page-- ;
                            pkWordRl.loadMoreComplete();
                            toTast(PeripheryFragment.this, throwable.toString());
                        }
                    }));
        }
    }

    @OnClick(R.id.back)
    public void back() {
        this.finishWithAnim();
    }

    public void init() {
        if (type != 0) {
            titleT.setText("课程列表");
        } else {
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
            pkWordRl.setAdapter(courseAdapter);
        } else {
            dataBeanList = new ArrayList<>();
            liveAdatper = new CourseAdapter(this);
            liveAdatper.setData(dataBeanList);
            pkWordRl.setAdapter(liveAdatper);
            liveAdatper.setSelectListener(new SelectListener() {
                @Override
                public void setListener(int position) {
                    RoundBean.LivePreviewBean.DataBean dataBean = dataBeanList.get(position);
                    if (dataBean != null) {
                        ClassDetailActivity1.start(PeripheryFragment.this, dataBean);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (pkWordRl != null){
            pkWordRl.destroy();
            pkWordRl = null;
        }
    }

}
