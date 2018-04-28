package thinku.com.word.utils;

import android.os.Build;
import android.text.Editable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/4/28.
 */

public class Utils {

    public static void removeOnGlobleListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public static String getEditTextString(EditText et) {
        Editable text = et.getText();
        return text != null && text.toString().trim().length() != 0 ? text.toString().trim() : null;
    }

    public static boolean getHttpMsgSu(int code) {
        if (code == 1) {
            return true;
        }
        return false;
    }

}
