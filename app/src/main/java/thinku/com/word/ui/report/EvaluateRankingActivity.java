package thinku.com.word.ui.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    private RecyclerView ranking_list;
    private SwipeRefreshLayout refer;


    private RankAdapter rankAdapter;
    private List<UserRankBeen.RankBean> rankBeanList;
    private Observable<String> observable ;
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
        observable = RxBus.get().register(C.RXBUS_HEAD_IMAGE ,String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                new GlideUtils().loadCircle(EvaluateRankingActivity.this , NetworkTitle.WORDRESOURE + s ,portrait);
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
        addToCompositeDis(HttpUtil.evRankObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<UserRankBeen>() {
                    @Override
                    public void accept(UserRankBeen userRankBeen) throws Exception {
                        dismissLoadDialog();
                        if (userRankBeen != null) {
                            referUI(userRankBeen);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE ,observable);
    }

    public void referUI(UserRankBeen userRankBeen) {
        rankBeanList.clear();
        rankBeanList.addAll(userRankBeen.getRank());
        rankAdapter.notifyDataSetChanged();
        name.setText(SharedPreferencesUtils.getString("nickname", EvaluateRankingActivity.this));
        num.setText(userRankBeen.getData().getNum());
        ranking_num.setText(userRankBeen.getData().getRank() +"");
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
        ranking_list = (RecyclerView) findViewById(R.id.ranking_list);
        rankBeanList = new ArrayList<>();
        rankAdapter = new RankAdapter(EvaluateRankingActivity.this, rankBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(EvaluateRankingActivity.this, LinearLayoutManager.VERTICAL, false);
        ranking_list.setLayoutManager(manager);
        ranking_list.setAdapter(rankAdapter);
        ranking_bg = (ImageView) findViewById(R.id.ranking_bg);
        ranking_bg.setBackgroundResource(R.mipmap.rank_me);
        refer = (SwipeRefreshLayout) findViewById(R.id.refer);
        refer.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        refer.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refer.setRefreshing(false);
                addNet();
            }
        });
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
