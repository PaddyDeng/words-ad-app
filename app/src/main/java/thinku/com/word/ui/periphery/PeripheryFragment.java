package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.CourseAdapter;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.utils.HttpUtils;

/**
 * 周边
 */

public class PeripheryFragment extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.course_list)
    RecyclerView courseList;
    Unbinder unbinder;
    @BindView(R.id.swipeRefer)
    BGARefreshLayout swipeRefer;

    private List<CourseBean> courseBeanList;
    private CourseAdapter courseAdapter;

    private int type ;
    public static void start(Context context , int type){
        Intent intent = new Intent(context ,PeripheryFragment.class);
        intent.putExtra("type" , type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_periphery);
        unbinder = ButterKnife.bind(this);
        try{
            type = getIntent().getIntExtra("type" , 1);
        }catch (Exception e){

        }
        init();
        initRefreshLayout();
        initData();
    }


    public void initData(){
        addToCompositeDis(HttpUtil.courseBeanObservable(type)
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                showLoadDialog();
            }
        }).subscribe(new Consumer<List<CourseBean>>() {
                    @Override
                    public void accept(@NonNull List<CourseBean> courseBeans) throws Exception {
                        dismissLoadDialog();
                        if (courseBeans != null && courseBeans.size() > 0){
                            courseBeanList.addAll(courseBeans);
                            courseAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        toTast(PeripheryFragment.this ,throwable.toString());
                    }
                }));
    }



    public void initRefreshLayout(){
        // 为BGARefreshLayout 设置代理
        swipeRefer.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        swipeRefer.setRefreshViewHolder(refreshViewHolder);
//
//
//        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
//        // 设置正在加载更多时不显示加载更多控件
//        // mRefreshLayout.setIsShowLoadingMoreView(false);
//        // 设置正在加载更多时的文本
//        refreshViewHolder.setLoadingMoreText(loadingMoreText);
//        // 设置整个加载更多控件的背景颜色资源 id
//        refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
//        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
//        // 设置下拉刷新控件的背景颜色资源 id
//        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
//        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
//        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//        mRefreshLayout.setCustomHeaderView(mBanner, false);
//        // 可选配置  -------------END
    }

    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }

    public void init() {
        titleT.setText("课程列表");
        courseBeanList = new ArrayList<>();
        courseAdapter = new CourseAdapter(this, courseBeanList);
        courseAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                CourseBean courseBean = courseBeanList.get(position);
                if (courseBean!= null ) {
                    PlayActivity.start(PeripheryFragment.this,courseBean.getContent(),courseBean.getName() ,courseBean.getUrl() );
                }
            }
        });
        courseList.setLayoutManager(new LinearLayoutManager(this));
        courseList.setAdapter(courseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (HttpUtils.isConnected(this)){    //  有网   加载数据
            swipeRefer.endRefreshing();
            initData();
        }else{
            // 网络不可用，结束下拉刷新
            toTast(this ,"网络不可用");
            swipeRefer.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        if (HttpUtils.isConnected(this)){    //  有网   加载数据
            swipeRefer.endLoadingMore();
            return true ;
        }else{
            // 网络不可用，结束下拉刷新
            toTast(this ,"网络不可用");
            swipeRefer.endLoadingMore();
            return false;
        }

    }

    public void beginRefreshing() {
        swipeRefer.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        swipeRefer.beginLoadingMore();
    }
}
