package thinku.com.word.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.bean.UserIndex;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.SignDate;

public class ShareDateActivity extends BaseActivity  implements PlatformActionListener{

    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.calendar)
    SignDate calendar;
    @BindView(R.id.day_num)
    TextView dayNum;
    @BindView(R.id.word_num)
    TextView wordNum;
    @BindView(R.id.qq)
    LinearLayout qq ;
    @BindView(R.id.qzone)
    LinearLayout qzone ;
    @BindView(R.id.weixin)
    LinearLayout weixin ;
    @BindView(R.id.weixinzone)
    LinearLayout weixinzone ;
    private List<Integer> list ;

    private String content ;
    public static void start(Context c ){
        Intent intent = new Intent(c,ShareDateActivity.class);
        c.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_date);
        ButterKnife.bind(this);

        initData();
        addIndex();
    }


    public AnimType getAnimType() {
        return AnimType.ANIM_TYPE_UP_IN;
    }


    public void initData(){
        addToCompositeDis(HttpUtil.userSingObservable()
                .subscribe(new Consumer<SingBeen>() {
                    @Override
                    public void accept(SingBeen singBeen) throws Exception {
                        if (singBeen != null){
                            referUi(singBeen);
                        }
                    }
                }));
    }

    /**
     *   刷新UI
     * @param singBeen
     */
    public void referUi(SingBeen singBeen){
        List<SingBeen.SignData> signDates = singBeen.getData();
        list = new ArrayList<>();
        if (signDates!= null){
            for (SingBeen.SignData data : signDates) {
                list.add(Integer.parseInt(StringUtils.spiltDay(data.getCreateDay())));
            }
            calendar.setSign(list);
        }
    }

    public void addIndex () {
        addToCompositeDis(HttpUtil.reciteIndex()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Consumer<UserIndex>() {
                    @Override
                    public void accept(@NonNull UserIndex userIndex) throws Exception {
                        if (userIndex != null) {
                            dayNum.setText(userIndex.getInsistDay());
                            wordNum.setText(userIndex.getToDayWords());
                            content = getResources().getString(R.string.share_content );
                            content = String.format(content ,userIndex.getInsistDay() , userIndex.getToDayWords() ,userIndex.getUserAllWords());
                        }
                    }
                }));
    }


    @OnClick({R.id.cancel , R.id.weixin , R.id.qzone , R.id.weixinzone , R.id.qq} )
    public void cancel(View view){
        switch (view.getId()) {
            case R.id.cancel:
            MainActivity.toMain(this);
            this.finishWithAnim();
                break;
            case R.id.weixin:
                Log.e(TAG, "weixin: "  );
                shareWeixin();
                break;
            case R.id.qzone:
                shareqzone(content);
                break;
            case R.id.weixinzone:
                shareWeixinFriends();
                break;
            case R.id.qq:
                shareQQ(content);
                break;
        }
    }


    public void shareqzone(String content){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = sdCardPath + File.separator  + "logo.png";
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(content);
        sp.setTitleUrl(NetworkTitle.WORD1+ File.separator +"wap/share/index?uid=" + SharedPreferencesUtils.getUid(this)+"&type=2");
        sp.setImagePath(filePath);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    toTast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toTast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toTast("取消分享");
            }
        });
        qzone.share(sp);
    }

    public void shareQQ(String content){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = sdCardPath + File.separator  + "logo.png";
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("雷哥单词");
        sp.setTitleUrl(NetworkTitle.WORD1+ File.separator +"wap/share/index?uid=" + SharedPreferencesUtils.getUid(this)+"&type=2");
        sp.setText(content);
        sp.setImagePath(filePath);
        Platform QQ = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
        QQ.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toTast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toTast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toTast("取消分享");
            }
        });
        QQ.share(sp);
    }

    public void shareWeixin(){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = sdCardPath + File.separator  + "logo.png";
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("雷哥单词");
        sp.setUrl(NetworkTitle.WORD1+ File.separator +"wap/share/index?uid=" + SharedPreferencesUtils.getUid(this)+"&type=2");
        sp.setText(content);
        sp.setImagePath(filePath);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
        Weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toTast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toTast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toTast("取消分享");
            }
        });
        Weixin.share(sp);
    }

    public void shareWeixinFriends(){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = sdCardPath + File.separator  + "logo.png";
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setUrl(NetworkTitle.WORD1+ File.separator +"wap/share/index?uid=" + SharedPreferencesUtils.getUid(this)+"&type=2");
        sp.setTitle(content);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setImagePath(filePath);
        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toTast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toTast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toTast("取消分享");
            }
        });
        wechatMoments.share(sp);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        this.finishWithAnim();
        MainActivity.toMain(this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e(TAG, "onError: " );
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e(TAG, "onCancel: " );
    }
}
