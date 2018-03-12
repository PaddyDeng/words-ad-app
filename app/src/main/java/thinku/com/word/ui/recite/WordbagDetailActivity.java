package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/9.
 */

public class WordbagDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,have_num,total_num,to_recite;
    private RecyclerView word_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbag_detail);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        to_recite.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        have_num = (TextView) findViewById(R.id.hava_num);
        total_num = (TextView) findViewById(R.id.total_num);
        to_recite = (TextView) findViewById(R.id.to_recite);
        word_list = (RecyclerView) findViewById(R.id.word_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.to_recite:

                break;
        }
    }
}
