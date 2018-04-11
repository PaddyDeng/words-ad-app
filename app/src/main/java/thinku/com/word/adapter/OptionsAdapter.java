package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/21.
 */

public class OptionsAdapter extends RecyclerView.Adapter {
    private Context context ;
    public OptionsAdapter (Context context){
        this.context  = context ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OptionsHolder optionsHolder = new OptionsHolder(LayoutInflater.from(context).inflate(R.layout.item_options ,parent ,false));
        return optionsHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class OptionsHolder extends RecyclerView.ViewHolder{
        private TextView textView ;
        public OptionsHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            textView = (TextView) itemView.findViewById(R.id.options_text);
        }
    }
}
