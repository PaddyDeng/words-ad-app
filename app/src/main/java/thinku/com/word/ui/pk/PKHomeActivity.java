package thinku.com.word.ui.pk;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.EventPkData;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.recite.MyPlanActivity;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

import static thinku.com.word.utils.C.PK_AGREE;
import static thinku.com.word.utils.C.PK_CANCEL;
import static thinku.com.word.utils.C.PK_MATCH_CANCLE;
import static thinku.com.word.utils.C.PK_MATCH_SUCCESS;
import static thinku.com.word.utils.C.PK_READY_SUCCESS;

public class PKHomeActivity extends BaseActivity {
    private static final String TAG = PKHomeActivity.class.getSimpleName();
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.middle_2)
    View middle2;
    @BindView(R.id.timer_rl)
    LinearLayout timerRl;
    private int[] match_ing = new int[]{
            R.mipmap.pk_match_0, R.mipmap.pk_match_1, R.mipmap.pk_match_2, R.mipmap.pk_match_3
    };
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.words_1)
    TextView words1;
    @BindView(R.id.pk_lose_img)
    ImageView pkLoseImg;
    @BindView(R.id.lose_num_1)
    TextView loseNum1;
    @BindView(R.id.win_num_1)
    TextView winNum1;
    @BindView(R.id.pk_win_img)
    ImageView pkWinImg;
    @BindView(R.id.img_1)
    CircleImageView img1;
    @BindView(R.id.name_1)
    TextView name1;
    @BindView(R.id.match_success)
    ImageView matchSuccess;
    @BindView(R.id.words_2)
    TextView words2;
    @BindView(R.id.pk_lose_img_1)
    ImageView pkLoseImg1;
    @BindView(R.id.lose_num_2)
    TextView loseNum2;
    @BindView(R.id.win_num_2)
    TextView winNum2;
    @BindView(R.id.pk_win_img_1)
    ImageView pkWinImg1;
    @BindView(R.id.img_2)
    CircleImageView img2;
    @BindView(R.id.name_2)
    TextView name2;
    @BindView(R.id.review_match)
    TextView reviewMatch;
    @BindView(R.id.pking)
    TextView pking;
    Unbinder unbinder;
    @BindView(R.id.words_rl)
    LinearLayout wordsRl;
    @BindView(R.id.words_r2)
    LinearLayout wordsR2;
    @BindView(R.id.pk_num_rl_1)
    RelativeLayout pkNumRl1;
    @BindView(R.id.pk_num_rl_2)
    RelativeLayout pkNumRl2;
    @BindView(R.id.pk_button)
    RelativeLayout pkButton;

    private EventPkListData eventPkListData;
    private ValueAnimator valueAnimator;
    private String mySelfUid;
    private String matchUid;

    private Disposable disposable ;
    public static void start(Context context) {
        Intent intent = new Intent(context, PKHomeActivity.class);
        context.startActivity(intent);
    }


    private EventPkData.UserBean mySelfUser;
    private EventPkData.UserBean mathUser;
    private String uid;
    private static boolean isInit = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkhome);
        unbinder = ButterKnife.bind(this);
        init();
        if (MyApplication.mediaPlayer == null){
            MyApplication.mediaPlayer = MediaPlayer.create(this , R.raw.pk_bg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            if (!MyApplication.mediaPlayer.isPlaying()) {
                MyApplication.mediaPlayer.start();
            }
    }

    public void init() {
        hideAll();
        EventBus.getDefault().register(this);
        uid = SharedPreferencesUtils.getString("uid", PKHomeActivity.this);
        addNet();
    }


    private void addNet() {
        addToCompositeDis(HttpUtil.pkMatchObservable()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())) {

                        }
                    }
                }));
    }

    @OnClick({R.id.back, R.id.review_match, R.id.pking})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finishWithAnim();
                if (MyApplication.mediaPlayer != null && MyApplication.mediaPlayer.isPlaying()){
                    MyApplication.mediaPlayer.stop();
                }
                break;
            case R.id.review_match:
                pkChose(PK_CANCEL);
                break;
            case R.id.pking:
                pkChose(PK_AGREE);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JPushData jPushData) {
        int type = jPushData.getType();
        switch (type) {
            case PK_MATCH_SUCCESS:
                showAll((EventPkData) jPushData.getMessage());
                break;
            case PK_MATCH_CANCLE:
                hideAll();
                addNet();
                break;
            case PK_READY_SUCCESS:
                eventPkListData = (EventPkListData) jPushData.getMessage();
                toPking(eventPkListData);
                break;
        }
    }

    /**
     * 同意开始 去pk界面
     */
    public void toPking(EventPkListData eventPkListData) {
        PkActivity.start(PKHomeActivity.this, eventPkListData);
        this.finishWithAnim();
    }

    /**
     * 展示所有界面
     */
    private void showAll(EventPkData eventPkData) {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (eventPkData != null) {
            setMySelfUser(eventPkData);
            SharedPreferencesUtils.setPk(PKHomeActivity.this, mathUser);
            mySelfUid = mySelfUser.getUid();
            matchUid = mathUser.getUid();
            words1.setText(mathUser.getWords());
            winNum1.setText("win：" + mathUser.getWin());
            loseNum1.setText("lose：" + mathUser.getLose());
            new GlideUtils().loadCircle(PKHomeActivity.this, NetworkTitle.WORDRESOURE + mathUser.getImage(), img1);
            name1.setText(mathUser.getNickname());
            words2.setText(mySelfUser.getWords());
            winNum2.setText("win：" + mySelfUser.getWin());
            loseNum2.setText("lose：" + mySelfUser.getLose());
            new GlideUtils().loadCircle(PKHomeActivity.this, NetworkTitle.WORDRESOURE + mySelfUser.getImage(), img2);
            name2.setText(mySelfUser.getNickname());
            words1.setVisibility(View.VISIBLE);
            wordsRl.setVisibility(View.VISIBLE);
            pkNumRl1.setVisibility(View.VISIBLE);
            name1.setVisibility(View.VISIBLE);
            wordsR2.setVisibility(View.VISIBLE);
            words2.setVisibility(View.VISIBLE);
            pkNumRl2.setVisibility(View.VISIBLE);
            name2.setVisibility(View.VISIBLE);
            pkButton.setVisibility(View.VISIBLE);
            matchSuccess.setImageResource(R.mipmap.match_success);
            timerRl.setVisibility(View.VISIBLE);
            disposable = RxHelper.countDown(15)
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            timer.setText("倒计时：" +integer+ "s");
                            Log.e(TAG, "accept: " + integer );
                            if (integer == 0){
                               pkChose(PK_CANCEL);
                            }
                        }
                    });
            addToCompositeDis(disposable);
        }
    }

    /**
     * pk选择
     */
    public void pkChose(final int type) {
        addToCompositeDis(HttpUtil.pkChoseObservable(mathUser.getUid(), type + "")
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())) {
                            if (type == PK_AGREE) {
                                toTast(PKHomeActivity.this, "等待对手开始");
                            } else {
                                hideAll();
                                addNet();
                            }
                        }
                    }
                }));
    }

    /**
     * 根据uid 确定选手位置  uid  == 存储uid   是本人  否则 是对手
     *
     * @param eventPkData
     */
    public void setMySelfUser(EventPkData eventPkData) {
        if (uid.equals(eventPkData.getUser1().getUid())) {
            mySelfUser = eventPkData.getUser1();
            mathUser = eventPkData.getUser2();
        } else {
            mySelfUser = eventPkData.getUser2();
            mathUser = eventPkData.getUser1();
        }
    }

    /**
     * 匹配中界面 隐藏界面
     */
    private void hideAll() {
        words1.setVisibility(View.INVISIBLE);
        wordsRl.setVisibility(View.INVISIBLE);
        pkNumRl1.setVisibility(View.INVISIBLE);
        name1.setVisibility(View.INVISIBLE);
        wordsR2.setVisibility(View.INVISIBLE);
        words2.setVisibility(View.INVISIBLE);
        pkNumRl2.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        pkButton.setVisibility(View.GONE);
        timerRl.setVisibility(View.GONE);
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 4).setDuration(2000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    matchSuccess.setImageResource(match_ing[i % match_ing.length]);
                }
            });
        }
        valueAnimator.start();
        if (disposable != null){
            disposable.dispose();
            disposable = null ;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        cancleAnimator();
        EventBus.getDefault().unregister(this);
        if (disposable != null){
            disposable.dispose();
            disposable = null ;
        }

    }


    /**
     * 暂停动画
     */
    public void cancleAnimator() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }
}
