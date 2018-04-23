package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectClickListener;
import thinku.com.word.callback.SelectRlClickListener;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PKAdapter extends RecyclerView.Adapter {

    private Context context ;
    private SelectRlClickListener selectRlClickListener ;
    private List<String> stringList ;
    public PKAdapter(Context context ,List<String> stringList){
        this.context = context ;
        this.stringList = stringList ;
    }

    public void setSelectRlClickListener(SelectRlClickListener selectRlClickListener){
        this.selectRlClickListener = selectRlClickListener ;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PKHolder pkHolder = new PKHolder(LayoutInflater.from(context).inflate(R.layout.item_pk_translate ,parent ,false));
        return pkHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PKHolder pkHolder = (PKHolder) holder;
        pkHolder.textView.setText(stringList.get(position));
        pkHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRlClickListener.setClickListener(position ,pkHolder ,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList == null? 0 : stringList.size();
    }

    public class PKHolder  extends RecyclerView.ViewHolder{
        private TextView textView ;
        public PKHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_translate);
        }
    }
}
