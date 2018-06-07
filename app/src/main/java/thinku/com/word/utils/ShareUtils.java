package thinku.com.word.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.widget.ScrollView;

import java.io.File;
import java.util.HashMap;
import java.util.PriorityQueue;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import thinku.com.word.R;
import thinku.com.word.http.NetworkTitle;

/**
 * 分享功能
 */

public class ShareUtils {
    private static final String TAG = ShareUtils.class.getSimpleName();
    /**
     * 只分享图片
     * @param context
     * @param imageFile
     */
    public static void shareOnlyImage(Activity context ,String imageFile ){
        if (Screenshot.screen(context ,imageFile)){
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 获取内置SD卡路径

            // title标题，微信、QQ和QQ空间等平台使用
//            oks.setTitle("分享");
//            // titleUrl QQ和QQ空间跳转链接
//            oks.setTitleUrl("http://sharesdk.cn");
//            // text是分享文本，所有平台都需要这个字段
//            oks.setText("我是分享文本");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath(imageFile);//确保SDcard下面存在此张图片
//            oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
            // url在微信、微博，Facebook等平台中使用
//            oks.setUrl("http://sharesdk.cn");
//            // comment是我对这条分享的评论，仅在人人网使用
//            oks.setComment("我是测试评论文本");
            // 启动分享GUI
            oks.show(context);
        }
    }


    public static void ShareOnlyScrollViewImage(Context context, NestedScrollView scrollView , String imageFile){
        Log.e(TAG, "ShareOnlyScrollViewImage: " + imageFile );
        if (FullScreenShot.getBitmapByView(scrollView ,imageFile)){
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath(imageFile);//确保SDcard下面存在此张图片
            // 启动分享GUI
            oks.show(context);
        }
    }
}
