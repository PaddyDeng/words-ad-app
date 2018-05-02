package thinku.com.word.ui.personalCenter;

import android.os.Bundle;
import android.widget.EditText;

import thinku.com.word.ui.personalCenter.dialog.BaseDialogView;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.utils.Utils;

/**
 * Created by fire on 2017/8/1  16:43.
 * 点击外部不 dismiss
 */

public abstract class BaseNoBackgDialog extends BaseDialogView {

    protected String getEditTxt(EditText editText) {
        return Utils.getEditTextString(editText);
    }

    protected boolean getHttpCodeSucc(int code) {
        if (Utils.getHttpMsgSu(code)) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
    }

    @Override
    protected int[] getWH() {
        int[] wh = {(int) (MeasureUtils.getScreenSize(getActivity()).x * 0.8), getDialog().getWindow().getAttributes().height};
        return wh;
    }

    @Override
    protected boolean isNoTitle() {
        return true;
    }
}
