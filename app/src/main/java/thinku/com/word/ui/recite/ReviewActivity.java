package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ReviewMainBeen;
import thinku.com.word.http.HttpUtil;

/**
 * 复习(选择复习方式)
 */

public class ReviewActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.error_rl)
    RelativeLayout errorRl;
    @BindView(R.id.time_rl)
    RelativeLayout timeRl;
    @BindView(R.id.listener_rl)
    RelativeLayout listenerRl;


    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Review();
    }

    private void initView() {
        titleT.setText("复习");
    }

    @OnClick({R.id.back ,R.id.error_rl ,R.id.time_rl ,R.id.listener_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.error_rl:
                ReviewErrorActivity.start(ReviewActivity.this ,num.getText().toString());
                break;
            case R.id.time_rl:
                ReviewTimeActivity.start(ReviewActivity.this );
                break;
            case R.id.listener_rl:
                ReviewExerciseActivity.start(ReviewActivity.this );
                break;
        }
    }

    /**
     * 复习
     */
    public void Review(){
        showLoadDialog();
        addToCompositeDis(HttpUtil.reviewMainObservable()
        .subscribe(new Consumer<ReviewMainBeen>() {
            @Override
            public void accept(@NonNull ReviewMainBeen reviewMainBeen) throws Exception {
                dismissLoadDialog();
                if (Integer.parseInt(reviewMainBeen.getCode()) == 1){
                    num.setText("共"+reviewMainBeen.getNum()+"词");
                }
            }
        }));
    }
}
