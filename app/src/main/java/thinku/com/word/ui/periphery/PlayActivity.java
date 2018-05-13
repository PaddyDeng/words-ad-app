package thinku.com.word.ui.periphery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gensee.common.ServiceType;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.DocInfo;
import com.gensee.entity.InitParam;
import com.gensee.entity.QAMsg;
import com.gensee.entity.VodObject;
import com.gensee.media.VODPlayer;
import com.gensee.taskret.OnTaskRet;
import com.gensee.utils.GenseeLog;
import com.gensee.view.GSDocViewGx;
import com.gensee.view.GSVideoView;
import com.gensee.vod.VodSite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseNoImmActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.view.VideoErrorView;
import thinku.com.word.ui.periphery.view.VideoSystemOverlay;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.HtmlUtil;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.utils.RxHelper;


public class PlayActivity extends BaseNoImmActivity implements VodSite.OnVodListener, VODPlayer.OnVodPlayListener
        , GestureDetector.OnGestureListener, SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.gsDocViewGx)
    GSDocViewGx gsDocViewGx;
    @BindView(R.id.gsVideoView)
    MoveGSVideoView gsVideoView;
    @BindView(R.id.video_system_overlay)
    VideoSystemOverlay videoSystemOverlay;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.player_lock_screen)
    ImageView playerLockScreen;
    @BindView(R.id.video_controller_error)
    VideoErrorView videoControllerError;
    @BindView(R.id.video_back)
    ImageView videoBack;
    @BindView(R.id.player_pause)
    ImageView playerPause;
    @BindView(R.id.player_progress)
    TextView playerProgress;
    @BindView(R.id.player_seek_bar)
    SeekBar playerSeekBar;
    @BindView(R.id.player_duration)
    TextView playerDuration;
    @BindView(R.id.video_full_screen)
    ImageView videoFullScreen;
    @BindView(R.id.video_title)
    TextView videoTitle;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.content)
    WebView contentTxt;

    //    手势
    private GestureDetector mGestureDetector;

    public static final int FINGER_BEHAVIOR_PROGRESS = 0x01;  //进度调节
    public static final int FINGER_BEHAVIOR_VOLUME = 0x02;  //音量调节
    public static final int FINGER_BEHAVIOR_BRIGHTNESS = 0x03;  //亮度调节
    private int mFingerBehavior;
    private float mCurrentVolume; // 鉴于音量范围值比较小 使用float类型施舍五入处理.
    private int mMaxVolume;
    private int mCurrentBrightness, mMaxBrightness;

    protected Activity activity;
    protected AudioManager am;

    private String url;
    private VodSite vodSite;
    private InitParam initParam;
    private String vodId;
    String msg = "";

    private int VIEDOPAUSEPALY = 0;
    private int speedItem = 0;
    private VODPlayer mVodPlayer;
    private int lastPostion = 0;
    private int DURITME = Toast.LENGTH_SHORT;
    private static final String DURATION = "DURATION";
    private boolean isTouch = false;

    private boolean isLock = false;  //  是否锁定屏幕
    private boolean mShow = false;   //  是否显示控制器
    private boolean NoEventShow = false;  // 是否有事件操作

    private Disposable disposable;
    private String content;

    //    seekbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (null != mVodPlayer) {
            int pos = seekBar.getProgress();
            GenseeLog.d(TAG, "onStopTrackingTouch pos = " + pos);
            mVodPlayer.seekTo(pos);
            NoEventShow = false;
            delayHideAll();
        }
    }

    interface MSG {
        int MSG_ON_INIT = 1;
        int MSG_ON_STOP = 2;
        int MSG_ON_POSITION = 3;
        int MSG_ON_VIDEOSIZE = 4;
        int MSG_ON_PAGE = 5;
        int MSG_ON_SEEK = 6;
        int MSG_ON_AUDIOLEVEL = 7;
        int MSG_ON_ERROR = 8;
        int MSG_ON_PAUSE = 9;
        int MSG_ON_RESUME = 10;
    }

    protected Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG.MSG_ON_INIT:

                    int max = msg.getData().getInt(DURATION);
                    playerSeekBar.setMax(max);
                    max = max / 1000;
                    GenseeLog.i(TAG, "MSG_ON_INIT duration = " + max);
                    playerDuration.setText(DateUtil.getTime(max));
                    mVodPlayer.seekTo(lastPostion);
                    playerPause.setImageResource(R.drawable.ic_video_pause);
                    NoEventShow = false;
                    delayHideAll();
//                    }
                    break;
                case MSG.MSG_ON_STOP:

                    break;
                case MSG.MSG_ON_VIDEOSIZE:

                    break;
                case MSG.MSG_ON_PAGE:
                    break;
                case MSG.MSG_ON_PAUSE:
                    VIEDOPAUSEPALY = 1;
                    playerPause.setImageResource(R.drawable.ic_video_play);
                    NoEventShow = false;
                    delayHideAll();
                    break;
                case MSG.MSG_ON_RESUME:
                    VIEDOPAUSEPALY = 0;
                    playerPause.setImageResource(R.drawable.ic_video_pause);
                    NoEventShow = false;
                    delayHideAll();
                    break;
                case MSG.MSG_ON_POSITION:
                    if (isTouch) {
                        return;
                    }
                case MSG.MSG_ON_SEEK:
                    isTouch = false;
                    int anyPosition = (Integer) msg.obj;
                    playerSeekBar.setProgress(anyPosition);
                    anyPosition = anyPosition / 1000;
                    playerProgress.setText(DateUtil.getTime(anyPosition));
                    break;
                case MSG.MSG_ON_AUDIOLEVEL:

                    break;
                case MSG.MSG_ON_ERROR:
                    int errorCode = (Integer) msg.obj;
                    switch (errorCode) {
                        case ERR_PAUSE:
                            Toast.makeText(getApplicationContext(), "暂停失败", DURITME)
                                    .show();
                            break;
                        case ERR_PLAY:
                            Toast.makeText(getApplicationContext(), "播放失败", DURITME)
                                    .show();
                            break;
                        case ERR_RESUME:
                            Toast.makeText(getApplicationContext(), "恢复失败", DURITME)
                                    .show();
                            break;
                        case ERR_SEEK:
                            Toast.makeText(getApplicationContext(), "进度变化失败", DURITME)
                                    .show();
                            break;
                        case ERR_STOP:
                            Toast.makeText(getApplicationContext(), "停止失败", DURITME)
                                    .show();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private String title;

    public static void start(Context context, String content, String title, String url) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        try {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");
        } catch (Exception e) {

        }
        init();
        initGesture();
        initView();
        showWeb();
    }

    public void showWeb() {
        String s = HtmlUtil.repairContent(content, NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        contentTxt.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
    }

    public void initView() {
        playerSeekBar.setOnSeekBarChangeListener(this);
    }


    @OnClick({R.id.player_pause, R.id.player_lock_screen, R.id.video_full_screen , R.id.video_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.player_pause:
                if (VIEDOPAUSEPALY == 0) {
                    mVodPlayer.pause();
                } else if (VIEDOPAUSEPALY == 1) {
                    mVodPlayer.resume();
                }
                break;
            case R.id.player_lock_screen:
                if (isLock) {
                    unLock();
                } else {
                    lock();
                }
                break;
            case R.id.video_full_screen:
                MeasureUtils.toggleScreenOrientation(PlayActivity.this);
                break;
            case R.id.video_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public void lock() {
        NoEventShow = false;
        playerLockScreen.setVisibility(View.VISIBLE);
        playerLockScreen.setImageResource(R.drawable.video_locked);
        layoutTitle.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.GONE);
        videoBack.setVisibility(View.GONE);
        isLock = true;
    }

    public void unLock() {
        playerLockScreen.setVisibility(View.VISIBLE);
        playerLockScreen.setImageResource(R.drawable.video_unlock);
        layoutTitle.setVisibility(View.VISIBLE);
        layoutBottom.setVisibility(View.VISIBLE);
        videoBack.setVisibility(View.VISIBLE);
        isLock = false;
        delayHideAll();
    }


    public void showAll() {
        NoEventShow = true;
        playerLockScreen.setVisibility(View.VISIBLE);
        layoutTitle.setVisibility(View.VISIBLE);
        layoutBottom.setVisibility(View.VISIBLE);
        videoBack.setVisibility(View.VISIBLE);
        mShow = true;
        delayHideAll();
    }

    public void hideAll() {
        playerLockScreen.setVisibility(View.GONE);
        layoutTitle.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.GONE);
        videoBack.setVisibility(View.GONE);
        mShow = false;
    }

    public void delayHideAll() {
        if (disposable == null) {
            disposable = RxHelper.delay(3000)
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            if (!isLock) {
                                hideAll();
                            }
                        }
                    });
            addToCompositeDis(disposable);
        } else {
            disposable.dispose();
            disposable = RxHelper.delay(3000)
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            if (!isLock) {
                                hideAll();
                            }
                        }
                    });
            addToCompositeDis(disposable);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initPlay(String s) {
        mVodPlayer = new VODPlayer();
        mVodPlayer.setGSVideoView(gsVideoView);
        mVodPlayer.setGSDocViewGx(gsDocViewGx);
        mVodPlayer.play(s, this, "", false);
    }


    @Override
    public void onBackPressed() {
        if (!isLock){
            if (!MeasureUtils.isPortrait(this)){
                MeasureUtils.toggleScreenOrientation(this);
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = MeasureUtils.dp2px(this, 200);
        } else {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        parent.setLayoutParams(params);
    }

    public void init() {
        gsDocViewGx.showFillView();
        gsDocViewGx.forbidZoomGestrue(true);
        gsVideoView.setRenderMode(GSVideoView.RenderMode.RM_FILL_XY);
        VodSite.init(this, new OnTaskRet() {
            @Override
            public void onTaskRet(boolean arg0, int arg1, String arg2) {

            }
        });

        //如果需要区分多用户，请使用带用户id的instance进行初始化，默认情况下用户id为0
        initParam();
        vodSite = new VodSite(this);
        vodSite.setVodListener(this);
        vodSite.getVodObject(initParam);
        EventBus.getDefault().register(this);
        videoTitle.setText(title);
    }

    public void initParam() {
        initParam = new InitParam();
        initParam.setServiceType(ServiceType.TRAINING);
        initParam.setDomain("bjsy.gensee.com");
        initParam.setLiveId(url);
        initParam.setNickName("word");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        VodSite.release();   //   释放
        mVodPlayer.stop();
        mVodPlayer.release();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void stopPlay() {
        if (mVodPlayer != null) {
            mVodPlayer.stop();
        }
    }

    private void release() {
        stopPlay();
        if (mVodPlayer != null) {
            mVodPlayer.release();
        }
    }


    public void toggle() {
        if (!isLock) {
            if (mShow) {
                hideAll();
            } else {
                showAll();
            }
        }
    }


    // Vod
    @Override
    public void onChatHistory(String s, List<ChatMsg> list, int i, boolean b) {

    }

    @Override
    public void onQaHistory(String s, List<QAMsg> list, int i, boolean b) {

    }

    @Override
    public void onVodErr(int errCode) {
        String msg = "";
        switch (errCode) {
            case ERR_DOMAIN:
                msg = "domain 不正确";
                break;
            case ERR_TIME_OUT:
                msg = "超时";
                break;
            case ERR_SITE_UNUSED:
                msg = "站点不可用";
                break;
            case ERR_UN_NET:
                msg = "无网络请检查网络连接";
                break;
            case ERR_DATA_TIMEOUT:
                msg = "数据过期";
                break;
            case ERR_SERVICE:
                msg = "请检查填写的serviceType";
                break;
            case ERR_PARAM:
                msg = "请检查参数";
                break;
            case -201:
                msg = "请先调用getVodObject";
                break;
            case ERR_VOD_INTI_FAIL:
                msg = "调用getVodObject失败";
                break;
            case ERR_VOD_NUM_UNEXIST:
                msg = "点播编号不存在或点播不存在";
                break;
            case ERR_VOD_PWD_ERR:
                msg = "点播密码错误";
                break;
            case ERR_VOD_ACC_PWD_ERR:
                msg = "登录帐号或登录密码错误";
                break;
            case ERR_UNSURPORT_MOBILE:
                msg = "不支持移动设备";
                break;
            default:
                break;
        }
        Log.e(TAG, "onVodErr: " + msg + "  " + errCode);
    }

    @Override
    public void onVodObject(String s) {
        vodId = s;
        EventBus.getDefault().post(s);
    }

    @Override
    public void onVodDetail(VodObject vodObject) {

    }


    // VodListener
    @Override
    public void onInit(int result, boolean haveVideo, int duration,
                       List<DocInfo> docInfos) {
        if (lastPostion >= duration - 1000) {
            lastPostion = 0;
        }
        Message message = new Message();
        message.what = MSG.MSG_ON_INIT;
        message.obj = docInfos;
        Bundle bundle = new Bundle();
        bundle.putInt(DURATION, duration);
        message.setData(bundle);
        myHandler.sendMessage(message);
    }

    @Override
    public void onPlayStop() {
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_STOP, 0));
    }

    @Override
    public void onPlayPause() {
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_PAUSE, 0));
    }

    @Override
    public void onPlayResume() {
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_RESUME, 0));
    }

    @Override
    public void onPosition(int position) {
        GenseeLog.d(TAG, "onPosition pos = " + position);
        lastPostion = position;
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_POSITION,
                position));
    }

    @Override
    public void onVideoSize(int i, int i1, int i2) {
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_VIDEOSIZE, 0));
    }

    @Override
    public void onPageSize(int position, int i1, int i2) {
        myHandler.sendMessage(myHandler
                .obtainMessage(MSG.MSG_ON_PAGE, position));
    }

    @Override
    public void onSeek(int position) {
        myHandler.sendMessage(myHandler
                .obtainMessage(MSG.MSG_ON_SEEK, position));
    }

    @Override
    public void onAudioLevel(int level) {
        myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_AUDIOLEVEL,
                level));
    }

    @Override
    public void onCaching(boolean errCode) {
    }

    @Override
    public void onVideoStart() {

    }

    @Override
    public void onChat(List<ChatMsg> list) {

    }

    @Override
    public void onDocInfo(List<DocInfo> list) {

    }

    @Override
    public void onChatCensor(String s, String s1) {

    }

    @Override
    public void onError(int errCode) {
        myHandler.sendMessage(myHandler
                .obtainMessage(MSG.MSG_ON_ERROR, errCode));
    }

    //  手势
    private void initGesture() {
        mGestureDetector = new GestureDetector(this.getApplicationContext(), this);
        am = (AudioManager) (this.getSystemService(Context.AUDIO_SERVICE));
        mMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mMaxBrightness = 255;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                endGesture(mFingerBehavior);
                break;
        }
        return true;
    }


    protected void endGesture(int behaviorType) {
        switch (behaviorType) {
            case FINGER_BEHAVIOR_BRIGHTNESS:
            case FINGER_BEHAVIOR_VOLUME:
                videoSystemOverlay.hide();
                // 音量调节
                break;
        }
    }


    protected void updateVolumeUI(int max, int progress) {
        videoSystemOverlay.show(VideoSystemOverlay.SystemType.VOLUME, max, progress);
        // sub
    }

    protected void updateLightUI(int max, int progress) {
        // sub
        videoSystemOverlay.show(VideoSystemOverlay.SystemType.BRIGHTNESS, max, progress);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //重置 手指行为
        mFingerBehavior = -1;
        mCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        try {
            mCurrentBrightness = (int) (activity.getWindow().getAttributes().screenBrightness * mMaxBrightness);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        toggle();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isLock) {
            return false;
        }
        final int width = parent.getWidth();
        final int height = parent.getHeight();
        if (width <= 0 || height <= 0) return false;

        /**
         * 根据手势起始2个点断言 后续行为. 规则如下:
         *  屏幕切分为正X:
         *  1.左右扇形区域为视频进度调节
         *  2.上下扇形区域 左半屏亮度调节 后半屏音量调节.
         */
        if (mFingerBehavior < 0) {
            float moveX = e2.getX() - e1.getX();
            float moveY = e2.getY() - e1.getY();
            if (Math.abs(moveX) >= Math.abs(moveY))
                mFingerBehavior = FINGER_BEHAVIOR_PROGRESS;
            else if (e1.getX() <= width / 2) mFingerBehavior = FINGER_BEHAVIOR_BRIGHTNESS;
            else mFingerBehavior = FINGER_BEHAVIOR_VOLUME;
        }

        switch (mFingerBehavior) {
            case FINGER_BEHAVIOR_VOLUME: { // 音量变化
                float progress = mMaxVolume * (distanceY / height) + mCurrentVolume;

                if (progress <= 0) progress = 0;
                if (progress >= mMaxVolume) progress = mMaxVolume;

                am.setStreamVolume(AudioManager.STREAM_MUSIC, Math.round(progress), 0);
                updateVolumeUI(mMaxVolume, Math.round(progress));
                mCurrentVolume = progress;
                break;
            }
            case FINGER_BEHAVIOR_BRIGHTNESS: { // 亮度变化
                try {
                    if (Settings.System.getInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE)
                            == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                        Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    }

                    int progress = (int) (mMaxBrightness * (distanceY / height) + mCurrentBrightness);

                    if (progress <= 0) progress = 0;
                    if (progress >= mMaxBrightness) progress = mMaxBrightness;

                    Window window = getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.screenBrightness = progress / (float) mMaxBrightness;
                    window.setAttributes(params);

                    updateLightUI(mMaxBrightness, progress);
                    mCurrentBrightness = progress;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
