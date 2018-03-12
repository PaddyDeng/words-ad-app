package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 设置模式
 */

public class TypeSettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,title_right;
    private TextView ebbinghaus,review,only_new;
    private int oldPage=-1;

    public static void start(Context context){
        Intent intent =new Intent(context,TypeSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_setting);
        findView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        title_right.setOnClickListener(this);
        ebbinghaus.setOnClickListener(this);
        review.setOnClickListener(this);
        only_new.setOnClickListener(this);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_right = (ImageView) findViewById(R.id.title_right);
        ebbinghaus = (TextView) findViewById(R.id.ebbinghaus);
        review = (TextView) findViewById(R.id.review);
        only_new = (TextView) findViewById(R.id.only_new);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.title_right:

                break;
            case R.id.ebbinghaus:
                setSelect(0);
                break;
            case R.id.review:
                setSelect(1);
                break;
            case R.id.only_new:
                setSelect(2);
                break;
        }
    }

    private void setSelect(int i){
        if(oldPage!=i){
            setSelect(oldPage,false);
            setSelect(i,true);
        }
    }
    private void setSelect(int i,boolean b){
        if(b)oldPage=i;
        switch (i){
            case 0:
                ebbinghaus.setSelected(b);
                if(b)ebbinghaus.setTextColor(getResources().getColor(R.color.white));
                else ebbinghaus.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case 1:
                review.setSelected(b);
                if(b)review.setTextColor(getResources().getColor(R.color.white));
                else review.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case 2:
                only_new.setSelected(b);
                if(b)only_new.setTextColor(getResources().getColor(R.color.white));
                else only_new.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
        }
    }
}
