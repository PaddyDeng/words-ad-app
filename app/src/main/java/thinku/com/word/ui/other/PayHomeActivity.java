package thinku.com.word.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/9.
 */

public class PayHomeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,title_right_t,bean_num,to_pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_home);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        title_right_t.setOnClickListener(this);
        to_pay.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("雷豆充值");
        title_right_t = (TextView) findViewById(R.id.title_right_t);
        title_right_t.setVisibility(View.VISIBLE);
        title_right_t.setText("明细");
        bean_num = (TextView) findViewById(R.id.bean_num);
        to_pay = (TextView) findViewById(R.id.to_pay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.title_right_t:
                break;
            case R.id.to_pay:
                break;
        }
    }
}
