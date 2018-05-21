package thinku.com.word.ui.personalCenter;

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
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.BackCode;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.ui.other.dialog.callback.DialogClickListener;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 设置模式
 */

public class TypeSettingActivity extends BaseActivity  implements DialogClickListener{
    private static final String TAG = TypeSettingActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 10 ;
    public static final int LGStudyEbbinghaus = 1;  // 艾宾浩斯记忆法
    public static final int LGStudyReview = 2;    //  复习记忆法
    public static final int LGStudyOnlyNew = 3;   // 只背新单词
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.ebbinghaus)
    TextView ebbinghaus;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.only_new)
    TextView onlyNew;
    Unbinder unbinder;
    @BindView(R.id.title_1)
    View title1;
    @BindView(R.id.title)
    RelativeLayout title;
    private int oldPage = -1;
    private static int status = 1;
    boolean isFirst = false;
    private String mode;   //  模式文字

    public static void start(Context context, boolean isFirst) {
        Intent intent = new Intent(context, TypeSettingActivity.class);
        intent.putExtra("first", isFirst);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_setting);
        unbinder = ButterKnife.bind(this);
        if (getIntent() != null) {
            isFirst = getIntent().getBooleanExtra("first", false);
        }
        if (isFirst) {
            title.setVisibility(View.GONE);
        }else{
            title1.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            title1.setBackgroundColor(getResources().getColor(R.color.mainColor));
        }
        initMode();
    }

    public void initMode(){
        String mode = SharedPreferencesUtils.getChoseMode(this);
        try{
            int userMode = Integer.parseInt(mode);
            switch (userMode){
                case LGStudyEbbinghaus:
                    ebbinghaus.setSelected(true);
                    review.setSelected(false);
                    onlyNew.setSelected(false);
                    break;
                case LGStudyReview:
                    ebbinghaus.setSelected(false);
                    review.setSelected(true);
                    onlyNew.setSelected(false);
                    break;
                case LGStudyOnlyNew:
                    ebbinghaus.setSelected(false);
                    review.setSelected(false);
                    onlyNew.setSelected(true);
                    break;
            }
        }catch (Exception e){

        }
    }


    @OnClick({R.id.back, R.id.title_right, R.id.ebbinghaus, R.id.review, R.id.only_new})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_right:
                if (!isFirst) {
                    choseStudyMode();
                } else {
                    SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, mode);
                    MainActivity.toMain(this);
                    this.finishWithAnim();
                }
                break;
            case R.id.ebbinghaus:
                if (isFirst) {
                    setSelect(0);
                    mode = ebbinghaus.getText().toString().trim();
                    SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, mode);
                    this.finishWithAnim();
                    MainActivity.toMain(this);
                }else{
                    setSelect(0);
                    mode = ebbinghaus.getText().toString().trim();
                }
                break;
            case R.id.review:
                if (isFirst) {
                    setSelect(1);
                    mode = review.getText().toString().trim();
                    SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, mode);
                    this.finishWithAnim();
                    MainActivity.toMain(this);
                }else{
                    setSelect(1);
                    mode = review.getText().toString().trim();
                }

                break;
            case R.id.only_new:
                if (isFirst) {
                    setSelect(2);
                    mode = onlyNew.getText().toString().trim();
                    SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, mode);
                    this.finishWithAnim();
                    MainActivity.toMain(this);
                }else{
                    setSelect(2);
                    mode = onlyNew.getText().toString().trim();
                }
                break;
        }
    }


    private void setSelect(int i) {
        if (oldPage != i) {
            setSelect(oldPage, false);
            setSelect(i, true);
        }
    }

    private void setSelect(int i, boolean b) {
        if (b) oldPage = i;
        switch (i) {
            case 0:
                ebbinghaus.setSelected(b);
                status = LGStudyEbbinghaus;
                if (b) ebbinghaus.setTextColor(getResources().getColor(R.color.white));
                else ebbinghaus.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case 1:
                review.setSelected(b);
                status = LGStudyReview;
                if (b) review.setTextColor(getResources().getColor(R.color.white));
                else review.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case 2:
                onlyNew.setSelected(b);
                status = LGStudyOnlyNew;
                if (b) onlyNew.setTextColor(getResources().getColor(R.color.white));
                else onlyNew.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
        }
    }

    /**
     * 修改学习模式
     */
    public void choseStudyMode() {
        addToCompositeDis(HttpUtil.choseStudyMode(status + "")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<BackCode>() {
                    @Override
                    public void accept(@NonNull BackCode backCode) throws Exception {
                        dismissLoadDialog();
                        if (backCode != null) {
                            if (backCode.getCode() == 99) {  //  未登录
                                LoginHelper.needLogin(TypeSettingActivity.this, "你还没登录，请先登录", REQUEST_CODE);
                            } else if (backCode.getCode() == 0) {
                                toTast(TypeSettingActivity.this, backCode.getMessage());
                            } else {
                                toTast(TypeSettingActivity.this, backCode.getMessage());
                                SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, mode);
                                finish();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TypeSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void clickTrue() {
        MainActivity.toMain(this);
    }

    @Override
    public void clickFalse() {
        MainActivity.toMain(this);
    }
}
