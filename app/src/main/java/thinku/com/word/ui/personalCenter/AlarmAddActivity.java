package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter1;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.db.ClockDao;
import thinku.com.word.db.bean.Clock;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.view.ClockDialog;
import thinku.com.word.view.wheelview.widget.WheelView;

public class AlarmAddActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.lin_rl)
    AutoLinearLayout linRl;
    @BindView(R.id.hour)
    WheelView hour;
    @BindView(R.id.minute)
    WheelView minute;
    @BindView(R.id.weeks)
    TextView weeks;
    @BindView(R.id.chose_weeks)
    RelativeLayout choseWeeks;

    private String minuteTxt;
    private String hourTxt;
    private ClockDao clockDao;

    private Clock defaultClock;

    private List<String> hours;
    private List<String> minutes;

    public static void start(Context context) {
        Intent intent = new Intent(context, AlarmAddActivity.class);
        context.startActivity(intent);
    }

    public static void toAddAlarm(Context context, Clock clock) {
        Intent intent = new Intent(context, AlarmAddActivity.class);
        intent.putExtra("data", clock);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add);
        ButterKnife.bind(this);
        try {
            defaultClock = (Clock) getIntent().getSerializableExtra("data");
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
        initView();
    }

    public void initView() {
        titleT.setText("编辑闹钟");
        titleIv.setBackgroundResource(R.mipmap.right);
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(i + "");
        }
        for (int i = 0; i < 60; i++) {
            minutes.add(i + "");
        }
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = getResources().getColor(R.color.mainColor);
        style.backgroundColor = getResources().getColor(R.color.gray);
        hour.setStyle(style);
        minute.setStyle(style);
        hour.setSkin(WheelView.Skin.Common);
        minute.setSkin(WheelView.Skin.Common);
        hour.setLoop(true);
        minute.setLoop(true);
        hour.setWheelAdapter(new MyWheelAdapter1(this));
        minute.setWheelAdapter(new MyWheelAdapter1(this));
        hour.setWheelSize(5);
        minute.setWheelSize(5);
        hour.setWheelData(hours);
        minute.setWheelData(minutes);
        hour.setExtraText("时", R.color.white, MeasureUtils.dp2px(this, 13), MeasureUtils.dp2px(this, 20));
        minute.setExtraText("分", R.color.white, MeasureUtils.dp2px(this, 13), MeasureUtils.dp2px(this, 20));
        if (defaultClock != null) {
            String time = defaultClock.getTime();
            String weekTxt = defaultClock.getWeek();
            String[] times = time.split(":");
            hour.setSelection(Integer.parseInt(times[0]));
            minute.setSelection(Integer.parseInt(times[1]));
            hourTxt = times[0];
            minuteTxt = times[1];
            weeks.setText(weekTxt);
        } else {
            hour.setSelection(12);
            minute.setSelection(30);
            hourTxt = "12";
            minuteTxt = "30";
        }
        hour.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                hourTxt = position < 10 ? "0" + hours.get(position) : "" + hours.get(position);
            }

        });
        minute.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                minuteTxt = position < 10 ? "0" + minutes.get(position) : "" + minutes.get(position);
            }
        });
    }

    @OnClick({R.id.chose_weeks, R.id.back, R.id.title_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.chose_weeks:
                choseWeeks();
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
            case R.id.title_iv:
                addClock();
                break;
        }
    }


    public void choseWeeks() {
        final ClockDialog clockDialog = new ClockDialog(this, weeks.getText().toString().trim());
        clockDialog.setOnReviewClickListener(new ClockDialog.OnReviewClickListener() {
            @Override
            public void onReviewClick() {
                StringBuilder choseWeeksText = new StringBuilder();
                boolean isCheckAll = true;
                for (int i = 0; i < clockDialog.checkBoxes.length; i++) {
                    if (clockDialog.checkBoxes[i].isChecked()) {
                        choseWeeksText.append(clockDialog.texts[i].getText().toString().trim() + '\t');
                    } else {
                        isCheckAll = false;
                    }
                }
                if (isCheckAll) weeks.setText("每天");
                else weeks.setText(choseWeeksText.toString());
                Log.e(TAG, "onReviewClick: " + weeks.getText() );
                clockDialog.dismiss();
            }
        });
        clockDialog.setCancelable(false);
        clockDialog.show();
    }


    /**
     * 将数字传化为集合，并且补充0
     *
     * @param startNum 数字起点
     * @param count    数字个数
     * @return
     */
    private static List<String> d(int startNum, int count) {
        String[] values = new String[count];
        for (int i = startNum; i < startNum + count; i++) {
            String tempValue = (i < 10 ? "0" : "") + i;
            values[i - startNum] = tempValue;
        }
        return Arrays.asList(values);
    }


    public void addClock() {
        if (clockDao == null) {
            clockDao = new ClockDao(this);
        }
        if (!TextUtils.isEmpty(weeks.getText().toString().trim())) {
            StringBuilder timeBuilder = new StringBuilder();
            Log.e(TAG, "addClock: " + weeks.getText() );
            int data = -1;
            if (defaultClock == null) {
                Clock clock = new Clock();
                clock.setWeek(weeks.getText().toString());
                clock.setClock(true);
                clock.setTime(timeBuilder.append(hourTxt + ":").append(minuteTxt).toString());
                data = clockDao.addClock(clock);
            } else {
                defaultClock.setWeek(weeks.getText().toString());
                defaultClock.setClock(true);
                defaultClock.setTime(timeBuilder.append(hourTxt + ":").append(minuteTxt).toString());
                data = clockDao.addClock(defaultClock);
            }
            if (data == -1) {
                toTast("添加闹钟失败");
            } else {
                toTast("添加成功");
                this.finishWithAnim();
            }
        } else {
            toTast("日期不能为空");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
