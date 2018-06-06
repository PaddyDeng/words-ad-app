package thinku.com.word.ui.pk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.PkResultBeen;

/**
 * Created by Administrator on 2018/6/6.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

    private Context context;
    private List<PkResultBeen.QuestionInfoBean> resultAdapterList;

    public ResultAdapter(Context context, List<PkResultBeen.QuestionInfoBean> resultAdapterList) {
        this.context = context;
        this.resultAdapterList = resultAdapterList;
    }

    @Override
    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ResultHolder resultHolder = new ResultHolder(LayoutInflater.from(context).inflate(R.layout.item_pk_result, parent, false));
        return resultHolder;
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        PkResultBeen.QuestionInfoBean questionBean = resultAdapterList.get(position);
        holder.userResult.setText(questionBean.getWords());
        holder.matchResult.setText(questionBean.getAnswer());
        if (questionBean.getSelf() == 1)
            holder.user.setBackgroundResource(R.drawable.right_correct_half_circle);
        else holder.user.setBackgroundResource(R.drawable.right_error_half_circle);
        if (questionBean.getPeer() == 1)
            holder.match.setBackgroundResource(R.drawable.left_correct_half_circle);
        else holder.match.setBackgroundResource(R.drawable.left_error_half_circle);
    }

    @Override
    public int getItemCount() {
        return resultAdapterList == null ? 0 : resultAdapterList.size();
    }

    static class ResultHolder extends RecyclerView.ViewHolder {
        private TextView userResult, matchResult;
        private View user, match;

        public ResultHolder(View itemView) {
            super(itemView);
            userResult = (TextView) itemView.findViewById(R.id.user_result);
            matchResult = (TextView) itemView.findViewById(R.id.match_result);
            user = itemView.findViewById(R.id.user);
            match = itemView.findViewById(R.id.match);
        }
    }
}
