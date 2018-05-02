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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.Timer;
import java.util.TimerTask;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.BackCode;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.PhoneAndEmailUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RigisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,agree;
    private EditText num_et,code_et,pass_et;
    private TextView send,rigister,protocol,to_login;
    private boolean isAgree=true;
    private boolean isSendCode;
    private Timer timer;
    private int recLen = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);
        findView();
        setClick();
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
        LoginHelper.initMessage(RigisterActivity.this);
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
                        Toast.makeText(RigisterActivity.this, "请填写账号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String num=num_et.getText().toString();
                    if(PhoneAndEmailUtils.isMobileNO(num_et.getText().toString())){
                        sendCode(num,0);
                    }else if(PhoneAndEmailUtils.isEmail(num_et.getText().toString())){
                        sendCode(num,1);
                    }else{
                        Toast.makeText(RigisterActivity.this,"请输入有效的手机号/邮箱",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                break;
            case R.id.rigister:
                if(!isAgree){
                    Toast.makeText(RigisterActivity.this,"请阅读并同意《用户协议》",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(num_et.getText())){
                    Toast.makeText(RigisterActivity.this,"请填写账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(code_et.getText())){
                    Toast.makeText(RigisterActivity.this,"请填写验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass_et.getText())){
                    Toast.makeText(RigisterActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RigisterActivity.this,"请填写有效的手机号/邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }

                showLoadDialog();
                //参数需要对一下
                Request<String> req = NoHttp.createStringRequest(NetworkTitle.DomainLoginNormal + NetworkChildren.REGISTER, RequestMethod.POST);
                req.set("registerStr",num).set("pass",pass).set("code",code).set("userName","").set("source","3").set("belong","2").set("type",type+"");
                String session = SharedPreferencesUtils.getSession(RigisterActivity.this,1);
                if(!TextUtils.isEmpty(session)){
                    req.setHeader("Cookie","PHPSESSID="+session);
                }
                request(0, req, new SimpleResponseListener<String>() {
                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        dismissLoadDialog();
                        if(response.isSucceed()){
                            try {
                                BackCode praiseBack = JSON.parseObject(response.get(), BackCode.class);
                                if(praiseBack.getCode()==1){
                                    Toast.makeText(RigisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                                    LoginHelper.againLogin(RigisterActivity.this,num ,pass,1);
                                    //跳转修改昵称
//                                    Intent intent =new Intent(RigisterActivity.this, NameActivity.class);
//                                    intent.putExtra("tag",1);
//                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(RigisterActivity.this,praiseBack.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){

                            }

                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        dismissLoadDialog();
                    }
                });




                break;
            case R.id.agree:
                isAgree=!isAgree;
                agree.setSelected(isAgree);
                break;
            case R.id.protocol:

                break;
            case R.id.to_login:
                finish();
                break;
        }
    }


    /**
     *   发送
     * @param num
     * @param i
     */
    private void sendCode(String num, int i) {

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
        Request<String> req = NoHttp.createStringRequest(sb.toString(), RequestMethod.POST);
        if(!isPhone)req.set("email",num);
        else if(isPhone)req.set("phoneNum",num);
        req.set("type","1");
        String session = SharedPreferencesUtils.getSession(RigisterActivity.this,1);
        if(!TextUtils.isEmpty(session)){
            req.setHeader("Cookie","PHPSESSID="+session);
        }
        request(0, req, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                dismissLoadDialog();
                if(response.isSucceed()){
                    try {
                        BackCode backCode = JSON.parseObject(response.get(), BackCode.class);
                        if(backCode.getCode()==1){
                            Toast.makeText(RigisterActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                            timerClick();
                        }else {
                            Toast.makeText(RigisterActivity.this,backCode.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){

                    }

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                dismissLoadDialog();
            }
        });
    }

    /**
     *  启动计时器
     */
    public void timerClick(){
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
