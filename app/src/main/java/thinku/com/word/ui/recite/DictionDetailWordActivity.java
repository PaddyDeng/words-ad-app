package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordParentAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ReciteWordParent;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.C;

/**
 * Created by Administrator on 2018/4/10.
 */

public class DictionDetailWordActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.familiar)
    LinearLayout familiar;
    @BindView(R.id.errors)
    LinearLayout errors;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.word)
    TextView word;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.phonogram)
    TextView phonogram;
    @BindView(R.id.mnemonic)
    TextView mnemonic;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.data_list)
    RecyclerView dataList;


    private RecitWordBeen recitWord;
    private  List<ReciteWordParent> reciteWordParents ;
    private  ReciteWordParentAdapter reciteWordParentAdapter ;

    public static void start(Context context, RecitWordBeen recitWordBeen) {
//        Intent intent = new Intent(context, DictionDetailWordActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("word" ,recitWordBeen);
//        intent.putExtra("data",bundle);
//        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_dictation_word);
        ButterKnife.bind(this);
        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            recitWord = bundle.getParcelable("word");
            initView();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: "+ e.toString() );
        }
    }

    public void initView() {
        familiar.setVisibility(View.GONE);
//        titleT.setText((Integer.parseInt(recitWord.getDoX())) + "/" + recitWord.getPlanWords());
        titleT.setVisibility(View.VISIBLE);
        word.setText(recitWord.getWords().getWord());
        phonogram.setText(recitWord.getWords().getPhonetic_us());
//        recitWord.setTag(C.NORMAL);
        mnemonic.setText(recitWord.getWords().getTranslate());
        initRecyclerView();
        IMAudioManager.instance().playSound(recitWord.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.back })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }


    public void initRecyclerView() {
        List<ReciteWordParent> reciteWordParents = reciteWordParents();
        dataList.setLayoutManager(new LinearLayoutManager(DictionDetailWordActivity.this));
        reciteWordParentAdapter = new ReciteWordParentAdapter(DictionDetailWordActivity.this, reciteWordParents);
        dataList.setAdapter(reciteWordParentAdapter);
    }
    /**
     * 得到数据
     *
     * @return
     */
    public List<ReciteWordParent> reciteWordParents() {
        reciteWordParents = new ArrayList<>();
        if (!TextUtils.isEmpty(recitWord.getWords().getMnemonic()) & !"".equals(recitWord.getWords().getMnemonic())) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("助句");
            RecitWordBeen.LowSentenceBean sentent = new RecitWordBeen.LowSentenceBean();
            sentent.setChinese(recitWord.getWords().getMnemonic());
//            sentent.setId("助句");
            List<RecitWordBeen.LowSentenceBean> arrs = new ArrayList<>();
            reciteWordParent.setSentenceList(arrs);
        }
        if (recitWord.getLowSentence() != null & recitWord.getLowSentence().size() > 0) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("短句");
            reciteWordParent.setSentenceList(recitWord.getLowSentence());
            reciteWordParents.add(reciteWordParent);
        }

        if (recitWord.getSentence() != null & recitWord.getSentence().size() > 0) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("例句");
            reciteWordParent.setSentenceList(recitWord.getSentence());
            reciteWordParents.add(reciteWordParent);
        }

        return reciteWordParents;
    }

}
