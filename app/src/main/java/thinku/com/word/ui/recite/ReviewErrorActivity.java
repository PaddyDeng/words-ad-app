package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import thinku.com.word.utils.PopHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 错题本
 */

public class ReviewErrorActivity extends BaseActivity {

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
    private PopHelper popHelper ;

    public static void start(Context context, String count) {
        Intent intent = new Intent(context, ReviewErrorActivity.class);
        intent.putExtra("count", count);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_error);
        ButterKnife.bind(this);
        try {
            count = getIntent().getStringExtra("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        findView();
        initPopWindow();
        wrongIndex();
    }

    private void initView(final List<WrongIndexBeen> wrongIndexBeens) {
        if (wrongIndexBeens != null & wrongIndexBeens.size() > 0) {
            ReviewErrorAdapter adapter = new ReviewErrorAdapter(ReviewErrorActivity.this, wrongIndexBeens, new SelectListener() {
                @Override
                public void setListener(int position) {
                    getWordDetails(wrongIndexBeens.get(position).getStart());
                }
            });
            listView.setAdapter(adapter);
        }
    }

    private void findView() {
        titleT.setText("错题本");
        GridLayoutManager manager = new GridLayoutManager(ReviewErrorActivity.this, 3);
        listView.setLayoutManager(manager);
        titleIv.setBackgroundResource(R.mipmap.wrong_chose);
        totalNum.setText(count);
        String choseMode = SharedPreferencesUtils.getChoseMode(ReviewErrorActivity.this);
        if (!TextUtils.isEmpty(choseMode)) choseTxt.setText(choseMode);
    }

    @OnClick({R.id.back, R.id.title_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_iv:
                showPopWindow();
                break;
        }
    }


    public void initPopWindow() {
      final  ArrayList<String> names = new ArrayList<>();
        names.add("中英");
        names.add("英中");
        names.add("听写");
        popHelper = PopHelper.create(ReviewErrorActivity.this);
        popHelper.setData(names);
        popHelper.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                choseTxt.setText(names.get(position));
                SharedPreferencesUtils.setChoseMode(ReviewErrorActivity.this,names.get(position));
                popHelper.dismiss();
            }
        });
        popHelper.initRecyclerView();
    }

    public void showPopWindow() {
        popHelper.show(titleIv);
    }


    /**
     * 错题
     */
    public void wrongIndex() {

        addToCompositeDis(HttpUtil.wrongIndexObservable()
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
        addToCompositeDis(HttpUtil.wrongIndexObservable(start+"")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
        .subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> strings) throws Exception {
                dismissLoadDialog();
                if (choseTxt.getText().toString().trim().equals("听写")){
                    DictionDetailActivity.start(ReviewErrorActivity.this , (ArrayList<String>) strings);
                }else {
                    thinku.com.word.ui.report.WordEvaluateFragment.start(ReviewErrorActivity.this, (ArrayList<String>) strings);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissLoadDialog();
                toTast(ReviewErrorActivity.this ,throwable.getMessage());
            }
        }));
    }
}
