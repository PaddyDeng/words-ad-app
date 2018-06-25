package thinku.com.word.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

import static android.widget.Toast.makeText;

/**
 * Created by Administrator on 2018/4/28.
 */

public class Utils {

    public static boolean LOG_H = true;

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

    public static void keyBordShowFromWindow(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }
    public static void toastShort(Context context, int id) {
        makeText(context, context.getString(id), Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(Context context, String msg) {
        makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(Context context, String msg, int time) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setDuration(time);
        toast.show();
    }

    public static void setVisible(View... views) {
        if (views != null && views.length > 0) {
            View[] v = views;
            int size = views.length;

            for (int i = 0; i < size; ++i) {
                View view = v[i];
                if (view != null && view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static void setInvisible(View... views) {
        if (views != null && views.length > 0) {
            View[] var4 = views;
            int var3 = views.length;

            for (int var2 = 0; var2 < var3; ++var2) {
                View view = var4[var2];
                if (view != null && view.getVisibility() != View.INVISIBLE) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    public static void setGone(View... views) {
        if (views != null && views.length > 0) {
            View[] v = views;
            int size = views.length;

            for (int i = 0; i < size; ++i) {
                View view = v[i];
                if (view != null && view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    public static int getCurrentVersionNum(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void logh(String tag, String msg) {
        if (LOG_H) {
            Log.d(tag, msg);
        }
    }

    public static boolean isBelowAndroidVersion(int version) {
        return Build.VERSION.SDK_INT < version;
    }

    public static void controlTvFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

}
