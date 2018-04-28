package thinku.com.word.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/4/28.
 */

public class VersionInfo {


    /**
     *   获取version  名字
     * @param context
     * @return
     */
    public static String versionName(Context context){
        PackageManager  packageManager = context.getPackageManager() ;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName() ,0);
            return packageInfo.versionName ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
