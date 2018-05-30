package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.utils.APPSmart;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;

import static thinku.com.word.utils.C.RXBUS_HEAD_IMAGE;

/**
 * Created by Administrator on 2018/2/7.
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    
    private ImageView back;
    private TextView title_t,name,setting;
    private ImageView portrait;
    private RelativeLayout type_setting,sign,feedback,clock,night,service;
    private Observable<String> observable ;
    public static void start(Context context){
        Intent intent = new Intent(context ,PersonalCenterActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        findView();
        setClick();
        observable =RxBus.get().register(RXBUS_HEAD_IMAGE ,String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String integer) throws Exception {
                init();
            }
        });
        init();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void init(){
        new GlideUtils().loadCircle(PersonalCenterActivity.this , NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(PersonalCenterActivity.this) ,portrait);
        name.setText(SharedPreferencesUtils.getNickName(PersonalCenterActivity.this));
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
                SignActivity.start(PersonalCenterActivity.this);
                break;
            case R.id.feedback:
                FeedBackActivity.start(this);
                break;
            case R.id.clock:
                AlarmActivity.toAlarm(this);
                break;
            case R.id.night:
                startActivity(new Intent(this ,ReadMeActivity.class));
                break;
            case R.id.service:
                APPSmart.openApplicationMarket(this ,getPackageName());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE ,observable);
    }
}
