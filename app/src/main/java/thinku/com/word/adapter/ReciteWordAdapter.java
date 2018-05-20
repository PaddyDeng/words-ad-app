package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.RecitWordBeen;

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
        reciteWordHolder.us.setText(sentence.getEnglish());
        reciteWordHolder.us.setVisibility(View.VISIBLE);
        reciteWordHolder.chinese.setText(sentence.getChinese());
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
