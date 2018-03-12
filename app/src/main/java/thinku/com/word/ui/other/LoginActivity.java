package thinku.com.word.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
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

    private static LoginActivity sInstance;
    public static LoginActivity getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        return null;
    }
    public static void finishNow(){
        sInstance.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        setClick();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
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
                SharedPreferencesUtils.setPassword(LoginActivity.this,phone,pass);
                LoginHelper.againLogin(LoginActivity.this,3);
                break;
            case R.id.register_btn:

                break;
            case R.id.forget_btn:

                break;
        }
    }
}
