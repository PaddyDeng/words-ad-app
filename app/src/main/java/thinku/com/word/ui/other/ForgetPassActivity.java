package thinku.com.word.ui.other;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private EditText num_et,code_et,pass_et;
    private TextView send,confirm;
    private boolean isSendCode;
    private Timer timer;
    private int recLen = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        confirm.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.send:
                if(!isSendCode) {
                    if (TextUtils.isEmpty(num_et.getText())) {
                        Toast.makeText(ForgetPassActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String num=num_et.getText().toString();
                    if(PhoneAndEmailUtils.isMobileNO(num_et.getText().toString())){
                        sendCode(num,0);
                    }else if(PhoneAndEmailUtils.isEmail(num_et.getText().toString())){
                        sendCode(num,1);
                    }else{
                        Toast.makeText(ForgetPassActivity.this,"请输入有效的手机号/邮箱",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.confirm:
                if(TextUtils.isEmpty(num_et.getText())){
                    Toast.makeText(ForgetPassActivity.this,"请填写账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(code_et.getText())){
                    Toast.makeText(ForgetPassActivity.this,"请填写验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass_et.getText())){
                    Toast.makeText(ForgetPassActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                int type =1;
                final String num =num_et.getText().toString();
                final String pass =pass_et.getText().toString();
                String code =code_et.getText().toString();
                if(PhoneAndEmailUtils.isMobileNO(num)){
                    type=1;
                }else if(PhoneAndEmailUtils.isEmail(num)){
                    type=2;
                }else{
                    Toast.makeText(ForgetPassActivity.this,"请填写有效的手机号/邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadDialog();
                Request<String> req = NoHttp.createStringRequest(NetworkTitle.DomainLoginNormal + NetworkChildren.FindPass, RequestMethod.POST);
                req.set("type",type+"").set("registerStr",num).set("pass",pass).set("code",code);
                String session = SharedPreferencesUtils.getSession(ForgetPassActivity.this,1);
                if(!TextUtils.isEmpty(session)){
                    req.setHeader("Cookie","PHPSESSID="+session);
                }
                request(0, req, new SimpleResponseListener<String>() {
                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        if(response.isSucceed()){
                            Gson gson = new Gson();
                            BackCode praiseBack = gson.fromJson(response.get(), BackCode.class);
                            if(praiseBack.getCode()==1){
                                LoginHelper.againLoginRetrofit(ForgetPassActivity.this, num ,pass ,new RequestCallback<UserInfo>(){

                                            @Override
                                            public void beforeRequest() {

                                            }

                                            @Override
                                            public void requestFail(String msg) {
                                                dismissLoadDialog();
                                                toTast(ForgetPassActivity.this ,msg);
                                            }

                                            @Override
                                            public void requestSuccess(UserInfo userInfo) {
                                                dismissLoadDialog();
                                                Toast.makeText(ForgetPassActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                                if (getHttpResSuc(userInfo.getCode())) {
                                                    SharedPreferencesUtils.setPassword(ForgetPassActivity.this, TextUtils.isEmpty(userInfo.getPhone()) ? userInfo.getEmail() : userInfo.getPhone(), userInfo.getPassword());
                                                    SharedPreferencesUtils.setLogin(ForgetPassActivity.this, userInfo);
                                                    ForgetPassActivity.this.finishWithAnim();
                                                    startActivity(new Intent(ForgetPassActivity.this ,MainActivity.class));
                                                }else{
                                                    toTast(userInfo.getMessage());
                                                }
                                            }

                                            @Override
                                            public void otherDeal(UserInfo userInfo) {

                                            }
                                        });

                            }else{
                                Toast.makeText(ForgetPassActivity.this,praiseBack.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        super.onFailed(what, response);
                    }
                });
                break;
        }
    }

    private void sendCode(final String num, final int i) {
        addToCompositeDis(HttpUtil.sendCode()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        if (voidResultBeen.getCode() == 1) {
                            StringBuffer sb =new StringBuffer();
                            sb.append(NetworkTitle.DomainLoginNormal);
                            boolean isPhone;
                            if(i==0){
                                sb.append(NetworkChildren.PHONECODE);
                                isPhone=true;
                            }else{
                                sb.append(NetworkChildren.MAILCODE);
                                isPhone=false;
                            }
                            showLoadDialog();
                            isSendCode =true;
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    recLen--;
                                    Message message = new Message();
                                    message.what = 1;
                                    handler.sendMessage(message);
                                }
                            }, 1000, 1000);
                            Request<String> req = NoHttp.createStringRequest(sb.toString(), RequestMethod.POST);
                            if(!isPhone)req.set("email",num);
                            else if(isPhone)req.set("phoneNum",num);
                            req.set("type","2");
                            String session = SharedPreferencesUtils.getSession(ForgetPassActivity.this,1);
                            if(!TextUtils.isEmpty(session)){
                                req.setHeader("Cookie","PHPSESSID="+session);
                            }
                            request(0, req, new SimpleResponseListener<String>() {
                                @Override
                                public void onSucceed(int what, Response<String> response) {
                                    dismissLoadDialog();
                                    if(response.isSucceed()){
                                        try {
                                            Gson gson = new Gson();
                                            BackCode backCode = gson.fromJson(response.get(), BackCode.class);
                                            if(backCode.getCode()==1){
                                                Toast.makeText(ForgetPassActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(ForgetPassActivity.this,backCode.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (Exception e){

                                        }

                                    }
                                }

                                @Override
                                public void onFailed(int what, Response<String> response) {
                                    dismissLoadDialog();
                                }
                            });
                        } else {
                            toTast(ForgetPassActivity.this ,voidResultBeen.getMessage());
                        }
                    }
                }));

    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    send.setText(recLen+"s");
                    if(recLen < 0){
                        timer.cancel();
                        isSendCode =false;
                        recLen=60;
                        send.setText("验证码");
                    }
            }
        }
    };
}
