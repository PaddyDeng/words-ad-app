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

import thinku.com.word.R;
import thinku.com.word.bean.UserRankBeen;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/4/16.
 */

public class RankAdapter extends RecyclerView.Adapter {

    private Context context ;
    private List<UserRankBeen.RankBean> rankBeanList ;

    public RankAdapter(Context context ,List<UserRankBeen.RankBean> rankBeanList){
        this.context = context ;
        this.rankBeanList = rankBeanList  ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankHolder rankHolder = new RankHolder(LayoutInflater.from(context).inflate(R.layout.item_ranking ,parent ,false));
        return rankHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RankHolder rankHolder = (RankHolder) holder;
        UserRankBeen.RankBean rankBean = rankBeanList.get(position % rankBeanList.size());
        if (position == 0){
            rankHolder.ranking_bg.setBackgroundResource(R.mipmap.rank_1);
        }else if(position ==1 ){
            rankHolder.ranking_bg.setBackgroundResource(R.mipmap.rank_2);
        }else if(position ==2 ){
            rankHolder.ranking_bg.setBackgroundResource(R.mipmap.rank_3);
        }else{
            rankHolder.ranking_bg.setBackgroundResource(R.mipmap.rank_no);
        }

        if (rankBean != null){
            rankHolder.name.setText(rankBean.getNickname());
            new GlideUtils().load(context , NetworkTitle.WORDRESOURE + rankBean.getImage() ,rankHolder.portrait);
            rankHolder.num.setText(rankBean.getNum());
            rankHolder.ranking_num.setText(position + 1 +"");
        }
    }

    @Override
    public int getItemCount() {
        return rankBeanList == null ? 0 : rankBeanList.size();
    }

    public class RankHolder extends RecyclerView.ViewHolder{
        private ImageView portrait ;
        private TextView  name ;
        private TextView num ;
        private ImageView ranking_bg ;
        private TextView ranking_num ;
        public RankHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            portrait = (ImageView) itemView.findViewById(R.id.portrait);
            name = (TextView) itemView.findViewById(R.id.name);
            num = (TextView) itemView.findViewById(R.id.num);
            ranking_bg = (ImageView) itemView.findViewById(R.id.ranking_bg);
            ranking_num = (TextView) itemView.findViewById(R.id.ranking_num);
        }
    }
}
