package thinku.com.word.ui.seacher;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.WordEvaluateEvent;
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

    private WordListBean recitWord;
    private int status;   //  单词状态
    private String wordId;   //  单词ID
    private int tag;

    //   根据List<wordsId>获取数据
    private ArrayList<String> words;
    private int i = 0;  //  遍历word的位置

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
            wordId = getIntent().getStringExtra("wordId");
            if (!TextUtils.isEmpty(wordId)) {
                fromWordsIdGetWordDetails(wordId, 1 + "", 1 + "", C.NORMAL_RECITE + "");
            }
        }
    }

    /**
     * 从wordsId 获取word 详情
     */
    public void fromWordsIdGetWordDetails(final String wordsId, final String all, final String now, final String tag) {
        addToCompositeDis(HttpUtil.wordDetailsObservable2(wordsId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<WordListBean>() {
                    @Override
                    public void accept(@NonNull WordListBean recitWordBeen) throws Exception {
                        dismissLoadDialog();
                        wordId = wordsId;
                        recitWord = recitWordBeen;
                        word.setText(recitWord.getWords().getWord());
                        phonogram.setText(recitWord.getWords().getPhonetic_us());
                        recitWord.setDoX(1);
                        IMAudioManager.instance().playSound(recitWord.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }

    @OnClick({R.id.back, R.id.familiar, R.id.errors})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.familiar:
                status = C.LGWordStatusFamiliar;
                WordEvaluateEvent wordEvaluateEvent = new WordEvaluateEvent();
                wordEvaluateEvent.setStatus(status + "");
                wordEvaluateEvent.setWordId(wordId);
                wordEvaluateEvent.setTag(C.NORMAL);
                updataStatus(wordEvaluateEvent);
                break;
            case R.id.errors:
                WordErrorActivity.start(WordEvaluateFragment.this, wordId);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
    }

    /**
     * 上传状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataStatus(final WordEvaluateEvent wordEvaluateEvent) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        }
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
                                        if (recitWord.getDoX() + 1 == Integer.parseInt("1")) {
                                            showCompelete();
                                        } else {
                                        }
                                    } else if (C.TAGS.equals(wordEvaluateEvent.getTag())) {
                                        if (recitWord.getDoX() + 1 == Integer.parseInt("1")) {
                                            showCompelete();
                                        } else {
                                        }
                                    }
                                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                                        pop();
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
