package thinku.com.word.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/22.
 */

public class AddCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,agree;
    private TextView title_t,card,agreement,confirm;
    private EditText name,id_num,card_num;

    private boolean isAgree=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        agree.setOnClickListener(this);
        card.setOnClickListener(this);
        agreement.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("添加银行卡");
        name = (EditText) findViewById(R.id.name);
        id_num = (EditText) findViewById(R.id.id_num);
        card = (TextView) findViewById(R.id.card);
        card_num = (EditText) findViewById(R.id.card_num);
        agree = (ImageView) findViewById(R.id.agree);
        agree.setSelected(true);
        agreement = (TextView) findViewById(R.id.agreement);
        confirm = (TextView) findViewById(R.id.confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.agree:
                isAgree=!isAgree;
                agree.setSelected(isAgree);
                break;
            case R.id.card://选择银行卡

                break;
            case R.id.agreement://协议
                break;
            case R.id.confirm://提交(未验证银行)
                if(!isAgree){
                    Toast.makeText(AddCardActivity.this,"请阅读并同意《银行卡绑定及解绑服务协议》",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(AddCardActivity.this,"请填写持卡人姓名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(id_num.getText())){
                    Toast.makeText(AddCardActivity.this,"请填写身份证号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(card_num.getText())){
                    Toast.makeText(AddCardActivity.this,"请填写银行卡号",Toast.LENGTH_SHORT).show();
                    return;
                }


                break;
        }
    }
}
