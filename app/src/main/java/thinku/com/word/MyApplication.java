package thinku.com.word;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.net.HttpCookie;
import java.net.URI;
import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static Context mContext;
    public static int MemoryMode = 0;  //  初始化记忆模式
    public static int WordStatus = 0; // 单词状态
    public static String session = "";
    private Stack<Activity> activityStack;
    private static MyApplication myApplication;
    private RefWatcher refWatcher;

    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        session = SharedPreferencesUtils.getSession(MyApplication.this, 4);

        //  Nohttp
        InitializationConfig config = InitializationConfig.newBuilder(this)
                .cookieStore(new DBCookieStore(this).setEnable(false))
                .addHeader("Cookie", "PHPSESSID=" + session)
                .build();
        NoHttp.initialize(config);

        // 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setDebug(true);
        Logger.setTag("NoHttpSample");
        IMAudioManager.instance().init(this);
        //  fragment  框架配置
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);

                        Log.e(TAG, "onException: " + e.getMessage());
                    }
                })
                .install();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        refWatcher = LeakCanary.install(this);
        MobSDK.init(this);   //  分享设置
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5af9002c");  //  讯飞语音
//        Stetho.initializeWithDefaults(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static MyApplication newInstance() {
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }

        }
        return myApplication;
    }

    /**
     * 添加Activity到堆栈
     */
    // Stack <Activity> activityStack;
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * Cookie管理监听。x
     */
    private DBCookieStore.CookieStoreListener mListener = new DBCookieStore.CookieStoreListener() {
        @Override
        public void onSaveCookie(URI uri, HttpCookie cookie) { // Cookie被保存时被调用。
            // 1. 判断这个被保存的Cookie是我们服务器下发的Session。
            // 2. 这里的JSessionId是Session的name，
            //    比如java的是JSessionId，PHP的是PSessionId，
            //    当然这里只是举例，实际java中和php不一定是这个，具体要咨询你们服务器开发人员。
            if ("PHPSESSID".equals(cookie.getName())) {
                // 设置有效期为最大。
                cookie.setMaxAge(0);
            }
        }

        @Override
        public void onRemoveCookie(URI uri, HttpCookie cookie) {// Cookie被移除时被调用。
            Log.i("移除cookie", cookie.getValue());
        }

    };


}
