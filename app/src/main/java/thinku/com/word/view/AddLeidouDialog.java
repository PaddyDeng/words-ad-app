package thinku.com.word.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/30.
 */

public class AddLeidouDialog extends AlertDialog {
    private TextView add ;
    private String content ;
    public AddLeidouDialog(@NonNull Context context , int  themRsId, String weeks) {
        super(context ,themRsId);
        this.content = weeks ;
    }

    public AddLeidouDialog(@NonNull Context context , String weeks) {
        super(context );
        this.content = weeks ;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_add);
        initView();
    }


    public void initView() {
      add = (TextView) findViewById(R.id.add);
      add.setText(content);
    }

}
