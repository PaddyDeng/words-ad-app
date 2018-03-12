package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 评估排名
 */

public class EvaluateRankingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,share,portrait;
    private TextView title_t,name,num,ranking_num,tv;
    private RecyclerView ranking_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_ranking);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        share.setOnClickListener(this);

    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("评估排名");
        share = (ImageView) findViewById(R.id.title_iv);
        portrait = (ImageView) findViewById(R.id.portrait);
        name = (TextView) findViewById(R.id.name);
        num = (TextView) findViewById(R.id.num);
        ranking_num = (TextView) findViewById(R.id.ranking_num);
        tv = (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.VISIBLE);
        ranking_list = (RecyclerView) findViewById(R.id.ranking_list);
        LinearLayoutManager manager =new LinearLayoutManager(EvaluateRankingActivity.this,LinearLayoutManager.VERTICAL,false);
        ranking_list.setLayoutManager(manager);
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
