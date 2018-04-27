package thinku.com.word.ui.pk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.pk.PkDiscoverDetailActivity;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/4/26.
 */

public class PkWordAdapter extends RecyclerView.Adapter {
    private static final String TAG = PkWordAdapter.class.getSimpleName();
    private Context context;
    private List<PkWordData.DataBean> dataBeanList;

    public PkWordAdapter(Context context, List<PkWordData.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PkWordHolder pkWordHolder = new PkWordHolder(LayoutInflater.from(context).inflate(R.layout.item_pk_word, parent, false));
        return pkWordHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PkWordHolder pkWordHolder = (PkWordHolder) holder;
        final PkWordData.DataBean dataBean = dataBeanList.get(position);
        new GlideUtils().loadRoundCircle(context, NetworkTitle.WORDRESOURE + dataBean.getImage(), pkWordHolder.image);
        pkWordHolder.title.setText(dataBean.getTitle());
        pkWordHolder.date.setText(dataBean.getDate());
        pkWordHolder.pk_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkDiscoverDetailActivity.start(context ,dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    public class PkWordHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView date;
        private TextView title;
        private RelativeLayout pk_rl ;

        public PkWordHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            image = (ImageView) itemView.findViewById(R.id.img);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);
            pk_rl = (RelativeLayout) itemView.findViewById(R.id.pk_rl);
        }
    }
}
