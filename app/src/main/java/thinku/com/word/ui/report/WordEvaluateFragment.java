package thinku.com.word.ui.report;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordAdapter;
import thinku.com.word.adapter.ReciteWordParentAdapter;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ReciteWordParent;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.WordEvaluateEvent;
import thinku.com.word.bean.WordReviewTodayBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.recite.WordErrorActivity;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.C;
import thinku.com.word.view.SuccessDialog;

/**
 * Created by Administrator on 2018/2/22.
 */

public class WordEvaluateFragment extends BaseFragmentActivitiy {
    private static final String TAG = WordEvaluateFragment.class.getSimpleName();
    @BindView(R.id.newWord)
    TextView newWord;
    @BindView(R.id.click)
    ImageView click;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;
    @BindView(R.id.content_hide)
    LinearLayout contentHide;
    @BindView(R.id.top)
    ImageView top;
    @BindView(R.id.content_show)
    ScrollView contentShow;
    @BindView(R.id.know)
    Button know;
    @BindView(R.id.unknow)
    Button unknow;
    @BindView(R.id.blurry)
    Button blurry;
    @BindView(R.id.bottom_click)
    LinearLayout bottomClick;
    @BindView(R.id.prencente)
    TextView prencente;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.help_memory_list)
    RecyclerView helpMemoryList;
    @BindView(R.id.help_memory)
    RelativeLayout helpMemory;
    @BindView(R.id.shor_sense_list)
    RecyclerView shorSenseList;
    @BindView(R.id.short_senese)
    RelativeLayout shortSenese;
    @BindView(R.id.sentences_list)
    RecyclerView sentencesList;
    @BindView(R.id.sentences)
    RelativeLayout sentences;
    @BindView(R.id.question_list)
    RecyclerView questionList;
    @BindView(R.id.question)
    RelativeLayout question;
    private String planWords;
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
    @BindView(R.id.phonogram)
    TextView phonogram;

    private RecitWordBeen recitWord;
    private int status;   //  单词状态
    private String wordId;   //  单词ID
    private int tag;

    //   根据List<wordsId>获取数据
    private ArrayList<String> words;
    private int i = 0;  //  遍历word的位置

    private List<ReciteWordParent> reciteWordParents;
    private ReciteWordParentAdapter reciteWordParentAdapter;


    private List<RecitWordBeen.LowSentenceBean>  lowSentenceBeen ;
    private List<RecitWordBeen.LowSentenceBean>   sentenceBeen ;

    private ReciteWordAdapter reciteWordAdapter ;


    private MediaPlayer dimPlayer ;
    private MediaPlayer notKnowPlayer ;
    private MediaPlayer knowPlayer ;
    private MediaPlayer knowWellPlayer ;
    /**
     * @param context
     * @param planWords
     * @param tag
     */
    public static void start(Context context, String planWords, int tag) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putExtra("planWords", planWords);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    public static void start(Context context, ArrayList<String> words) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putStringArrayListExtra("words", words);
        context.startActivity(intent);
    }

    public static void start(Context context, String wordId) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putExtra("wordId", wordId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_evaluate);
        ButterKnife.bind(this);
        Intent intent = null;
        intent = getIntent();
        if (intent != null) {
            planWords = getIntent().getStringExtra("planWords");
            tag = getIntent().getIntExtra("tag", 100);
            words = getIntent().getStringArrayListExtra("words");
            wordId = getIntent().getStringExtra("wordId");
            if (!TextUtils.isEmpty(planWords)) {
                reciteWords();
            }
            if (words != null && words.size() > 0) {
                reciteWords(words);
            }
            if (!TextUtils.isEmpty(wordId)) {
                fromWordsIdGetWordDetails(wordId, 1 + "", 1 + "", C.NORMAL_RECITE + "");
            }
        }
        setFocusable();
        initAudioManager();
    }


    public void initAudioManager(){
        dimPlayer = MediaPlayer.create(this ,R.raw.dim);
        knowPlayer = MediaPlayer.create(this ,R.raw.eva_right_and_know);
        notKnowPlayer = MediaPlayer.create(this ,R.raw.eva_error_and_not_know);
        knowWellPlayer = MediaPlayer.create(this ,R.raw.know_well);
    }

    /**
     * 正常获取word ，直接获取word 详情
     */
    public void normalReciteWords() {

        addToCompositeDis(HttpUtil.reciteWordsObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<RecitWordBeen>() {
                    @Override
                    public void accept(@NonNull RecitWordBeen recitWordBeen) throws Exception {
                        dismissLoadDialog();
                        if (getHttpResSuc(recitWordBeen.getCode())) {
                            referUi1(recitWordBeen);
                        } else if (recitWordBeen.getCode() == 98) {
                            isReview();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    public void referUi1(final RecitWordBeen recitWord) {
        this.recitWord = recitWord;
        initRecycler();
        //  首页显示的内容
        newWord.setText("新学" + recitWord.getNeedReviewWords() + " |需复习" + recitWord.getUserNeedReviewWords());
        prencente.setText("认知率" + recitWord.getPercent() + "%");
        phonogram.setText(recitWord.getWords().getPhonetic_us());
        name.setText(recitWord.getWords().getTranslate());
        if (C.NORMAL.equals(recitWord.getTag())) {
            blurry.setText("模糊");
        } else if (C.TAGS.equals(recitWord.getTag())) {
            blurry.setText("忘记");
        }
        IMAudioManager.instance().playSound(recitWord.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                IMAudioManager.instance().playSound(recitWord.getWords().getUk_audio(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                    }
                });
            }

        });
        word.setText(recitWord.getWords().getWord());
        contentShow.setVisibility(View.GONE);
        contentHide.setVisibility(View.VISIBLE);

        getData(recitWord);
        rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  显示更多内容
                contentShow.scrollTo(0 ,0);
                contentShow.setVisibility(View.VISIBLE);
                contentHide.setVisibility(View.GONE);

            }
        });
    }

    /**
     * 获取RecyclerView  数据
     */
    public void getData(RecitWordBeen recitWord){
        lowSentenceBeen = new ArrayList<>();
        sentenceBeen = new ArrayList<>();
            try{
                if (recitWord.getLowSentence() == null || recitWord.getLowSentence().size() == 0){
                    shortSenese.setVisibility(View.GONE);
                }else {
                    if (recitWord.getLowSentence().size() > 4) {
                        for (int i = 0; i < 4; i++) {
                            lowSentenceBeen.add((RecitWordBeen.LowSentenceBean) recitWord.getLowSentence().get(i));
                        }
                    } else {
                        lowSentenceBeen.addAll(recitWord.getLowSentence());
                    }
                    ReciteWordAdapter low = new ReciteWordAdapter(this, lowSentenceBeen);
                    shorSenseList.setAdapter(low);
                    shortSenese.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                shortSenese.setVisibility(View.GONE);
            }

        try{
            if (recitWord.getSentence() == null || recitWord.getSentence().size() == 0) {
                    sentences.setVisibility(View.GONE);
            }else{
                sentenceBeen.addAll(recitWord.getSentence());
                ReciteWordAdapter sentence = new ReciteWordAdapter(this, sentenceBeen);
                sentencesList.setAdapter(sentence);
                sentences.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            sentences.setVisibility(View.GONE);
        }
    }

    public void setFocusable(){
        helpMemoryList.setFocusableInTouchMode(false);
        helpMemoryList.requestFocus();
        shorSenseList.setFocusableInTouchMode(false);
        shorSenseList.requestFocus();
        sentencesList.setFocusableInTouchMode(false);
        sentencesList.requestFocus();
    }
    public void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        helpMemoryList.setLayoutManager(linearLayoutManager);
        shorSenseList.setLayoutManager(linearLayoutManager1);
        sentencesList.setLayoutManager(linearLayoutManager2);
    }


    /**
     * 从tag 获取wordsId
     */
    public void fromTagGetWrodsId(int tag) {

        addToCompositeDis(HttpUtil.wordReviewTodayBeenObservable(tag + "")
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<WordReviewTodayBeen>() {
                    @Override
                    public void accept(@NonNull WordReviewTodayBeen wordReviewTodayBeen) throws Exception {
                        dismissLoadDialog();
                        fromWordsIdGetWordDetails(wordReviewTodayBeen.getWordsId(), wordReviewTodayBeen.getAll(), wordReviewTodayBeen.getNow(), C.TAGS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }


    //   请求复习单词
    public void reciteWords() {
        if (tag == 100) {
            normalReciteWords();
        } else {
            //   获取wordsId
            fromTagGetWrodsId(tag);
        }
    }

    /**
     * 通过wordsId  数组获取word 详情
     *
     * @param wordIds
     */
    public void reciteWords(ArrayList<String> wordIds) {
        fromWordsIdGetWordDetails(wordIds.get(0), wordIds.size() + "", 0 + "", C.LISTWORD);
    }

    /**
     * 从wordsId 获取word 详情
     */
    public void fromWordsIdGetWordDetails(final String wordsId, final String all, final String now, final String tag) {
        addToCompositeDis(HttpUtil.wordDetailsObservable(wordsId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<RecitWordBeen>() {
                    @Override
                    public void accept(@NonNull RecitWordBeen recitWordBeen) throws Exception {
                        dismissLoadDialog();
                        recitWordBeen.setTag(tag);
                        referUi1(recitWordBeen);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    /**
     * 是否为review 状态
     */
    public void isReview() {

        addToCompositeDis(HttpUtil.isReviewObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        dismissLoadDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    @OnClick({R.id.back, R.id.familiar, R.id.errors, R.id.unknow, R.id.know, R.id.blurry})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.familiar:
                if (knowWellPlayer != null && !knowWellPlayer.isPlaying()) knowWellPlayer.start();
                status = C.LGWordStatusFamiliar;
                WordEvaluateEvent wordEvaluateEvent = new WordEvaluateEvent();
                wordEvaluateEvent.setStatus(status + "");
                wordEvaluateEvent.setWordId(this.recitWord.getWords().getId());
                wordEvaluateEvent.setTag(recitWord.getTag() + "");
                updataStatus(wordEvaluateEvent);
                break;
            case R.id.errors:
                WordErrorActivity.start(WordEvaluateFragment.this, wordId);
                break;
            case R.id.unknow:
                if (notKnowPlayer != null && !notKnowPlayer.isPlaying()) notKnowPlayer.start();
                WordEvaluateEvent wordEvaluateEvent1 = new WordEvaluateEvent();
                wordEvaluateEvent1.setStatus(C.LGWordStatusIncoginzance + "");
                wordEvaluateEvent1.setWordId(this.recitWord.getWords().getId());
                wordEvaluateEvent1.setTag(recitWord.getTag() + "");
                updataStatus(wordEvaluateEvent1);
                break;
            case R.id.know:
                if (knowPlayer != null && !knowPlayer.isPlaying()) knowPlayer.start();
                WordEvaluateEvent wordEvaluateEvent2 = new WordEvaluateEvent();
                wordEvaluateEvent2.setStatus(C.LGWordStatusKnow + "");
                wordEvaluateEvent2.setWordId(this.recitWord.getWords().getId());
                wordEvaluateEvent2.setTag(recitWord.getTag() + "");
                updataStatus(wordEvaluateEvent2);
                break;
            case R.id.blurry:
                if (dimPlayer != null && !dimPlayer.isPlaying()) dimPlayer.start();
                WordEvaluateEvent wordEvaluateEvent3 = new WordEvaluateEvent();
                wordEvaluateEvent3.setStatus(C.LGWordStatusVague + "");
                wordEvaluateEvent3.setWordId(this.recitWord.getWords().getId());
                wordEvaluateEvent3.setTag(recitWord.getTag() + "");
                updataStatus(wordEvaluateEvent3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
        if (dimPlayer != null){
            if (dimPlayer.isPlaying()) dimPlayer.stop();
            dimPlayer.release();
        }
        if (knowWellPlayer != null){
            if (knowWellPlayer.isPlaying()) knowWellPlayer.stop();
            knowWellPlayer.release();
        }
        if (notKnowPlayer != null){
            if (notKnowPlayer.isPlaying()) notKnowPlayer.stop();
            notKnowPlayer.release();
        }
        if (knowPlayer != null){
            if (knowPlayer.isPlaying()) knowPlayer.stop();
            knowPlayer.release();
        }
    }

    /**
     * 上传状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataStatus(final WordEvaluateEvent wordEvaluateEvent) {
        if (!TextUtils.isEmpty(wordEvaluateEvent.getTag())) {
            if (C.LISTWORD.equals(wordEvaluateEvent.getTag())) {
                addToCompositeDis(HttpUtil.reviewUpdataObservable(wordEvaluateEvent.getWordId(), wordEvaluateEvent.getStatus())
                        .subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                                if (getHttpResSuc(voidResultBeen.getCode())) {
                                    if (i >= (words.size() - 1)) {
                                        showCompelete();
                                    } else {
                                        i++;
                                        fromWordsIdGetWordDetails(words.get(i), words.size() + "", i + "", C.LISTWORD);
                                    }
                                } else {
                                    toTast(WordEvaluateFragment.this, voidResultBeen.getMessage());
                                }
                            }
                        }));
            } else {
                addToCompositeDis(HttpUtil.updataStatus(wordEvaluateEvent.getWordId(), wordEvaluateEvent.getStatus())
                        .subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                                if (getHttpResSuc(voidResultBeen.getCode())) {
                                    if (C.NORMAL.equals(wordEvaluateEvent.getTag())) {
                                        if (false) {
                                            showCompelete();
                                        } else {
                                            reciteWords();
                                        }
                                    } else if (C.TAGS.equals(wordEvaluateEvent.getTag())) {
                                        if (false) {
                                            showCompelete();
                                        } else {
                                            fromTagGetWrodsId(tag);
                                        }
                                    }
                                } else {
                                    toTast(WordEvaluateFragment.this, voidResultBeen.getMessage());
                                }
                            }
                        }));
            }
        }
    }

    /**
     * 显示完成
     */
    public void showCompelete() {
        final SuccessDialog successDialog = new SuccessDialog(WordEvaluateFragment.this);
        successDialog.getWindow().setBackgroundDrawableResource(R.color.color_translate_black);
        successDialog.show();
    }

}
