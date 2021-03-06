package thinku.com.word.ui.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.personalCenter.SetNickNameActivity;
import thinku.com.word.ui.personalCenter.feature.AuthCode;
import thinku.com.word.ui.share.ShareDateActivity;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private EditText num_et, code_et, pass_et;
    private TextView send, confirm;
    private boolean isSendCode;
    private Timer timer;
    private int recLen = 60;
    private AuthCode mAuthCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findView();
        setClick();
        mAuthCode = new AuthCode(60 * 1000, 1000, this, send, R.drawable.blue_round);
        getAuthCode();

    }

    private void setClick() {
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }


    /**
     * 获取验证码
     */
    private void getAuthCode() {
        addToCompositeDis(HttpUtil.sendCode()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {

                    }
                }));
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        num_et = (EditText) findViewById(R.id.num_et);
        code_et = (EditText) findViewById(R.id.code_et);
        pass_et = (EditText) findViewById(R.id.pass_et);
        send = (TextView) findViewById(R.id.send);
        confirm = (TextView) findViewById(R.id.confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                if (!isSendCode) {
                    if (TextUtils.isEmpty(num_et.getText())) {
                        Toast.makeText(ForgetPassActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String num = num_et.getText().toString();
                    if (PhoneAndEmailUtils.isMobileNO(num_et.getText().toString())) {
                        sendCode(num, 0);
                    } else if (PhoneAndEmailUtils.isEmail(num_et.getText().toString())) {
                        sendCode(num, 1);
                    } else {
                        Toast.makeText(ForgetPassActivity.this, "请输入有效的手机号/邮箱", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(num_et.getText())) {
                    Toast.makeText(ForgetPassActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code_et.getText())) {
                    Toast.makeText(ForgetPassActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass_et.getText())) {
                    Toast.makeText(ForgetPassActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                int type = 1;
                final String num = num_et.getText().toString();
                final String pass = pass_et.getText().toString();
                String code = code_et.getText().toString();
                if (PhoneAndEmailUtils.isMobileNO(num)) {
                    type = 1;
                } else if (PhoneAndEmailUtils.isEmail(num)) {
                    type = 2;
                } else {
                    Toast.makeText(ForgetPassActivity.this, "请填写有效的手机号/邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadDialog();

                addToCompositeDis(HttpUtil.findPass(type + "", num, pass, code)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                showLoadDialog();
                            }
                        }).subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> userInfo) throws Exception {
                                dismissLoadDialog();
                                if (userInfo.getCode() == 1) {
                                    LoginHelper.againLoginRetrofit(ForgetPassActivity.this, num, pass, new RequestCallback<UserInfo>() {

                                        @Override
                                        public void beforeRequest() {
                                            dismissLoadDialog();
                                        }

                                        @Override
                                        public void requestFail(String msg) {
                                            dismissLoadDialog();
                                            toTast(ForgetPassActivity.this, msg);
                                        }

                                        @Override
                                        public void requestSuccess(UserInfo userInfo) {
                                            dismissLoadDialog();
                                            Toast.makeText(ForgetPassActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            if (getHttpResSuc(userInfo.getCode())) {
                                                if (TextUtils.isEmpty(userInfo.getNickname())) {
                                                    SharedPreferencesUtils.setLogin(ForgetPassActivity.this, userInfo);
                                                    SetNickNameActivity.start(ForgetPassActivity.this, userInfo);
                                                } else {
                                                    SharedPreferencesUtils.setPassword(ForgetPassActivity.this, TextUtils.isEmpty(userInfo.getPhone()) ? userInfo.getEmail() : userInfo.getPhone(), userInfo.getPassword());
                                                    SharedPreferencesUtils.setLogin(ForgetPassActivity.this, userInfo);
                                                    MainActivity.toMain(ForgetPassActivity.this);
                                                    startActivity(new Intent(ForgetPassActivity.this, MainActivity.class));
                                                }

                                            } else {
                                                toTast(userInfo.getMessage());
                                            }
                                        }

                                        @Override
                                        public void otherDeal(UserInfo userInfo) {
                                            dismissLoadDialog();
                                        }
                                    });

                                } else {
                                    dismissLoadDialog();
                                    Toast.makeText(ForgetPassActivity.this, userInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                dismissLoadDialog();
                                toTast(ForgetPassActivity.this, throwable.getMessage());
                            }
                        }));
                break;
        }
    }

    /**
     * 发送
     *
     * @param num
     * @param i
     */
    private void sendCode(final String num, final int i) {
        mAuthCode.start();
        if (i == 0) {
            addToCompositeDis(HttpUtil.phoneCodeObservable(num, 2 + "")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            if (voidResultBeen.getCode() == 1) {
                                toTast(ForgetPassActivity.this, voidResultBeen.getMessage());
                            } else {
                                mAuthCode.sendAgain();
                                toTast(ForgetPassActivity.this, voidResultBeen.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            toTast(ForgetPassActivity.this, HttpUtils.onError(throwable));
                            mAuthCode.sendAgain();
                        }
                    })
            );
        } else if (i == 1) {

            addToCompositeDis(HttpUtil.emailCodeObservable(num, 2 + "")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            if (voidResultBeen.getCode() == 1) {
                                toTast(ForgetPassActivity.this, voidResultBeen.getMessage());
                            } else {
                                mAuthCode.sendAgain();
                                toTast(ForgetPassActivity.this, voidResultBeen.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            toTast(ForgetPassActivity.this, HttpUtils.onError(throwable));
                            mAuthCode.sendAgain();
                        }
                    })
            );

        }
    }

}
