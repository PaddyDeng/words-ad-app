package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import thinku.com.word.base.BaseActivity;
import thinku.com.word.ui.personalCenter.adapter.ClockAdapter;
import thinku.com.word.ui.personalCenter.bean.Clock;
import thinku.com.word.view.ClockDialog;
import thinku.com.word.view.loopview.LoopView;
import thinku.com.word.view.loopview.OnItemSelectedListener;

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
    LoopView hour;
    @BindView(R.id.minute)
    LoopView minute;
    @BindView(R.id.weeks)
    TextView weeks;
    @BindView(R.id.chose_weeks)
    RelativeLayout choseWeeks;

    private StringBuilder choseWeeksText ;

    public static void start(Context context) {
        Intent intent = new Intent(context, AlarmAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add);
        ButterKnife.bind(this);
        initView();

    }

    public void initView() {
        titleT.setText("编辑闹钟");
        titleIv.setBackgroundResource(R.mipmap.right);
        hour.setItems(d(0, 24));
        hour.setCurrentPosition(12);
        hour.setOuterTextColor(R.color.black);
        minute.setItems(d(0, 60));
        minute.setCurrentPosition(30);
        minute.setOuterTextColor(R.color.black);
        hour.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                toTast(AlarmAddActivity.this, index + "");
            }
        });
        minute.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                toTast(AlarmAddActivity.this, index + "");
            }
        });
        choseWeeksText = new StringBuilder();
    }

    @OnClick({R.id.chose_weeks ,R.id.back})
    public void click(View view){
        switch (view.getId()){
            case R.id.chose_weeks:
                choseWeeks();
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
        }
    }


    public void choseWeeks(){
        final ClockDialog clockDialog = new ClockDialog(this);
        clockDialog.setOnReviewClickListener(new ClockDialog.OnReviewClickListener() {
            @Override
            public void onReviewClick() {
                for (int i = 0  ;i < clockDialog.checkBoxes.length ; i++){
                    if (clockDialog.checkBoxes[i].isChecked()) choseWeeksText.append(clockDialog.texts[i].getText().toString().trim() +'\t');
                    clockDialog.dismiss();
                    weeks.setText(choseWeeksText.toString());
                }
            }
        });
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



}
