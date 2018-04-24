package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.PKAdapter;
import thinku.com.word.adapter.been.PKSelctBeen;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.PkingData;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.callback.SelectAnswerClickListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.LoadingCustomView;

import static thinku.com.word.utils.C.PKING;
import static thinku.com.word.utils.C.PK_READY_SUCCESS;
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
    @BindView(R.id.music)
    ImageView music;
    @BindView(R.id.word_translate)
    TextView wordTranslate;
    @BindView(R.id.pk_rl)
    RecyclerView pkRl;
    @BindView(R.id.userCurrent)
    TextView userCurrent;
    @BindView(R.id.rivalCurrent)
    TextView rivalCurrent;

    private EventPkListData eventPkListData;
    private int totalId;
    private List<EventPkListData.WordsBean> wordsBeanList;
    private static int wordIndex = 0;
    private String userImg;
    private String matchImg;
    private PkingData pkingData;
    private PkingData.UserBean mySelfUser ;
    private PkingData.UserBean mathUser ;
    private int userAccuracy ;  //  自己的正确率
    private int mathAccuracy ;  //  对手的正确率
    private List<PKSelctBeen> pkSelectBeenList ;
    private PKAdapter  pkAdapter ;
    private EventPkListData.WordsBean wordsBeen ;
    private int userNum ;   //  自己的做题进度
    private int mathNum ;   //  对手的做题进度
    private String wordsId ;
    private String answer ;
    private int type ;
    private int duration ;
    private static Disposable timePosable ;  //  倒计时  ；
    private float currentNum = 0f ;   // 正确的个数
    private String mySelfUid ;
    private String matchUid ;
    public static void start(Context context, String userImg, String matchImg ,EventPkListData eventPkListData ,String mySelfUid , String matchUid) {
        Intent intent = new Intent(context, PkActivity.class);
        intent.putExtra("data" ,eventPkListData);
        intent.putExtra("userImg", userImg);
        intent.putExtra("matchImg", matchImg);
        intent.putExtra("mySelfUid" ,mySelfUid);
        intent.putExtra("matchUid" ,matchUid);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk);
        ButterKnife.bind(this);
        try {
            userImg = getIntent().getStringExtra("userImg");
            matchImg = getIntent().getStringExtra("matchImg");
            eventPkListData = getIntent().getParcelableExtra("data");
            matchUid = getIntent().getStringExtra("matchUid");
            mySelfUid = getIntent().getStringExtra("mySelfUid");
            new GlideUtils().load(PkActivity.this, NetworkTitle.WORDRESOURE + userImg, userImage);
            new GlideUtils().load(PkActivity.this, NetworkTitle.WORDRESOURE + matchImg, rivalImage);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
        init();
    }


    public void init() {
        EventBus.getDefault().register(PkActivity.this);
        pkSelectBeenList = new ArrayList<>();
        pkAdapter = new PKAdapter(this ,pkSelectBeenList);
        pkRl.setLayoutManager(new LinearLayoutManager(this));
        pkRl.setAdapter(pkAdapter);
        pkAdapter.setSelectRlClickListener(new SelectAnswerClickListener() {
            @Override
            public void onClick(int position, View view, int AnswerPosition) {
                timePosable.dispose();
                if (position != AnswerPosition) {
                    pkSelectBeenList.get(position).setChose(true);
                    pkSelectBeenList.get(AnswerPosition).setChose(true);
                    type = PK_TYPE_ERROR;
                    currentNum ++ ;
                }else{
                    type = PK_TYPE_CURRENT ;
                    pkSelectBeenList.get(AnswerPosition).setChose(true);

                }
                pkAdapter.notifyDataSetChanged();
                addToCompositeDis(HttpUtil.pkAnswerObservable(totalId+"" ,wordsId ,answer ,type +"" ,duration+"")
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                    }
                }));
                addToCompositeDis(RxHelper.delay(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        pkNext();
                    }
                }));
            }
        });
        initEventPkData();
    }

    /**
     * pk中下一个单词
     */
    public void pkNext(){
        if (wordIndex <= wordsBeanList.size()) {
            wordIndex++;
            referUI(wordsBeanList.get(wordIndex));
            referLoadPKUi();
        }else{
            addToCompositeDis(HttpUtil.pkFinshObservable(matchUid ,totalId+"")
            .subscribe(new Consumer<ResultBeen<Void>>() {
                @Override
                public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                    if (getHttpResSuc(voidResultBeen.getCode())){
                        // TODO: 2018/4/24  跳转
                    }else{
                      waiteMatch();
                    }
                }
            }));
        }
    }

    /**
     * 等待对手完成
     */
    public void waiteMatch(){

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
     * @param pkingdata
     */
    public void referPkUi(PkingData pkingdata) {
        setMySelfUser(pkingdata);
        if (wordsBeanList != null && wordsBeanList.size() > 0){
            rivalCurrent.setText("进度：" + (Integer.parseInt(mathUser.getNum()) ) +"/" + wordsBeanList.size());
        }
        userAccuracy = mySelfUser.getAccuracy();
        mathAccuracy = mathUser.getAccuracy();
        referAccuracy();
    }


    /**
     *  本地更新pk进度ui
     */
    public void referLoadPKUi(){
        userCurrent.setText("进度：" + (userNum) +"/" + wordsBeanList.size());
        if (wordIndex >0) {
            userAccuracy = Math.round((currentNum / wordIndex) * 100);
        }
        referAccuracy();
    }


    /**
     * 根据uid 确定选手位置  uid  == 存储uid   是本人  否则 是对手
     * @param eventPkData
     */
    public void setMySelfUser(PkingData eventPkData){
        if (mySelfUid.equals(eventPkData.getUser1().getUid())){
            mySelfUser = eventPkData.getUser1();
            mathUser = eventPkData.getUser2();
        }else{
            mySelfUser = eventPkData.getUser2();
            mathUser = eventPkData.getUser1();
        }
    }

    /**
     *  根据自己和对手的正确率的比来刷新做题进度
     */
    public void referAccuracy(){
        float toatlPercent = userAccuracy + mathAccuracy ;
        if (toatlPercent == 0){
            userCorrectRate.setText("50%");
            rivalCurrentRate.setText("50");
            pkProgress.setProgress(50);
        }else{
            NumberFormat nf   =   NumberFormat.getPercentInstance();
            float userRate = Math.round((userAccuracy / (userAccuracy + mathAccuracy)) * 100);
            userCorrectRate.setText( userRate+"%");
            rivalCurrentRate.setText((100 - userRate) +"%");
            pkProgress.setProgress(userRate);
        }
    }

    public void initEventPkData() {
        totalId = eventPkListData.getTotalId();
        wordsBeanList = eventPkListData.getWords();
        referUI(wordsBeanList.get(wordIndex));
    }

    public void referUI(final EventPkListData.WordsBean wordsBean) {
        timer.setText("10s");
        timePosable = RxHelper.countDown(10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                if (integer >=0) {
                    duration = 10 - integer;
                    timer.setText(integer + "s");
                }else{
                    pkNext();
                }
            }
        });
        addToCompositeDis(timePosable);
        wordsId = wordsBean.getWordsId() ;
        answer = wordsBean.getAnswer();
        userNum = (wordIndex + 1) ;
        this.wordsBeen = wordsBean ;
        word.setText(wordsBean.getWord());
        wordTranslate.setText(wordsBean.getPhonetic_uk());
        pkSelectBeenList.clear();
        for (String select : StringUtils.spiltString(wordsBean.getSelect())){
            PKSelctBeen pkSelectBeene = new PKSelctBeen();
            pkSelectBeene.setSelect(select);
            pkSelectBeene.setChose(false);
            if (wordsBean.getAnswer().equals(select)){
                pkSelectBeene.setAnswer(true);
            }
            pkSelectBeenList.add(pkSelectBeene);
        }
        pkAdapter.notifyDataSetChanged();
    }



    @OnClick(R.id.music_rl)
    public void music(){
        IMAudioManager.instance().playSound(wordsBeen.getUk_audio(), new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(PkActivity.this);
        wordIndex =  0 ;
        timePosable = null ;
    }



}
