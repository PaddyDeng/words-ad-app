package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;

/**
 * Created by Administrator on 2018/2/22.
 */

public class ErrorTypeAdapter extends RecyclerView.Adapter<ErrorTypeAdapter.ViewHolder>{
    private Context context;
    private String[] datas;
    private SelectListener listener;
    private int selectP=0;

    public ErrorTypeAdapter(Context context, String[] datas, SelectListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ErrorTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_error_type,parent,false);
        ViewHolder holer =new ViewHolder(v);
        return holer;
    }

    @Override
    public void onBindViewHolder(ErrorTypeAdapter.ViewHolder holder, final int position) {
        holder.tv.setText(datas[position]);
        if(selectP==position)holder.iv.setSelected(true);
        else holder.iv.setSelected(false);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectP=position;
                notifyDataSetChanged();
                listener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;
        private final ImageView iv;
        private final View rl;

        public ViewHolder(View v) {
            super(v);
            AutoUtils.autoSize(v);
            tv = (TextView) v.findViewById(R.id.tv);
            iv = (ImageView) v.findViewById(R.id.iv);
            rl = v.findViewById(R.id.rl);
        }
    }
}
