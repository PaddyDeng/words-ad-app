package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.BackCode;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.recite.MyPlanActivity;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

import static thinku.com.word.http.NetworkChildren.CHOOSE_STUDY_MODE;
import static thinku.com.word.http.NetworkTitle.WORD;

/**
 * 设置模式
 */

public class TypeSettingActivity extends BaseActivity {
    private static final String TAG = TypeSettingActivity.class.getSimpleName();
    private static final int LGStudyEbbinghaus = 1;  // 艾宾浩斯记忆法
    private static final int LGStudyReview = 2;    //  复习记忆法
    private static final int LGStudyOnlyNew = 3;   // 只背新单词
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
    private int oldPage = -1;
    private static int status = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_setting);
        unbinder = ButterKnife.bind(this);
    }



    @OnClick({R.id.back,R.id.title_right ,R.id.ebbinghaus ,R.id.review , R.id.only_new})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_right:
                choseStudyMode();
                break;
            case R.id.ebbinghaus:
                setSelect(0);
                break;
            case R.id.review:
                setSelect(1);
                break;
            case R.id.only_new:
                setSelect(2);
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
        addToCompositeDis(HttpUtil.choseStudyMode(status+"")
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                showLoadDialog();
            }
        })
        .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissLoadDialog();
            }
        })
        .subscribe(new Consumer<BackCode>() {
            @Override
            public void accept(@NonNull BackCode backCode) throws Exception {
                dismissLoadDialog();
                if (backCode != null) {
                    if (backCode.getCode() == 99) {  //  未登录
                        LoginHelper.needLogin(TypeSettingActivity.this, "你还没登录，请先登录");
                    } else if (backCode.getCode() == 0) {
                        toTast(TypeSettingActivity.this, backCode.getMessage());
                    } else {
                        toTast(TypeSettingActivity.this, backCode.getMessage());
                        SharedPreferencesUtils.saveMemoryMode(TypeSettingActivity.this, status);
                        finish();
                    }
                }else{
                    LoginHelper.needLogin(TypeSettingActivity.this, "你还没登录，请先登录");
                }
            }
        }));
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static void start(Context context){
        Intent intent =new Intent(context,TypeSettingActivity.class);
        context.startActivity(intent);
    }

}
