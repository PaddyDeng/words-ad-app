package thinku.com.word.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.HtmlUtil;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.utils.WordStartAndEnd;
import thinku.com.word.view.CenterAlignImageSpan;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ReciteWordAdapter extends RecyclerView.Adapter {
    private static final String TAG = ReciteWordAdapter.class.getSimpleName();
    private List<RecitWordBeen.LowSentenceBean> sentences;
    private Context context;
    public ReciteWordAdapter(Context context, List<RecitWordBeen.LowSentenceBean> sentences) {
        this.context = context;
        this.sentences = sentences;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word_evaluate_data_two, parent, false);
        ReciteWordHolder holder = new ReciteWordHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReciteWordHolder reciteWordHolder = (ReciteWordHolder) holder;
        RecitWordBeen.LowSentenceBean sentence = sentences.get(position);
        String content =  HtmlUtil.replaceSpace(sentence.getEnglish());
        SpannableString spannableString = new SpannableString(content + "    ");
        if (!TextUtils.isEmpty(sentence.getWord())){
            if (HtmlUtil.getPatternIndexs(sentence.getWord() ,sentence.getEnglish()).size() > 0) {
                if (sentence.isDialog()){
                    reciteWordHolder.img.setVisibility(View.GONE);
                    setAllWordGreen(sentence.getWord() ,content ,spannableString );
                    reciteWordHolder.us.setText(spannableString);
                }else {
                    reciteWordHolder.img.setVisibility(View.VISIBLE);
                    setAllWordGreen(sentence.getWord() ,content ,spannableString);
                    reciteWordHolder.us.setText(spannableString);
                    //  在文本末尾添加图片
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.music);
                    drawable.setBounds(MeasureUtils.dp2px(context, 5), MeasureUtils.dp2px(context, 0)
                            , MeasureUtils.dp2px(context, 17), MeasureUtils.dp2px(context, 12));
                    CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                    spannableString.setSpan(imageSpan, content.length(), content.length() + 2, ImageSpan.ALIGN_BASELINE);
                    reciteWordHolder.us.setText(spannableString);
                }
            }else{
                if (sentence.isDialog()){
                    reciteWordHolder.img.setVisibility(View.GONE);
                    content = HtmlUtil.replaceSpace(sentence.getEnglish());
                    reciteWordHolder.us.setText(content);
                }else {
                    reciteWordHolder.img.setVisibility(View.VISIBLE);
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.music);
                    drawable.setBounds(MeasureUtils.dp2px(context, 5), MeasureUtils.dp2px(context, 0)
                            , MeasureUtils.dp2px(context, 17), MeasureUtils.dp2px(context, 12));
                    CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                    spannableString.setSpan(imageSpan, content.length(), content.length() + 2, ImageSpan.ALIGN_BASELINE);
                    reciteWordHolder.us.setText(spannableString);
                }
            }
            final String urlPath = NetworkTitle.youdao +content ;
            reciteWordHolder.us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IMAudioManager.instance().playSound(urlPath, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                        }
                    });
                }
            });
        }else {
            reciteWordHolder.us.setText(HtmlUtil.replaceSpace(sentence.getEnglish()));
        }
        reciteWordHolder.us.setVisibility(View.VISIBLE);
        reciteWordHolder.chinese.setText(HtmlUtil.replaceSpace(sentence.getChinese()));
    }

    @Override
    public int getItemCount() {
        return sentences == null ? 0 : sentences.size();
    }

    class ReciteWordHolder extends RecyclerView.ViewHolder {
        private TextView us, chinese;
        private ImageView img ;
        public ReciteWordHolder(View itemView) {
            super(itemView);
            us = (TextView) itemView.findViewById(R.id.us);
            chinese = (TextView) itemView.findViewById(R.id.chinese);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }


    private void  setColorGreen(SpannableString spannableString ,int start , int end  ){
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#31b272")), start,  //  index + sentence.getWord().length()
                end  , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private void addGreen(WordStartAndEnd wordStartAndEnd , SpannableString spannableString , String content){
        int start = wordStartAndEnd.getStart() ;
        int end = wordStartAndEnd.getEnd() ;
        if (start != -1 ){
           int spaceIndex = StringUtils.match(content ,end);
           setColorGreen(spannableString ,start ,spaceIndex);
        }
    }

    private void setAllWordGreen(String word ,String content ,SpannableString spannableString){
        List<WordStartAndEnd> wordStartAndEnds = HtmlUtil.getPatternIndexs(word ,content);
        if (wordStartAndEnds != null && wordStartAndEnds.size() > 0) {
            for (WordStartAndEnd wordStartAndEnd : wordStartAndEnds) {
                 addGreen(wordStartAndEnd ,spannableString ,content);
            }
        }
    }
}
