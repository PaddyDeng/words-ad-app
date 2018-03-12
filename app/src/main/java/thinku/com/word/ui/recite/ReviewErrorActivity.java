package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.ReviewErrorAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;

/**
 * 错题本
 */

public class ReviewErrorActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,title_iv;
    private TextView title_t,total_num;
    private RecyclerView list_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_error);
        findView();
        initView();
        setClick();
    }

    private void initView() {
        int total=200;
        List<String> list =new ArrayList<>();
        for (int i = 0; i < total/20; i++) {
            list.add(i*20+"-"+(i+1)*20);
        }

        ReviewErrorAdapter adapter =new ReviewErrorAdapter(ReviewErrorActivity.this, list, new SelectListener() {
            @Override
            public void setListener(int position) {

            }
        });
        list_view.setAdapter(adapter);
    }

    private void setClick() {
        back.setOnClickListener(this);
        title_iv.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("错题本");
        title_iv = (ImageView) findViewById(R.id.title_iv);
        total_num = (TextView) findViewById(R.id.total_num);
        list_view = (RecyclerView) findViewById(R.id.list_view);
        GridLayoutManager manager =new GridLayoutManager(ReviewErrorActivity.this,3);
        list_view.setLayoutManager(manager);
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
