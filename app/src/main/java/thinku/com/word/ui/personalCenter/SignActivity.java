package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.view.SignDate;

/**
 * 签到
 */

public class SignActivity extends BaseActivity implements View.OnClickListener {

    private SignDate calendar;
    private ImageView back;
    private TextView title_t,total_num,sign,bean_num;


    public static void start(Context context){
        Intent intent = new Intent(context ,SignActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        findView();
        setClick();
        initData();
    }


    public void initData(){
        addToCompositeDis(HttpUtil.userSingObservable()
        .subscribe(new Consumer<SingBeen>() {
            @Override
            public void accept(SingBeen singBeen) throws Exception {
                if (singBeen != null){

                }
            }
        }));
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("打卡签到");
        calendar = (SignDate) findViewById(R.id.calendar);
        total_num = (TextView) findViewById(R.id.total_num);
        sign = (TextView) findViewById(R.id.sign);
        bean_num = (TextView) findViewById(R.id.bean_num);
    }
    private void setClick() {
        back.setOnClickListener(this);
        sign.setOnClickListener(this);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(6);
        list.add(18);
        calendar.setSign(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sign:

                break;
        }
    }
}
