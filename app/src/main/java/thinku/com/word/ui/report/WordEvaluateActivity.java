package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/22.
 */

public class WordEvaluateActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,play;
    private LinearLayout familiar,errors;
    private TextView word,phonogram;
    private RecyclerView option_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_evaluate);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        familiar.setOnClickListener(this);
        errors.setOnClickListener(this);
        play.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        familiar = (LinearLayout) findViewById(R.id.familiar);
        errors = (LinearLayout) findViewById(R.id.errors);
        play = (ImageView) findViewById(R.id.play);
        word = (TextView) findViewById(R.id.word);
        phonogram = (TextView) findViewById(R.id.phonogram);
        option_list = (RecyclerView) findViewById(R.id.option_list);
        LinearLayoutManager manager =new LinearLayoutManager(WordEvaluateActivity.this,LinearLayoutManager.VERTICAL,false);
        option_list.setLayoutManager(manager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.familiar:
                break;
            case R.id.errors:
                break;
            case R.id.play:
                break;
        }
    }
}
