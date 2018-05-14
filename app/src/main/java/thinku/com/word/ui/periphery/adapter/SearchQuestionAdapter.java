package thinku.com.word.ui.periphery.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.ui.seacher.WordBean;


public class SearchQuestionAdapter extends RecyclerView.Adapter {
    private static final String TAG = LiveAdapter.class.getSimpleName();
    private List<WordBean> words;
    private Context context;
    private SelectListener selectListener;

    public SearchQuestionAdapter(Context context, List<WordBean> words) {
        this.context = context;
        this.words = words;
    }

    public void SelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearcherHolder liveHolder = new SearcherHolder(LayoutInflater.from(context).inflate(R.layout.search_question_item_layout, parent, false));
        return liveHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SearcherHolder liveHolder = (SearcherHolder) holder;
        liveHolder.title.setText(words.get(position).getWord());
        liveHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words == null ? 0 : words.size();
    }

    private class SearcherHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public SearcherHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.search_link_content);
        }
    }
}
