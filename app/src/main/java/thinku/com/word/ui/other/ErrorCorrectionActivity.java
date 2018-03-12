package thinku.com.word.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.adapter.ErrorTypeAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;

/**
 * Created by Administrator on 2018/2/22.
 */

public class ErrorCorrectionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,submit,cancel;
    private RecyclerView type_list;
    private int type=0;//1开始

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_correction);
        findView();
        initView();
        setClick();
    }

    private void initView() {
        String[] types={"单词拼写错误","格式有错误","翻译错误","其他"};
        ErrorTypeAdapter adapter =new ErrorTypeAdapter(ErrorCorrectionActivity.this, types, new SelectListener() {
            @Override
            public void setListener(int position) {

            }
        });
        type_list.setAdapter(adapter);
    }

    private void setClick() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("单词纠错");
        type_list = (RecyclerView) findViewById(R.id.type_list);
        LinearLayoutManager manager =new LinearLayoutManager(ErrorCorrectionActivity.this,LinearLayoutManager.VERTICAL,false);
        type_list.setLayoutManager(manager);
        submit = (TextView) findViewById(R.id.submit);
        cancel = (TextView) findViewById(R.id.cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:

                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
}
