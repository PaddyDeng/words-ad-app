package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 复习(选择复习方式)
 */

public class ReviewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,num;
    private RelativeLayout error_rl,time_rl,listener_rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        error_rl.setOnClickListener(this);
        time_rl.setOnClickListener(this);
        listener_rl.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("复习");
        error_rl = (RelativeLayout) findViewById(R.id.error_rl);
        num = (TextView) findViewById(R.id.num);
        time_rl = (RelativeLayout) findViewById(R.id.time_rl);
        listener_rl = (RelativeLayout) findViewById(R.id.listener_rl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.error_rl:
                break;
            case R.id.time_rl:
                break;
            case R.id.listener_rl:
                break;
        }
    }
}
