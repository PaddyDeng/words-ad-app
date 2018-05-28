
        package thinku.com.word.view;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Paint.Align;
        import android.graphics.Paint.Style;
        import android.graphics.Rect;
        import android.graphics.RectF;
        import android.support.annotation.ColorInt;
        import android.text.TextPaint;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.MotionEvent;
        import android.view.View;

        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import thinku.com.word.R;

/**
 * 柱状�?
 */
public class BarChartView extends View {
    private static final String TAG = BarChartView.class.getSimpleName();
    /**
     * 公共部分
     */
    private String xString = "1天前";
    protected float canvasHeight;
    protected float canvasWidth;
    private Paint mPaint;
    private int maxValue = 0;// Y轴最大值
    private int minValue;
    // private float titleHeight = 0.0f;
    private float taggingHeight; // 标注的高度
    private float marginTop; // 与顶部留的空隙
    private float marginBottom = 0; // 与底部留的空隙
    private float marginLeft; // 与顶部留的空隙
    private float marginRight = 0; // 与底部留的空隙

    private int horizontalNum = 7;// 横坐标数量

    private RectF coordinateRect = new RectF(); // 画图区域

    private int colorCoordinates = 0xFF999999; // 坐标轴的颜色

    private int[] colors = {R.color.color_notknow, R.color.color_forget, R.color.color_dim ,
    R.color.color_know , R.color.color_know_well}; // 柱子的颜色
    private int yIndex = 5; // Y轴位置
    private Map<String, List<Integer>> yRawData = new HashMap<>();
    private int priceWeight = 1; // 倍数


    public void setMaxValue(int Max) {
        this.maxValue = Max + (Max / 10);
    }

    /**
     * 横坐标值
     */
    private List<String> xRawDatas = new ArrayList<>();

    private String company = ""; // 单位
    private boolean isCompanyUpdate = false; // 单位
    private String companyNews; // 单位
    private List<String> tagging = new ArrayList<>(); // 标注
    private List<TaggingCoordinate> taggingCoordinates = new ArrayList<>(); // 标注的坐标

    private int offsetWidth = 0;    //  整个坐标系的宽度
    private int offsetWidthMax = 0;
    private GestureDetector mGestureDetector;
    private boolean isInteger = true;// 是否是整数坐标
    private float cutoffwidth = 0;
    private TextPaint hintPaint;
    private Rect rectF;
    private Paint paint;
    private float x ;  //  x轴的y坐标的点
    private Context context ;
    private TextPaint hintTextPaint ;

    public BarChartView(Context context, List<String> tagging,
                        List<String> xRawData, List<Float>... yRawData) {
        super(context);
        initView(context);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setWillNotDraw(false);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginBottom = dip2px(10);
        marginLeft = dip2px(30);
        marginRight = dip2px(10);
        marginTop = dip2px(10);
        taggingHeight = 0;
        mGestureDetector = new GestureDetector(getContext(),
                new GestureListener());
        hintPaint = new TextPaint();
        hintPaint.setTextSize(sp2px(12));
        hintPaint.setTextAlign(Align.CENTER);
        hintPaint.setColor(getResources().getColor(R.color.mainColor));

        hintTextPaint = new TextPaint();
        hintTextPaint.setTextSize(sp2px(12));
        hintTextPaint.setTextAlign(Align.CENTER);
        hintTextPaint.setAntiAlias(true);
        hintTextPaint.setColor(getResources().getColor(R.color.black));
        paint = new Paint();
        paint.setTextSize(sp2px(10));
        paint.setAntiAlias(true);
        this.context  =  context ;
    }

    private float mDownPosX = 0;
    private float mDownPosY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (offsetWidthMax != 0) {
            final float x = event.getX();
            final float y = event.getY();
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mDownPosX = x;
                    mDownPosY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float deltaX = Math.abs(x - mDownPosX);
                    final float deltaY = Math.abs(y - mDownPosY);
                    if (deltaX > deltaY) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                    break;
            }
            mGestureDetector.onTouchEvent(event);
            return true;
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
            return super.dispatchTouchEvent(event);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.canvasHeight = h;
        this.canvasWidth = w;
        rectF = new Rect();
        paint.getTextBounds(xString, 0, xString.length(), rectF);
        coordinateRect = new RectF(marginLeft, marginTop + taggingHeight,
                canvasWidth - marginRight, canvasHeight - marginBottom - rectF.height() * 3);
        horizontalNum = (int) (coordinateRect.width() / dip2px(60));
        updateCutoffwidth(offsetWidth);
    }

    public int getSize() {
        if (xRawDatas == null) {
            return 0;
        }
        return xRawDatas.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(dip2px(1));
        drawAllXLine(canvas);
        drawBar(canvas);
    }

    /**
     * 画所有横向表格，包括X轴
     */
    private void drawAllXLine(Canvas canvas) {
        float cutoffHeight = (coordinateRect.height() - rectF.height() * 3) / 5;
        int averageValue = (maxValue - minValue - 1) / 5 + 1;

        float startX = coordinateRect.left + offsetWidth;
        float stopX = coordinateRect.right + offsetWidth;
        for (int i = 0; i < 6; i++) {
            if (yIndex == i) {
                drawText("0", startX - dip2px(5),
                        coordinateRect.height() - dip2px(10) - rectF.height() / 2, canvas, Align.RIGHT, 8,
                        colorCoordinates);
                mPaint.setColor(colorCoordinates);
                x = (coordinateRect.height() - dip2px(10) - rectF.height() /2) ;
                canvas.drawLine(startX, coordinateRect.top, startX,
                        coordinateRect.height() - dip2px(10) - rectF.height() / 2, mPaint);
                canvas.drawLine(startX, coordinateRect.height() - dip2px(10) - rectF.height() / 2,
                        stopX, coordinateRect.height() - dip2px(10) - rectF.height() / 2, mPaint);  // x 轴
            } else {
                drawText((maxValue - averageValue * i)
                                / priceWeight
                                + "", startX - dip2px(5), coordinateRect.top
                                + cutoffHeight * i + dip2px(2), canvas, Align.RIGHT, 8,
                        colorCoordinates);
                canvas.drawLine(startX, coordinateRect.top + cutoffHeight * i + dip2px(2), startX + coordinateRect.width(), coordinateRect.top + cutoffHeight * i + dip2px(2), hintPaint);
            }
        }
    }

    /**
     * 画柱子
     */
    private void drawBar(Canvas canvas) {
        boolean  isRel = false ;
        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < xRawDatas.size(); i++) {
            float stopY = x;
            float newY = x;
            float startX  = x;
            float stopX = x  ;
            float total  = 0 ;
            if (xRawDatas.get(i).indexOf("后") != -1){
                isRel = true ;
            }
            List<Integer> data = yRawData.get(xRawDatas.get(i));
            for (int j = 0; j < data.size(); j++) {

                //  画柱状图
                 startX =  coordinateRect.left + cutoffwidth
                        * i * (yRawData.size() + 1) + cutoffwidth + cutoffwidth
                        * yRawData.size() / 2  - coordinateRect.width() / 15 / 2;
                 stopX = startX + coordinateRect.width() / 15;
                if (stopX <= offsetWidth + coordinateRect.left) {
                    continue;
                }
                Integer yData = data.get(j);
                if (yData == Float.MAX_VALUE) {
                    continue;
                }
                newY = stopY - getCutoffKLY(yData);
                if (newY < coordinateRect.top){
                    newY = coordinateRect.top ;
                }
                if (!isRel) mPaint.setColor(context.getResources().getColor(colors[j % colors.length]));
                else mPaint.setColor(context.getResources().getColor(R.color.gray_text));
                if (newY > coordinateRect.height()){
                    newY = coordinateRect.height();
                }
                if (startX < offsetWidth + coordinateRect.left) {
                    startX = offsetWidth + coordinateRect.left;
                } else if (stopX > offsetWidth + coordinateRect.right) {
//                    stopX = offsetWidth + coordinateRect.right;
                } else {

                }
                canvas.drawRect(startX, newY, stopX, stopY, mPaint);
                total += yData;
                stopY = newY;
                }
                if (total >0) canvas.drawText((int)total+"" ,startX + (stopX - startX) /2  ,stopY - dip2px(2) , hintTextPaint);
            if (coordinateRect.left + cutoffwidth * i * (yRawData.size() + 1)
                    + cutoffwidth > offsetWidth + coordinateRect.left) {
                String s = xRawDatas.get(i);
                if (s.length() >= 3) {
                    for (int k = 0; k < 3; k++) {
                        String text;
                        if (s.length() == 3) {
                            text = s.substring(k, k + 1);
                            drawText(text, coordinateRect.left + cutoffwidth
                                            * i * (yRawData.size() + 1) + cutoffwidth + cutoffwidth
                                            * yRawData.size() / 2, coordinateRect.height() - dip2px(5) + rectF.height() * k,
                                    canvas, Align.CENTER, 10, Color.BLACK); // X轴上的坐标
                        } else {
                            if (k == 0) {
                                text = s.substring(k, k + 2);
                            } else {
                                text = s.substring(k + 1, k + 2);
                            }

                            drawText(text, coordinateRect.left + cutoffwidth
                                            * i * (yRawData.size() + 1) + cutoffwidth + cutoffwidth
                                            * yRawData.size() / 2, coordinateRect.height() - dip2px(5) + rectF.height() * k,
                                    canvas, Align.CENTER, 10, Color.BLACK); // X轴上的坐标
                        }

                    }
                } else {
                    String text;
                    for (int m = 0; m < 2; m++) {
                        text = s.substring(m, m + 1);
                        drawText(text, coordinateRect.left + cutoffwidth
                                        * i * (yRawData.size() + 1) + cutoffwidth + cutoffwidth
                                        * yRawData.size() / 2, coordinateRect.height() - dip2px(5) + rectF.height() * m,
                                canvas, Align.CENTER, 10, Color.BLACK); // X轴上的坐标
                    }
                }
            }
        }
    }

    /**
     * 设置值 tagging：标注 xRawData：x轴坐标 yRawData：为柱形图的内容
     */
    public void setData(List<String> xRawData,
                        Map<String, List<Integer>> yRawData) {
        this.xRawDatas.clear();
        this.yRawData.clear();
        this.xRawDatas.addAll(xRawData);
        this.yRawData = yRawData;
        updateCutoffwidth1((int) (16 * cutoffwidth));
        invalidate();
    }


    private void updateCutoffwidth( int  width) {
        if (xRawDatas != null) {
            if (xRawDatas.size() > horizontalNum) {
                offsetWidthMax = (int) (((this.yRawData.size() + 1)
                        * xRawDatas.size() + 1)
                        * coordinateRect.width()
                        / ((this.yRawData.size() + 1) * horizontalNum + 1) - coordinateRect
                        .width());
                offsetWidth = offsetWidthMax;
                cutoffwidth = coordinateRect.width()
                        / (horizontalNum * (this.yRawData.size() + 1) + 1);
                int start = (int) (offsetWidth / ((this.yRawData.size() + 1) * cutoffwidth));
                int stop = (int) ((offsetWidth + coordinateRect.width() + cutoffwidth
                        * this.yRawData.size()) / ((this.yRawData.size() + 1) * cutoffwidth));
                initMaxAndMin(start, stop);
            } else {
                cutoffwidth = coordinateRect.width()
                        / (xRawDatas.size() * (this.yRawData.size() + 1) + 1);
                if (cutoffwidth > coordinateRect.width() / 8) {
                    cutoffwidth = coordinateRect.width() / 8;
                }
                initMaxAndMin(0, this.xRawDatas.size());
                offsetWidth = 0;
                offsetWidthMax = 0;
            }
            scroll(width);
        }
    }


    private void updateCutoffwidth1( int  width) {
        if (xRawDatas != null) {
            if (xRawDatas.size() > horizontalNum) {
                offsetWidthMax = (int) (((this.yRawData.size() + 1)
                        * xRawDatas.size() + 1)
                        * coordinateRect.width()
                        / ((this.yRawData.size() + 1) * horizontalNum + 1) - coordinateRect
                        .width());
                offsetWidth = width;
                cutoffwidth = coordinateRect.width()
                        / (horizontalNum * (this.yRawData.size() + 1) + 1);
                int start = (int) (offsetWidth / ((this.yRawData.size() + 1) * cutoffwidth));
                int stop = (int) ((offsetWidth + coordinateRect.width() + cutoffwidth
                        * this.yRawData.size()) / ((this.yRawData.size() + 1) * cutoffwidth));
                initMaxAndMin(start, stop);
            } else {
                cutoffwidth = coordinateRect.width()
                        / (xRawDatas.size() * (this.yRawData.size() + 1) + 1);
                if (cutoffwidth > coordinateRect.width() / 8) {
                    cutoffwidth = coordinateRect.width() / 8;
                }
                initMaxAndMin(0, this.xRawDatas.size());
                offsetWidth = 0;
                offsetWidthMax = 0;
            }
            scroll(width);
        }
    }


    //  确定矩形大小以及Y轴数据
    private boolean initMaxAndMin(int start, int stop) {
        if (stop <= 0) {
            return true;
        }
        if (stop > this.xRawDatas.size()) {
            stop = this.xRawDatas.size();
        }
        Map<String, List<Integer>> yRawDataNews = new HashMap<>();
        for (int i = start; i < stop; i++) {
            yRawDataNews.put(xRawDatas.get(i), yRawData.get(xRawDatas.get(i)));
        }
        int maxValueNews = maxValue;
        int minValueNews = 0;
        if (isInteger) {
            if (maxValueNews >= 0 && minValueNews >= 0) {
                minValueNews = 0;
                maxValueNews = ((int) ((maxValueNews - 1) / 5 + 1)) * 5;
                yIndex = 5;
            } else if (maxValueNews <= 0 && minValueNews < 0) {
                maxValueNews = 0;
                minValueNews = ((int) ((minValueNews) / 5)) * 5;
                yIndex = 0;
            } else {
                float proportion = Math.abs(maxValueNews / minValueNews); // 比例
                if (proportion >= 4) {
                    maxValueNews = ((int) ((maxValueNews - 1) / 4 + 1)) * 4;
                    minValueNews = -maxValueNews / 4;
                    yIndex = 4;
                } else if (proportion >= 1) {
                    maxValueNews = ((int) ((maxValueNews - 1) / 3 + 1)) * 3;
                    minValueNews = -maxValueNews * 2 / 3;
                    yIndex = 3;
                } else if (proportion >= 1 / 4) {
                    minValueNews = ((int) ((minValueNews) / 3)) * 3;
                    maxValueNews = -minValueNews * 2 / 3;
                    yIndex = 2;
                } else {
                    minValueNews = ((int) ((minValueNews) / 4)) * 4;
                    maxValueNews = -minValueNews / 4;
                    yIndex = 1;
                }
            }
        } else {
            if (minValueNews < 0 && maxValueNews > 0) {
                float proportion = Math.abs(maxValueNews / minValueNews); // 比例
                if (proportion >= 4) {
                    minValueNews = -maxValueNews / 4;
                    yIndex = 4;
                } else if (proportion >= 1) {
                    minValueNews = -maxValueNews * 2 / 3;
                    yIndex = 3;
                } else if (proportion >= 1 / 4) {
                    maxValueNews = -minValueNews * 2 / 3;
                    yIndex = 2;
                } else {
                    maxValueNews = -minValueNews / 4;
                    yIndex = 1;
                }
            } else if (minValueNews >= 0 && maxValueNews > 0) {
                yIndex = 5;
                minValueNews = 0;
            } else if (minValueNews < 0 && maxValueNews <= 0) {
                yIndex = 0;
                maxValueNews = 0;
            }
        }

        if (maxValueNews == minValueNews) {
            minValueNews = +1;
        }
        if (maxValueNews == maxValue && minValue == minValueNews) {
            return true;
        }
        maxValue = maxValueNews;
        minValue = minValueNews;
        if (isCompanyUpdate) {
            if (maxValue > 300000000 || minValue < -300000000) {
                companyNews = "亿" + company;
                priceWeight = 100000000;
            } else if (maxValue > 3000000 || minValue < -3000000) {
                companyNews = "百万" + company;
                priceWeight = 1000000;
            } else if (maxValue > 30000 || minValue < -30000) {
                companyNews = "万" + company;
                priceWeight = 10000;
            } else if (maxValue > 300 || minValue < -300) {
                companyNews = "百" + company;
                priceWeight = 100;
            } else {
                companyNews = company;
            }
        } else {
            companyNews = company;
        }
        if (minValue < 0) {
            marginBottom = dip2px(30);
        }
        coordinateRect = new RectF(marginLeft, marginTop,
                canvasWidth - marginRight, canvasHeight - marginBottom);
        return false;
    }

    /**
     * 根据数据大小返回Y坐标
     */
    private float getCutoffKLY(float price) {
        Log.e(TAG, "getCutoffKLY: " + price +"  " + maxValue );
        float priceY =(coordinateRect.height()  - coordinateRect.height() / 15) * price / maxValue ;
//        if (priceY < coordinateRect.top)
//            priceY = coordinateRect.top;
//        if (priceY > coordinateRect.height() - dip2px(10) - rectF.height() / 2)
//            priceY = coordinateRect.height() - dip2px(10) - rectF.height() / 2;
        return priceY;
    }


    /**
     * 设置是否内容为整型
     *
     * @param integer
     */
    public void setInteger(boolean integer) {
        isInteger = integer;
    }


    private static class TaggingCoordinate {
        private float height;
        private float width;

        public float height() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float width() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }
    }


    protected void drawText(String text, float x, float y, Canvas canvas,
                            Align align, float textSize, @ColorInt int color) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(sp2px(10));
        p.setColor(color);
        p.setTextAlign(align);
        canvas.drawText(text, x, y, p);
    }

    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        /**
         * Touch了滑动时触发
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (offsetWidthMax == 0) {
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
            if (distanceX < 0) {
                // 往左滑动
                if (offsetWidth > 0) {
                    offsetWidth += distanceX;
                    if (offsetWidth < 0) {
                        offsetWidth = 0;
                    }
                    scroll(offsetWidth);
                }
            } else {
                // 往右滑动
                if (offsetWidth < offsetWidthMax) {
                    offsetWidth = offsetWidth + distanceX < offsetWidthMax ? (int) (offsetWidth + distanceX)
                            : offsetWidthMax;
                    scroll(offsetWidth);
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public void scroll(int width ) {
        scrollTo(offsetWidth, 0);
        int start = (int) (offsetWidth / ((yRawData.size() + 1) * cutoffwidth));
        int stop = (int) ((offsetWidth + coordinateRect.width() + yRawData
                .size() * cutoffwidth) / ((yRawData.size() + 1) * cutoffwidth));
        if (!initMaxAndMin(start, stop)) {
            invalidate();
        }
    }

    protected float px2sp(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    protected float sp2px(float spValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (spValue * scale + 0.5f);
    }

    protected float dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public static String getTwoStepAndInt(double vaule) {
        try {
            if (vaule == (int) vaule) {
                return ((int) vaule) + "";
            } else {
                DecimalFormat df = new DecimalFormat("###########0.00");
                return df.format(Math.round(vaule * 100) / 100.0);
            }
        } catch (Exception e) {
        }
        return Math.round(vaule * 100) / 100.00f + "";
    }

}