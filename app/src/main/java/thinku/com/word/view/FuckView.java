package thinku.com.word.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FuckView extends View {
    private String text="词汇量：3269";
    private List<Map<String, Integer>> mListPoint = new ArrayList<Map<String,Integer>>();

    Paint mPaint = new Paint();
    private float width ;
    private float height ;
    public FuckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

    }

    public FuckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public FuckView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //换图区域，宽高自己设定，要圆要扁请随意
        RectF rectF = new RectF(0, 0, 1000, 500);
        //这个功能的重要点 其实是先画一个轨迹 然后再把文字画到这个轨迹上 所以 想要什么样的扭曲，就画什么样的轨迹，下面就是画一个轨迹
        Path path=new Path();
        //画一个弧形 也可以是椭圆  上面的矩形就是这个图形的区域大小了 后面两个参数记住 一个是起始位置 一个是所画角度而不是终点位置
        path.addArc(rectF,-160,180);
        mPaint.setColor(Color.RED);
        //去锯齿的 不多说
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);

        // 画布下移
        canvas.translate(0, 160);
        // 绘制路径
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        // 下面就是绘制文本了
        mPaint.setStyle(Paint.Style.FILL);
        //把传进来的string转成char【】类型 下面要用到
        char[] chars= text.toCharArray();
        for(int i=0;i<chars.length;i++){
            //这里就是让文字由小到大了，里面的参数嘛 自己看着整 发挥创造性了
            mPaint.setTextSize(60+3*i);
            char[] chars1=new char[1];
            chars1[0]=chars[i];
            //下面就是一个字符一个字符的往后画了 根据自己的需求调整宽度 尺寸
            //这里说明一下参数，免得大家自己又要去看去猜去摸索 如果单纯的不需要文字逐渐变大或者啥的 只需要扭曲
            //那就直接用canvas.drawTextOnPath（）；把string path 往里面传就好了
            //然后下面的0 的位置是X的偏移 1是写Y轴的偏移 这个就可以让那个path是在文字下面 或者穿过中间 或者在下面了
            //差不多了 我很懒 只给个思路 毕竟大家拿过去都要自己改改的
            canvas.drawTextOnPath(chars1,0,1, path, i*(15+i), 20+i, mPaint);

        }


    }

    public void setFuckText(String text)
    {
        this.text=text;
        invalidate();
    }
}
