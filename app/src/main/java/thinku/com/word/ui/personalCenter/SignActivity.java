package thinku.com.word.ui.personalCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.view.SignDate;

/**
 * 签到
 */

public class SignActivity extends BaseActivity implements View.OnClickListener {

    private SignDate calendar;
    private ImageView back;
    private TextView title_t,total_num,sign,bean_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        findView();
        setClick();
    }



    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("打卡签到");
        calendar = (SignDate) findViewById(R.id.calendar);
        total_num = (TextView) findViewById(R.id.total_num);
        sign = (TextView) findViewById(R.id.sign);
        bean_num = (TextView) findViewById(R.id.bean_num);
    }
    private void setClick() {
        back.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sign:


                break;
        }
    }
}
