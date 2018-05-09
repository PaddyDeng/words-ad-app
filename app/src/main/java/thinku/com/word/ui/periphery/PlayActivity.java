package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gensee.common.ServiceType;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.DocInfo;
import com.gensee.entity.InitParam;
import com.gensee.entity.QAMsg;
import com.gensee.entity.VodObject;
import com.gensee.media.VODPlayer;
import com.gensee.view.GSDocViewGx;
import com.gensee.view.GSVideoView;
import com.gensee.vod.VodSite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import thinku.com.word.R;
import thinku.com.word.base.BaseNoImmActivity;

public class PlayActivity extends BaseNoImmActivity implements VodSite.OnVodListener ,VODPlayer.OnVodPlayListener {

    @BindView(R.id.gsDocViewGx)
    GSDocViewGx gsDocViewGx;
    @BindView(R.id.gsVideoView)
    GSVideoView gsVideoView;
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
    private String url;
    private VodSite vodSite;
    private InitParam initParam;
    private String vodId;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        try {
            url = getIntent().getStringExtra("url");
        } catch (Exception e) {

        }
        init();
        initPlay();
    }

    public void initPlay() {
        VODPlayer player = new VODPlayer();
        player.setGSVideoView(gsVideoView);
        player.setGSDocViewGx(gsDocViewGx);
        player.play(vodId ,this ,"" ,false);
    }

    public void init() {
        VodSite.init(this, null);     //建议在app启动时优先调用
        vodSite = new VodSite(this);
        vodSite.setVodListener(this);
        initParam();
        vodSite.getVodObject(initParam);
    }

    public void initParam() {
        initParam = new InitParam();
        initParam.setServiceType(ServiceType.TRAINING);
        initParam.setDomain("bjsy.gensee.com");
        initParam.setNumber(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VodSite.release();   //   释放
    }

    // Vod
    @Override
    public void onChatHistory(String s, List<ChatMsg> list, int i, boolean b) {

    }

    @Override
    public void onQaHistory(String s, List<QAMsg> list, int i, boolean b) {

    }

    @Override
    public void onVodErr(int i) {

    }

    @Override
    public void onVodObject(String s) {
        vodId = s;
    }

    @Override
    public void onVodDetail(VodObject vodObject) {

    }


    // VODLSITEENR
    @Override
    public void onInit(int i, boolean b, int i1, List<DocInfo> list) {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayResume() {

    }

    @Override
    public void onPosition(int i) {

    }

    @Override
    public void onVideoSize(int i, int i1, int i2) {

    }

    @Override
    public void onPageSize(int i, int i1, int i2) {

    }

    @Override
    public void onSeek(int i) {

    }

    @Override
    public void onAudioLevel(int i) {

    }

    @Override
    public void onCaching(boolean b) {

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
    public void onError(int i) {

    }
}
