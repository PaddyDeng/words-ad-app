package thinku.com.word.ui.report;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.EvaWordAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.EVAnswerBeen;
import thinku.com.word.bean.EvaWordBeen;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.recite.WordErrorActivity;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.StringUtils;

/**
 * Created by Administrator on 2018/4/10.
 */

public class EvaWordActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.familiar)
    LinearLayout familiar;
    @BindView(R.id.errors)
    LinearLayout errors;
    @BindView(R.id.word)
    TextView word;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.mnemonic)
    TextView mnemonic;
    @BindView(R.id.data_list)
    RecyclerView dataList;
    private EvaWordAdapter evaWordAdapter;
    private List<String> EvaWordList = new ArrayList<>();
    private String answer ;
    private Unbinder unbinder ;
    private int type  ;    //  答案是否正确   1 正确  0 错误
    private int isKnow  = 0 ;   // 单词是否认识    0  不认识  1 为认识
    private String wordId ;  //  单词id
    private String userAnswer ;  //  用户选择单词
    private String duration ; //  做题时间
    private long startDuration ;  //  开始时间
    private long endDuration ;   // 做完时间
    private MyApplication myApplication ;
    public static void start(Context context) {
        Intent intent = new Intent(context, EvaWordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eva);
        unbinder = ButterKnife.bind(this);
        initView();
        initRecy();
        myApplication = (MyApplication) MyApplication.getInstance();
        myApplication.addActivity(this);
        addNet();
    }

    public void initView(){
        familiar.setVisibility(View.GONE);
    }

    public void initRecy() {
        dataList.setLayoutManager(new LinearLayoutManager(EvaWordActivity.this));
        evaWordAdapter = new EvaWordAdapter(EvaWordActivity.this ,EvaWordList);
        evaWordAdapter.setSelectClickListener(new SelectRlClickListener() {

            @Override
            public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                EvaWordAdapter.EvaHolder evaHolder = (EvaWordAdapter.EvaHolder) viewHolder;
                if (position <EvaWordList.size()) {
                    userAnswer = EvaWordList.get(position);
                    if (EvaWordList.get(position).equals(answer)) {
                        evaHolder.error.setVisibility(View.GONE);
                        evaHolder.rl.setBackgroundResource(R.drawable.main_20round_tv);
                        type = 1 ;
                    } else {
                        evaHolder.error.setVisibility(View.VISIBLE);
                        type = 0 ;
                    }
                }else {
                    evaHolder.rl.setBackgroundResource(R.drawable.yellow_20round);
                    isKnow = 1 ;
                    userAnswer = "不认识";
                }
                referWord();
            }
        });
        dataList.setAdapter(evaWordAdapter);
    }

    /**
     *   刷新界面
     */
    public synchronized void referWord(){
            endDuration = System.currentTimeMillis();
            duration = (endDuration - startDuration) / 1000 + "";
            addToCompositeDis(HttpUtil.evAnseerObservable(wordId, type + "", userAnswer, duration, isKnow + "")
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<EVAnswerBeen>() {
                        @Override
                        public void accept(EVAnswerBeen evAnswerBeen) throws Exception {
                            dismissLoadDialog();
                            if (getHttpResSuc(evAnswerBeen.getCode())) {
                                toTast(EvaWordActivity.this, evAnswerBeen.getMessage());
                                myApplication.finishAllActivity();
                                EvaluateResultActivity.start(EvaWordActivity.this);
//                                refreshActivity();
                            } else if (evAnswerBeen.getCode() == 2) {
                                //  跳转结果页
                                myApplication.finishAllActivity();
                                EvaluateResultActivity.start(EvaWordActivity.this);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            dismissLoadDialog();
                        }
                    }));
    }

    public void refreshActivity(){
        EvaWordActivity.this.finishWithAnim();
        Intent intent = new Intent();
        intent.setClass(EvaWordActivity.this,EvaWordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 请求网络
     */
    public void addNet() {
        addToCompositeDis(HttpUtil.evaWordBeenObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<EvaWordBeen>() {
                    @Override
                    public void accept(EvaWordBeen evaWordBeen) throws Exception {
                        Log.e(TAG, "accept: " + evaWordBeen.getWords().getSelect() );
                        dismissLoadDialog();
                        startDuration = System.currentTimeMillis() ;
                        if (getHttpResSuc(evaWordBeen.getCode())) {
                            if (evaWordBeen.getWords() != null) {
                                answer = evaWordBeen.getWords().getAnswer();
                                word.setText(evaWordBeen.getWords().getWord());
                                mnemonic.setText("["+evaWordBeen.getWords().getPhonetic_uk()+"]");
                                wordId = evaWordBeen.getWords().getWordsId();
                                IMAudioManager.instance().playSound(evaWordBeen.getWords().getUk_audio(), new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                    }
                                });
                                EvaWordList.clear();
                                EvaWordList.addAll(StringUtils.spiltString(evaWordBeen.getWords().getSelect()));
                                evaWordAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    @OnClick({R.id.back ,R.id.errors})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                myApplication.finishAllActivity();
                break;
            case R.id.errors:
                toWordError();
                break;
        }
    }

    public void toWordError(){
        WordErrorActivity.start(EvaWordActivity.this ,wordId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
