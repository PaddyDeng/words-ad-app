package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 测评结果
 */

public class EvaluateResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,share;
    private TextView title_t,know_num,unknow_num,ranking;
    private RecyclerView know_list;
    private View know_rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_result);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        know_rl.setOnClickListener(this);
        ranking.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("测评结果");
        share = (ImageView) findViewById(R.id.title_iv);
        know_num = (TextView) findViewById(R.id.know_num);
        know_list = (RecyclerView) findViewById(R.id.know_list);
        unknow_num = (TextView) findViewById(R.id.unknow_num);
        ranking = (TextView) findViewById(R.id.ranking);
        know_rl = findViewById(R.id.know_rl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.title_iv:
                break;
            case R.id.know_rl:
                break;
            case R.id.ranking:
                break;
        }
    }
}
