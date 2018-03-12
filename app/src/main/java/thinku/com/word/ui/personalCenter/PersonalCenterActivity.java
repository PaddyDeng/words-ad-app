package thinku.com.word.ui.personalCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/7.
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,name,setting;
    private ImageView portrait;
    private RelativeLayout type_setting,sign,feedback,clock,night,service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        findView();
        setClick();
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("个人中心");
        portrait = (ImageView) findViewById(R.id.portrait);
        name = (TextView) findViewById(R.id.name);
        setting = (TextView) findViewById(R.id.setting);
        type_setting = (RelativeLayout) findViewById(R.id.type_setting);
        sign = (RelativeLayout) findViewById(R.id.sign);
        feedback = (RelativeLayout) findViewById(R.id.feedback);
        clock = (RelativeLayout) findViewById(R.id.clock);
        night = (RelativeLayout) findViewById(R.id.night);
        service = (RelativeLayout) findViewById(R.id.service);
    }

    private void setClick() {
        back.setOnClickListener(this);
        setting.setOnClickListener(this);
        type_setting.setOnClickListener(this);
        sign.setOnClickListener(this);
        feedback.setOnClickListener(this);
        clock.setOnClickListener(this);
        night.setOnClickListener(this);
        service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.setting:
                SettingActivity.start(PersonalCenterActivity.this);
                break;
            case R.id.type_setting:
                TypeSettingActivity.start(PersonalCenterActivity.this);
                break;
            case R.id.sign:
                
                break;
            case R.id.feedback:

                break;
            case R.id.clock:

                break;
            case R.id.night:

                break;
            case R.id.service:
                break;
        }
    }
}
