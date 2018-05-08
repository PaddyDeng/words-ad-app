package thinku.com.word.ui.periphery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/5/8.
 */

public class EvaAdapter extends RecyclerView.Adapter {

    private List<RoundBean.CaseBean>  caseBeanList ;
    private Context context ;
    private SelectListener selectListener ;
    public EvaAdapter(Context context , List<RoundBean.CaseBean> caseBeanList){
        this.context = context ;
        this.caseBeanList = caseBeanList ;
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EvaHolder evaHolder = new EvaHolder(LayoutInflater.from(context).inflate(R.layout.item_round_eva , parent , false));
        return evaHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        EvaHolder evaHolder = (EvaHolder) holder;
        RoundBean.CaseBean caseBean = caseBeanList.get(position);
        evaHolder.name.setText(caseBean.getName());
        new GlideUtils().loadRoundCircle(context ,caseBean.getImage() ,evaHolder.image);
        evaHolder.eva_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return caseBeanList == null ? 0 : caseBeanList.size();
    }

    public class EvaHolder extends RecyclerView.ViewHolder{
        private ImageView image ;
        private TextView name ;
        private RelativeLayout eva_rl ;
        public EvaHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            image = (ImageView) itemView.findViewById(R.id.user_image);
            name = (TextView) itemView.findViewById(R.id.user_name);
            eva_rl = (RelativeLayout) itemView.findViewById(R.id.eva_rl);
        }
    }

}
