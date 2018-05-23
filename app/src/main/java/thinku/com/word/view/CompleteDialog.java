package thinku.com.word.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.ui.other.dialog.callback.DialogClickListener;

/**
 * Created by Administrator on 2018/3/30.
 */

public class CompleteDialog extends AlertDialog {

    private TextView keep , cancel ;
    private DialogClickListener dialogClickListener ;
    public CompleteDialog(@NonNull Context context ) {
        super(context);
    }


    public CompleteDialog(@NonNull Context context , String content) {
        super(context);
    }


    public CompleteDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CompleteDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setDialogClickListener(DialogClickListener dialogClickListener){
         this.dialogClickListener  = dialogClickListener ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_word_complete);
        setCanceledOnTouchOutside(false);
        keep = (TextView) findViewById(R.id.keep);
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.clickTrue();
            }
        });
    }
}
