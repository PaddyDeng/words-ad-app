package thinku.com.word.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.utils.C;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReviewDialog extends AlertDialog    {

   private TextView num , review ,chose ;
    private OnReviewClickListener onReviewClick ;
    private OnNotReviewClickListener onNotReviewClickListener ;
    public ReviewDialog(@NonNull Context context) {
        super(context);
    }

    public ReviewDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ReviewDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dailog_review);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    public void setOnReviewClickListener(OnReviewClickListener onNoOnclickListener) {

        this.onReviewClick = onNoOnclickListener;
    }


    public void setOnNotReviewClickListener( OnNotReviewClickListener onYesOnclickListener) {
        this.onNotReviewClickListener = onYesOnclickListener;
    }

    public void initEvent() {
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReviewClick != null) {
                    onReviewClick.onReviewClick();
                }
            }
        });

        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNotReviewClickListener != null) {
                    onNotReviewClickListener.onNotReviewClick();
                }
            }
        });

    }

    public void initView() {
        review = (TextView) findViewById(R.id.review);
        num = (TextView) findViewById(R.id.num);
        chose = (TextView) findViewById(R.id.chose);
    }





    public interface OnReviewClickListener {
        void onReviewClick();
    }

    public interface OnNotReviewClickListener {
        void onNotReviewClick();
    }
}
