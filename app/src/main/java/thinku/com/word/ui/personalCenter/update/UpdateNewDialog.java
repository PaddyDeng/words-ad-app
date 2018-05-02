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

    public static UpdateNewDialog getInstance(ICallBack<String> callBack) {
        UpdateNewDialog simpleDialog = new UpdateNewDialog();
        simpleDialog.mCallBack = callBack;
        return simpleDialog;
    }

    @BindView(R.id.close_iv)
    ImageView close;
    @BindView(R.id.imme_update_btn)
    TextView confirmTxt;

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
