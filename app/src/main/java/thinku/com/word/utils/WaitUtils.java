package thinku.com.word.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import thinku.com.word.ui.other.dialog.DialogWait;

/**
 * Created by Administrator on 2018/1/23.
 */

public class WaitUtils {
    private static Map<String,DialogWait> dialogs =new HashMap<>();
    public static void show(Context context,String tag){
        if(dialogs.containsKey(tag)){
            dialogs.get(tag).dismiss();
            dialogs.remove(tag);
        }
        DialogWait dialog =new DialogWait(context);
        dialogs.put(tag,dialog);
        dialog.show();
    }

    public static void setHint(String tag,String hint){
        DialogWait dialogWait = dialogs.get(tag);
        if(dialogWait.isShowing())dialogWait.setHint(hint);
    }
    public static void dismiss(String tag){
        DialogWait dialogWait = dialogs.get(tag);
        try {
            if(dialogWait.isShowing())dialogWait.dismiss();
            dialogs.remove(tag);
        }catch (Exception e){
            dialogs.remove(tag);
        }
    }
    public static boolean isRunning(String tag){
        if(dialogs.containsKey(tag))return true;
        else return false;
    }
}
