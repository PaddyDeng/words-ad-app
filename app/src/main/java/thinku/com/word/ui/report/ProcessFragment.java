package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.GMATBagAdapter;
import thinku.com.word.adapter.WordRankAdapter;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.TrackBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.share.ShareDateActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.WaitUtils;
import thinku.com.word.view.CirView;

/**
 * 背单词轨迹
 */

public class ProcessFragment extends BaseFragment {
    private static final String TAG = ProcessFragment.class.getSimpleName();
    @BindView(R.id.total_day)
    TextView totalDay;
    @BindView(R.id.total_num)
    TextView totalNum;
    @BindView(R.id.know_num)
    TextView knowNum;
    @BindView(R.id.unknow_num)
    TextView unknowNum;
    @BindView(R.id.bag_list)
    RecyclerView bagList;
    @BindView(R.id.portrait)
    CircleImageView portrait;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.total_word_num)
    TextView totalWordNum;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.ranking)
    TextView ranking;
    @BindView(R.id.ranking_list)
    RecyclerView rankingList;
    Unbinder unbinder;

    CirView cirView;

    private List<TrackBeen.PackageBean> packageBeanList;
    private List<TrackBeen.RankBean> rankBeanList;
    private GMATBagAdapter gmatBagAdapter;
    private WordRankAdapter wordRankAdapter;
    private Observable<String> observable;
    private Observable<Boolean> referUiObservable ;
    public static ProcessFragment newInstance() {
        ProcessFragment processFragment = new ProcessFragment();
        return processFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_process, container, false);
        unbinder = ButterKnife.bind(this, view);
        cirView = (CirView) view.findViewById(R.id.cirView);
        //  去出焦点， 解决刷新回到recyclerView
        bagList.setFocusableInTouchMode(false);
        bagList.requestFocus();
        rankingList.setFocusableInTouchMode(false);
        rankingList.requestFocus();
        initAdapter();
        referNetUi();
        observable = RxBus.get().register(C.RXBUS_HEAD_IMAGE, String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + s, portrait);
            }
        });
        new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(_mActivity), portrait);
        referUiObservable = RxBus.get().register(C.RXBUS_REPORT_PAGE ,Boolean.class);
        referUiObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                referNetUi();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.e(TAG, "onHiddenChanged: " + hidden );
        if (!hidden) referNetUi();
    }


    // 请求网络刷新UI
    public void referNetUi() {
        addToCompositeDis(HttpUtil.trackObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Consumer<TrackBeen>() {
                    @Override
                    public void accept(TrackBeen trackBeen) throws Exception {
                        if (trackBeen.getCode() == 99) {
                            LoginHelper.needLogin(_mActivity, "您未登陆，请先登陆");
                        } else {
                                new GlideUtils().loadCircle(_mActivity ,NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(_mActivity),portrait);
                            if (trackBeen != null) {
                                totalDay.setText(trackBeen.getInsistDay() + "");
                                totalNum.setText(trackBeen.getUserAllWords());
                                knowNum.setText(trackBeen.getKnow());
                                unknowNum.setText(trackBeen.getNotKnow());
                                name.setText(SharedPreferencesUtils.getString("nickname", _mActivity));
                                totalWordNum.setText(trackBeen.getData().getNum() + "");
                                ranking.setText(trackBeen.getData().getRank() + "");
                                packageBeanList.clear();
                                packageBeanList.addAll(trackBeen.getPackageX());
                                gmatBagAdapter.notifyDataSetChanged();
                                rankBeanList.clear();
                                rankBeanList.addAll(trackBeen.getRank());
                                wordRankAdapter.notifyDataSetChanged();
                                cirView.setData(trackBeen.getNewX(), trackBeen.getReview());
                                SharedPreferencesUtils.setEvaluationNum(_mActivity, trackBeen.getEv().getNum());
                                SharedPreferencesUtils.setRankScore(_mActivity, trackBeen.getData().getRank() + "");
                                SharedPreferencesUtils.setRankNum(_mActivity, trackBeen.getData().getNum() + "");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toTast(_mActivity, HttpUtils.onError(throwable));
                    }
                }));
    }

    /**
     * 初始化adapter
     */
    public void initAdapter() {
        packageBeanList = new ArrayList<>();
        rankBeanList = new ArrayList<>();
        gmatBagAdapter = new GMATBagAdapter(_mActivity, packageBeanList);
        bagList.setLayoutManager(new LinearLayoutManager(_mActivity));
        bagList.setAdapter(gmatBagAdapter);
        wordRankAdapter = new WordRankAdapter(_mActivity, rankBeanList);
        rankingList.setLayoutManager(new LinearLayoutManager(_mActivity));
        rankingList.setAdapter(wordRankAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE, observable);
        RxBus.get().unregister(C.RXBUS_REPORT_PAGE ,referUiObservable);
    }

}
