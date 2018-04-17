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

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.TrackBeen;
import thinku.com.word.utils.ColorUtils;
import thinku.com.word.view.LoadingCustomView;
import thinku.com.word.view.ProgressView;

import static thinku.com.word.view.LoadingCustomView.HOLLOW;

/**
 * Created by Administrator on 2018/3/19.
 */

public class GMATBagAdapter extends RecyclerView.Adapter {

    private Context context ;
    private List<TrackBeen.PackageBean> packageBeanList ;
    public GMATBagAdapter(Context context , List<TrackBeen.PackageBean> packageBeanList){
        this.context = context ;
        this.packageBeanList =  packageBeanList ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WordBagHolder wordBagHolder = new WordBagHolder(LayoutInflater.from(context).inflate(R.layout.item_baglist ,parent ,false));
        return wordBagHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordBagHolder wordBagHolder = (WordBagHolder) holder;
        TrackBeen.PackageBean  packageBean = packageBeanList.get(position % packageBeanList.size());
        if (packageBean != null) {
            try {
                wordBagHolder.name.setText(packageBean.getName());
                wordBagHolder.num.setText("（" + packageBean.getConsole() + "/" + packageBean.getAll() + "）");
                wordBagHolder.progressBar.setProgress((packageBean.getConsole() / Float.parseFloat(packageBean.getAll())) * 100);
                wordBagHolder.progressBar.setProgressColor(ColorUtils.getProgressColor(context ,Float.parseFloat(packageBean.getAll()) ,packageBean.getConsole()));
                wordBagHolder.progressBar.setProgressBarFrameHeight(3);
            }catch (Exception e){
                wordBagHolder.name.setText(packageBean.getName());
                wordBagHolder.num.setText("（" + packageBean.getConsole() + "/" + packageBean.getAll() + "）");
                wordBagHolder.progressBar.setProgress((packageBean.getConsole() / Integer.parseInt(packageBean.getAll())) * 100);
                wordBagHolder.progressBar.setProgressColor(context.getResources().getColor(R.color.progress_clolor1));
                wordBagHolder.progressBar.setProgressBarFrameHeight(3);
            }
        }
    }

    @Override
    public int getItemCount() {
        return packageBeanList == null ? 0 : packageBeanList.size();
    }

    private class WordBagHolder extends RecyclerView.ViewHolder{
        private TextView name ;
        private LoadingCustomView progressBar ;
        private TextView num ;
        public WordBagHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            progressBar = (LoadingCustomView) itemView.findViewById(R.id.progress);
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }
}
