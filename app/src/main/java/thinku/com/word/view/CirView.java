package thinku.com.word.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import thinku.com.word.R;
import thinku.com.word.utils.MeasureUtils;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public class CirView extends View {

    private static final int defultSize = 100;
    private Paint paint;
    private static int mear;
    private static final String NewLableFirst = "平均每天新";
    private static final String NewLableSecond = " 学单词量";
    private static final String ReviewLableFirst = " 平均每天复";
    private static final String ReviewLableSecond = "习单词量";
    private Rect lableWidth;
    private Rect lableSecondWidth;
    private TextPaint textPaintLable;
    private TextPaint textPaintNum;
    private float reviewX;
    private float reviewY;
    private float newX;
    private float newY;
    private float newValue;  //  新学单词数
    private float reviewValue;  //  复习单词数
    private float totalValue;  // 总数
    private float radius;  //  获取圆半径
    private int width;
    private int height;
    private Context context;
    private float newBaseY;  //  新单词的字体base
    private float reviewBaseY;  //  复习单词的字体base
    private float baseTextSize;

    public CirView(Context context) {
        this(context, null);
    }

    public void setData(float newValue, float reviewValue) {
        this.newValue = newValue;
        this.reviewValue = reviewValue;
        totalValue = newValue + reviewValue;
        invalidate();
    }

    public CirView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getCurrentSize(widthMeasureSpec);
        height = getCurrentSize(heightMeasureSpec);
        radius = width > height ? height / 2 : width / 2;
        setMeasuredDimension(width, height);
    }


    //  获取size
    public int getCurrentSize(int measureSpec) {
        int size = defultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int currentSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:  // 没有指定大小 ， 设置为默认
                size = defultSize;
                break;
            case MeasureSpec.AT_MOST:  // 相当于 wrap_content
                size = currentSize;
                break;
            case MeasureSpec.EXACTLY:
                size = currentSize;
                break;
        }
        return size;
    }

    public void drawArc(Canvas canvas) {
        if (totalValue <= 0) {
            return;
        }
        canvas.translate(width / 2, height / 2);
        paint.setColor(Color.GREEN);
        RectF rect = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rect, -90, (float) Math.round((reviewValue / totalValue) * 360), true, paint);
        paint.setColor(context.getResources().getColor(R.color.circle_green));
        canvas.drawArc(rect, (float) Math.round((reviewValue / totalValue) * 360 - 90), (Math.round((newValue / totalValue) * 360)), true, paint);
        //  求出扇形的中间坐标
        onInitValue();

        //  划线
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        canvas.drawLine(reviewX, reviewY, centerX(reviewX), reviewBaseY, paint);
        canvas.drawLine(newX, newY, centerX(newX), newBaseY, paint);
        drawNewText((int) newValue + "", canvas);
        drawReviewText((int) reviewValue + "", canvas);
    }


    /**
     * 如果X 为右边  则+ mear , 否则 - mear
     *
     * @param textX
     * @return
     */
    public float centerX(float textX) {
        if (textX >= 0) {
            return textX + mear;
        } else {
            return textX - mear;
        }
    }


    /**
     * 根据角度大小来确定xy 的正负值
     */
    private void onInitValue() {
        reviewY = -(float) (radius * Math.cos(AngleToRadian(Math.round(reviewValue / 2 / totalValue * 360))));
        reviewX = (float) (radius * Math.sin(AngleToRadian(Math.round(reviewValue / 2 / totalValue * 360))));
        newY = -(float) (radius * Math.cos(AngleToRadian(Math.round((reviewValue + newValue / 2) / totalValue * 360))));
        newX = (float) (radius * Math.sin(AngleToRadian(Math.round((reviewValue + newValue / 2) / totalValue * 360))));
        newBaseY = baseY(newY);
        reviewBaseY = baseY(reviewY);
    }

    /**
     * 如果字体距离圆心的距离大于2分之一 半径 ，  基线就在半径位置，  否则 就在圆心位置
     *
     * @param y
     * @return
     */
    public float baseY(float y) {
        if (Math.abs(y) < radius / 2) {
            return 0;
        } else {
            if (y > 0) {
                return radius / 4;
            } else {
                return -radius / 2;
            }
        }
    }


    public float AngleToRadian(int angle) {
        return (float) ((Math.PI / 180.0f) * angle);
    }

    public void drawNewText(String newWord, Canvas canvas) {
        canvas.drawText(NewLableFirst, centerTextX(newX),
                newBaseY, textPaintLable);
        canvas.drawText(NewLableSecond, centerTextX(newX),
                newBaseY + (lableWidth.bottom - lableWidth.top)
                        + (lableSecondWidth.bottom - lableSecondWidth.top) / 2, textPaintLable);
        Rect rect = new Rect();
        textPaintNum.getTextBounds(newWord, 0, newWord.length(), rect);
        textPaintNum.setColor(context.getResources().getColor(R.color.circle_green));
        canvas.drawText(newWord, centerTextX(newX), newBaseY + (lableWidth.bottom - lableWidth.top) +
                (lableSecondWidth.bottom - lableSecondWidth.top) + (rect.bottom - rect.top), textPaintNum);
    }

    public void drawReviewText(String reviewWord, Canvas canvas) {
        canvas.drawText(ReviewLableFirst, centerTextX(reviewX), reviewBaseY, textPaintLable);
        canvas.drawText(ReviewLableSecond, centerTextX(reviewX), reviewBaseY + (lableWidth.bottom - lableWidth.top) + (lableSecondWidth.bottom - lableSecondWidth.top) / 2, textPaintLable);
        Rect rect = new Rect();
        textPaintNum.getTextBounds(reviewWord, 0, reviewWord.length(), rect);
        textPaintNum.setColor(context.getResources().getColor(R.color.circle_yellow));
        canvas.drawText(reviewWord, centerTextX(reviewX), reviewBaseY + (lableWidth.bottom - lableWidth.top) + (lableSecondWidth.bottom - lableSecondWidth.top) + (rect.bottom - rect.top), textPaintNum);
    }

    /**
     * 如果X 为右边  则+ mear , 否则 - mear
     *
     * @param textX
     * @return
     */
    public float centerTextX(float textX) {
        if (textX >= 0) {
            return textX + mear + (lableWidth.right - lableWidth.left) / 2;
        } else {
            return textX - mear - (lableWidth.right - lableWidth.left) / 2;
        }
    }

    public void initPaint(Context context) {
        this.context = context;
        mear = MeasureUtils.dp2px(context, 60);
        baseTextSize = MeasureUtils.dp2px(context, 13);
        lableWidth = new Rect();
        lableSecondWidth = new Rect();
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.circle_yellow));
        textPaintLable = new TextPaint();
        textPaintLable.setAntiAlias(true);
        textPaintLable.setColor(Color.BLACK);
        textPaintLable.setTextSize(baseTextSize);
        textPaintNum = new TextPaint();
        textPaintNum.setTextSize(baseTextSize);
        textPaintNum.setAntiAlias(true);
        textPaintNum.setTextAlign(Paint.Align.CENTER);
        textPaintLable.setTextAlign(Paint.Align.CENTER);
        textPaintLable.getTextBounds(NewLableFirst, 0, NewLableFirst.length(), lableWidth);
        textPaintLable.getTextBounds(NewLableSecond, 0, NewLableSecond.length(), lableSecondWidth);
    }

}
