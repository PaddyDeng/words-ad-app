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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.periphery.adapter.CourseAdapter;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.utils.HttpUtils;

/**
 * 周边
 */

public class PeripheryFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

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
    public static PeripheryFragment newInstance() {
        PeripheryFragment peripheryFragment = new PeripheryFragment();
        return peripheryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_periphery, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initRefreshLayout();
        return view;
    }


    public void initRefreshLayout(){
        // 为BGARefreshLayout 设置代理
        swipeRefer.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(_mActivity, true);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    public void init() {
        titleT.setText("课程列表");
        courseAdapter = new CourseAdapter(_mActivity, courseBeanList);
        courseList.setLayoutManager(new LinearLayoutManager(_mActivity));
        courseList.setAdapter(courseAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (HttpUtils.isConnected(_mActivity)){    //  有网   加载数据
            swipeRefer.endRefreshing();
        }else{
            // 网络不可用，结束下拉刷新
            toTast(_mActivity ,"网络不可用");
            swipeRefer.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        if (HttpUtils.isConnected(_mActivity)){    //  有网   加载数据
            swipeRefer.endLoadingMore();
            return true ;
        }else{
            // 网络不可用，结束下拉刷新
            toTast(_mActivity ,"网络不可用");
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
