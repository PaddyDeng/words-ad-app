package thinku.com.word.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thinku.com.word.utils.MeasureUtils;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FuckView extends View {
    private String text="";
    private List<Map<String, Integer>> mListPoint = new ArrayList<Map<String,Integer>>();

    Paint mPaint ;
    private int width ;
    private int height ;
    Point start  ;
    Point end ;
    Point control ;
    private Context context ;
    public FuckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint(context);

    }

    public FuckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public FuckView(Context context) {
        super(context);
        initPaint(context);
    }

    public void initPaint(Context context){
        this.context = context ;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(MeasureUtils.sp2px(context ,17));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        init(width ,height);
    }

    /**
     * 初始化貝塞爾曲線的3個點
     * start  為  （0 ， height/2）
     * end  (width ,height / 2 )
     * control (width / 2 , height)
     * @param width
     * @param height
     */
    public void init(int width ,int height){
        start = new Point(0 , height /2 );
        end = new Point(width , height / 2 );
        control = new Point(width /2 , 0);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //换图区域，宽高自己设定，要圆要扁请随意
        //这个功能的重要点 其实是先画一个轨迹 然后再把文字画到这个轨迹上 所以 想要什么样的扭曲，就画什么样的轨迹，下面就是画一个轨迹
        Path path=new Path();
        path.moveTo(start.x , start.y);
        path.quadTo(control.x , control.y ,end.x ,end.y);
        canvas.drawTextOnPath(text ,path ,0 , MeasureUtils.dp2px(this.context ,10) ,mPaint);
        }



    public void setFuckText(String text)
    {
        this.text=text;
        invalidate();
    }
}
