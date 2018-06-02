package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.ReviewErrorAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.WrongIndexBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;

/**
 * 听写练习分类
 */

public class ReviewExerciseTypeActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.total_num)
    TextView totalNum;
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.chose_txt)
    TextView choseTxt;

    private String count;
    private int status ;
    private String name ;


    public static void start(Context context , int status , String name , String count ) {
        Intent intent = new Intent(context, ReviewExerciseTypeActivity.class);
        intent.putExtra("status" ,status);
        intent.putExtra("name" , name );
        intent.putExtra("count" ,count);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_error);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null){
            status = intent.getIntExtra("status" ,0);
            name = intent.getStringExtra("name");
            count = intent.getStringExtra("count");
        }
        findView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        wrongIndex();
    }

    private void initView(final List<WrongIndexBeen> wrongIndexBeens) {
        if (wrongIndexBeens != null & wrongIndexBeens.size() > 0) {
            ReviewErrorAdapter adapter = new ReviewErrorAdapter(ReviewExerciseTypeActivity.this, wrongIndexBeens, new SelectListener() {
                @Override
                public void setListener(int position) {
                    getWordDetails(wrongIndexBeens.get(position).getStart());
                }
            });
            listView.setAdapter(adapter);
        }
    }

    private void findView() {
        titleT.setText(name);
        GridLayoutManager manager = new GridLayoutManager(ReviewExerciseTypeActivity.this, 3);
        listView.setLayoutManager(manager);
        titleIv.setVisibility(View.GONE);
        totalNum.setText(count);
    }

    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 错题
     */
    public void wrongIndex() {

        addToCompositeDis(HttpUtil.dictionGroupObservable(status+"")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
        .subscribe(new Consumer<List<WrongIndexBeen>>() {
            @Override
            public void accept(@NonNull List<WrongIndexBeen> wrongIndexBeens) throws Exception {
                dismissLoadDialog();
                if (wrongIndexBeens != null && wrongIndexBeens.size() > 0)
                    initView(wrongIndexBeens);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissLoadDialog();
            }
        }));
    }

    /**
     * 获取单词详情
     */
    public void getWordDetails(int start){

        addToCompositeDis(HttpUtil.dictationWordsObservable(status +"" , start +"")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        dismissLoadDialog();
                        if (strings.size() > 0) {
                            DictionDetailActivity.start(ReviewExerciseTypeActivity.this, (ArrayList<String>) strings);
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
