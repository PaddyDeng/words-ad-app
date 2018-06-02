package thinku.com.word.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/6/2/002.
 */

public class NoScrollView extends ScrollView {
    public NoScrollView(Context context) {
        super(context);
    }

    public NoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 解决RecyclerView 自己滚到RecyclerView 中
     * @param direction
     * @param previouslyFocusedRect
     * @return
     */
    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return true ;
    }
}
