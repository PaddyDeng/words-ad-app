package thinku.com.word.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

/**
 * Created by Administrator on 2018/3/22.
 * sharepref 工具类
 */

public class SharePref {

    protected static String PREFS_NAME = "SHAREDPREF_WORD";
    private final static String PREFS_KEY_COOKIE = "prefs_key_cookie";
    public static final String PREFS_STR_INVALID = "";
    public static String getCookie(Context c) {
        String pwd = getString(PREFS_KEY_COOKIE, c);
        if (isInvalidPrefString(pwd)) {
            return PREFS_STR_INVALID;
        }
        pwd = new String(Base64.decode(pwd.getBytes(),
                Base64.DEFAULT));
        return pwd;
    }

    public static void saveCookie(Context c, String cookie) {
        setString(PREFS_KEY_COOKIE, new String(Base64.encodeToString(cookie.getBytes(),
                Base64.DEFAULT)), c);
    }


    public static void setString(String key, String value, Context c) {
        SharedPreferences.Editor editor = c.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean isInvalidPrefString(String value) {
        return value == null || "".equals(value);
    }

    public static String getString(String key, Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getString(key, "");
    }

}
