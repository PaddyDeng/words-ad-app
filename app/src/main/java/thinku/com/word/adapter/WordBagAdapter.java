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
import thinku.com.word.bean.Package;
import thinku.com.word.callback.DeleteListener;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.ui.other.dialog.DialogDeleteWordBag;
import thinku.com.word.ui.recite.WordPackageActivity;
import thinku.com.word.view.ProgressView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class WordBagAdapter extends RecyclerView.Adapter<WordBagAdapter.ViewHolder> {
    private Context context;
    private List<Package.PackData> datas;
    private boolean isDelete;
    private SelectRlClickListener listener;
    private int selectP = 0;
    public DeleteListener deleteListener ;

    public WordBagAdapter(Context context, List<Package.PackData> datas, SelectRlClickListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    public void setDeleteListener(DeleteListener deleteListener){
        this.deleteListener = deleteListener ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_word_bag, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (isDelete) {
            if (null == datas || position == datas.size()) {
                holder.delete.setVisibility(View.GONE);
            } else {
                holder.delete.setVisibility(View.VISIBLE);
            }
        } else {
            holder.delete.setVisibility(View.GONE);
        }
        if (null == datas || position == datas.size()) {
            holder.ll.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
        } else {
            holder.ll.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordPackageActivity.start(context);
            }
        });

        if (position != datas.size()) {
            final Package.PackData packData = datas.get(position);
            holder.name.setText(packData.getName());
            holder.num.setText("(" + packData.getUserWords() + "/" + packData.getTotal() + ")");
            holder.progress.setMaxCount(Float.parseFloat(packData.getTotal()));
            holder.progress.setCurrentCount(Float.parseFloat(packData.getUserWords()));


            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setClickListener(position , holder ,v);
                    selectP = position;
                    notifyDataSetChanged();
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogDeleteWordBag dialog = new DialogDeleteWordBag(context);
                    dialog.show();
                    dialog.setContent("你确定删除" + packData.getName() + "词包的" + packData.getTotal() + "个单词？" , deleteListener , position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return null == datas ? 1 : datas.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name, num, add;
        public final ProgressView progress;
        public final RelativeLayout rl;
        public final LinearLayout ll;
        public final ImageView delete;
        public final TextView study;

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
            study = (TextView) v.findViewById(R.id.study);
        }

        public void autoClick(){
            rl.performClick();
        }
    }
}
