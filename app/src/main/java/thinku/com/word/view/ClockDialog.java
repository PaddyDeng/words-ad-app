package thinku.com.word.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ClockDialog extends AlertDialog {
    private static final String TAG = ClockDialog.class.getSimpleName();
    private OnReviewClickListener onReviewClickListener;

    private CheckBox mondayCheck ,tuesdayCheck ,wedCheck ,thuCheck ,friCheck ,satCheck ,sunCheck ;
    private TextView montxt ,tuetxt ,wedtxt ,thutxt ,fritxt ,sattxt ,suntxt ;
    private TextView definite ;
    public CheckBox[] checkBoxes = new CheckBox[7];
    public TextView[] texts = new TextView[7];
    public ClockDialog(@NonNull Context context) {
        super(context);
    }

    public ClockDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ClockDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_week_chose);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
        initCheck();
    }

    public void initView(){
        mondayCheck = (CheckBox) findViewById(R.id.monday_check);
        tuesdayCheck = (CheckBox) findViewById(R.id.tuesday_check);
        wedCheck = (CheckBox) findViewById(R.id.wed_check);
        thuCheck = (CheckBox) findViewById(R.id.thu_check);
        friCheck = (CheckBox) findViewById(R.id.fri_check);
        satCheck = (CheckBox) findViewById(R.id.sat_check);
        sunCheck = (CheckBox) findViewById(R.id.sun_check);
        definite = (TextView) findViewById(R.id.definite);
        montxt = (TextView) findViewById(R.id.mon_txt);
        tuetxt = (TextView) findViewById(R.id.tue_txt);
        wedtxt  = (TextView) findViewById(R.id.wek_txt);
        thutxt = (TextView) findViewById(R.id.thu_txt);
        fritxt = (TextView) findViewById(R.id.fri_txt);
        sattxt = (TextView) findViewById(R.id.sat_txt);
        suntxt = (TextView) findViewById(R.id.sun_txt);
    }

    public void initCheck(){
        checkBoxes[0] = mondayCheck ;
        checkBoxes[1] = tuesdayCheck ;
        checkBoxes[2] = wedCheck ;
        checkBoxes[3] = thuCheck ;
        checkBoxes[4] = friCheck ;
        checkBoxes[5] = satCheck ;
        checkBoxes[6] = sunCheck ;
        texts[0] = montxt ;
        texts[1] = tuetxt ;
        texts[2] = wedtxt ;
        texts[3] = thutxt ;
        texts[4] = fritxt ;
        texts[5] = sattxt ;
        texts[6] = suntxt ;
    }

    public void setOnReviewClickListener(OnReviewClickListener onNoOnclickListener) {
        this.onReviewClickListener = onNoOnclickListener;
    }


    public void initEvent() {
        definite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReviewClickListener != null) {
                    onReviewClickListener.onReviewClick();
                }
            }
        });
    }



    public interface OnReviewClickListener {
        void onReviewClick();
    }

}
