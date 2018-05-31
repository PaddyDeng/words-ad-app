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
import thinku.com.word.ui.other.dialog.DialogDeleteWordBag;
import thinku.com.word.ui.recite.WordPackageActivity;
import thinku.com.word.view.ProgressView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class WordBagAdapter extends RecyclerView.Adapter {
    private static final int PACK = 1;   //  词包布局
    private static final int ADD = 2;    // 添加词包布局
    private Context context;
    private List<Package.PackData> datas;
    private boolean isDelete;
    private SelectListener listener;
    private int selectP = 0;

    public DeleteListener deleteListener;

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public WordBagAdapter(Context context, List<Package.PackData> datas, SelectListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
    }


    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }


    public void setSelectP(int selectP) {
        this.selectP = selectP;
        notifyDataSetChanged();
    }

    public int getSelectP() {
        return this.selectP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PACK) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_word_bag, parent, false);
            PakcHolder holder = new PakcHolder(v);
            return holder;
        } else if (viewType == ADD) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_add_pack, parent, false);
            AddHolder addHolder = new AddHolder(v);
            addHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WordPackageActivity.start(context);
                }
            });
            return addHolder;
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_add_pack, parent, false);
            AddHolder addHolder = new AddHolder(v);
            addHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WordPackageActivity.start(context);
                }
            });
            return addHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PakcHolder) {
            PakcHolder pakcHolder = (PakcHolder) holder;
            if (isDelete) {
                pakcHolder.delete.setVisibility(View.VISIBLE);
            } else {
                pakcHolder.delete.setVisibility(View.GONE);
            }
            if (position < datas.size()) {
                if (selectP == position) {
                    pakcHolder.study.setVisibility(View.VISIBLE);
                    pakcHolder.rl.setSelected(true);
                    pakcHolder.name.setTextColor(context.getResources().getColor(R.color.white));
                    pakcHolder.num.setTextColor(context.getResources().getColor(R.color.white));
                    //设置进度条颜色
                    pakcHolder.progress.setColor(android.R.color.transparent, R.color.white, R.color.drak_green);
                } else {
                    pakcHolder.study.setVisibility(View.INVISIBLE);
                    pakcHolder.rl.setSelected(false);
                    pakcHolder.name.setTextColor(context.getResources().getColor(R.color.gray_text));
                    pakcHolder.num.setTextColor(context.getResources().getColor(R.color.gray_text));
                    //设置进度条颜色
                    pakcHolder.progress.setColor(android.R.color.transparent, R.color.white, R.color.white);
                }

                final Package.PackData packData = datas.get(position);
                pakcHolder.name.setText(packData.getName());
                pakcHolder.num.setText("(" + packData.getUserWords() + "/" + packData.getTotal() + ")");
                pakcHolder.progress.setMaxCount(Float.parseFloat(packData.getTotal()));
                pakcHolder.progress.setCurrentCount(Float.parseFloat(packData.getUserWords()));
                pakcHolder.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setListener(position);
                        selectP = position;
                        notifyDataSetChanged();
                    }
                });
                pakcHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDeleteWordBag dialog = new DialogDeleteWordBag(context);
                        dialog.show();
                        dialog.setContent("你确定删除" + packData.getName() + "词包的" + packData.getTotal() + "个单词？", deleteListener, position, holder);
                    }
                });
            }
        }
    }


    public int getItemViewType(int position) {
        if (position == datas.size()) {
            return ADD;
        } else if (position < datas.size()) {
            return PACK;
        } else {
            return super.getItemViewType(position);
        }
    }

    public int getItemCount() {
        return null == datas ? 1 : datas.size() + 1;
    }

    class PakcHolder extends RecyclerView.ViewHolder {

        public final TextView name, num, add;
        public final ProgressView progress;
        public final RelativeLayout rl;
        public final LinearLayout ll;
        public final ImageView delete;
        public final TextView study;

        public PakcHolder(View v) {
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
    }


    class AddHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl;

        public AddHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }

}
