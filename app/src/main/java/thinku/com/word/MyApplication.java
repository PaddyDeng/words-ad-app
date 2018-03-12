package thinku.com.word;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.net.HttpCookie;
import java.net.URI;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyApplication extends Application {
    private static Context mContext;

    public static Context getInstance(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        InitializationConfig config =InitializationConfig.newBuilder(this).cookieStore(new DBCookieStore(this).setEnable(false)).build();
        NoHttp.initialize(config);

        // 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setDebug(true);
        Logger.setTag("NoHttpSample");
    }
    /**
     * Cookie管理监听。
     */
    private DBCookieStore.CookieStoreListener mListener = new DBCookieStore.CookieStoreListener() {
        @Override
        public void onSaveCookie(URI uri, HttpCookie cookie) { // Cookie被保存时被调用。
            // 1. 判断这个被保存的Cookie是我们服务器下发的Session。
            // 2. 这里的JSessionId是Session的name，
            //    比如java的是JSessionId，PHP的是PSessionId，
            //    当然这里只是举例，实际java中和php不一定是这个，具体要咨询你们服务器开发人员。
            if("PHPSESSID".equals(cookie.getName())) {
                // 设置有效期为最大。
                cookie.setMaxAge(0);
            }
        }

        @Override
        public void onRemoveCookie(URI uri, HttpCookie cookie) {// Cookie被移除时被调用。
            Log.i("移除cookie",cookie.getValue());
        }
    };
}
