package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectClickListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.ui.report.bean.WordEva;

/**
 * Created by Administrator on 2018/4/13.
 */

public class EvaWordAdapter extends RecyclerView.Adapter {
    private List<WordEva> eva;
    private Context context ;
    private SelectRlClickListener selectRlClickListener;
    public EvaWordAdapter(Context context ,List<WordEva> eva){
        this.eva = eva ;
        this.context = context ;
    }

    public void setSelectClickListener(SelectRlClickListener selectRlClickListener){
        this.selectRlClickListener = selectRlClickListener ;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EvaHolder evaHolder = new EvaHolder(LayoutInflater.from(context).inflate(R.layout.item_eva_wrod ,parent ,false));
        return evaHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final EvaHolder evaHolder = (EvaHolder) holder;
            WordEva wordEva = eva.get(position);
            if (position < eva.size()) evaHolder.evaItem.setText(wordEva.getContent());
            else evaHolder.evaItem.setText("不认识");
            evaHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectRlClickListener.setClickListener(position, evaHolder, v);
                }
            });
            if (wordEva.isAnswer()) {
                evaHolder.rl.setBackground(context.getResources().getDrawable(R.drawable.main_20round_tv));
            }
    }

    @Override
    public int getItemCount() {
        return (eva == null && eva.size() > 0 )? 0 : (eva.size() + 1);
    }

    public class EvaHolder extends RecyclerView.ViewHolder{
        public TextView evaItem ;
        public ImageView error ;
        public RelativeLayout rl ;
        public EvaHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            evaItem = (TextView) itemView.findViewById(R.id.item);
            error = (ImageView) itemView.findViewById(R.id.error);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }
}
