package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;
import thinku.com.word.bean.PkIndexBeen;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/4/17.
 */

public class PkRankAdapter extends RecyclerView.Adapter {

    private Context context ;
    private List<PkIndexBeen.RankingListBean> rankingListBeans ;

    public PkRankAdapter(Context context , List<PkIndexBeen.RankingListBean> rankingListBeans){
        this.context = context ;
        this.rankingListBeans = rankingListBeans ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PKRankHolder pkRankHolder = new PKRankHolder(LayoutInflater.from(context).inflate(R.layout.item_pk_rank , parent ,false));
        return pkRankHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PKRankHolder  pkRankHolder = (PKRankHolder) holder;
        PkIndexBeen.RankingListBean rankingListBean = rankingListBeans.get(position % rankingListBeans.size());
        if (position == 0 ){
            pkRankHolder.rank_bg.setVisibility(View.VISIBLE);
            pkRankHolder.rank.setVisibility(View.GONE);
            pkRankHolder.rank_bg.setImageResource(R.mipmap.rank_one);
        }else if (position == 1){
            pkRankHolder.rank.setVisibility(View.GONE);
            pkRankHolder.rank_bg.setVisibility(View.VISIBLE);
            pkRankHolder.rank_bg.setImageResource(R.mipmap.rank_two);
        }else if(position == 2){
            pkRankHolder.rank.setVisibility(View.GONE);
            pkRankHolder.rank_bg.setVisibility(View.VISIBLE);
            pkRankHolder.rank_bg.setImageResource(R.mipmap.rank_three);
        }else{
            pkRankHolder.rank_bg.setVisibility(View.GONE);
            pkRankHolder.rank.setVisibility(View.VISIBLE);
            pkRankHolder.rank.setText(position + 1 +"");
        }
        new GlideUtils().loadCircle(context , NetworkTitle.WORDRESOURE + rankingListBean.getImage() , pkRankHolder.head_image);
        pkRankHolder.name.setText(rankingListBean.getNickname());
        pkRankHolder.win_num.setText("win：" + rankingListBean.getWin());
        pkRankHolder.lose_num.setText("lose：" + rankingListBean.getLose());
        pkRankHolder.vocabulary.setText(rankingListBean.getUserWords());
    }

    @Override
    public int getItemCount() {
        return rankingListBeans == null ? 0:rankingListBeans.size();
    }

    public class PKRankHolder extends RecyclerView.ViewHolder{
        private ImageView rank_bg ;
        private CircleImageView head_image ;
        private TextView name , win_num ,lose_num  ,vocabulary , rank ;
        public PKRankHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            rank_bg = (ImageView) itemView.findViewById(R.id.rank_bg);
            head_image = (CircleImageView) itemView.findViewById(R.id.head_image);
            name = (TextView) itemView.findViewById(R.id.name);
            win_num = (TextView) itemView.findViewById(R.id.win_num);
            lose_num = (TextView) itemView.findViewById(R.id.lose_num);
            vocabulary = (TextView) itemView.findViewById(R.id.vocabulary);
            rank = (TextView) itemView.findViewById(R.id.rank);
        }
    }
}
