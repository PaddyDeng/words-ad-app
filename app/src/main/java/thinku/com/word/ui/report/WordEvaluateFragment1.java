package thinku.com.word.ui.report;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.adapter.QuestionAdapter;
import thinku.com.word.ui.recite.WordErrorActivity;
import thinku.com.word.ui.report.adapter.SimilarAdapter;
import thinku.com.word.ui.report.bean.QuestionBean;
import thinku.com.word.ui.webView.WebViewActivity;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.HtmlUtil;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.view.DislocationLayoutManager;
import thinku.com.word.view.FABRecyclerView;
import thinku.com.word.view.RatingBar;
import thinku.com.word.view.ShapeWordDialog;


public class WordEvaluateFragment1 extends BaseActivity {
    private static final String TAG = WordEvaluateFragment1.class.getSimpleName();
    @BindView(R.id.top)
    ImageView top;
    @BindView(R.id.content_show)
    NestedScrollView contentShow;
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
    private String planWords;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.familiar)
    LinearLayout familiar;
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
    @BindView(R.id.rat_diff)
    RatingBar ratDiff ;
    @BindView(R.id.similar_list)
    FABRecyclerView similarList;
    @BindView(R.id.similar)
    RelativeLayout similar ;

    private RecitWordBeen recitWord;
    private String wordId;   //  单词ID
    private List<RecitWordBeen.LowSentenceBean> lowSentenceBeen;
    private List<RecitWordBeen.LowSentenceBean> sentenceBeen;
    private List<QuestionBean.QslctarrBean> questions;
    private List<RecitWordBeen.SimilarWords> similarWords ;


    private ReciteWordAdapter low;
    private ReciteWordAdapter sentence;
    private QuestionAdapter questionAdapter;

    private ShapeWordDialog shapeWordDialog ;  // 形近词dialog
    private SimilarAdapter similarAdapter ;

    private ObjectAnimator sentenceShowAnimator ;  //  例句展开动画
    private ObjectAnimator sentenceHideAnimator ;  //  例句隐藏动画
    private ObjectAnimator lowSentenceShowAnimator ;  //  短句展开动画
    private ObjectAnimator lowSentnceHideAnimator ;   //  短句隐藏动画
    public static void start(Context context, String wordId) {
        Intent intent = new Intent(context, WordEvaluateFragment1.class);
        intent.putExtra("wordId", wordId);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_evaluate1);
        ButterKnife.bind(this);
        Intent intent = null;
        intent = getIntent();
        if (intent != null){
            wordId = intent.getStringExtra("wordId");
            fromWordsIdGetWordDetails(wordId);
        }
        setFocusable();
        initRecycler();
    }


    /**
     * @param recitWord
     */
    public void referUi1(final RecitWordBeen recitWord) {
        this.recitWord = recitWord ;
        prencente.setText("认知率：" + recitWord.getPercent() + "%");
        phonogram.setText(recitWord.getWords().getPhonetic_us());
        name.setText(recitWord.getWords().getTranslate());
        word.setText(recitWord.getWords().getWord());
        try{
            int leve = Integer.parseInt(recitWord.getWords().getLevel());
            ratDiff.setStar(leve);
        }catch (Exception e){
            ratDiff.setStar(0);
        }
        contentShow.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(recitWord.getWords().getMnemonic())) {
            String content = HtmlUtil.replaceRN(recitWord.getWords().getMnemonic()) ;
            content = HtmlUtil.replaceSpace(content);
            helpContent.setText(content);
            helpMemory.setVisibility(View.VISIBLE);
        } else helpMemory.setVisibility(View.GONE);
        playMusic();
        getData(recitWord);
    }


    public void playMusic(){
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

    /**
     * 获取RecyclerView  数据
     */
    public void getData(RecitWordBeen recitWord) {
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
                String content = HtmlUtil.getHtml(questionBean.getArticle() ,recitWord.getWords().getWord());
                String urlContent = HtmlUtil.repairContent(content , NetworkTitle.GMAT);
                article.loadDataWithBaseURL(null, urlContent, "text/html", " charset=UTF-8", null);//这种写法可以正确解码
            } else {
                article.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(questionBean.getQuestion())) {
                question_home.setVisibility(View.VISIBLE);
                String content = HtmlUtil.getHtml(questionBean.getQuestion() ,recitWord.getWords().getWord());
                String urlContent = HtmlUtil.repairContent(content ,NetworkTitle.GMAT);
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
        if (recitWord.getSimilarWords() != null && recitWord.getSimilarWords().size() > 0){
            similar.setVisibility(View.VISIBLE);
            similarWords.addAll(recitWord.getSimilarWords());
            similarAdapter.notifyDataSetChanged();
        }else{
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
        questionList.setFocusableInTouchMode(false);
        questionList.requestFocus();
    }

    public void initRecycler() {
        shapeWordDialog = new ShapeWordDialog(this);
        shapeWordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
        shapeWordDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        familiar.setVisibility(View.GONE);
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
        similarAdapter = new SimilarAdapter(this ,similarWords);
        similarAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                if (shapeWordDialog != null){
                    shapeWordDialog.setWordId(similarWords.get(position).getId());
                }
                shapeWordDialog.show();
            }
        });
        similarList.setAdapter(similarAdapter);
        similarList.setNestedScrollingEnabled(false);
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
                        if (recitWordBeen.getCode() == 99){
                            LoginHelper.needLogin(WordEvaluateFragment1.this , "");
                        }else {
                            if (recitWordBeen != null) {
                                referUi1(recitWordBeen);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        toTast(WordEvaluateFragment1.this ,throwable.getMessage());
                    }
                }));
    }


    @OnClick({R.id.back, R.id.familiar, R.id.errors , R.id.play , R.id.show_more_low_sentence , R.id.show_more_sentence})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishWithMusic();
                break;
            case R.id.familiar:
                break;
            case R.id.errors:
                WordErrorActivity.start(WordEvaluateFragment1.this, wordId);
                break;
            case R.id.play:
                playMusic();
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


    public void finishWithMusic(){
        this.finishWithAnim();
        IMAudioManager.instance().release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                break;
        }
        WebViewActivity.start(this, url);
    }




}
