package thinku.com.word.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import thinku.com.word.R;
import thinku.com.word.ui.other.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2018/5/27/027.
 */

public class NotificationUtil {
    public  static void setNotificatio(Context context , int notifyId){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(context , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentText("Word 背单词")
                .setContentText("应该被单词了")
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.logo)
        .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_SHOW_LIGHTS ;
        notification.flags = Notification.FLAG_AUTO_CANCEL ;
        mNotificationManager.notify(notifyId ,notification);
    }
}
