package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;
import thinku.com.word.adapter.PKAdapter;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.PkingData;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.LoadingCustomView;

import static thinku.com.word.utils.C.PKING;
import static thinku.com.word.utils.C.PK_READY_SUCCESS;

public class PkActivity extends AppCompatActivity {
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
    private String uid ;
    private PkingData.UserBean mySelfUser ;
    private PkingData.UserBean mathUser ;
    private String userAccuracy ;  //  自己的正确率
    private String mathAccuracy ;  //  对手的正确率
    private List<String> stringList ;
    private PKAdapter  pkAdapter ;
    private EventPkListData.WordsBean wordsBeen ;
    public static void start(Context context, String userImg, String matchImg) {
        Intent intent = new Intent(context, PkActivity.class);
        intent.putExtra("userImg", userImg);
        intent.putExtra("matchImg", matchImg);
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
            new GlideUtils().load(PkActivity.this, NetworkTitle.WORDRESOURE + userImg, userImage);
            new GlideUtils().load(PkActivity.this, NetworkTitle.WORDRESOURE + matchImg, rivalImage);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }


    public void init() {
        EventBus.getDefault().register(PkActivity.this);
        uid = SharedPreferencesUtils.getString("uid",PkActivity.this);
        pkAdapter = new PKAdapter(this ,stringList);
        pkRl.setLayoutManager(new LinearLayoutManager(this));
        pkRl.setAdapter(pkAdapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JPushData jPushData) {
        int type = jPushData.getType();
        switch (type) {
            case PK_READY_SUCCESS:
                eventPkListData = (EventPkListData) jPushData.getMessage();
                initEventPkData();
                break;
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
            userCurrent.setText("进度：" + (Integer.parseInt(mySelfUser.getNum()) + 1) +"/" + wordsBeanList.size());
            rivalCurrent.setText("进度：" + (Integer.parseInt(mathUser.getNum()) + 1) +"/" + wordsBeanList.size());
        }
        referAccuracy(mySelfUser.getAccuracy() ,mathUser.getAccuracy());
    }


    /**
     * 根据uid 确定选手位置  uid  == 存储uid   是本人  否则 是对手
     * @param eventPkData
     */
    public void setMySelfUser(PkingData eventPkData){
        if (uid.equals(eventPkData.getUser1().getUid())){
            mySelfUser = eventPkData.getUser1();
            mathUser = eventPkData.getUser2();
        }else{
            mySelfUser = eventPkData.getUser2();
            mathUser = eventPkData.getUser1();
        }
    }

    /**
     *  刷新做题进度
     * @param userPercent   自己做题正确率
     * @param matchPercent  对手做题正确率
     */
    public void referAccuracy(float userPercent , float matchPercent){
        float toatlPercent = userPercent + matchPercent ;
        if (toatlPercent == 0){
            userCorrectRate.setText("50%");
            rivalCurrent.setText("50");
            pkProgress.setProgress(50);
        }else{
            NumberFormat nf   =   NumberFormat.getPercentInstance();
            float userRate = Math.round(userPercent / (userPercent + matchPercent) * 100);
            userCorrectRate.setText( userRate+"%");
            rivalCurrent.setText((100 - userRate) +"%");
            pkProgress.setProgress(userPercent);
        }
    }

    public void initEventPkData() {
        totalId = eventPkListData.getTotalId();
        wordsBeanList = eventPkListData.getWords();

        referUI(wordsBeanList.get(wordIndex));
    }

    public void referUI(final EventPkListData.WordsBean wordsBean) {
        this.wordsBeen = wordsBean ;
        word.setText(wordsBean.getWord());
        wordTranslate.setText(wordsBean.getPhonetic_uk());
        stringList.clear();
        stringList.addAll(StringUtils.spiltString(wordsBean.getSelect()));
        pkAdapter.setSelectRlClickListener(new SelectRlClickListener() {
            @Override
            public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                if (wordsBean.getAnswer().equals(stringList.get(position))){
                    view.setBackgroundResource(R.drawable.main_20round_tv);
                }else{
                    view.setBackgroundResource(R.drawable.red_20round);
                }

            }
        });
        pkAdapter.notifyDataSetChanged();

    }


    @OnClick(R.id.music_rl)
    public void music(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(PkActivity.this);
    }


}
