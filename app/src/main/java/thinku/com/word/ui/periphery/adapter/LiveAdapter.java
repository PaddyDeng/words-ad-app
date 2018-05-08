package thinku.com.word.ui.periphery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.StringUtils;

/**
 * Created by Administrator on 2018/5/7.
 */

public class LiveAdapter extends RecyclerView.Adapter {
    private static final String TAG =LiveAdapter.class.getSimpleName();
    private List<RoundBean.LivePreviewBean.DataBean> dataBeanList ;
    private Context context ;
    private SelectListener selectListener ;
    public  LiveAdapter (Context context , List<RoundBean.LivePreviewBean.DataBean> dataBeenList){
        this.context = context ;
        this.dataBeanList = dataBeenList ;
    }

    public void  SelectListener (SelectListener selectListener){
        this. selectListener = selectListener ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LiveHolder liveHolder = new LiveHolder(LayoutInflater.from(context).inflate(R.layout.item_live ,parent ,false));
        return liveHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LiveHolder liveHolder = (LiveHolder) holder;
        RoundBean.LivePreviewBean.DataBean dataBean = dataBeanList.get(position);
        liveHolder.title.setText(dataBean.getCatName());
        liveHolder.date.setText(dataBean.getCnName());
        liveHolder.name.setText(dataBean.getName());
        liveHolder.introduction.setText(dataBean.getAlternatives());
        GlideUtils.load(context , NetworkTitle.OPENRESOURE + dataBean.getArticle()  ,liveHolder.teacher_image);
        liveHolder.teacher_name.setText(dataBean.getListeningFile());
        if (!TextUtils.isEmpty(dataBean.getIsTitle())){
            liveHolder.month.setText(Integer.parseInt(StringUtils.spiltInt(dataBean.getIsTitle()).get(1)) +" 月课程");
        }else{
            liveHolder.month.setText("");
        }
        liveHolder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    private class LiveHolder extends RecyclerView.ViewHolder{
        private TextView title ,date ,name ,introduction ,teacher_name ,reserve ,month ;
        private CircleImageView teacher_image ;
        public LiveHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.time);
            name = (TextView) itemView.findViewById(R.id.name);
            introduction = (TextView) itemView.findViewById(R.id.introduction);
            teacher_name = (TextView) itemView.findViewById(R.id.teacher_name);
            reserve = (TextView) itemView.findViewById(R.id.reserve);
            teacher_image = (CircleImageView) itemView.findViewById(R.id.teacher_image);
            month = (TextView) itemView.findViewById(R.id.month);
        }
    }
}
