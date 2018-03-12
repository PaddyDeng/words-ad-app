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
 * 选择银行
 */

public class CardTypeActivity   extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t;
    private RecyclerView card_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_type);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("选择银行");
        card_list = (RecyclerView) findViewById(R.id.card_list);
        LinearLayoutManager manager =new LinearLayoutManager(CardTypeActivity.this,LinearLayoutManager.VERTICAL,false);
        card_list.setLayoutManager(manager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
