package thinku.com.word.ui.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.RankAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.UserRankBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 评估排名
 */

public class EvaluateRankingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back, share, portrait, ranking_bg;
    private TextView title_t, name, num, ranking_num, tv;
    private XRecyclerView ranking_list;

    private RankAdapter rankAdapter;
    private List<UserRankBeen.RankBean> rankBeanList;
    private Observable<String> observable;
    private int page = 1 ;
    private int size = 15 ;
    public static void start(Context context) {
        Intent intent = new Intent(context, EvaluateRankingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_ranking);
        ButterKnife.bind(this);
        findView();
        setClick();
        addNet();
        observable = RxBus.get().register(C.RXBUS_HEAD_IMAGE, String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                new GlideUtils().loadCircle(EvaluateRankingActivity.this, NetworkTitle.WORDRESOURE + s, portrait);
                addNet();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setClick() {
        back.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    public void addNet() {
        addToCompositeDis(HttpUtil.evRankObservable(page +"" , size +"")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showLoadDialog();
                    }
                }).subscribe(new Consumer<UserRankBeen>() {
                    @Override
                    public void accept(UserRankBeen userRankBeen) throws Exception {
//                        dismissLoadDialog();
                        ranking_list.loadMoreComplete();
                        if (userRankBeen != null) {
                            referUI(userRankBeen);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        dismissLoadDialog();
                        ranking_list.loadMoreComplete();
                        page-- ;
                        toTast(throwable.getMessage());
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE, observable);
        if (ranking_list != null) {
            ranking_list.destroy();
            ranking_list = null;
        }

    }

    public void referUI(UserRankBeen userRankBeen) {
        rankBeanList.addAll(userRankBeen.getRank());
        rankAdapter.notifyDataSetChanged();
        name.setText(SharedPreferencesUtils.getString("nickname", EvaluateRankingActivity.this));
        num.setText(userRankBeen.getData().getNum());
        ranking_num.setText(userRankBeen.getData().getRank() + "");
        new GlideUtils().loadCircle(EvaluateRankingActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(this), portrait);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("评估排名");
        share = (ImageView) findViewById(R.id.title_iv);
        share.setBackgroundResource(R.mipmap.share);
        share.setOnClickListener(this);
        portrait = (ImageView) findViewById(R.id.portrait);
        name = (TextView) findViewById(R.id.name);
        num = (TextView) findViewById(R.id.num);
        ranking_num = (TextView) findViewById(R.id.ranking_num);
        tv = (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.VISIBLE);
        ranking_list = (XRecyclerView) findViewById(R.id.ranking_list);
        rankBeanList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(EvaluateRankingActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ranking_list.setLayoutManager(manager);
        rankAdapter = new RankAdapter(this, rankBeanList);
        ranking_list.setAdapter(rankAdapter);
        ranking_list.setRefreshProgressStyle(ProgressStyle.SysProgress);
        ranking_list.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        ranking_list.setArrowImageView(R.drawable.iconfont_downgrey);
        ranking_list
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        ranking_list.getDefaultFootView().setLoadingHint("加载中");
        ranking_list.getDefaultFootView().setNoMoreHint("加载完成");
        ranking_list.setLimitNumberToCallLoadMore(10);
        ranking_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                addNet();
                ranking_list.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++ ;
                addNet();

            }
        });
        ranking_bg = (ImageView) findViewById(R.id.ranking_bg);
        ranking_bg.setBackgroundResource(R.mipmap.rank_me);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_iv:
                share();
                break;
        }
    }

    public void share() {
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片文件路径
        String filePath = sdCardPath + File.separator + System.currentTimeMillis() + ".png";
        ShareUtils.shareOnlyImage(this, filePath);
    }


}
