package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.ui.personalCenter.adapter.ClockAdapter;
import thinku.com.word.ui.personalCenter.bean.Clock;

public class AlarmActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.no_alarm)
    TextView noAlarm;
    @BindView(R.id.alarm)
    RecyclerView alarm;


    private ClockAdapter clockAdapter ;
    private List<Clock> clocks ;

    public static void toAlarm(Context context){
        Intent intent = new Intent(context ,AlarmActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        titleT.setText("闹钟");
        titleIv.setBackgroundResource(R.mipmap.alarm_add);
        clocks = new ArrayList<>();
        Clock  clock = new Clock();
        clock.setCheck(true);
        clock.setTime("13:30");
        clock.setWeek("星期一");
        clocks.add(clock);
        clockAdapter = new ClockAdapter(this ,clocks);
        alarm.setLayoutManager(new LinearLayoutManager(this));
        alarm.setAdapter(clockAdapter);
    }

    @OnClick({R.id.title_iv , R.id.back})
    public void click(View view ){
        switch (view.getId()){
            case R.id.title_iv:
                AlarmAddActivity.start(this);
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
        }
    }
}
