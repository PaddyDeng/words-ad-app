package thinku.com.word.ui.periphery;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.gensee.view.GSVideoView;

/**
 * Created by Administrator on 2018/5/11.
 */

public class MoveGSVideoView extends GSVideoView {
    private final static String TAG = MoveGSVideoView.class.getSimpleName();
    private int lastX ;
    private int lastY ;
    public MoveGSVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MoveGSVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveGSVideoView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x ;
                lastY = y ;
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = x -lastX ;
                int offY = y - lastY ;
                move(offX ,offY);
                break;

        }
        return true ;
    }

    /**
     * 移动不能超出边界
     * @param offX
     * @param offY
     */
    public void move(int offX , int offY){
        ViewGroup parent = (ViewGroup) this.getParent();
        int parentRight = parent.getRight() ;
        int parentBottom = parent.getBottom() ;
        Log.i(TAG, "parentRight: " + parentRight + " parentBottom: " + parentBottom);
        if (getLeft() + offX >= 0 && getTop() + offY >= 0 &&
                getRight() + offX <= parentRight && getBottom() + offY <= parentBottom){
            layout(getLeft() + offX , getTop()+offY ,getRight()+offX ,getBottom()+offY);
        }

    }
}
