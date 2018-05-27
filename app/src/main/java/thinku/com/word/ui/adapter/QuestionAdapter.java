package thinku.com.word.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.ui.report.bean.QuestionBean;

/**
 * Created by Administrator on 2018/5/25.
 */

public class QuestionAdapter extends RecyclerView.Adapter {

    private Context context ;
    private List<QuestionBean.QslctarrBean> contents ;
    private SelectRlClickListener  selectRlClickListener ;
    public QuestionAdapter(Context context , List<QuestionBean.QslctarrBean> contents){
        this.contents = contents ;
        this.context = context ;
    }

    public void setSelectRlClickListener(SelectRlClickListener selectListener){
        this.selectRlClickListener = selectListener ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        QuestionHolder questionHolder = new QuestionHolder(LayoutInflater.from(context).inflate(R.layout.item_question ,parent ,false));
        return questionHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final QuestionHolder questionHolder = (QuestionHolder) holder;
        QuestionBean.QslctarrBean qslctarrBean = contents.get(position);
        if (qslctarrBean.isAnswer() == 1){
            questionHolder.question.setImageResource(R.mipmap.green_circle);
        }else if (qslctarrBean.isAnswer() == 0){
            questionHolder.question.setImageResource(R.mipmap.gray_circle);
        }else{
            questionHolder.question.setImageResource(R.mipmap.circle_red);
        }

        questionHolder.textView.setText(qslctarrBean.getName() + "  " +Html.fromHtml(qslctarrBean.getSelect()));
        questionHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRlClickListener.setClickListener(position ,questionHolder ,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents == null ? 0 : contents.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder{
        public ImageView question ;
        public TextView textView ;
        private LinearLayout rl ;
        public QuestionHolder(View itemView) {
            super(itemView);
            question = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.content);
            rl = (LinearLayout) itemView.findViewById(R.id.rl);
        }

        public void setImage(int imageId){
            question.setImageResource(imageId);
        }

    }
}
