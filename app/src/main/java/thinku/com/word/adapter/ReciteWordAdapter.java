package thinku.com.word.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.utils.HtmlUtil;

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
        if (!TextUtils.isEmpty(sentence.getWord())){
            if (sentence.getEnglish().indexOf(sentence.getWord()) != -1) {
                String content = HtmlUtil.replaceSpace(sentence.getEnglish());
                int index = content.indexOf(sentence.getWord()) ;
                SpannableString spannableString = new SpannableString(content);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#31b272")), index, index + sentence.getWord().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                reciteWordHolder.us.setText(spannableString);
            }else{
                String content = HtmlUtil.replaceSpace(sentence.getEnglish());
                reciteWordHolder.us.setText(content);
            }
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

        public ReciteWordHolder(View itemView) {
            super(itemView);
            us = (TextView) itemView.findViewById(R.id.us);
            chinese = (TextView) itemView.findViewById(R.id.chinese);
        }
    }
}
