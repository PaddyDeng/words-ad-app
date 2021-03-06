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

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportActivity;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.LoginInfo;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.ui.personalCenter.SetNickNameActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/5.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private EditText num_et,pass_et;
    private TextView login,register_btn,forget_btn;
    private String phone;
    private String pass;
    private Observable<Boolean> observable ;
    public static void toLogin(Context context){
        Intent intent = new Intent(context ,LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        setClick();
       observable =  RxBus.get().register(C.RXBUS_LOGIN_BACKMAIN ,Boolean.class);
    }

    private void setClick() {
        back.setOnClickListener(this);
        login.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forget_btn.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        num_et = (EditText) findViewById(R.id.num_et);
        pass_et = (EditText) findViewById(R.id.pass_et);
        login = (TextView) findViewById(R.id.login);
        register_btn = (TextView) findViewById(R.id.register_btn);
        forget_btn = (TextView) findViewById(R.id.forget_btn);
        if (SharedPreferencesUtils.getLoginInfo(this) != null){
            LoginInfo userInfo = SharedPreferencesUtils.getLoginInfo(this);
                num_et.setText(userInfo.getNum());
                pass_et.setText(userInfo.getPass());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                MainActivity.toMain(this);
                break;
            case R.id.login:
                if(TextUtils.isEmpty(num_et.getText())){
                    Toast.makeText(LoginActivity.this,"请填写账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass_et.getText())){
                    Toast.makeText(LoginActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                phone = num_et.getText().toString();
                if(!PhoneAndEmailUtils.isMobileNO(phone)&&!PhoneAndEmailUtils.isEmail(phone)){
                    Toast.makeText(LoginActivity.this,"请填写正确的手机号码或邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }
                pass = pass_et.getText().toString();
                LoginInfo loginInfo = new LoginInfo(phone ,pass);
                SharedPreferencesUtils.setLoginInfo(LoginActivity.this ,loginInfo);
                LoginHelper.againLoginRetrofit(LoginActivity.this, phone, pass, new RequestCallback<UserInfo>() {
                    @Override
                    public void beforeRequest() {
                        showLoadDialog();
                    }

                    @Override
                    public void requestFail(String msg) {
                        dismissLoadDialog();
                        toTast(LoginActivity.this ,msg);
                    }
                    @Override
                    public void requestSuccess(UserInfo userInfo) {
                        dismissLoadDialog();
                        if (getHttpResSuc(userInfo.getCode())) {
                            if (TextUtils.isEmpty(userInfo.getNickname())){
                                SharedPreferencesUtils.setLogin(LoginActivity.this, userInfo);
                                SetNickNameActivity.start(LoginActivity.this , userInfo);
                                MyApplication.isLogin = true;
                            }else {
                                MyApplication.isLogin = true;
                                RxBus.get().post(C.RXBUS_LOGIN, true);
                                SharedPreferencesUtils.setPassword(LoginActivity.this, TextUtils.isEmpty(userInfo.getPhone()) ? userInfo.getEmail() : userInfo.getPhone(), userInfo.getPassword());
                                SharedPreferencesUtils.setLogin(LoginActivity.this, userInfo);
                                RxHelper.delay(500)
                                        .subscribe(new Consumer<Integer>() {
                                            @Override
                                            public void accept(@NonNull Integer integer) throws Exception {
                                                MainActivity.toMain(LoginActivity.this);
                                            }
                                        });
                                LoginActivity.this.finish();
                            }

                        }else{
                            toTast(userInfo.getMessage());
                        }
                    }


                    @Override
                    public void otherDeal(UserInfo userInfoResultBeen) {
                            dismissLoadDialog();
                    }
                });
                break;
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this ,RigisterActivity.class));
                break;
            case R.id.forget_btn:
                startActivity(new Intent(LoginActivity.this ,ForgetPassActivity.class));
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_LOGIN_BACKMAIN  , observable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
