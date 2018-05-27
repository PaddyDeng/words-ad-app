package thinku.com.word.ui.other.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.callback.DeleteListener;
import thinku.com.word.utils.LoginHelper;

/**
 * 删除词包弹窗
 */

public class DialogDeleteWordBag extends Dialog{
    private Context context;
    private TextView content_t,y,n;
    private String content;
    private DeleteListener listener;
    private int position ;
    private RecyclerView.ViewHolder holder ;
    public DialogDeleteWordBag(@NonNull Context context) {
        super(context, R.style.AlphaDialogAct);
        this.context=context;
    }

    public void setContent(String s, DeleteListener listener , int position , RecyclerView.ViewHolder holder){
        this.content=s;
        this.listener=listener;
        this.position = position ;
        this.holder = holder ;
        if(null!=content_t)content_t.setText(content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_word_bag);
        n = (TextView) findViewById(R.id.n);
        y = (TextView) findViewById(R.id.y);
        content_t = (TextView) findViewById(R.id.content_t);
        if(!TextUtils.isEmpty(content))content_t.setText(content);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.delete( holder,position);
                dismiss();
            }
        });
    }
}
