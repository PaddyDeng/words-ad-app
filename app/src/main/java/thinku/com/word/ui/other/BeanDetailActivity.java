package thinku.com.word.ui.other;

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
 * Created by Administrator on 2018/2/22.
 */

public class BeanDetailActivity extends BaseActivity {

    private ImageView back;
    private TextView title_t;
    private RecyclerView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_detail);
        findView();
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("明细");
        list = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager manager =new LinearLayoutManager(BeanDetailActivity.this,LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(manager);
    }
}
