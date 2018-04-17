package thinku.com.word.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface SelectRlClickListener {
    void setClickListener(int position , RecyclerView.ViewHolder viewHolder ,View view);
}
