package thinku.com.word.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import thinku.com.word.JsonFormat.FromJsonUtils;
import thinku.com.word.bean.EventPkData;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.PkingData;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: ");
			printBundle(bundle);
		} catch (Exception e) {

		}

	}

	// 打印所有的 intent extra 数据
	private static void printBundle(Bundle bundle) {
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}
				Log.e(TAG, "printBundle: " + bundle.getString(JPushInterface.EXTRA_EXTRA) );
				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					switch (json.optInt("type")){

						case 1:
							JPushData<EventPkData> jPushData = FromJsonUtils.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), EventPkData.class);
							EventBus.getDefault().post(jPushData);
							break;
						case 3:

							JPushData  jPushData1 = new JPushData();
							jPushData1.setType(3);
							EventBus.getDefault().post(jPushData1);
							break;
						case 2:
							JPushData<EventPkListData> jPushData2  = FromJsonUtils.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA) ,EventPkListData.class);
							EventBus.getDefault().post(jPushData2);
							break;
						case 4:
							JPushData<PkingData> jPushData3 = FromJsonUtils.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA) ,PkingData.class);
							EventBus.getDefault().post(jPushData3);
							break;
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!" + e.getMessage());
				}

			}
		}
	}
}
