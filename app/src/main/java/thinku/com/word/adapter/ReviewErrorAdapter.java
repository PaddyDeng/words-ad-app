package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.WrongIndexBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.view.AutoZoomTextView;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ReviewErrorAdapter extends RecyclerView.Adapter<ReviewErrorAdapter.ViewHolder>{
    private Context context;
    private List<WrongIndexBeen> datas;
    private SelectListener listener;

    public ReviewErrorAdapter(Context context,  List<WrongIndexBeen> datas, SelectListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ReviewErrorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_review_error,parent,false);
        ViewHolder holder =new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewErrorAdapter.ViewHolder holder, final int position) {
        WrongIndexBeen wrongIndexBeen = datas.get(position);
        holder.tv.setText(wrongIndexBeen.getName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final LinearLayout ll;
        private final ImageView iv;
        private final AutoZoomTextView tv;

        public ViewHolder(View v) {
            super(v);
            AutoUtils.autoSize(v);
            ll = (LinearLayout) v.findViewById(R.id.ll);
            iv = (ImageView) v.findViewById(R.id.iv);
            tv = (AutoZoomTextView) v.findViewById(R.id.tv);
        }
    }
}
