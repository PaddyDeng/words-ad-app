package thinku.com.word.callback;

import android.support.v7.widget.RecyclerView;

import thinku.com.word.adapter.WordBagAdapter;

/**
 * Created by Administrator on 2018/2/8.
 */

public interface DeleteListener {
    void delete(final RecyclerView.ViewHolder viewHolder , int poisition);
}
