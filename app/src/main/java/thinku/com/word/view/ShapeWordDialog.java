package thinku.com.word.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordAdapter;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.HttpUtils;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ShapeWordDialog extends AlertDialog {

    private TextView word, phonogram, name;
    private ImageView cancel;
    private LinearLayout play;
    private Context context;
    private ReciteWordAdapter sentence;
    private List<RecitWordBeen.LowSentenceBean> sentenceBeen;
    private RecyclerView sentences_list;

    public  ShapeWordDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ShapeWordDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ShapeWordDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_shape_word);
        initView();
        init();
    }

    public void setWordId(String wordId) {
        addNet(wordId);
    }

    public void init() {
        sentenceBeen = new ArrayList<>();
        sentence = new ReciteWordAdapter(context, sentenceBeen);
        sentences_list.setLayoutManager(new LinearLayoutManager(context));
        sentences_list.setAdapter(sentence);
    }

    public void initView() {
        word = (TextView) findViewById(R.id.word);
        phonogram = (TextView) findViewById(R.id.phonogram);
        name = (TextView) findViewById(R.id.name);
        play = (LinearLayout) findViewById(R.id.play);
        sentences_list = (RecyclerView) findViewById(R.id.sentences_list);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void addNet(String wordId) {
        if (HttpUtils.isConnected(context)) {
            HttpUtil.wordDetailsObservable(wordId)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {

                        }
                    }).subscribe(new Consumer<RecitWordBeen>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull RecitWordBeen recitWordBeen) throws Exception {
                    if (recitWordBeen != null) {
                        referUi(recitWordBeen);
                    } else {
                        Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    public void referUi(final RecitWordBeen recitWordBeen) {
        word.setText(recitWordBeen.getWords().getWord());
        if (!TextUtils.isEmpty(recitWordBeen.getWords().getPhonetic_us())){
            phonogram.setText(recitWordBeen.getWords().getPhonetic_us());
        }else{
            if (!TextUtils.isEmpty(recitWordBeen.getWords().getPhonetic_uk()))  phonogram.setText(recitWordBeen.getWords().getPhonetic_us());
        }
        phonogram.setText(recitWordBeen.getWords().getPhonetic_uk());
        if (!TextUtils.isEmpty(recitWordBeen.getWords().getTranslate())){
            name.setText(recitWordBeen.getWords().getTranslate());
            name.setVisibility(View.VISIBLE);
        }else name.setVisibility(View.GONE);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(recitWordBeen.getWords().getUs_audio())) {
                    IMAudioManager.instance().playSound(recitWordBeen.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                        }
                    });
                } else {
                    IMAudioManager.instance().playSound(recitWordBeen.getWords().getUk_audio(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                        }
                    });
                }
            }
        });
        //  只显示一个
        if (recitWordBeen.getSentence() != null && recitWordBeen.getSentence().size() > 0) {
            sentenceBeen.clear();
            RecitWordBeen.LowSentenceBean sentenceBean = recitWordBeen.getSentence().get(0);
            sentenceBean.setWord(recitWordBeen.getWords().getWord());
            sentenceBean.setDialog(true);
            sentenceBeen.add(sentenceBean);
            sentence.notifyDataSetChanged();
        }
    }
}
