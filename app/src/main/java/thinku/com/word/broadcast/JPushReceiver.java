package thinku.com.word.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/4/19.
 */
public class JPushReceiver extends BroadcastReceiver {
        private static final String TAG = "TalkReceiver";


        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "title: " + title );
            String title1 = bundle.getString(JPushInterface.EXTRA_TITLE);
            Log.e(TAG, "title1: " + title1 );
        }

    }
