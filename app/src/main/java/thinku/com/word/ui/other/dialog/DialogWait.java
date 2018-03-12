package thinku.com.word.ui.other.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import thinku.com.word.R;


/**
 * Created by Administrator on 2018/1/23.
 */

public class DialogWait extends Dialog{
    private TextView loading_msg;
    public DialogWait(@NonNull Context context) {
        super(context, R.style.AlphaDialogAct);
    }

    public void setHint(String s){
        loading_msg.setText(s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        loading_msg = (TextView) findViewById(R.id.loading_msg);
    }
}
