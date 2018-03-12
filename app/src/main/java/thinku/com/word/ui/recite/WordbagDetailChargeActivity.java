package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/9.
 */

public class WordbagDetailChargeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView bean_num,can_num,to_recite,to_buy,buy,title_t,to_pay;
    private EditText et;
    private LinearLayout bottom_1,bottom_2;
    private RecyclerView word_list;
    private LinearLayout have_ll,unhave_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbag_detail_charge);
        findView();

        setClick();
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        bean_num = (TextView) findViewById(R.id.bean_num);
        can_num = (TextView) findViewById(R.id.can_num);
        to_recite = (TextView) findViewById(R.id.to_recite);
        to_buy = (TextView) findViewById(R.id.to_buy);
        et = (EditText) findViewById(R.id.et);
        buy = (TextView) findViewById(R.id.buy);
        bottom_1 = (LinearLayout) findViewById(R.id.bottom_1);
        bottom_2 = (LinearLayout) findViewById(R.id.bottom_2);
        word_list = (RecyclerView) findViewById(R.id.word_list);
        have_ll = (LinearLayout) findViewById(R.id.have_ll);
        unhave_ll = (LinearLayout) findViewById(R.id.unhave_ll);
        to_pay = (TextView) findViewById(R.id.to_pay);
    }

    private void setClick() {
        back.setOnClickListener(this);
        to_recite.setOnClickListener(this);
        to_buy.setOnClickListener(this);
        buy.setOnClickListener(this);
        to_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.to_recite:
                break;
            case R.id.to_buy:
                bottom_1.setVisibility(View.GONE);
                bottom_2.setVisibility(View.VISIBLE);
                break;
            case R.id.buy:

                et.setText("");
                bottom_2.setVisibility(View.GONE);
                bottom_1.setVisibility(View.VISIBLE);
                break;
            case R.id.to_pay:

                break;
        }
    }
}
