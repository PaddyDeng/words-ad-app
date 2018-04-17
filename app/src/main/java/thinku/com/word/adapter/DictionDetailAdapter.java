package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectClickListener;
import thinku.com.word.callback.SelectListener;

/**
 * Created by Administrator on 2018/4/8.
 */

public class DictionDetailAdapter extends RecyclerView.Adapter {

    private List<String> words;
    private Context context;
    private SelectClickListener selectListener ;

    public DictionDetailAdapter(List<String> words, Context context) {
        this.words = words;
        this.context = context;
    }

    public void setSelectListener(SelectClickListener selectListener){
        this.selectListener = selectListener ;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DictionDetailHolder dictionDetailHolder =new DictionDetailHolder( LayoutInflater.from(context).inflate(R.layout.item_diction_detail,parent ,false));
        return dictionDetailHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DictionDetailHolder dictionDetailHolder = (DictionDetailHolder) holder;
        String word = words.get(position);
        if (!TextUtils.isEmpty(word)) {
            dictionDetailHolder.textView.setText(word);
            dictionDetailHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (dictionDetailHolder.textView.isSelected()) dictionDetailHolder.textView.setSelected(false);
                    else dictionDetailHolder.textView.setSelected(true);
                    selectListener.onClick(position ,dictionDetailHolder.textView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null==words?0:words.size();
    }

    public class DictionDetailHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public DictionDetailHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            textView = (TextView) itemView.findViewById(R.id.word_spilt);
        }
    }
}
