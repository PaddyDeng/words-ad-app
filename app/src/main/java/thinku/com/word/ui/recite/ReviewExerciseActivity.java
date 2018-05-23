package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
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
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.Dictation;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.C;

/**
 * Created by Administrator on 2018/2/23.
 * 听写练习界面
 */

public class ReviewExerciseActivity extends BaseActivity {
    private static final String TAG = ReviewExerciseActivity.class.getSimpleName();
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.text_all)
    TextView textAll;
    @BindView(R.id.blurry_rl)
    RelativeLayout blurryRl;
    @BindView(R.id.text_all_not_know)
    TextView textAllNotKnow;
    @BindView(R.id.not_know_rl)
    RelativeLayout notKnowRl;
    @BindView(R.id.text_all_all)
    TextView textAllAll;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;

    private Dictation dictation ;
    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewExerciseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_practice);
        ButterKnife.bind(this);
        findView();
        initData();
    }


    private void findView() {
        titleT.setText("听写练习");
    }

    public void initData() {

        addToCompositeDis(HttpUtil.dictationObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<Dictation>() {
                    @Override
                    public void accept(@NonNull Dictation dictation) throws Exception {
                        if (dictation != null) {
                            referUi(dictation);
                        }
                    }
                }));
    }

    public void referUi(Dictation dictation) {
        this .dictation = dictation ;
        textAll.setText("共（" + dictation.getDim() + "）词");
        textAllNotKnow.setText("共（" + dictation.getIncognizant() + "）词");
        textAllAll.setText("共（" + dictation.getAll() + "）词");
    }

    @OnClick({R.id.back, R.id.all_rl, R.id.not_know_rl, R.id.blurry_rl})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.all_rl:
                getWordId(C.LGWordStatusNone , "全部词组" ,"共（" + dictation.getAll() + "）词");
                break;
            case R.id.not_know_rl:
                getWordId(C.LGWordStatusIncoginzance ,"不认识词组" ,"共（" + dictation.getIncognizant() + "）词");
                break;
            case R.id.blurry_rl:   //  模糊
                getWordId(C.LGWordStatusVague , "模糊词组"  ,"共（" + dictation.getDim() + "）词");
                break;
        }
    }

    public void getWordId(final int status ,final String name , String count) {
        ReviewExerciseTypeActivity.start(ReviewExerciseActivity.this  ,status ,name , count );
    }

}
