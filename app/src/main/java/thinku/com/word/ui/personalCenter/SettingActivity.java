package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2018/2/7.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,portrait;
    private TextView title_t,name,nick,phone,email,pass,version,font;
    private LinearLayout personal_detail;
    private RelativeLayout portrait_rl,name_rl,nick_rl,phone_rl,email_rl,pass_rl,version_rl,font_rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView();
        setClick();
        init();
    }

    public static void start(Context context){
        Intent intent =new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }


    public void init(){
        new GlideUtils().load(SettingActivity.this , NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(SettingActivity.this),portrait);

    }
    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("账号设置");
        personal_detail = (LinearLayout) findViewById(R.id.personal_detail);
        portrait_rl = (RelativeLayout) findViewById(R.id.portrait_rl);
        portrait = (ImageView) findViewById(R.id.portrait);
        name_rl = (RelativeLayout) findViewById(R.id.name_rl);
        name = (TextView) findViewById(R.id.name);
        nick_rl = (RelativeLayout) findViewById(R.id.nick_rl);
        nick = (TextView) findViewById(R.id.nick);
        phone_rl = (RelativeLayout) findViewById(R.id.phone_rl);
        phone = (TextView) findViewById(R.id.phone);
        email_rl = (RelativeLayout) findViewById(R.id.email_rl);
        email = (TextView) findViewById(R.id.email);
        pass_rl = (RelativeLayout) findViewById(R.id.pass_rl);
        pass = (TextView) findViewById(R.id.pass);
        version_rl = (RelativeLayout) findViewById(R.id.version_rl);
        version = (TextView) findViewById(R.id.version);
        font_rl = (RelativeLayout) findViewById(R.id.font_rl);
        font = (TextView) findViewById(R.id.font);
    }
    private void setClick() {
        back.setOnClickListener(this);
        portrait_rl.setOnClickListener(this);
        name_rl.setOnClickListener(this);
        nick_rl.setOnClickListener(this);
        phone_rl.setOnClickListener(this);
        email_rl.setOnClickListener(this);
        pass_rl.setOnClickListener(this);
        version_rl.setOnClickListener(this);
        font_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.portrait_rl:
                break;
            case R.id.name_rl:
                break;
            case R.id.nick_rl:
                break;
            case R.id.phone_rl:
                break;
            case R.id.email_rl:
                break;
            case R.id.pass_rl:
                break;
            case R.id.version_rl:
                break;
            case R.id.font_rl:
                break;
        }
    }
}
