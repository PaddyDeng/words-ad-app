package thinku.com.word.utils.clock;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import thinku.com.word.utils.NotificationUtil;

/**
 * Created by loongggdroid on 2016/3/21.
 */
public class LoongggAlarmReceiver extends BroadcastReceiver {
    private final static String TAG = LoongggAlarmReceiver.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onReceive: " );
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
//        int flag = intent.getIntExtra("soundOrVibrator", 0);
//        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
//        clockIntent.putExtra("msg", msg);
//        clockIntent.putExtra("flag", flag);
//        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(clockIntent);
        NotificationUtil.setNotificatio(context ,1);
    }
}
