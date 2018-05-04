package thinku.com.word.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/5/4.
 */

public interface LongClickLisetener {
    void LongClickListener(int position , RecyclerView.ViewHolder hodler ,View view);
}
