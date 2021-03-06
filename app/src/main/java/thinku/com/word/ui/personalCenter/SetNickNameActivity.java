package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.ui.share.ShareDateActivity;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

import static thinku.com.word.utils.HttpUtils.getHttpMsgSu;

public class SetNickNameActivity extends BaseActivity {

    @BindView(R.id.act_set_nick_name_tv)
    EditText setNickTv;
    @BindView(R.id.act_set_nick_confirm_btn)
    TextView confirmBtn;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    private boolean hasNickName;
    private UserInfo userInfo ;

    public static void start(Context context , UserInfo userInfo){
        Intent intent = new Intent(context ,SetNickNameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user" ,userInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void start(Context context ){
        Intent intent = new Intent(context ,SetNickNameActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        if (intent != null){
            try {
                Bundle bundle = intent.getExtras();
                userInfo = bundle.getParcelable("user");
            }catch (Exception e){

            }
        }
    }

    protected void initView() {
        titleT.setText("起一个闪亮亮的昵称吧");
        String nickname = SharedPreferencesUtils.getNickName(this);
        if (!TextUtils.isEmpty(nickname)) {
            hasNickName = true;
        }
        setNickTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enable(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void enable(String s) {
        if (s.length() >= 2 && s.length() <= 8) {
            confirmBtn.setClickable(true);
            confirmBtn.setTextColor(getResources().getColor(R.color.white));
            confirmBtn.setBackgroundResource(R.drawable.main_10round_tv);
        } else {
            confirmBtn.setClickable(false);
            confirmBtn.setTextColor(getResources().getColor(R.color.gray_text));
            confirmBtn.setBackgroundColor(getResources().getColor(R.color.color_gray_bg));
        }
    }

    @OnClick({R.id.act_set_nick_confirm_btn , R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_set_nick_confirm_btn:
                setNickName();
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
            default:
                break;
        }
    }


    private boolean checkName(final EditText editText) {
        String name = editText.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            toTast("请填写昵称");
            return false;
        } else if (name.equals(SharedPreferencesUtils.getNickName(this))) {
            toTast("与当前昵称一致，无须修改");
            return false;
        }
        return true;
    }

    private void setNickName() {
        if (!checkName(setNickTv))
            return;
        final String nickName = setNickTv.getText().toString().trim();
        Log.i("昵称1", nickName);
        addToCompositeDis(HttpUtil.newModifyName(nickName)
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
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> bean) throws Exception {
                        setResult(RESULT_OK);
                        dismissLoadDialog();
                        toTast(bean.getMessage());
                        if (getHttpMsgSu(bean.getCode())) {
                            saveUserInf(nickName);
                            Log.e(TAG, "accept: " );
                            resetSession();
                            hasNickName = true;
                            EventBus.getDefault().post(nickName);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(SetNickNameActivity.this, HttpUtils.onError(throwable));
                    }
                }));
    }

    private void saveUserInf(String name) {
        if (!TextUtils.isEmpty(name)) {
            Log.i("昵称2", name);
            SharedPreferencesUtils.setNickName(this, name);
        }
    }

    /**
     * 修改账号需要重新获取Session
     */
    private void resetSession() {
        UserInfo userInfo1 ;
        if (userInfo != null){
            Log.e(TAG, "onCreate: + userInfo "   );
            userInfo1 = userInfo;
        }else{
            userInfo1 =SharedPreferencesUtils.getUserInfo(this);
        }
        Log.e("Ok", "resetSession: " + userInfo1.getUid());
        LoginHelper.setSession(this, userInfo1, new ICallBack() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                finishWithAnim();
                MainActivity.toMain(SetNickNameActivity.this);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
