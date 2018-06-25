package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.pk.adapter.PkWordAdapter;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.ui.share.ShareDateActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.WaitUtils;

/**
 * PK单词团
 */

public class PkWordFragment extends BaseFragment {
    private static final String TAG = PkWordFragment.class.getSimpleName();
    XRecyclerView pkWordRl;
    Unbinder unbinder;

    private PkWordAdapter pkWordAdapter ;
    private List<PkWordData.DataBean>  dataBeanList ;
    private Observable referUiObsrvable ;
    private int page = 1 ;
    public static PkWordFragment newInstance() {
        PkWordFragment pkPageFragment = new PkWordFragment();
        return pkPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk_word, container, false);
        unbinder = ButterKnife.bind(this, view);
        referUiObsrvable = RxBus.get().register(C.RXBUS_PK_WORD ,Boolean.class);
        referUiObsrvable.subscribe(new Consumer() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                addNet();
            }
        });
        pkWordRl = (XRecyclerView) view.findViewById(R.id.pk_word_rl);
        initRecy();
        addNet();
        return view;
    }

    public void initRecy(){
        dataBeanList = new ArrayList<>();
        pkWordAdapter = new PkWordAdapter(_mActivity , dataBeanList);
        pkWordRl.setLayoutManager(new LinearLayoutManager(_mActivity));
        pkWordRl.setAdapter(pkWordAdapter);
        pkWordRl.setRefreshProgressStyle(ProgressStyle.SysProgress);
        pkWordRl.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        pkWordRl.setArrowImageView(R.drawable.iconfont_downgrey);
        pkWordRl
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        pkWordRl.setLoadingMoreEnabled(true);
        pkWordRl.getDefaultFootView().setLoadingHint("加载中");
        pkWordRl.getDefaultFootView().setNoMoreHint("加载完成");
        pkWordRl.setLimitNumberToCallLoadMore(10);
        pkWordRl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                addNet();
                pkWordRl.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                addNet();

            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) addNet();
    }

    /**
     *  获取数据
     */
    public void addNet(){
        addToCompositeDis(HttpUtil.pkDiscoverObservable(page +"" ,"20")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
        .subscribe(new Consumer<PkWordData>() {
            @Override
            public void accept(@NonNull PkWordData pkWordData) throws Exception {
                pkWordRl.loadMoreComplete();
                if (getHttpResSuc(pkWordData.getCode())) {
                    page ++ ;
                    if (pkWordData.getData() != null && pkWordData.getData().size() > 0) {
                        referUi(pkWordData.getData());
                    }
                }else if (pkWordData.getCode() == 99){
                    LoginHelper.needLogin(_mActivity ,"您还未登陆， 请先登陆");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                pkWordRl.loadMoreComplete();
                toTast(_mActivity, HttpUtils.onError(throwable));
            }
        }));
    }


    public void referUi(List<PkWordData.DataBean> dataBeans){
        dataBeanList.addAll(dataBeans);
        pkWordAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister(C.RXBUS_PK_WORD ,referUiObsrvable);
        if(pkWordRl != null){
            pkWordRl.destroy();
            pkWordRl = null;
        }
    }
}
