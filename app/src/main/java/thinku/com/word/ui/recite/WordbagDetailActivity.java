package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.WordAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.PackageDetails;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

import static thinku.com.word.http.NetworkTitle.WORD;

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
    RecyclerView wordList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private String catId;
    private int page = 1;
    private String total ;
    private String userwords;
    private WordAdapter wordAdapter ;
    private String name ;
    private List<PackageDetails.Word> words = new ArrayList<>();

    public static void start(Context context, String catId,String total ,String userwords , String name) {
        Intent intent = new Intent(context, WordbagDetailActivity.class);
        intent.putExtra("catId", catId);
        intent.putExtra("total" ,total);
        intent.putExtra("userwords" ,userwords);
        intent.putExtra("name" ,name);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        try {
            catId = intent.getStringExtra("catId");
            total = intent.getStringExtra("total");
            userwords = intent.getStringExtra("userwords");
            name  = intent.getStringExtra("name");
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_wordbag_detail);
        ButterKnife.bind(this);
        init();
        initData();
        initRefer();
        titleT.setText(name);
    }

    public void init(){
        havaNum.setText(userwords);
        totalNum.setText(total);
        wordList.setLayoutManager(new LinearLayoutManager(this));

    }
    public void initRefer(){
        refresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++ ;
                initData();
            }
        });
    }

    public void initData() {
        addToCompositeDis(HttpUtil.packageDetailsObservable(catId+"" ,page+"")
        .subscribe(new Consumer<PackageDetails>() {
            @Override
            public void accept(@NonNull PackageDetails packageDetails) throws Exception {
                refresh.setRefreshing(false);
                words.addAll(packageDetails.getPackageDetails());
                wordAdapter = new WordAdapter(WordbagDetailActivity.this ,words);
                wordList.setAdapter(wordAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                refresh.setRefreshing(false);
            }
        }));
    }



    @OnClick({R.id.back ,R.id.to_recite})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_recite:
                recite_word();
                break;
        }
    }

    /**
     * 开始背单词
     */
    public void recite_word(){
        addToCompositeDis(HttpUtil.addPackageObservable(catId+"")
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                showLoadDialog();
            }
        })
        .subscribe(new Consumer<ResultBeen<Void>>() {
            @Override
            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                dismissLoadDialog();
                if (getHttpResSuc(voidResultBeen.getCode())){
                    toTast(WordbagDetailActivity.this ,voidResultBeen.getMessage());
                    Intent intent = new Intent(WordbagDetailActivity.this ,MyPlanActivity.class);
                    startActivity(intent);
                }else{
                    toTast(WordbagDetailActivity.this ,voidResultBeen.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissLoadDialog();
            }
        }));
    }

}
