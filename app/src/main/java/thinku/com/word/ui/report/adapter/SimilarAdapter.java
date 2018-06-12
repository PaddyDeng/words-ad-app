package thinku.com.word.ui.report.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.callback.SelectListener;

/**
 *  背单词中相似词
 */

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarHolder> {
    private static final String TAG = SimilarAdapter.class.getSimpleName();
    private List<RecitWordBeen.SimilarWords> similarWordsList ;
    private Context context ;
    private SelectListener selectListener ;
    public SimilarAdapter(Context context , List<RecitWordBeen.SimilarWords> similarWordsList){
        this.context = context ;
        this.similarWordsList = similarWordsList ;
    }
    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener ;
    }
    @Override
    public SimilarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimilarHolder similarHolder = new SimilarHolder(LayoutInflater.from(context).inflate(R.layout.item_similar_word , parent , false));
        return similarHolder;
    }
    @Override
    public void onBindViewHolder(SimilarHolder holder, final int position) {
        RecitWordBeen.SimilarWords similarWords = similarWordsList.get(position);
        holder.word.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.word.getPaint().setAntiAlias(true);
        holder.word.setText(similarWords.getWord());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) selectListener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return similarWordsList == null ? 0 : similarWordsList.size();
    }

    static class SimilarHolder extends RecyclerView.ViewHolder{
        private TextView word ;
        private RelativeLayout rl ;
        public SimilarHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }
}
