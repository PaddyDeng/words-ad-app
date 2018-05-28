package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.SignDate;

/**
 * 签到
 */

public class SignActivity extends BaseActivity implements View.OnClickListener {

    private SignDate calendar;
    private ImageView back;
    private TextView title_t,total_num,sign,bean_num;

    private List<Integer> list ;
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
                   referUi(singBeen);
                }
            }
        }));
    }

    /**
     *   刷新UI
     * @param singBeen
     */
    public void referUi(SingBeen singBeen){
        List<SingBeen.SignData> signDates = singBeen.getData();
        list = new ArrayList<>();
        if (signDates!= null){
            for (SingBeen.SignData data : signDates) {
                list.add(Integer.parseInt(StringUtils.spiltDay(data.getCreateDay())));
            }
            calendar.setSign(list);
        }
        total_num.setText("累计打卡：" + singBeen.getNum()+"天");
        bean_num.setText(singBeen.getIntegral());
        if (singBeen.getType() == 0){
            sign.setEnabled(true);
            sign.setText("签到");
        }else{
            sign.setEnabled(false);
            sign.setText("已签到");
        }
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sign:
                sign();
                break;
        }
    }

    /**
     * 签到
     */
    public void sign(){
        addToCompositeDis(HttpUtil.singObservable()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())) {
                            toTast(SignActivity.this, voidResultBeen.getMessage());
                            list.add(DateUtil.getToday());
                            calendar.setSign(list);
                            initData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}
