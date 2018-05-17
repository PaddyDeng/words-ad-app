package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.view.wheelview.widget.WheelView;

import static thinku.com.word.R.id.title_t;

public class AddMyPlanActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(title_t)
    TextView titleT;
    @BindView(R.id.title_iv)
    TextView titleIv;
    @BindView(R.id.day)
    TextView daytext;
    @BindView(R.id.num)
    TextView numtext;
    @BindView(R.id.num_of_day)
    TextView numOfDay;
    @BindView(R.id.wheelView_rl)
    RelativeLayout wheelViewRl;
    @BindView(R.id.num_of_word)
    TextView numOfWord;
    @BindView(R.id.wheelView_r2)
    RelativeLayout wheelViewR2;

    Unbinder unbinder ;
    private WheelView wheel_day ,wheel_num;
    private int day = 1;
    private int word = 1;
    private static int isFirst = 0 ;
    private List<String> dayList, wordList;
    private String words;
    private String days;
    private String packId ;
    private int total ;
    public static void start(Context context  , String packId , String total){
        Intent intent = new Intent(context ,AddMyPlanActivity.class);
        intent.putExtra("packId" ,packId);
        intent.putExtra("total" , total);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_plan);
        unbinder = ButterKnife.bind(this);
        init();
        Intent intent = getIntent();
        if (intent != null){
            packId = intent.getStringExtra("packId");
            total = Integer.parseInt(intent.getStringExtra("total"));
            referUi();
        }

    }

    public void referUi(){
        setWheel(total ,1 ,1 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    public  void init(){
        titleT.setText("我的计划");
        titleIv.setText("确定");
        setTextFlag(daytext);
        setTextFlag(numtext);
    }

    /**
     * 设置下划线
     * @param textView
     */
    public void setTextFlag(TextView textView){
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }

    public void setWheel(final int totals, final int dayInt, int wordInt) {
        dayList = new ArrayList<>();
        wordList = new ArrayList<>();
        for (int i = 0; i < totals; i++) {
            dayList.add(i + 1 + "天");
            wordList.add(totals - i + "个");
        }
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = getResources().getColor(R.color.mainColor);
        style.backgroundColor = getResources().getColor(R.color.gray);
        wheel_day = new WheelView(this ,style);
        wheel_day.setWheelAdapter(new MyWheelAdapter(this));
        wheel_day.setWheelSize(9);
        wheel_day.setSkin(WheelView.Skin.Common);
        wheel_day.setWheelData(dayList);
        wheel_day.setSelection(dayInt -1);
        wheel_day.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst ++ ;
                if (isFirst ==1) {
                    day2num(position);
                }else{
                    isFirst = 0 ;
                }
                days = numOfDay.getText().toString().trim();
                words = numOfWord.getText().toString().trim();
                daytext.setText(days);
                numtext.setText(words);

            }
        });
        wheelViewRl.addView(wheel_day);
        numOfDay.setText(dayList.get(dayInt - 1));

        wheel_num = new WheelView(this ,style);
        wheel_num.setWheelAdapter(new MyWheelAdapter(this));
        wheel_num.setWheelSize(9);
        wheel_num.setSkin(WheelView.Skin.Common);
        wheel_num.setWheelData(wordList);

        wheel_num.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst ++ ;
                if (isFirst ==1) {
                    num2day(position);
                }else{
                    isFirst = 0 ;
                }
                words = numOfWord.getText().toString().trim();
                days = numOfDay.getText().toString().trim();
                daytext.setText(days);
                numtext.setText(words);
            }
        });
//        wheel_num.setSelection(totals - wordInt );
        wheelViewR2.addView(wheel_num);
        numOfWord.setText(wordList.get(totals - wordInt));
        days = dayList.get(dayInt - 1);
        words = wordList.get(totals - wordInt);
    }


    private void day2num(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (i + 1));
//        wheel_num.(wordList, total - n);
        wheel_num.setSelection(total - n);
        numOfDay.setText(dayList.get(i));
        numOfWord.setText(wordList.get(total - n));
    }

    private void num2day(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (total - i));
//        wheel_day.setItems(dayList, n - 1);
        wheel_day.setSelection(n -1 );
        numOfDay.setText(dayList.get(n - 1));
        numOfWord.setText(wordList.get(i));
    }

    @OnClick({R.id.back ,R.id.title_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.back:
                finishWithAnim();
                break;
            case R.id.title_iv:
                break;
        }
    }


}