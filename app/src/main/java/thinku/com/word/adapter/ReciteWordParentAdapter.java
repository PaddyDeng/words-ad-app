package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.ReciteWordParent;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ReciteWordParentAdapter extends RecyclerView.Adapter {

    private List<ReciteWordParent> sentences;
    private Context context;
    private ReciteWordAdapter reciteWordAdapter ;

    public ReciteWordParentAdapter(Context context, List<ReciteWordParent> sentences) {
        this.context = context;
        this.sentences = sentences;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word_evaluate, parent, false);
        ReciteWordHolder holder = new ReciteWordHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReciteWordHolder reciteWordHolder = (ReciteWordHolder) holder;
        ReciteWordParent reciteWordParent = sentences.get(position);
        reciteWordHolder.name.setText(reciteWordParent.getName());
        reciteWordHolder.list.setLayoutManager(new LinearLayoutManager(context));
        reciteWordAdapter = new ReciteWordAdapter(context ,reciteWordParent.getSentenceList());
        reciteWordHolder.list.setAdapter(reciteWordAdapter);
    }

    @Override
    public int getItemCount() {
        return sentences == null ? 0 : sentences.size();
    }

    class ReciteWordHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RecyclerView list ;

        public ReciteWordHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            list = (RecyclerView) itemView.findViewById(R.id.list);
        }
    }
}
