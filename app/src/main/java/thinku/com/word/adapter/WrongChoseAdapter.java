package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;

/**
 * Created by Administrator on 2018/4/2.
 */

public class WrongChoseAdapter extends RecyclerView.Adapter {
    private static final String TAG = WrongChoseAdapter.class.getSimpleName();
    private List<String> names ;
    private Context context ;
    private SelectListener selectListener;

    public WrongChoseAdapter(Context context ,List<String> names){
        this.names = names ;
        this.context = context ;
    }

    public void setSelectListener (SelectListener selectListener){
        this.selectListener  = selectListener ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WrongChoseHolder holder = new WrongChoseHolder(LayoutInflater.from(context).inflate(R.layout.item_wrong_chose_popwindow ,null ,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        WrongChoseHolder wrongChoseHolder = (WrongChoseHolder) holder;
        String name = names.get(position);
        wrongChoseHolder.name.setText(name);

        if (position == (names.size() -1)){
            wrongChoseHolder.interval.setVisibility(View.GONE);
        }else{
            wrongChoseHolder.interval.setVisibility(View.VISIBLE);
        }
        wrongChoseHolder.chose_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  null ==names ? 0:names.size();
    }

    public class WrongChoseHolder extends RecyclerView.ViewHolder{
        private TextView name ;
        private View interval ;
        private LinearLayout chose_rl ;
        public WrongChoseHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            interval = itemView.findViewById(R.id.interval);
            chose_rl = (LinearLayout) itemView.findViewById(R.id.chose_rl);
        }
    }
}
