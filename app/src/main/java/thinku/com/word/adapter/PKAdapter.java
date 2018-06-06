package thinku.com.word.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.been.PKSelctBeen;
import thinku.com.word.callback.SelectAnswerClickListener;
import thinku.com.word.callback.SelectClickListener;
import thinku.com.word.callback.SelectRlClickListener;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PKAdapter extends RecyclerView.Adapter {
    private static final String TAG = PKAdapter.class.getSimpleName();
    private Context context ;
    private SelectAnswerClickListener selectAnswerClickListener ;
    private List<PKSelctBeen> stringList ;
    private int currentPosition =  -1 ;  //  正确的
    public PKAdapter(Context context ,List<PKSelctBeen> stringList  ){
        this.context = context ;
        this.stringList = stringList ;
    }

    public void setSelectRlClickListener(SelectAnswerClickListener selectAnswerClickListener){
        this.selectAnswerClickListener = selectAnswerClickListener ;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PKHolder pkHolder = new PKHolder(LayoutInflater.from(context).inflate(R.layout.item_pk_translate ,parent ,false));
        return pkHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PKHolder pkHolder = (PKHolder) holder;
        PKSelctBeen pkSelectBeen = stringList.get(position);
        pkHolder.textView.setText(pkSelectBeen.getSelect());
        if (pkSelectBeen.isAnswer()){
            currentPosition  = position;
            Log.d(TAG, "currentPosition>>>>>>>>>>>>>>>>>>>" + currentPosition );
            pkHolder.textView.setBackgroundResource(R.drawable.pk_select_main);
        }else{
            pkHolder.textView.setBackgroundResource(R.drawable.pk_select_red);
        }

        if (pkSelectBeen.isChose()){
            pkHolder.textView.setSelected(true);
            pkHolder.textView.setTextColor(Color.WHITE);
        }else{
            pkHolder.textView.setSelected(false);
            pkHolder.textView.setTextColor(context.getResources().getColor(R.color.gray_text));
        }
        pkHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswerClickListener.onClick(position ,v ,currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  stringList == null ? 0 :stringList.size();
    }

    public class PKHolder  extends RecyclerView.ViewHolder{
        private TextView textView ;
        public PKHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_translate);
        }
    }
}
