package thinku.com.word;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;

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

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static Context mContext;
    public static int MemoryMode = 0;  //  初始化记忆模式
    public static int WordStatus = 0; // 单词状态
    public static String session = "";
    private Stack<Activity> activityStack;
    private static MyApplication myApplication;
    public static MediaPlayer mediaPlayer ;
    public static int task ;
    public static String signTime ;
    public static boolean isLogin = false ;
    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        session = SharedPreferencesUtils.getSession(MyApplication.this, 4);

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
//        refWatcher = LeakCanary.install(this);
        MobSDK.init(this);   //  分享设置
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5af9002c");  //  讯飞语音

//        Stetho.initializeWithDefaults(this);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

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

}
