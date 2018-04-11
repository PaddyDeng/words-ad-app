package thinku.com.word.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.utils.C;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SuccessDialog extends AlertDialog {

    private TextView center ;
    private String content ;
    public SuccessDialog(@NonNull Context context ) {
        super(context);
    }


    public SuccessDialog(@NonNull Context context ,String content) {
        super(context);
        this.content = content ;
    }


    public SuccessDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SuccessDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_word_success);
        setCanceledOnTouchOutside(false);
        center = (TextView) findViewById(R.id.center);
        if (!TextUtils.isEmpty(content))  center.setText(content);
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessDialog.this.dismiss();
            }
        });
    }


}
