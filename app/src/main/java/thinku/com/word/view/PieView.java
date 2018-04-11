package thinku.com.word.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/20.
 * 圆环viwe
 */

public class PieView extends View {

    private int ScrWidth, ScrHeight;
    private Context context;
    //演示用的百分比例,实际使用中，即为外部传入的比例参数
    private  float arrPer[] = new float[] {
            25f ,25f ,25f ,25f
    };
    private  int value = 3600 ;
    private final int colors[] = new int[]{
            R.color.pie_green, R.color.pie_orange_3, R.color.pie_blue, R.color.pie_orange
    };

    public void setData(float[] arrPer ,int value){
        this.arrPer = arrPer ;
        this.value = value ;
        this.invalidate();
    }

    public PieView(Context context ) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ScrWidth = MeasureSpec.getSize(widthMeasureSpec);
        ScrHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void onDraw(Canvas canvas) {
        float cirX = ScrWidth / 2;
        float cirY = ScrHeight / 2;
        float radius = ScrHeight / 2;

        float arcLeft = cirX - radius;
        float arcTop = cirY - radius;
        float arcRight = cirX + radius;
        float arcBottom = cirY + radius;
        RectF arcRF0 = new RectF(arcLeft, arcTop, arcRight, arcBottom);


        //位置计算类
        XChartCalc xcalc = new XChartCalc();
        //画笔初始化
        Paint PaintArc = new Paint();
        PaintArc.setAntiAlias(true);
        float Percentage = 0.0f;
        float CurrPer = 0.0f;
        int i = 0;
        for (i = 0; i < arrPer.length; i++) {

            //将百分比转换为饼图显示角度
            Percentage = 360 * (arrPer[i] / 100);
            Percentage = (float) (Math.round(Percentage * 100)) / 100;
            PaintArc.setColor(context.getResources().getColor(colors[i]));
            //在饼图中显示所占比例
            canvas.drawArc(arcRF0, CurrPer, Percentage, true, PaintArc);
            //下次的起始角度
            CurrPer += Percentage;
        }
        //画圆心
        PaintArc.setColor(Color.WHITE);
        canvas.drawCircle(cirX, cirY, radius / 1.5f, PaintArc);
        TextPaint paint = new TextPaint();
        paint.setColor(context.getResources().getColor(R.color.gray_text));
        paint.setTextSize(30);
        String text = "总量";
        int baseX = (int) (cirX - paint.measureText(text) / 2);
        int baseY = (int) (cirY - radius / 1.5f / 5f);
        canvas.drawText(text, baseX, baseY, paint);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int baseX1 = (int) (cirX - paint.measureText("" + value) / 2);
        int baseY1 = (int) (cirY + fontMetrics.descent - fontMetrics.ascent);
        canvas.drawText("" + value, baseX1, baseY1, paint);
    }

}
