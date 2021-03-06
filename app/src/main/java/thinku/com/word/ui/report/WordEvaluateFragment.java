package thinku.com.word.ui.report;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.adapter.QuestionAdapter;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.ui.personalCenter.FeedBackActivity;
import thinku.com.word.ui.recite.WordErrorActivity;
import thinku.com.word.ui.report.adapter.SimilarAdapter;
import thinku.com.word.ui.report.bean.QuestionBean;
import thinku.com.word.ui.report.bean.ReviewBean;
import thinku.com.word.ui.report.bean.ReviewCaseBean;
import thinku.com.word.ui.report.bean.WordsBean;
import thinku.com.word.ui.share.ShareDateActivity;
import thinku.com.word.ui.webView.WebViewActivity;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.C;
import thinku.com.word.utils.HtmlUtil;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.MutePopHelper;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.view.DislocationLayoutManager;
import thinku.com.word.view.FABRecyclerView;
import thinku.com.word.view.RatingBar;
import thinku.com.word.view.ShapeWordDialog;

/**
 * Created by Administrator on 2018/2/22.
 */

public class WordEvaluateFragment extends BaseActivity {
    private static final String TAG = WordEvaluateFragment.class.getSimpleName();
    @BindView(R.id.newWord)
    TextView newWord;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;
    @BindView(R.id.content_hide)
    LinearLayout contentHide;
    @BindView(R.id.content_show)
    NestedScrollView contentShow;
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
    @BindView(R.id.help_content)
    TextView helpContent;
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
    @BindView(R.id.youdao)
    LinearLayout youdao;
    @BindView(R.id.jinshan)
    LinearLayout jinshan;
    @BindView(R.id.biying)
    LinearLayout biying;
    @BindView(R.id.niujing)
    LinearLayout niujing;
    @BindView(R.id.show_more_sentence_image)
    ImageView showMoreSentenceImage;
    @BindView(R.id.show_more_sentence)
    LinearLayout showMoreSentence;
    @BindView(R.id.show_more_low_sentence_image)
    ImageView showMoreLowSentenceImage;
    @BindView(R.id.show_more_low_sentence)
    LinearLayout showMoreLowSentence;
    @BindView(R.id.show_more_low_sentence_text)
    TextView showMoreLowSentenceText ;
    @BindView(R.id.show_more_sentence_text)
    TextView showMoreSentenceText ;
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
    @BindView(R.id.article)
    WebView article;
    @BindView(R.id.question_home)
    WebView question_home;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.rat_diff)
    RatingBar ratDiff;
    @BindView(R.id.similar_list)
    FABRecyclerView similarList;
    @BindView(R.id.similar)
    RelativeLayout similar;
    private RecitWordBeen recitWord;
    private String wordId;   //  单词ID
    private int tag;  //  tag ==  100    背单词   ，
    private boolean isNewAiBinHaoSi = false;  //  是否进入新艾宾浩斯
    //   根据List<wordsId>获取数据
    private ArrayList<String> words;

    private int posiiton = 0;
    private List<RecitWordBeen.LowSentenceBean> lowSentenceBeen;
    private List<RecitWordBeen.LowSentenceBean> sentenceBeen;
    private List<QuestionBean.QslctarrBean> questions;
    private List<RecitWordBeen.SimilarWords> similarWords;

    private MediaPlayer dimPlayer;
    private MediaPlayer notKnowPlayer;
    private MediaPlayer knowPlayer;
    private MediaPlayer knowWellPlayer;

    private ReciteWordAdapter low;
    private ReciteWordAdapter sentence;
    private QuestionAdapter questionAdapter;
    private SimilarAdapter similarAdapter;
    private boolean isUpdataReview = false;   //  新艾宾浩斯，  老艾宾浩斯  和 复习模式下 都是reviewUpdata
    private boolean isNormal = false;

    private MutePopHelper mutePopHelper;  //  音效popWindow ;
    private boolean isPlay;  //  控制音效开关
    private boolean autoPlay ;   //   单词播放音效
    private WordsBean data;
    private ShapeWordDialog shapeWordDialog;  // 形近词dialog


    private ObjectAnimator sentenceShowAnimator ;  //  例句展开动画
    private ObjectAnimator sentenceHideAnimator ;  //  例句隐藏动画
    private ObjectAnimator lowSentenceShowAnimator ;  //  短句展开动画
    private ObjectAnimator lowSentnceHideAnimator ;   //  短句隐藏动画
    /**
     * @param context
     * @param tag     tag 表明是背单词进入 还是其他情况进入
     */
    public static void start(Context context, int tag) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    public static void start(Context context, ArrayList<String> words) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putStringArrayListExtra("words", words);
        context.startActivity(intent);
    }

    public static void start(Context context, WordsBean wordsBean) {
        Intent intent = new Intent(context, WordEvaluateFragment.class);
        intent.putExtra("data", wordsBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_evaluate);
        ButterKnife.bind(this);
        setFocusable();
        initAudioManager();
        initPop();
        initRecycler();
        isPlay = SharedPreferencesUtils.getPlayMusic(this);
        autoPlay = SharedPreferencesUtils.getAutoPlayMusic(this);
        Intent intent = getIntent();
        if (intent != null) {
            tag = getIntent().getIntExtra("tag", 0);
            words = getIntent().getStringArrayListExtra("words");
            data = getIntent().getParcelableExtra("data");
            if (tag == C.NORMAL) {
                reciteWords();
            }
            //  传过来一个数组 必定是复习状态
            if (words != null && words.size() > 0) {
                tag = C.REVIEW;
                isUpdataReview = true;
                isNormal = false;
                reciteWords(words);
            }

            if (data != null) {
                tag = data.getTag();
                isUpdataReview = data.isUpdataReview();
                isNormal = data.isNormal();
                isNewAiBinHaoSi = data.getMode();
                words = (ArrayList<String>) data.getWords();
                posiiton = data.getPoistion();
                fromWordsIdGetWordDetails(words.get(data.getPoistion()));
            }
        }
    }

    private ObjectAnimator showAnimator(ImageView imageView){
        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f);
        objectAnimator.setDuration(300);
        return objectAnimator ;
    }
    private ObjectAnimator hideAnimator(ImageView imageView){
        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat(imageView, "rotation", 180f, 360f);
        objectAnimator.setDuration(300);
        return objectAnimator ;
    }

    /**
     * 初始化静音popWindow
     */
    public void initPop() {
        mutePopHelper = MutePopHelper.create(this);
        mutePopHelper.setSelectListener(new MutePopHelper.MuteOpenListener() {
            @Override
            public void openMusic() {
                isPlay = true;
                SharedPreferencesUtils.setPlayMusic(WordEvaluateFragment.this, true);
            }

            @Override
            public void closeMusic() {
                isPlay = false;
                SharedPreferencesUtils.setPlayMusic(WordEvaluateFragment.this, false);
            }

            @Override
            public void onDismissListener() {
                setBackground(1.0f);
            }

            @Override
            public void onShowListener() {
                setBackground(0.5f);
            }

            @Override
            public void toError() {
                WordErrorActivity.start(WordEvaluateFragment.this, wordId);
            }

            @Override
            public void openAutoMusic() {
                autoPlay = true;
                SharedPreferencesUtils.setAutoPlayMusic(WordEvaluateFragment.this, true);
            }

            @Override
            public void closeAutoMusic() {
                autoPlay = false;
                SharedPreferencesUtils.setAutoPlayMusic(WordEvaluateFragment.this, false);
            }
        });
        mutePopHelper.init();
        isPlay = SharedPreferencesUtils.getPlayMusic(this);
        shapeWordDialog = new ShapeWordDialog(this);
        shapeWordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
        shapeWordDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
    }

    public void setBackground(float f) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    public void initAudioManager() {
        dimPlayer = MediaPlayer.create(this, R.raw.dim);
        knowPlayer = MediaPlayer.create(this, R.raw.eva_right_and_know);
        notKnowPlayer = MediaPlayer.create(this, R.raw.eva_error_and_not_know);
        knowWellPlayer = MediaPlayer.create(this, R.raw.know_well);
    }


    public void playMusic(boolean isAuto) {
        if (autoPlay || !isAuto) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (!TextUtils.isEmpty(recitWord.getWords().getUs_audio())) {
                        IMAudioManager.instance().playSound(recitWord.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                            }
                        });
                    } else {
                        if (!TextUtils.isEmpty(recitWord.getWords().getUk_audio()))
                            IMAudioManager.instance().playSound(recitWord.getWords().getUk_audio(), new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {

                                }
                            });
                    }
                }
            }).start();
        }
    }

    /**
     * 正常背单词情况
     * 根据code不同  处理情况不同
     * code  ==2    已经背完单词  返回首页
     * code == 98  调新艾宾浩斯接口
     * code  == 96  分享
     * code == 95  调老艾宾浩斯接口
     */
    public void normalReciteWords() {
        tag = C.NORMAL;
        posiiton = 0;
        isNewAiBinHaoSi = false;
        isUpdataReview = false;
        isNormal = true;
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
                            newAiBinHaoSi();
                        } else if (recitWordBeen.getCode() == 2) {
                            toTast(WordEvaluateFragment.this, "你已经背完该词包");
                            MainActivity.toMain(WordEvaluateFragment.this);
                        } else if (recitWordBeen.getCode() == 96) {
                            share();
                        } else if (recitWordBeen.getCode() == 95) {
                            oldAiBinHaoSi();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                        dismissLoadDialog();

                    }
                }));
    }

    /**
     * is-review
     * 新艾宾浩斯接口
     * code  == 0  等于0  调用nowFinsh
     * code 不等于0 ， 返回wordId数组 ,  用id 获得word ，若熟识或者认识，将该id从此数组中去除，直到此数组为0  调用nowFinsh
     */
    public void newAiBinHaoSi() {
        isUpdataReview = true;
        isNewAiBinHaoSi = true;
        isNormal = false;
        addToCompositeDis(HttpUtil.isReviewObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<ReviewCaseBean>() {
                    @Override
                    public void accept(@NonNull ReviewCaseBean voidResultBeen) throws Exception {
//                        dismissLoadDialog();
                        if (voidResultBeen.getCode() == 0) {
                            nowFinsh();
                        } else {
                            if (words != null) {
                                words.clear();
                                words.addAll(voidResultBeen.getWords());
                            } else {
                                words = (ArrayList<String>) voidResultBeen.getWords();
                            }
                            reciteWords(words);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        dismissLoadDialog();
                        toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                    }
                }));
    }

    /**
     * if  成功  又调用正常背单词接口
     */
    public void nowFinsh() {
        addToCompositeDis(HttpUtil.nowFinshObservable()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())) {
                            normalReciteWords();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                    }
                }));
    }

    /**
     * review_casewords
     * 老艾宾浩斯接口
     * code = 2  分享
     * 否则 返回wordId 数组 ，用id 获得word ，若未熟识或者认识，将该id从此数组中去除，直到此数组为0   弹分享
     */
    public void oldAiBinHaoSi() {
        isUpdataReview = true;
        isNormal = false;
        addToCompositeDis(HttpUtil.wordReviewTodayBeenObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                }).subscribe(new Consumer<ReviewBean>() {
                    @Override
                    public void accept(@NonNull ReviewBean reviewCaseBean) throws Exception {
                        if (reviewCaseBean.getCode() == 2) {
                            share();
                        } else {
                            if (words != null) {
                                words.clear();
                                words.addAll(reviewCaseBean.getWords());
                            } else {
                                words = (ArrayList<String>) reviewCaseBean.getWords();
                            }
                            reciteWords(words);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                    }
                }));
    }

    /**
     * 日历分享
     */
    public void share() {
        ShareDateActivity.start(this);
    }

    /**
     * @param recitWord
     */
    public void referUi1(final RecitWordBeen recitWord) {
        this.recitWord = recitWord;
        wordId = this.recitWord.getWords().getId();
        //  首页显示的内容
        setStudyAndReviewNum(recitWord);
        if (SharedPreferencesUtils.getChoseMode(WordEvaluateFragment.this).equals("英中") && tag == C.REVIEW) {
            word.setVisibility(View.VISIBLE);
            name.setVisibility(View.GONE);
        } else if (SharedPreferencesUtils.getChoseMode(WordEvaluateFragment.this).equals("中英") && tag == C.REVIEW) {
            name.setVisibility(View.VISIBLE);
            word.setVisibility(View.GONE);
        } else if (tag == C.NORMAL) {
            name.setVisibility(View.GONE);
            word.setVisibility(View.VISIBLE);
        }
        prencente.setText("认知率：" + recitWord.getPercent() + "%");
        if (!TextUtils.isEmpty(recitWord.getWords().getPhonetic_us())){
            phonogram.setText(recitWord.getWords().getPhonetic_us());
        }else{
           if (!TextUtils.isEmpty(recitWord.getWords().getPhonetic_uk())) phonogram.setText(recitWord.getWords().getPhonetic_uk());
        }
        name.setText(recitWord.getWords().getTranslate());
        try {
            int leve = Integer.parseInt(recitWord.getWords().getLevel());
            ratDiff.setStar(leve);
        } catch (Exception e) {
            ratDiff.setStar(0);
        }

        if (tag == 100) {
            blurry.setText("模糊");
        } else {
            blurry.setText("忘记");
        }
        word.setText(recitWord.getWords().getWord());
        contentShow.setVisibility(View.GONE);
        bottomClick.setVisibility(View.GONE);
        contentHide.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(recitWord.getWords().getMnemonic())) {
            String content = HtmlUtil.replaceRN(recitWord.getWords().getMnemonic());
            content = HtmlUtil.replaceSpace(content);
            helpContent.setText(content);
            helpMemory.setVisibility(View.VISIBLE);
        } else helpMemory.setVisibility(View.GONE);
        playMusic(true);
        getData(recitWord);
        contentShow.post(new Runnable() {
            @Override
            public void run() {
                contentShow.smoothScrollTo(0, 0);
            }
        });

        rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  显示更多内容
                contentShow.setVisibility(View.VISIBLE);
                contentHide.setVisibility(View.GONE);
                bottomClick.setVisibility(View.VISIBLE);
                if (name.getVisibility() == View.GONE) name.setVisibility(View.VISIBLE);
                if (phonogram.getVisibility() == View.GONE) phonogram.setVisibility(View.VISIBLE);
                if (word.getVisibility() == View.GONE) word.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addView() {

    }

    /**
     * task == 3  老艾宾浩斯  新艾宾浩斯是背单词中
     * 新学 需复习 中
     * 新学数据为 do
     * 需复习  正常背单词 userNeedRevies    进入新艾宾浩斯 needReviews   + 剩余数组   , 复习模式下  数组数量
     **/
    public void setStudyAndReviewNum(RecitWordBeen recitWord) {
        if (tag == C.NORMAL) {
            if (isNewAiBinHaoSi) {
                if (words != null) {
                    newWord.setText("新学" + recitWord.getDoX() + " | 需复习" + (recitWord.getNeedReviewWords() + (words.size() - posiiton)));
                }
            } else {
                newWord.setText("新学" + recitWord.getDoX() + " | 需复习" + recitWord.getUserNeedReviewWords());
            }
        } else {
            if (words != null) newWord.setText("需复习" + (words.size() - posiiton));
        }
    }

    /**
     * 获取RecyclerView  数据
     */
    public void getData(RecitWordBeen recitWord) {
        questionList.setFocusableInTouchMode(false);
        questionList.requestFocus();
        try {
            if (recitWord.getLowSentence() == null) {
                shortSenese.setVisibility(View.GONE);
            } else {
                if (recitWord.getLowSentence().size() == 0) shortSenese.setVisibility(View.GONE);
                else {
                    lowSentenceBeen.clear();
                    if (recitWord.getLowSentence().size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            lowSentenceBeen.add(recitWord.getLowSentence().get(i));
                        }
                        showMoreLowSentence.setVisibility(View.VISIBLE);
                    } else {
                        lowSentenceBeen.addAll(recitWord.getLowSentence());
                        showMoreLowSentence.setVisibility(View.GONE);
                    }
                    low.notifyDataSetChanged();
                    shortSenese.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            shortSenese.setVisibility(View.GONE);
        }
        try {
            if (recitWord.getSentence() == null) sentences.setVisibility(View.GONE);
            else {
                if (recitWord.getSentence().size() == 0) sentences.setVisibility(View.GONE);
                else {
                    sentenceBeen.clear();
                    if (recitWord.getSentence().size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            recitWord.getSentence().get(i).setWord(recitWord.getWords().getWord());
                            sentenceBeen.add(recitWord.getSentence().get(i));
                        }
                        showMoreSentence.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < recitWord.getSentence().size(); i++) {
                            recitWord.getSentence().get(i).setWord(recitWord.getWords().getWord());
                            sentenceBeen.add(recitWord.getSentence().get(i));
                        }
                        showMoreSentence.setVisibility(View.GONE);
                    }
                    sentence.notifyDataSetChanged();
                    sentences.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception e) {
            sentences.setVisibility(View.GONE);
        }

        if (recitWord.getQuestion() == null) question.setVisibility(View.GONE);
        else {
            question.setVisibility(View.VISIBLE);
            final QuestionBean questionBean = recitWord.getQuestion();
            if (!TextUtils.isEmpty(questionBean.getArticle())) {
                article.setVisibility(View.VISIBLE);
                String content = HtmlUtil.getHtml(questionBean.getArticle(), recitWord.getWords().getWord());
                String urlContent = HtmlUtil.repairContent(content, NetworkTitle.GMAT);
                article.loadDataWithBaseURL(null, urlContent, "text/html", " charset=UTF-8", null);//这种写法可以正确解码
            } else {
                article.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(questionBean.getQuestion())) {
                question_home.setVisibility(View.VISIBLE);
                String content = HtmlUtil.getHtml(questionBean.getQuestion(), recitWord.getWords().getWord());
                String urlContent = HtmlUtil.repairContent(content, NetworkTitle.GMAT);
                question_home.loadDataWithBaseURL(null, urlContent, "text/html", " charset=UTF-8", null);//这种写法可以正确解码
            } else {
                question_home.setVisibility(View.GONE);
            }

            if (questionBean.getQslctarr() != null && questionBean.getQslctarr().size() > 0) {
                questions.clear();
                questions.addAll(questionBean.getQslctarr());
                questionList.setVisibility(View.VISIBLE);
                questionAdapter.setSelectRlClickListener(new SelectRlClickListener() {
                    @Override
                    public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                        QuestionAdapter.QuestionHolder holder = (QuestionAdapter.QuestionHolder) viewHolder;
                        int currentP = getCurrentP(questionBean.getQuestionanswer());
                        if (position == currentP) {
                            questions.get(position).setAnswer(1);
                            questionAdapter.notifyItemChanged(currentP);
                        } else {
                            questions.get(position).setAnswer(2);
                            questions.get(currentP).setAnswer(1);
                            questionAdapter.notifyItemChanged(currentP);
                            questionAdapter.notifyItemChanged(position);
                        }
                    }
                });
                questionAdapter.notifyDataSetChanged();
            } else {
                questionList.setVisibility(View.GONE);
            }
        }
        if (recitWord.getSimilarWords() != null && recitWord.getSimilarWords().size() > 0) {
            similar.setVisibility(View.VISIBLE);
            similarWords.addAll(recitWord.getSimilarWords());
            similarAdapter.notifyDataSetChanged();
        } else {
            similar.setVisibility(View.GONE);
        }

    }

    public int getCurrentP(String answer) {
        int currentP = 0;
        switch (answer) {
            case "A":
                currentP = 0;
                break;
            case "B":
                currentP = 1;
                break;
            case "C":
                currentP = 2;
                break;
            case "D":
                currentP = 3;
                break;
            case "E":
                currentP = 4;
                break;
            case "F":
                currentP = 5;
                break;

        }
        return currentP;
    }

    public void setFocusable() {
        shorSenseList.setFocusableInTouchMode(false);
        shorSenseList.requestFocus();
        sentencesList.setFocusableInTouchMode(false);
        sentencesList.requestFocus();

    }

    public void initRecycler() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        shorSenseList.setLayoutManager(linearLayoutManager1);
        sentencesList.setLayoutManager(linearLayoutManager2);
        questionList.setLayoutManager(linearLayoutManager3);
        lowSentenceBeen = new ArrayList<>();
        sentenceBeen = new ArrayList<>();
        low = new ReciteWordAdapter(this, lowSentenceBeen);
        shorSenseList.setAdapter(low);
        sentence = new ReciteWordAdapter(this, sentenceBeen);
        sentencesList.setAdapter(sentence);
        questions = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questions);
        questionList.setAdapter(questionAdapter);
        DislocationLayoutManager disLayoutManager = new DislocationLayoutManager();
        similarList.setLayoutManager(disLayoutManager);
        similarWords = new ArrayList<>();
        similarAdapter = new SimilarAdapter(this, similarWords);
        similarAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                if (shapeWordDialog != null) {
                    shapeWordDialog.setWordId(similarWords.get(position).getId());
                }
                shapeWordDialog.show();
            }
        });
        similarList.setAdapter(similarAdapter);
        similarList.setNestedScrollingEnabled(false);
    }


    /**
     * tag  == 100    背单词  ，   正常背单词
     * <p>
     * tag == 200 ,  复习单词  和艾宾浩斯背单词
     */
    public void reciteWords() {
        //
        isNewAiBinHaoSi = false;
        isUpdataReview = false;
        if (tag == C.NORMAL) {
            if (MyApplication.task == 3) {
                oldAiBinHaoSi();
            } else {
                normalReciteWords();
            }
        } else {
            //  复习单词
            isUpdataReview = true;
            if (MyApplication.task == 3) {
                addToCompositeDis(HttpUtil.reviewCaseObservable()
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {

                            }
                        }).subscribe(new Consumer<ResultBeen<List<String>>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<List<String>> listResultBeen) throws Exception {

                                if (words != null) {
                                    words.clear();
                                    words.addAll(listResultBeen.getData());
                                } else {
                                    posiiton = 0;
                                    words = (ArrayList<String>) listResultBeen.getData();
                                    fromWordsIdGetWordDetails(words.get(posiiton));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
//                                dismissLoadDialog();
                                toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                                Log.e(TAG, "reviewCaseObservable>>>>>>>>>: " + throwable.getMessage() );
                            }
                        }));
            }
        }
    }

    /**
     * 通过wordsId  数组获取word 详情
     *
     * @param wordIds
     */
    public void reciteWords(ArrayList<String> wordIds) {
        posiiton = 0;
        fromWordsIdGetWordDetails(wordIds.get(posiiton));
    }

    /**
     * 从wordsId 获取word 详情
     */
    public void fromWordsIdGetWordDetails(final String wordsId) {
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
                        referUi1(recitWordBeen);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        Log.e("error", "fromWordsIdGetWordDetails>>>>>>>>> " +throwable.getMessage() );
                        toTast(WordEvaluateFragment.this, throwable.getMessage());
                    }
                }));
    }


    @OnClick({R.id.back, R.id.familiar, R.id.errors, R.id.unknow, R.id.know, R.id.blurry, R.id.play , R.id.show_more_low_sentence , R.id.show_more_sentence})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishWithMusic();
                break;
            case R.id.familiar:
                if (isPlay && knowWellPlayer != null && !knowWellPlayer.isPlaying())
                    knowWellPlayer.start();
                updataStatus(C.LGWordStatusFamiliar);
                break;
            case R.id.errors:
                mutePopHelper.show(errors);
                break;
            case R.id.unknow:
                if (isPlay && notKnowPlayer != null) {
                    if (!notKnowPlayer.isPlaying()) {
                        notKnowPlayer.start();
                    }
                }
                if (tag == C.NORMAL) {
                    addWords(recitWord);
                }
                updataStatus(C.LGWordStatusIncoginzance);
                break;
            case R.id.know:
                if (isPlay && knowPlayer != null && !knowPlayer.isPlaying()) knowPlayer.start();
                updataStatus(C.LGWordStatusKnow);
                break;
            case R.id.blurry:
                if (isPlay && dimPlayer != null && !dimPlayer.isPlaying()) dimPlayer.start();
                if (tag == C.NORMAL) {
                    addWords(recitWord);
                }
                if ("忘记".equals(blurry.getText().toString().trim())) {
                    updataStatus(C.LGWordStatusForget);
                } else {
                    updataStatus(C.LGWordStatusVague);
                }
                break;
            case R.id.play:
                playMusic(false);
                break;
            case R.id.show_more_low_sentence:
                showMoreLowSentence();
                break;
            case R.id.show_more_sentence:
                showMoreSentence();
                break;
        }
    }

    /**
     * 显示更多例句
     */
    private void showMoreSentence(){
        if (sentenceBeen.size() <= 3){
            sentenceBeen.clear();
            sentenceBeen.addAll(recitWord.getSentence());
            sentence.notifyDataSetChanged();
            if (sentenceShowAnimator == null){
                sentenceShowAnimator = showAnimator(showMoreSentenceImage);
            }
            startAnimator(sentenceShowAnimator);
            showMoreSentenceText.setText(getResources().getString(R.string.hide_more));
        }else{
            List<RecitWordBeen.LowSentenceBean> sentences = new ArrayList<>();
            for (int i = 0 ; i < 3; i++){
                RecitWordBeen.LowSentenceBean sentence = sentenceBeen.get(i);
                sentences.add(sentence);
            }
            sentenceBeen.clear();
            sentenceBeen.addAll(sentences);
            sentence.notifyDataSetChanged();
            if (sentenceHideAnimator == null){
                sentenceHideAnimator = hideAnimator(showMoreSentenceImage);
            }
            startAnimator(sentenceHideAnimator);
            showMoreSentenceText.setText(getResources().getString(R.string.show_more));
        }
    }

    private void startAnimator(ObjectAnimator objectAnimator){
        if (objectAnimator != null){
            objectAnimator.start();
        }
    }


    /**
     * 显示更多短句
     */
    public void showMoreLowSentence(){
        if (lowSentenceBeen.size() <= 3){
            lowSentenceBeen.addAll(recitWord.getLowSentence());
            low.notifyDataSetChanged();
            if (lowSentenceShowAnimator == null){
                lowSentenceShowAnimator = showAnimator(showMoreLowSentenceImage);
            }
            startAnimator(lowSentenceShowAnimator);
            showMoreLowSentenceText.setText(getResources().getString(R.string.hide_more));
        }else{
            List<RecitWordBeen.LowSentenceBean> sentences = new ArrayList<>();
            for (int i = 0 ; i < 3; i++){
                RecitWordBeen.LowSentenceBean lowSentence = lowSentenceBeen.get(i);
                sentences.add(lowSentence);
            }
            lowSentenceBeen.clear();
            lowSentenceBeen.addAll(sentences);
            low.notifyDataSetChanged();
            if (lowSentnceHideAnimator == null){
                lowSentnceHideAnimator = hideAnimator(showMoreLowSentenceImage);
            }
            startAnimator(lowSentnceHideAnimator);
            showMoreLowSentenceText.setText(getResources().getString(R.string.show_more));
        }
    }


    private void finishWithMusic() {
        this.finishWithAnim();
        IMAudioManager.instance().release();
    }

    public void addWords(RecitWordBeen recitWord) {
        if (words != null) {
            words.add(recitWord.getWords().getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (dimPlayer != null) {
            if (dimPlayer.isPlaying()) dimPlayer.stop();
            dimPlayer.release();
        }
        if (knowWellPlayer != null) {
            if (knowWellPlayer.isPlaying()) knowWellPlayer.stop();
            knowWellPlayer.release();
        }
        if (notKnowPlayer != null) {
            if (notKnowPlayer.isPlaying()) notKnowPlayer.stop();
            notKnowPlayer.release();
        }
        if (knowPlayer != null) {
            if (knowPlayer.isPlaying()) knowPlayer.stop();
            knowPlayer.release();
        }
        mutePopHelper.onDestory();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void referStart(WordsBean word) {
        start(this, word);
        this.finishWithAnim();
    }

    /**
     * 背单词中 正常背单词 上传状态   又调用背单词接口
     * words  中 用worIds 获取数据
     * <p>
     * isNewAibinhaosi
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataStatus(int status) {
        final WordsBean word = new WordsBean();
        int type = 0;
        if (tag == C.NORMAL) {
            //  背单词状态上传
            if (isUpdataReview) {
                if (isNewAiBinHaoSi) {
                    type = 1;
                } else {
                    type = 0;
                }
                addToCompositeDis(HttpUtil.reviewUpdataObservable(recitWord.getWords().getId(), status + "", type + "")
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                    showLoadDialog();
                            }
                        }).subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
//                                    dismissLoadDialog();
                                posiiton++;
                                if (isNewAiBinHaoSi) {
                                    if (posiiton == words.size()) {
                                        nowFinsh();
                                    } else {
                                        word.setPoistion(posiiton);
                                        word.setTag(C.NORMAL);
                                        word.setUpdataReview(isUpdataReview);
                                        word.setWords(words);
                                        word.setPoistion(posiiton);
                                        word.setMode(isNewAiBinHaoSi);
                                        referStart(word);
//                                        fromWordsIdGetWordDetails(words.get(posiiton));
                                    }
                                } else {
                                    if (posiiton >= words.size()) {
                                        share();
                                    } else if (posiiton < words.size()) {
                                        word.setPoistion(posiiton);
                                        word.setTag(C.NORMAL);
                                        word.setUpdataReview(isUpdataReview);
                                        word.setWords(words);
                                        word.setPoistion(posiiton);
                                        word.setMode(isNewAiBinHaoSi);
                                        referStart(word);
//                                        fromWordsIdGetWordDetails(words.get(posiiton));
                                    }
                                }

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
//                                    dismissLoadDialog();
                                Log.e("error", "reviewUpdataObservable>>>>>>>>> " +throwable.getMessage() );
                                toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                            }
                        }));
            } else {
                addToCompositeDis(HttpUtil.updataStatus(recitWord.getWords().getId(), status + "")
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                    showLoadDialog();
                            }
                        }).subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
//                                    dismissLoadDialog();
                                if (isNormal) {
//                                    reciteWords();
                                    WordEvaluateFragment.start(WordEvaluateFragment.this, C.NORMAL);
                                    WordEvaluateFragment.this.finishWithAnim();
                                } else {
                                    posiiton++;
                                    if (isNewAiBinHaoSi) {
                                        if (posiiton == words.size()) {
                                            nowFinsh();
                                        } else {
//                                            fromWordsIdGetWordDetails(words.get(posiiton));
                                            word.setPoistion(posiiton);
                                            word.setTag(C.NORMAL);
                                            word.setUpdataReview(isUpdataReview);
                                            word.setWords(words);
                                            word.setPoistion(posiiton);
                                            word.setMode(isNewAiBinHaoSi);
                                            referStart(word);
                                        }
                                    } else {
                                        if (posiiton >= words.size()) {
                                            share();
                                        } else if (posiiton < words.size()) {
//                                            fromWordsIdGetWordDetails(words.get(posiiton));
                                            word.setPoistion(posiiton);
                                            word.setTag(C.NORMAL);
                                            word.setUpdataReview(isUpdataReview);
                                            word.setWords(words);
                                            word.setPoistion(posiiton);
                                            word.setMode(isNewAiBinHaoSi);
                                            referStart(word);
                                        }
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
//                                    dismissLoadDialog();
                                Log.e("error", "updataStatus>>>>>>>>> " +throwable.getMessage() );
                                toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                            }
                        }));

            }
        } else if (tag == C.REVIEW) {
            // 复习模式下上传
            if (isNewAiBinHaoSi) {
                type = 1;
            } else {
                type = 0;
            }
            addToCompositeDis(HttpUtil.reviewUpdataObservable(recitWord.getWords().getId(), status + "", type + "")
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
//                                showLoadDialog();
                        }
                    }).subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
//                                dismissLoadDialog();
                            posiiton++;
                            if (words != null) {
                                if (posiiton < words.size()) {
                                    word.setPoistion(posiiton);
                                    word.setTag(C.REVIEW);
                                    word.setUpdataReview(isUpdataReview);
                                    word.setWords(words);
                                    word.setPoistion(posiiton);
                                    word.setMode(isNewAiBinHaoSi);
                                    referStart(word);
//                                    fromWordsIdGetWordDetails(words.get(posiiton));
                                } else {
                                    if (isNewAiBinHaoSi) {
                                        nowFinsh();
                                    } else {
                                        finishWithAnim();
                                    }
                                }
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
//                                dismissLoadDialog();
                            Log.e("error", "REVIEW ======updataStatus>>>>>>>>> " +throwable.getMessage() );
                            toTast(WordEvaluateFragment.this, HttpUtils.onError(throwable));
                        }
                    }));

        }
    }

    @OnClick({R.id.youdao, R.id.niujing, R.id.biying, R.id.jinshan})
    public void wordInterface(View view) {
        String word = recitWord.getWords().getWord();
        String url = "";
        switch (view.getId()) {
            case R.id.youdao:
                url = "http://m.youdao.com/dict?le=eng&q=" + word.replace(" ", "+");
                break;
            case R.id.jinshan:
                url = "http://www.iciba.com/" + word.replace(" ", "+");
                break;
            case R.id.biying:
                url = "https://cn.bing.com/dict/search?q=" + word.replace(" ", "+");
                break;
            case R.id.niujing:
                url = "https://www.oxfordlearnersdictionaries.com/definition/english/" + word.replace(" ", "-") + "?q=" + word.replace(" ", "+");
//                  url = "https://www.oxfordlearnersdictionaries.com/definition/english/reflex-angle?q=reflex+angle";
                break;
        }
        WebViewActivity.start(this, url);
    }


}
