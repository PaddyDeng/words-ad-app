package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 评估首页
 */

public class EvaluateFirstActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,portrait;
    private TextView name,state,evaluate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_first);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        evaluate.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        portrait = (ImageView) findViewById(R.id.portrait);
        name = (TextView) findViewById(R.id.name);
        state = (TextView) findViewById(R.id.state);
        evaluate = (TextView) findViewById(R.id.evaluate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.evaluate:
                break;
        }
    }
}
