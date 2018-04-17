package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.WordResultBeen;
import thinku.com.word.bean.WrodRateData;

/**
 * Created by Administrator on 2018/4/13.
 */

public class EVWordResultAdapter extends RecyclerView.Adapter {

    private List<WrodRateData> wrodRateDataList ;
    private Context context ;

    public EVWordResultAdapter (Context context ,List<WrodRateData> wrodRateDataList ){
        this.context = context ;
        this.wrodRateDataList = wrodRateDataList ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WordResultHolder wordResultHolder = new WordResultHolder(LayoutInflater.from(context).inflate(R.layout.item_ev_word_result ,parent ,false));
        return wordResultHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordResultHolder wordResultHolder = (WordResultHolder) holder;
        WrodRateData wrodRateData = wrodRateDataList.get(position);
            wordResultHolder.name.setText(wrodRateData.getName());
            wordResultHolder.rate.setText(wrodRateData.getRate());
    }

    @Override
    public int getItemCount() {
        return wrodRateDataList == null ? 0 : wrodRateDataList.size();
    }

    private class WordResultHolder extends RecyclerView.ViewHolder{
        private TextView name ;
        private TextView rate ;
        public WordResultHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            rate = (TextView) itemView.findViewById(R.id.rate);
        }
    }
}
