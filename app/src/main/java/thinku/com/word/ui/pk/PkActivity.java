package thinku.com.word.ui.pk;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.PKAdapter;
import thinku.com.word.adapter.been.PKSelctBeen;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.PkingData;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.callback.SelectAnswerClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.LoadingCustomView;

import static thinku.com.word.utils.C.PKING;
import static thinku.com.word.utils.C.PK_TYPE_CURRENT;
import static thinku.com.word.utils.C.PK_TYPE_ERROR;

public class PkActivity extends BaseActivity {
    private static final String TAG = PkActivity.class.getSimpleName();
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.userCorrectRate)
    TextView userCorrectRate;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.rivalImage)
    CircleImageView rivalImage;
    @BindView(R.id.rivalCurrentRate)
    TextView rivalCurrentRate;
    @BindView(R.id.pk_progress)
    LoadingCustomView pkProgress;
    @BindView(R.id.word)
    TextView word;
    @BindView(R.id.pk_rl)
    RecyclerView pkRl;
    @BindView(R.id.userCurrent)
    TextView userCurrent;
    @BindView(R.id.rivalCurrent)
    TextView rivalCurrent;
    @BindView(R.id.pk_wait_animator)
    ImageView pkWaitAnimator;
    @BindView(R.id.pk_wait_rl)
    AutoRelativeLayout pkWaitRl;

    private EventPkListData eventPkListData;
    private int totalId;
    private List<EventPkListData.WordsBean> wordsBeanList;
    private static int wordIndex = 0;
    private String userImg;
    private String matchImg;
    private PkingData pkingData;
    private PkingData.UserBean mySelfUser;
    private PkingData.UserBean mathUser;
    private int userAccuracy;  //  自己的正确率
    private int mathAccuracy;  //  对手的正确率
    private List<PKSelctBeen> pkSelectBeenList;
    private PKAdapter pkAdapter;
    private EventPkListData.WordsBean wordsBeen;
    private int userNum;   //  自己的做题进度
    private int mathNum;   //  对手的做题进度
    private String wordsId;
    private String answer;
    private int type;
    private int duration;
    private static Disposable timePosable;  //  倒计时  ；
    private float currentNum = 0f;   // 正确的个数
    private String mySelfUid;
    private String matchUid;
    private ValueAnimator valueAnimator;
    private boolean isOne;
    private boolean isClick = true;
    private int[] waitAnimator = new int[]{
            R.mipmap.pk_wait_0, R.mipmap.pk_wait_1, R.mipmap.pk_wait_2, R.mipmap.pk_wait_3};

    public static void start(Context context, EventPkListData eventPkListData) {
        Intent intent = new Intent(context, PkActivity.class);
        intent.putExtra("data", eventPkListData);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk);
        ButterKnife.bind(this);
        try {
            userImg = SharedPreferencesUtils.getImage(PkActivity.this);
            matchImg = SharedPreferencesUtils.getPKMatchImage(PkActivity.this);
            eventPkListData = getIntent().getParcelableExtra("data");
            matchUid = SharedPreferencesUtils.getPKMatchUid(PkActivity.this);
            mySelfUid = SharedPreferencesUtils.getUid(PkActivity.this);
            new GlideUtils().loadCircle(PkActivity.this, NetworkTitle.WORDRESOURE + userImg, userImage);
            new GlideUtils().loadCircle(PkActivity.this, NetworkTitle.WORDRESOURE + matchImg, rivalImage);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void init() {
        EventBus.getDefault().register(PkActivity.this);
        pkSelectBeenList = new ArrayList<>();
        pkAdapter = new PKAdapter(this, pkSelectBeenList);
        pkRl.setLayoutManager(new LinearLayoutManager(this));
        pkRl.setAdapter(pkAdapter);
        pkAdapter.setSelectRlClickListener(new SelectAnswerClickListener() {
            @Override
            public void onClick(int position, View view, int AnswerPosition) {
                if (isClick) {
                    if (timePosable != null) timePosable.dispose();
                    isOne = true;
                    if (position != AnswerPosition && AnswerPosition != -1) {
                        pkSelectBeenList.get(position).setChose(true);
                        pkSelectBeenList.get(AnswerPosition).setChose(true);
                        type = PK_TYPE_ERROR;
                    } else {
                        currentNum++;
                        type = PK_TYPE_CURRENT;
                        pkSelectBeenList.get(AnswerPosition).setChose(true);

                    }
                    pkAdapter.notifyDataSetChanged();
                    addToCompositeDis(HttpUtil.pkAnswerObservable(totalId + "", wordsId, answer, type + "", duration + "")
                            .subscribe(new Consumer<ResultBeen<Void>>() {
                                @Override
                                public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                                }
                            }));
                    addToCompositeDis(RxHelper.delay(300)
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
                                    pkNext(false);
                                }
                            }));
                    isClick = false;
                }
            }
        });
        initEventPkData();
    }

    /**
     * pk中下一个单词
     */
    public void pkNext(Boolean isFinsh) {
        Log.e(TAG, "pkNext: ");
        isClick = true;
        if (timePosable != null) timePosable.dispose();
        if (isFinsh) {
            addToCompositeDis(HttpUtil.pkAnswerObservable(totalId + "", wordsId, "", PK_TYPE_ERROR + "", duration + "")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {

                        }
                    }));
        }
        wordIndex++;
        if (wordIndex <= (wordsBeanList.size() - 1)) {
            referUI(wordsBeanList.get(wordIndex));
            referLoadPKUi();
        } else {
            isClick = false;
            addToCompositeDis(HttpUtil.pkFinshObservable(matchUid, totalId + "")
                    .subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            if (getHttpResSuc(voidResultBeen.getCode())) {
                                if (MyApplication.mediaPlayer != null && MyApplication.mediaPlayer.isPlaying()) {
                                    MyApplication.mediaPlayer.stop();
                                }
                                PKResultActivity.start(PkActivity.this, matchUid, totalId + "");
                                PkActivity.this.finishWithAnim();

                            } else {
                                waiteMatch();
                            }
                        }
                    }));
        }
    }

    /**
     * 等待对手完成
     */
    public void waiteMatch() {
        if (timePosable != null) timePosable.dispose();
        pkWaitRl.setVisibility(View.VISIBLE);
        pkRl.setVisibility(View.GONE);
        word.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 4).setDuration(2000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    pkWaitAnimator.setImageResource(waitAnimator[i % waitAnimator.length]);
                }
            });
        }
        valueAnimator.start();


        netPoll();

    }

    //  每隔两秒循环请求接口，获取结果
    public void netPoll() {
        addToCompositeDis(HttpUtil.pkPollObservable(matchUid, totalId + "")
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())) {
                            PKResultActivity.start(PkActivity.this, matchUid, totalId + "");
                            PkActivity.this.finishWithAnim();
                            if (MyApplication.mediaPlayer != null && MyApplication.mediaPlayer.isPlaying()) {
                                MyApplication.mediaPlayer.stop();
                            }
                        } else {
                            addToCompositeDis(RxHelper.delay(2000)
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(@NonNull Integer integer) throws Exception {
                                            netPoll();
                                        }
                                    }));
                        }
                    }
                }));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JPushData jPushData) {
        int type = jPushData.getType();
        switch (type) {
            case PKING:
                pkingData = (PkingData) jPushData.getMessage();
                referPkUi(pkingData);
                break;
        }
    }


    /**
     * 刷新自己以及对手做题进度
     *
     * @param pkingdata
     */
    public void referPkUi(PkingData pkingdata) {
        setMySelfUser(pkingdata);
        if (wordsBeanList != null && wordsBeanList.size() > 0) {
            rivalCurrent.setText("进度：" + (Integer.parseInt(mathUser.getNum())) + "/" + wordsBeanList.size());
        }
        mathAccuracy = mathUser.getAccuracy();
        referAccuracy();
    }


    /**
     * 本地更新pk进度ui
     */
    public void referLoadPKUi() {
        userCurrent.setText("进度：" + (userNum) + "/" + wordsBeanList.size());
        if (wordIndex > 0) {
            userAccuracy = Math.round((currentNum / wordIndex) * 100);
        }
        referAccuracy();
    }


    /**
     * 根据uid 确定选手位置  uid  == 存储uid   是本人  否则 是对手
     *
     * @param eventPkData
     */
    public void setMySelfUser(PkingData eventPkData) {
        if (mySelfUid.equals(eventPkData.getUser1().getUid())) {
            mySelfUser = eventPkData.getUser1();
            mathUser = eventPkData.getUser2();
        } else {
            mySelfUser = eventPkData.getUser2();
            mathUser = eventPkData.getUser1();
        }
    }

    /**
     * 根据自己和对手的正确率的比来刷新做题进度
     */
    public void referAccuracy() {
        float toatlPercent = userAccuracy + mathAccuracy;
        if (toatlPercent == 0) {
            userCorrectRate.setText("0%");
            rivalCurrentRate.setText("0%");
            pkProgress.setProgress(50);
        } else {
            NumberFormat nf = NumberFormat.getPercentInstance();
            float userRate = Math.round((userAccuracy / (userAccuracy + mathAccuracy)) * 100);
            userCorrectRate.setText(userAccuracy + "%");
            rivalCurrentRate.setText(mathAccuracy + "%");
            pkProgress.setProgress(userRate);
        }
    }

    public void initEventPkData() {
        totalId = eventPkListData.getTotalId();
        wordsBeanList = eventPkListData.getWords();
        if (wordsBeanList != null && wordsBeanList.size() > 0)
            referUI(wordsBeanList.get(wordIndex));
    }

    public void referUI(final EventPkListData.WordsBean wordsBean) {
        if (timePosable != null) timePosable.dispose();
        isOne = false;
        timer.setText("10s");
        timePosable = RxHelper.countDown(10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
                if (integer > 1) {
                    duration = 9 - integer;
                    timer.setText((integer - 1) + "s");
                } else {
                    if (!isOne) {
                        pkNext(true);
                    }
                }
            }
        });
        addToCompositeDis(timePosable);
        wordsId = wordsBean.getWordsId();
        answer = wordsBean.getAnswer();
        userNum = (wordIndex + 1);
        this.wordsBeen = wordsBean;
        word.setText(wordsBean.getWord());
        pkSelectBeenList.clear();
        for (String select : StringUtils.spiltString(wordsBean.getSelect())) {
            PKSelctBeen pkSelectBeene = new PKSelctBeen();
            pkSelectBeene.setSelect(select);
            pkSelectBeene.setChose(false);
            if (wordsBean.getAnswer().equals(select)) {
                pkSelectBeene.setAnswer(true);
            }
            pkSelectBeenList.add(pkSelectBeene);
        }
        pkAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(PkActivity.this);
        wordIndex = 0;
        timePosable = null;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }


}
