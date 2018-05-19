package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import thinku.com.word.adapter.PkRankAdapter;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.PkIndexBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.WaitUtils;
import thinku.com.word.view.ProgressView;

/**
 * PK子页
 */

public class PKPageFragment extends BaseFragment {
    private static final String TAG = PKPageFragment.class.getSimpleName();
    @BindView(R.id.swipeRefer)
    SwipeRefreshLayout swipeRefer;
    Unbinder unbinder;
    private TextView to_pk, name, win_num, lose_num, vocabulary;
    private ImageView portrait;
    private ProgressView winning_probability;
    private RecyclerView ranking_list;


    private PkRankAdapter pkRankAdapter;
    private List<PkIndexBeen.RankingListBean> rankingListBeans;

    public static PKPageFragment newInstance() {
        PKPageFragment pkPageFragment = new PKPageFragment();
        return pkPageFragment;
    }

    private Observable<String> observable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
        initRecy();
        addNet();
        observable = RxBus.get().register(C.RXBUS_HEAD_IMAGE, String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + s, portrait);
                addNet();
            }
        });
        swipeRefer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefer.setRefreshing(false);
                addNet();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE, observable);
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
                        WaitUtils.show(_mActivity, TAG);
                    }
                })
                .subscribe(new Consumer<PkIndexBeen>() {
                    @Override
                    public void accept(PkIndexBeen pkIndexBeen) throws Exception {
                        if (WaitUtils.isRunning(TAG)) {
                            WaitUtils.dismiss(TAG);
                        }
                        if (pkIndexBeen != null) {
                            referUi(pkIndexBeen);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (WaitUtils.isRunning(TAG)) {
                            WaitUtils.dismiss(TAG);
                        }
                    }
                }));
    }

    public void referUi(PkIndexBeen pkIndexBeen) {
        PkIndexBeen.UserBean userBean = pkIndexBeen.getUser();
        if (userBean != null) {
            new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + userBean.getImage(), portrait);
            name.setText(userBean.getNickname());
            win_num.setText("win：" + userBean.getWin());
            lose_num.setText("loss：" + userBean.getLose());
            vocabulary.setText(userBean.getWords());
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
        ranking_list = (RecyclerView) v.findViewById(R.id.ranking_list);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
