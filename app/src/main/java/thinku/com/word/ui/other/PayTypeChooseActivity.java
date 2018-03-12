package thinku.com.word.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 充值,记得限制输入框2位小数
 */

public class PayTypeChooseActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,wechat_select,alipay_select,card_select;
    private TextView title_t,to_pay;
    private EditText et;
    private LinearLayout wechat,alipay;
    private RelativeLayout card;
    private int selectP=-1;
    private List<ImageView> ivs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_type_choose);
        findView();
        initView();
        setClick();
    }

    private void initView() {
        ivs=new ArrayList<>();
        ivs.add(wechat_select);
        ivs.add(alipay_select);
        ivs.add(card_select);
    }


    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("充值");
        et = (EditText) findViewById(R.id.et);
        wechat = (LinearLayout) findViewById(R.id.wechat);
        alipay = (LinearLayout) findViewById(R.id.alipay);
        card = (RelativeLayout) findViewById(R.id.card);
        wechat_select = (ImageView) findViewById(R.id.wechat_select);
        alipay_select = (ImageView) findViewById(R.id.alipay_select);
        card_select = (ImageView) findViewById(R.id.card_select);
        to_pay = (TextView) findViewById(R.id.to_pay);
    }

    private void setClick() {
        back.setOnClickListener(this);
        wechat.setOnClickListener(this);
        alipay.setOnClickListener(this);
        card.setOnClickListener(this);
        card_select.setOnClickListener(this);
        to_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.wechat:
                setSelect(0);
                break;
            case R.id.alipay:
                setSelect(1);
                break;
            case R.id.card:

                break;
            case R.id.card_select:
                setSelect(2);
                break;
            case R.id.to_pay:
                break;
        }
    }

    private void setSelect(int i){
        for (int j = 0; j < ivs.size(); j++) {
            if(i==j) ivs.get(j).setSelected(true);
            else ivs.get(j).setSelected(false);
        }
        selectP=i;
    }

}
