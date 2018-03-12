package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.DeleteListener;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.ui.other.dialog.DialogDeleteWordBag;
import thinku.com.word.view.ProgressView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class WordBagAdapter extends RecyclerView.Adapter<WordBagAdapter.ViewHolder>{
    private Context context;
    private List<String> datas;
    private boolean isDelete;
    private SelectListener listener;
    private int selectP=0;

    public WordBagAdapter(Context context, List<String> datas,SelectListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener =listener;
    }

    public void setDelete(boolean isDelete){
        this.isDelete =isDelete;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_word_bag,parent,false);
        ViewHolder holder =new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(isDelete){
            if(null==datas||position==datas.size()){
                holder.delete.setVisibility(View.GONE);
            }else{
                holder.delete.setVisibility(View.VISIBLE);
            }
        }else{
            holder.delete.setVisibility(View.GONE);
        }
        if(null==datas||position==datas.size()){
            holder.ll.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
        }else{
            holder.ll.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
        }

        if(position!=datas.size()){
            if(selectP==position&&position!=datas.size()){
                holder.rl.setSelected(true);
                holder.name.setTextColor(context.getResources().getColor(R.color.white));
                holder.num.setTextColor(context.getResources().getColor(R.color.white));
                //设置进度条颜色
                holder.progress.setColor(android.R.color.transparent,R.color.white,R.color.drak_green);
            }else{
                holder.rl.setSelected(false);
                holder.name.setTextColor(context.getResources().getColor(R.color.black));
                holder.num.setTextColor(context.getResources().getColor(R.color.dark_gray));
                holder.progress.setColor(android.R.color.transparent,R.color.white,R.color.dark_gray_l);
            }

            final String s =datas.get(position);
            holder.name.setText(s);

            holder.progress.setMaxCount(400);
            holder.progress.setCurrentCount(200);


            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setListener(position);
                    selectP=position;
                    notifyDataSetChanged();
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogDeleteWordBag dialog =new DialogDeleteWordBag(context);
                    dialog.show();
                    dialog.setContent("你确定删除" + s + "词包的" + 400 + "个单词？", new DeleteListener() {
                        @Override
                        public void delete() {
                            datas.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return null==datas?1:datas.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView name,num,add;
        private final ProgressView progress;
        private final RelativeLayout rl;
        private final LinearLayout ll;
        private final ImageView delete;

        public ViewHolder(View v) {
            super(v);
            AutoUtils.autoSize(v);
            name = (TextView) v.findViewById(R.id.name);
            num = (TextView) v.findViewById(R.id.num);
            progress = (ProgressView) v.findViewById(R.id.progress);
            rl = (RelativeLayout) v.findViewById(R.id.rl);
            add = (TextView) v.findViewById(R.id.add);
            ll = (LinearLayout) v.findViewById(R.id.ll);
            delete = (ImageView) v.findViewById(R.id.delete);
        }
    }
}
