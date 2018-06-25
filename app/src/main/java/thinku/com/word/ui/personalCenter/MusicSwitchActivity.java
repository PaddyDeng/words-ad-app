package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.utils.SharedPreferencesUtils;

public class MusicSwitchActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.home_music_switch)
    SwitchView homeMusicSwitch;
    @BindView(R.id.home_music)
    RelativeLayout homeMusic;
    @BindView(R.id.recite_music_switch)
    SwitchView reciteMusicSwitch;
    @BindView(R.id.recite_rl)
    RelativeLayout reciteRl;
    @BindView(R.id.eva_music_switch)
    SwitchView evaMusicSwitch;
    @BindView(R.id.eva_music)
    RelativeLayout evaMusic;
    @BindView(R.id.pk_result_music_switch)
    SwitchView pkResultMusicSwitch;
    @BindView(R.id.pk_result_music)
    RelativeLayout pkResultMusic;
    @BindView(R.id.pk_bg_music_switch)
    SwitchView pkBgMusicSwitch;
    @BindView(R.id.pk_bg_music)
    RelativeLayout pkBgMusic;
    @BindView(R.id.auto_music_switch)
    SwitchView autoMusicSwitch;
    @BindView(R.id.auto_music)
    RelativeLayout autoMusic;

    private boolean homePlay , recitePlay ,evaPlay ,pkResultPlay ,pkBgPlay ,AutoPlay ;
    public static void start(Context c) {
        Intent intent = new Intent(c, MusicSwitchActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_switch);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titleT.setText("音效开关");
        homePlay = SharedPreferencesUtils.getHomeMusic(this);
        recitePlay = SharedPreferencesUtils.getPlayMusic(this);
        evaPlay = SharedPreferencesUtils.getEvaMusic(this);
        pkResultPlay = SharedPreferencesUtils.getPkResultMusic(this);
        pkBgPlay = SharedPreferencesUtils.getPkBgMusic(this);
        AutoPlay = SharedPreferencesUtils.getAutoPlayMusic(this);
        homeMusicSwitch.setOpened(homePlay);
        reciteMusicSwitch.setOpened(recitePlay);
        evaMusicSwitch.setOpened(evaPlay);
        pkResultMusicSwitch.setOpened(pkResultPlay);
        pkBgMusicSwitch.setOpened(pkBgPlay);
        autoMusicSwitch.setOpened(AutoPlay);
        switchButton();
    }

    @OnClick({R.id.back})
    public void back(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishWithAnim();
                break;
        }
    }
    public void switchButton(){
                homeMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setHomeMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setHomeMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });
                reciteMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setPlayMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setPlayMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });
                evaMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setEvaMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setEvaMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });
                pkResultMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setPkResultMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setPkResultMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });

                pkBgMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setPkBgMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setPkBgMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });
                autoMusicSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                    @Override
                    public void toggleToOn(SwitchView view) {
                        SharedPreferencesUtils.setAutoPlayMusic(MusicSwitchActivity.this ,true);
                        view.setOpened(true);
                    }

                    @Override
                    public void toggleToOff(SwitchView view) {
                        SharedPreferencesUtils.setAutoPlayMusic(MusicSwitchActivity.this ,false);
                        view.setOpened(false);
                    }
                });
        }
}
