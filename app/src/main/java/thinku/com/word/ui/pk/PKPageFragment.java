package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.PkRankAdapter;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.PkIndexBeen;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.WaitUtils;
import thinku.com.word.view.ProgressView;

/**
 * PK子页
 */

public class PKPageFragment extends BaseFragment {
    private static final String TAG = PKPageFragment.class.getSimpleName();
    Unbinder unbinder;
    private TextView to_pk, name, win_num, lose_num, vocabulary;
    private ImageView portrait;
    private ProgressView winning_probability;
    private XRecyclerView ranking_list;

    @BindView(R.id.userInfo)
    AutoRelativeLayout userInfo ;
    private PkRankAdapter pkRankAdapter;
    private List<PkIndexBeen.RankingListBean> rankingListBeans;

    public static PKPageFragment newInstance() {
        PKPageFragment pkPageFragment = new PKPageFragment();
        return pkPageFragment;
    }

    private Observable<String> observable;

    private Observable<Boolean> referUiObservable ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        observable= RxBus.get().register(C.RXBUS_HEAD_IMAGE ,String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String aBoolean) throws Exception {
                addNet();
            }
        });
        referUiObservable = RxBus.get().register(C.RXBUS_PK_PAGE ,Boolean.class);
        referUiObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                addNet();
            }
        });
        findView(view);
        setClick();
        initRecy();
        addNet();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE, observable);
        RxBus.get().unregister(C.RXBUS_PK_PAGE ,referUiObservable);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initRecy() {
        rankingListBeans = new ArrayList<>();
        ranking_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        pkRankAdapter = new PkRankAdapter(_mActivity, rankingListBeans);
        ranking_list.setAdapter(pkRankAdapter);
    }

    public void addNet() {
        addToCompositeDis(HttpUtil.pkIndexObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<PkIndexBeen>() {
                    @Override
                    public void accept(PkIndexBeen pkIndexBeen) throws Exception {

                        if (pkIndexBeen != null) {
                                referUi(pkIndexBeen);
                        }else{
                            initReferUi();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.toString() );
                    }
                }));
    }



    public void initReferUi(){
        userInfo.setVisibility(View.GONE);
    }
    public void referUi(PkIndexBeen pkIndexBeen) {
        PkIndexBeen.UserBean userBean = pkIndexBeen.getUser();
        if (userBean != null) {
            new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + userBean.getImage(), portrait);
            name.setText(userBean.getNickname());
            win_num.setText("win：" + userBean.getWin());
            lose_num.setText("loss：" + userBean.getLose());
            vocabulary.setText(userBean.getWords());
        }else{
            userInfo.setVisibility(View.GONE);
        }
        if (pkIndexBeen.getRankingList() != null && pkIndexBeen.getRankingList().size() > 0) {
            rankingListBeans.clear();
            rankingListBeans.addAll(pkIndexBeen.getRankingList());
            pkRankAdapter.notifyDataSetChanged();
        }
        winning_probability.setMaxCount(Integer.parseInt(userBean.getWin()) + Integer.parseInt(userBean.getLose()));
        winning_probability.setCurrentCount(Integer.parseInt(userBean.getWin()));
        winning_probability.setColor(R.color.white, R.color.gray_text, R.color.yellow_right_round);
    }

    private void setClick() {
        to_pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PKHomeActivity.start(_mActivity);
            }
        });
    }

    private void findView(View v) {
        to_pk = (TextView) v.findViewById(R.id.to_pk);
        portrait = (ImageView) v.findViewById(R.id.portrait);
        name = (TextView) v.findViewById(R.id.name);
        winning_probability = (ProgressView) v.findViewById(R.id.winning_probability);
        win_num = (TextView) v.findViewById(R.id.win_num);
        lose_num = (TextView) v.findViewById(R.id.lose_num);
        vocabulary = (TextView) v.findViewById(R.id.vocabulary);
        ranking_list = (XRecyclerView) v.findViewById(R.id.ranking_list);
        ranking_list.setRefreshProgressStyle(ProgressStyle.SysProgress);
        ranking_list.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        ranking_list.setArrowImageView(R.drawable.iconfont_downgrey);
        ranking_list
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        ranking_list.getDefaultFootView().setLoadingHint("加载中");
        ranking_list.getDefaultFootView().setNoMoreHint("加载完成");
        ranking_list.setLimitNumberToCallLoadMore(2);
        ranking_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                addNet();
                ranking_list.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                addNet();
                ranking_list.loadMoreComplete();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + hidden );
        if (!hidden) addNet();
    }

}
