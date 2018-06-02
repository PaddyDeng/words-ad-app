package thinku.com.word.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.LoginInfo;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.personalCenter.SetNickNameActivity;
import thinku.com.word.ui.personalCenter.feature.AuthCode;
import thinku.com.word.utils.C;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
import thinku.com.word.utils.RegexValidateUtil;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RigisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back, agree;
    private EditText num_et, code_et, pass_et;
    private TextView send, rigister, protocol, to_login;
    private boolean isAgree = true;
    private boolean isSendCode;
    private Timer timer;
    private int recLen = 60;
    private AuthCode mAuthCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);
        findView();
        setClick();
        getAuthCode();
        mAuthCode = new AuthCode(60 * 1000, 1000, this, send, R.drawable.blue_round);
    }


    private void setClick() {
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        rigister.setOnClickListener(this);
        agree.setOnClickListener(this);
        protocol.setOnClickListener(this);
        to_login.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        num_et = (EditText) findViewById(R.id.num_et);
        code_et = (EditText) findViewById(R.id.code_et);
        send = (TextView) findViewById(R.id.send);
        pass_et = (EditText) findViewById(R.id.pass_et);
        rigister = (TextView) findViewById(R.id.rigister);
        agree = (ImageView) findViewById(R.id.agree);
        protocol = (TextView) findViewById(R.id.protocol);
        to_login = (TextView) findViewById(R.id.to_login);
        agree.setSelected(true);
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
                        Toast.makeText(RigisterActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String num = num_et.getText().toString();
                    if (PhoneAndEmailUtils.isMobileNO(num_et.getText().toString())) {
                        sendCode(num, 0);
                    } else if (PhoneAndEmailUtils.isEmail(num_et.getText().toString())) {
                        sendCode(num, 1);
                    } else {
                        Toast.makeText(RigisterActivity.this, "请输入有效的手机号/邮箱", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                break;
            case R.id.rigister:
                if (!isAgree) {
                    Toast.makeText(RigisterActivity.this, "请阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(num_et.getText())) {
                    Toast.makeText(RigisterActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code_et.getText())) {
                    Toast.makeText(RigisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass_et.getText())) {
                    Toast.makeText(RigisterActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RigisterActivity.this, "请填写有效的手机号/邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginInfo loginInfo = new LoginInfo(num ,pass);
                SharedPreferencesUtils.setLoginInfo(RigisterActivity.this ,loginInfo);
                showLoadDialog();
                //参数需要对一下
                addToCompositeDis(HttpUtil.register(type + "", num, pass, code, num, "5")
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                showLoadDialog();
                            }
                        })
                        .subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                                if (getHttpResSuc(voidResultBeen.getCode())) {
                                    Toast.makeText(RigisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    LoginHelper.againLoginRetrofit(RigisterActivity.this, num, pass, new RequestCallback<UserInfo>() {
                                        @Override
                                        public void beforeRequest() {

                                        }

                                        @Override
                                        public void requestFail(String msg) {
                                            dismissLoadDialog();
                                            toTast(RigisterActivity.this, msg);
                                        }

                                        @Override
                                        public void requestSuccess(final UserInfo userInfo) {
                                            dismissLoadDialog();
                                            if (getHttpResSuc(userInfo.getCode())) {
                                                if (TextUtils.isEmpty(userInfo.getNickname())) {
                                                    SharedPreferencesUtils.setLogin(RigisterActivity.this, userInfo);
                                                    RxHelper.delay(1000)
                                                            .subscribe(new Consumer<Integer>() {
                                                                @Override
                                                                public void accept(@NonNull Integer integer) throws Exception {
                                                                    SetNickNameActivity.start(RigisterActivity.this, userInfo);
                                                                }
                                                            });
                                                } else {
                                                    MyApplication.isLogin = true;
                                                    RxBus.get().post(C.RXBUS_LOGIN, true);
                                                    SharedPreferencesUtils.setPassword(RigisterActivity.this, TextUtils.isEmpty(userInfo.getPhone()) ? userInfo.getEmail() : userInfo.getPhone(), userInfo.getPassword());
                                                    SharedPreferencesUtils.setLogin(RigisterActivity.this, userInfo);
                                                    RxHelper.delay(500)
                                                            .subscribe(new Consumer<Integer>() {
                                                                @Override
                                                                public void accept(@NonNull Integer integer) throws Exception {
                                                                    MainActivity.toMain(RigisterActivity.this);
                                                                }
                                                            });
                                                    RigisterActivity.this.finish();
                                                }
                                            } else {
                                                toTast(userInfo.getMessage());
                                            }
                                        }

                                        @Override
                                        public void otherDeal(UserInfo userInfo) {

                                        }
                                    });
                                } else {
                                    dismissLoadDialog();
                                    toTast(RigisterActivity.this, voidResultBeen.getMessage());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                dismissLoadDialog();
                                toTast(RigisterActivity.this, "网络出错");
                            }
                        }));
                break;
            case R.id.agree:
                isAgree = !isAgree;
                agree.setSelected(isAgree);
                break;
            case R.id.protocol:
                startActivity(new Intent(RigisterActivity.this, UserActivity.class));
                break;
            case R.id.to_login:
                RigisterActivity.this.finishWithAnim();
                break;
        }
    }


//    public  void updataMode(final String status , final Context context){
//        addToCompositeDis(HttpUtil.choseStudyMode(status)
//                .subscribe(new Consumer<BackCode>() {
//                    @Override
//                    public void accept(@NonNull BackCode backCode) throws Exception {
//                        SharedPreferencesUtils.setStudyMode(context ,status);
//                    }
//                }));
//    }
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



    private boolean check(final String content, final boolean modifyEmail) {
        if (modifyEmail) {
            if (checkEmail(content)) return false;
        } else {
            if (checkPhone(content)) return false;
        }
        return true;
    }

    private boolean checkEmail(String acc) {
        if (TextUtils.isEmpty(acc)) {
            toTast(this, "请填写邮箱");
            return true;
        } else if (!RegexValidateUtil.checkEmail(acc)) {
            toTast(this, "邮箱格式错误");
            return true;
        } else if (acc.equals(SharedPreferencesUtils.getUserInfo(this).getEmail())) {
            toTast(this, "与当前邮箱一致，无须修改");
            return true;
        }
        return false;
    }

    private boolean checkPhone(String acc) {
        if (TextUtils.isEmpty(acc)) {
            toTast(this, "请填写手机号");
            return true;
        } else if (!RegexValidateUtil.checkPhoneNumber(acc)) {
            toTast(this, "手机号格式错误");
            return true;
        } else if (acc.equals(SharedPreferencesUtils.getUserInfo(this).getPhone())) {
            toTast(this, "与当前手机号一致，无须修改");
            return true;
        }
        return false;
    }

    /**
     * 发送
     *
     * @param num
     * @param i
     */
    private void sendCode(final String num, final int i) {
        mAuthCode.start();
        if (i ==0){
            addToCompositeDis(HttpUtil.phoneCodeObservable(num , 1+"")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            if (voidResultBeen.getCode() == 1) {
                                toTast(RigisterActivity.this, voidResultBeen.getMessage());
                            } else {
                                mAuthCode.sendAgain();
                                toTast(RigisterActivity.this, voidResultBeen.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            mAuthCode.sendAgain();
                        }
                    })
            );
        }else if (i ==1){

            addToCompositeDis(HttpUtil.emailCodeObservable(num , 1+"")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            if (voidResultBeen.getCode() == 1) {

                                toTast(RigisterActivity.this, voidResultBeen.getMessage());
                            } else {
                                mAuthCode.sendAgain();
                                toTast(RigisterActivity.this, voidResultBeen.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            mAuthCode.sendAgain();
                        }
                    })
            );

        }
    }

}
