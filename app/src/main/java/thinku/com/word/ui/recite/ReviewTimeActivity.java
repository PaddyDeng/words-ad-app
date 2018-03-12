package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.view.wheelView.WheelView;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ReviewTimeActivity extends BaseActivity implements View.OnClickListener {

    private WheelView wheel_start,wheel_end;
    private ImageView back,title_iv;
    private TextView title_t,start_time,end_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_time);
        findView();
        initData();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        title_iv.setOnClickListener(this);
    }

    private void initData() {
        List<String> startList =new ArrayList<>();
        List<String> endList =new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            startList.add("测试"+i);
            endList.add("测试"+i);
        }
        wheel_start.setItems(startList,0);
        wheel_end.setItems(endList,0);
        wheel_start.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {

            }
        });
        wheel_end.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {

            }
        });
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("按时间复习");
        title_iv = (ImageView) findViewById(R.id.title_iv);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        wheel_start = (WheelView) findViewById(R.id.wheel_start);
        wheel_end = (WheelView) findViewById(R.id.wheel_end);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.title_iv:
                break;
        }
    }
}
