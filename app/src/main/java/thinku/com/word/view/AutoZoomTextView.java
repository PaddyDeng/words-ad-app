package thinku.com.word.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/2/6.
 */

public class AutoZoomTextView extends AppCompatTextView {

    /**
     * 自动缩放
     */
    private boolean isAutoZoom;
    private int maxWidth;
    private float defaultTextSize = 0.0f;

    public AutoZoomTextView(Context context) {
        this(context, null);
    }

    public AutoZoomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);

    }

    public AutoZoomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setSingleLine(true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoZoomTextView);
        isAutoZoom = a.getBoolean(R.styleable.AutoZoomTextView_is_auto_zoom, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        if (defaultTextSize == 0.0f) {
            defaultTextSize = getTextSize();
        }
        float textSize = defaultTextSize;
        paint.setTextSize(textSize);
        if (isAutoZoom) {
            if (maxWidth == 0)
                maxWidth = getWidth();
            float textViewWidth = maxWidth - getPaddingLeft() - getPaddingRight();//不包含左右padding的空间宽度
            float textViewWidth1 = textViewWidth - paint.getFontSpacing() * 2;//不包含左右字体空间
            String text = getText().toString();
            float textWidth = paint.measureText(text);
            while (textWidth > textViewWidth1) {
                textSize--;
                paint.setTextSize(textSize);
                textWidth = paint.measureText(text);
                textViewWidth1 = textViewWidth - paint.getFontSpacing() * 2;
            }
        }
        super.onDraw(canvas);
    }

    public boolean isAutoZoom() {
        return isAutoZoom;
    }

    public void setIsAutoZoom(boolean isAutoZAoom) {
        this.isAutoZoom = isAutoZAoom;
    }
}