package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.WordAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.PackageDetails;
import thinku.com.word.http.HttpUtil;

/**
 * Created by Administrator on 2018/2/9.
 */

public class WordbagDetailActivity extends BaseActivity {
    private static final String TAG = WordbagDetailActivity.class.getSimpleName();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.hava_num)
    TextView havaNum;
    @BindView(R.id.total_num)
    TextView totalNum;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.to_recite)
    TextView toRecite;
    @BindView(R.id.word_list)
    XRecyclerView pkWordRl;
    private String catId;
    private int page = 1;
    private String total;
    private String userwords;
    private WordAdapter wordAdapter;
    private String name;
    private int getIs;
    private List<PackageDetails.Word> words = new ArrayList<>();
    public static void start(Context context, String catId, String total, String userwords, String name, int getIs) {
        Intent intent = new Intent(context, WordbagDetailActivity.class);
        intent.putExtra("catId", catId);
        intent.putExtra("total", total);
        intent.putExtra("userwords", userwords);
        intent.putExtra("name", name);
        intent.putExtra("getIs", getIs);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            catId = intent.getStringExtra("catId");
            total = intent.getStringExtra("total");
            userwords = intent.getStringExtra("userwords");
            name = intent.getStringExtra("name");
            getIs = intent.getIntExtra("getIs", 0);
        }
        setContentView(R.layout.activity_wordbag_detail);
        ButterKnife.bind(this);
        init();
        initData();
        initRecycler();
        titleT.setText(name);
    }

    public void initRecycler(){
        wordAdapter = new WordAdapter(WordbagDetailActivity.this, words);
        pkWordRl.setLayoutManager(new LinearLayoutManager(WordbagDetailActivity.this));
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
        pkWordRl.setAdapter(wordAdapter);
        pkWordRl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                pkWordRl.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++ ;
                initData();

            }
        });
    }

    public void init() {
        havaNum.setText(userwords);
        totalNum.setText(total);

    }


    public void initData() {
        addToCompositeDis(HttpUtil.packageDetailsObservable(catId + "", page + "")
                .subscribe(new Consumer<PackageDetails>() {
                    @Override
                    public void accept(@NonNull PackageDetails packageDetails) throws Exception {
                        if (packageDetails.getPackageDetails() != null && packageDetails.getPackageDetails().size() > 0) {
                            pkWordRl.loadMoreComplete();
                            words.addAll(packageDetails.getPackageDetails());
                            wordAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        pkWordRl.loadMoreComplete();
                        page-- ;
                        toTast(WordbagDetailActivity.this ,throwable.getMessage());
                    }
                }));
    }


    @OnClick({R.id.back, R.id.to_recite})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finishWithAnim();
                break;
            case R.id.to_recite:
                recite_word();
                break;
        }
    }

    /**
     * 开始背单词
     */
    public void recite_word() {
        if (getIs == 1) {
            toTast("已添加该词包");
        } else {
            AddMyPlanActivity.start(this, catId, total , name);
        }
    }

}
