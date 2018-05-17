package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.WordPackageChildeBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.utils.ColorUtils;
import thinku.com.word.view.LoadingCustomView;

import static thinku.com.word.view.LoadingCustomView.HOLLOW;

/**
 * Created by Administrator on 2018/3/27.
 */

public class PackageTwoAdapter  extends RecyclerView.Adapter{

    private Context context ;
    private SelectListener selectListener ;
    private List<WordPackageChildeBeen> wordPackageChildeBeens ;

    public PackageTwoAdapter (Context context ,SelectListener selectListener , List<WordPackageChildeBeen> wordPackageChildeBeens ){
        this.context = context ;
        this.selectListener = selectListener ;
        this.wordPackageChildeBeens = wordPackageChildeBeens ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PackTwoHolder holder = new PackTwoHolder(LayoutInflater.from(context).inflate(R.layout.item_word_secode_title ,parent ,false));
        return holder;
    }

    public void setData(List<WordPackageChildeBeen> wordPackageChildeBeens){
        this.wordPackageChildeBeens = wordPackageChildeBeens ;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PackTwoHolder packTwoHolder = (PackTwoHolder) holder;
        WordPackageChildeBeen childeBeens = wordPackageChildeBeens.get(position);
        try {
            float userwords = Float.valueOf(childeBeens.getUserWords());
            float total = Float.valueOf(childeBeens.getTotal());
            packTwoHolder.word_package_title.setText(childeBeens.getName() + "（" + childeBeens.getUserWords() + "/" + childeBeens.getTotal() + "）");
            packTwoHolder.progress.setProgressBankgroundColor(context.getResources().getColor(R.color.color_progress_side));
            packTwoHolder.progress.setProgress((userwords / total) * 100);
            packTwoHolder.progress.setProgressBarBankgroundStyle(HOLLOW);
            packTwoHolder.progress.setProgressColor(ColorUtils.getProgressColor(context ,total ,userwords));
            packTwoHolder.progress.setProgressBarFrameHeight(3);
            if (childeBeens.getIs() == 1 &&  userwords == total){
                packTwoHolder.status.setText("已完成");
                packTwoHolder.status.setVisibility(View.VISIBLE);
            }else if (childeBeens.getIs() ==1 ){
                packTwoHolder.status.setText("已选择");
                packTwoHolder.status.setVisibility(View.VISIBLE);
            }else{
                packTwoHolder.status.setVisibility(View.GONE);
            }
        }catch (Exception e){
            packTwoHolder.progress.setProgressBankgroundColor(context.getResources().getColor(R.color.color_progress_side));
            packTwoHolder.progress.setProgress(40f);
            packTwoHolder.progress.setProgressBarBankgroundStyle(HOLLOW);
            packTwoHolder.progress.setProgressColor(context.getResources().getColor(R.color.progress_clolor1));
            packTwoHolder.progress.setProgressBarFrameHeight(3);
        }
        packTwoHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wordPackageChildeBeens.size();
    }

    class PackTwoHolder extends RecyclerView.ViewHolder{
        private TextView word_package_title ,status ;
        private LoadingCustomView progress ;
        private RelativeLayout rl ;
        public PackTwoHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            word_package_title = (TextView) itemView.findViewById(R.id.word_package_title);
            progress = (LoadingCustomView) itemView.findViewById(R.id.word_package_progress);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
