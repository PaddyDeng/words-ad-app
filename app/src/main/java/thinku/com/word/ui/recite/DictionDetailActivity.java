package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.DictionDetailAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.callback.SelectClickListener;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.report.*;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.C;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.SuccessDialog;

import static thinku.com.word.R.id.height;
import static thinku.com.word.R.id.list;


public class DictionDetailActivity extends BaseActivity {
    private static final String TAG = DictionDetailActivity.class.getSimpleName();
    private static final int time = 16 ;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.phonogram)
    TextView phonogram;
    @BindView(R.id.r1)
    LinearLayout r1;
    @BindView(R.id.translate)
    TextView translate;
    @BindView(R.id.word)
    LinearLayout word;
    @BindView(R.id.word_spilt)
    RecyclerView wordSpilt;
    @BindView(R.id.tip)
    TextView tip;

    private ArrayList<String> wordList ;
    private List<String> words ;
    private DictionDetailAdapter dictionDetailAdapter ;
    private List<TextView> texts ;
    private int word_position_min = 0  ;  //  存储上面单词位置中空白处最小的值；
    private HashMap<Integer ,Integer> word_word ;  //  一个下面单词位置与上面单词位置对应关系的容器
    int i = 0 ;
    private RecitWordBeen recitWord;

    private int wordIdIndex =  0 ;  //  数组中wordIn 下标位置
    private Disposable countTime ;
    public  static void  start(Context context , ArrayList<String> wordList){
        Intent intent  = new Intent(context ,DictionDetailActivity.class);
        intent.putStringArrayListExtra("words" ,wordList);
        context.startActivity(intent);
    }

    public static void start(Context context ,String wordId , int size){
        Intent intent  = new Intent(context ,DictionDetailActivity.class);
        intent.putExtra("wordId" ,wordId);
        intent.putExtra("wordSize" ,size);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diction_detail);
        ButterKnife.bind(this);
        try{
            wordList = getIntent().getStringArrayListExtra("words");
            reciteWords(wordList);
        }catch (Exception e){

        }
        initView();
    }

    /**
     * 通过wordsId  数组获取word 详情
     */
    public void reciteWords(ArrayList<String> wordIds) {
        fromWordsIdGetWordDetails(wordIds.get(wordIdIndex), wordIds.size() + "", 0 + "");
    }

    /**
     *   每一次刷新初始化view
     */
    public void referData(){
        if (word_word != null && word_word.size() != 0){
            word_word.clear();
        }
        if (texts != null && texts.size() != 0){
            texts.clear();
        }
        i = 0  ;
        word_position_min = 0 ;
    }
    /**
     * 从wordsId 获取word 详情
     */
    public void fromWordsIdGetWordDetails(final String wordsId, final String all, final String now) {
        word.removeAllViews();
        texts = new ArrayList<>();
        word_word = new HashMap<>();
        referData();
        if (countTime != null){
            countTime.dispose();
        }
        addToCompositeDis(HttpUtil.wordDetailsObservable(wordsId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<RecitWordBeen>() {
                    @Override
                    public void accept(@NonNull RecitWordBeen strings) throws Exception {
                        dismissLoadDialog();
                        titleT.setText((Integer.parseInt(now) + 1) + "/" + Integer.parseInt(all));
                        titleT.setVisibility(View.VISIBLE);
                        recitWord = strings ;
                        recitWord.setPlanWords(all);
                        recitWord.setDoX(Integer.parseInt(now) + 1+"");
                        phonogram.setText(recitWord.getWords().getPhonetic_us());
                        IMAudioManager.instance().playSound(recitWord.getWords().getUs_audio(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show();
                            }
                        });
                        translate.setText(StringUtils.spilt(recitWord.getWords().getTranslate()));
                        words = StringUtils.splitString(recitWord.getWords().getWord());
                        addUpWordView();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }
    public void initView(){
        titleIv.setBackgroundResource(R.mipmap.clock);
    }


    public void initTimeText(){
        choseTxt.setText("16");
//        choseTxt.setTextSize(MeasureUtils.sp2px(this ,16));
        countTime = RxHelper.countDown(time)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        choseTxt.setText(integer+"");
                    }
                });
        addToCompositeDis(countTime);
    }

    /**
     *  添加上面的word TextView
     */
    public void addUpWordView(){
        //  words.size()
        for (int i = 0 ; i < words.size() ; i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.setMargins(MeasureUtils.dp2px(this ,10) , 0 ,0 ,0);
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setWidth(MeasureUtils.dp2px(this ,48));
            textView.setHeight(MeasureUtils.dp2px(this ,30));
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setBackgroundResource(R.drawable.text_bottom);
            texts.add(textView);
            word.addView(textView);
        }
        initRecycler();
    }

    //  初始化RecyclerView 数据
    public void initRecycler(){
        wordSpilt.setLayoutManager(new GridLayoutManager(this ,3));
        dictionDetailAdapter = new DictionDetailAdapter(words ,DictionDetailActivity.this);
        dictionDetailAdapter.setSelectListener(new SelectClickListener() {
            @Override
            public void onClick(int position, View view) {
                if (view.isSelected()){
                    if ( words != null && i <words.size()) {
                       setUpText(position);
                    }
                }else{
                    if (word_word != null && word_word.size() != 0) {
                        int upWordPosition = word_word.get(position);
                        if (upWordPosition < word_position_min) word_position_min = upWordPosition;
                        i = word_position_min;
                        texts.get(upWordPosition).setText("");
                    }
                }
            }
        });
        wordSpilt.setAdapter(dictionDetailAdapter);
        initTimeText();
    }




    @OnClick({R.id.back ,R.id.tip})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tip:
                toWordTip(recitWord);
                break;

        }
    }

    public void toWordTip(RecitWordBeen recitWordBeen){
        DictionDetailWordActivity.start(DictionDetailActivity.this ,recitWordBeen);
    }

    /**
     *
     * @param position 赋值的text内容
     *  判断上面的TextView的内容是否为空，为空的进行赋值,不为空的不赋值,运用递归，继续查询下一个textView的市容是否为空
     */
    public void setUpText (int position){
        if (TextUtils.isEmpty(texts.get(i).getText().toString().trim())){
            texts.get(i).setText(words.get(position));
            word_word.put(position, i);
            i++;
            if (wordIdIndex < wordList.size() -1) {
                if (i == (texts.size())) {
                    wordIdIndex++;
                    fromWordsIdGetWordDetails(wordList.get(wordIdIndex), wordList.size() + "", wordIdIndex + "");
                }
            }else{
                showCompelete();
            }
        }else{
            i++ ;
            if (i  < texts.size()) setUpText( position);
            else i = 0 ;
        }
    }

    /**
     * 显示完成
     */
    public void showCompelete() {
        final SuccessDialog successDialog = new SuccessDialog(DictionDetailActivity.this , "今天你的复习任务完成了");
        successDialog.getWindow().setBackgroundDrawableResource(R.color.color_translate_black);
        successDialog.show();
    }

}
