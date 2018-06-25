package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.RigisterActivity;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.Utils;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.phone)
    EditText phone;

    public static void start(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleT.setText("意见反馈");
        titleRightT.setText("提交");
        titleRightT.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_right_t ,R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_right_t:
                submitFeed();
                break;
            case R.id.back:
                FeedBackActivity.this.finishWithAnim();
                break;
        }
    }

    public void submitFeed() {
        String strContent = Utils.getEditTextString(content);
        if (!TextUtils.isEmpty(strContent)) {
            addToCompositeDis(HttpUtil.feedBackObservable(strContent)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            dismissLoadDialog();
                            if (voidResultBeen.getCode() == 99) {
                                LoginHelper.needLogin(FeedBackActivity.this, "你还没登录，请先登录");
                            } else {
                                toTast(FeedBackActivity.this, voidResultBeen.getMessage());
                                FeedBackActivity.this.finishWithAnim();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            toTast(FeedBackActivity.this, HttpUtils.onError(throwable));
                        }
                    }));
        } else {
            toTast(this, "反馈内容不能为空");
        }
    }

}
