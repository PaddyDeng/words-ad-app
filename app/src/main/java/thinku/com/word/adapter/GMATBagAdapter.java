package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import thinku.com.word.R;
import thinku.com.word.view.ProgressView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class GMATBagAdapter extends RecyclerView.Adapter {

    private Context context ;
    public GMATBagAdapter(Context context){
        this.context = context ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WordBagHolder wordBagHolder = new WordBagHolder(LayoutInflater.from(context).inflate(R.layout.item_baglist ,parent ,false));
        return wordBagHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private class WordBagHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar ;
        private TextView num ;
        public WordBagHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }
}
