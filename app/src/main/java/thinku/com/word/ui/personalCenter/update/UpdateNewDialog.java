package thinku.com.word.ui.personalCenter.update;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import thinku.com.word.R;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.ui.personalCenter.BaseNoBackgDialog;

/**
 * Created by fire on 2017/8/1  11:16.
 */

public class UpdateNewDialog extends BaseNoBackgDialog {

    private static ICallBack<String> mCallBack;
    private static String content ;
    public static UpdateNewDialog getInstance(String con,ICallBack<String> callBack) {
        UpdateNewDialog simpleDialog = new UpdateNewDialog();
        content = con ;
        simpleDialog.mCallBack = callBack;
        return simpleDialog;
    }

    @BindView(R.id.close_iv)
    ImageView close;
    @BindView(R.id.imme_update_btn)
    TextView confirmTxt;
    @BindView(R.id.dialog_new_tip_content_tv)
    TextView newTipContent ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onFail();
                    mCallBack = null;
                }
                dismiss();
            }
        });
        confirmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onSuccess("");
                    mCallBack = null;
                }
                dismiss();
            }
        });
        newTipContent.setText(content);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_tip_update_new_layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCallBack != null)
            mCallBack = null;
    }

}
