package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/20.
 * 单词报告排名adapter
 */

public class WordRankAdapter extends RecyclerView.Adapter {
    private Context context ;

    public WordRankAdapter (Context context){
        this.context = context ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WordRankHolder wordRankHolder = new WordRankHolder(LayoutInflater.from(context).inflate(R.layout.item_word_rank ,parent ,false));
        return wordRankHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordRankHolder wordRankHolder  = (WordRankHolder) holder;
        if (position == 0 ){
            wordRankHolder.score_img.setImageResource(R.mipmap.rank_one);
            wordRankHolder.score_img.setVisibility(View.VISIBLE);
//            wordRankHolder.score_img.setVisibility(View.VISIBLE);
        }else if(position == 1){
            wordRankHolder.score_img.setImageResource(R.mipmap.rank_two);
            wordRankHolder.score_img.setVisibility(View.VISIBLE);
        }else if(position == 2 ){
            wordRankHolder.score_img.setImageResource(R.mipmap.rank_three);
            wordRankHolder.score_img.setVisibility(View.VISIBLE);
        }else{
            wordRankHolder.score_txt.setText(position+"");
            wordRankHolder.score_txt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class WordRankHolder extends RecyclerView.ViewHolder{
        private CircleImageView   head_image;
        private ImageView score_img ;
        private TextView score_txt , name ,score ;
        public WordRankHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            score_img = (ImageView) itemView.findViewById(R.id.score_img);
            head_image = (CircleImageView) itemView.findViewById(R.id.head_image);
            score_txt = (TextView) itemView.findViewById(R.id.score_txt);
            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
        }
    }
}
