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

public class ReviewDialog extends AlertDialog implements CompoundButton.OnCheckedChangeListener   {

    private TextView name, all, know, incognizant, dim, all_2;
    private TextView review, not_review;
    private String name_txt, all_txt, know_txt, incognizan_txt, dim_txt, all_2_txt;
    private CheckBox[] checkBoxes = new CheckBox[4];
    private int[] statusList = new int[]{
            C.LGWordStatusKnow  , C.LGWordStatusIncoginzance ,C.LGWordStatusVague ,C.LGWordStatusNone
    };
    private OnReviewClickListener onReviewClickListener;
    private OnNotReviewClickListener onNotReviewClickListener;
    public  int status ;
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
        initData();
        initEvent();
    }

    public void setOnReviewClickListener(OnReviewClickListener onNoOnclickListener) {

        this.onReviewClickListener = onNoOnclickListener;
    }


    public void setOnNotReviewClickListener( OnNotReviewClickListener onYesOnclickListener) {
        this.onNotReviewClickListener = onYesOnclickListener;
    }

    public void initEvent() {
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReviewClickListener != null) {
                    onReviewClickListener.onReviewClick();
                }
            }
        });

        not_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNotReviewClickListener != null) {
                    onNotReviewClickListener.onNotReviewClick();
                }
            }
        });

    }

    public void initData() {
        if (name_txt != null) {
            name.setText(name_txt);
        }
        if (all_txt != null) {
            all.setText(all_txt);
        }

        if (know_txt != null) {
            know.setText(know_txt);
        }

        if (incognizan_txt != null) {
            incognizant.setText(incognizan_txt);
        }

        if (dim_txt != null) {
            dim.setText(dim_txt);
        }

        if (all_2_txt != null) {
            all_2.setText(all_2_txt);
        }
    }


    public void setName_txt(String name_txt) {
        this.name_txt = name_txt;
    }

    public void setAll_txt(String all_txt) {
        this.all_txt = all_txt;
    }

    public void setKnow_txt(String know_txt) {
        this.know_txt = know_txt;
    }

    public void setIncognizan_txt(String incognizan_txt) {
        this.incognizan_txt = incognizan_txt;
    }

    public void setDim_txt(String dim_txt) {
        this.dim_txt = dim_txt;
    }

    public void setAll_2_txt(String all_2_txt) {
        this.all_2_txt = all_2_txt;
    }

    public void initView() {
        all = (TextView) findViewById(R.id.all);
        know = (TextView) findViewById(R.id.know);
        name = (TextView) findViewById(R.id.name);
        incognizant = (TextView) findViewById(R.id.incognizant);
        dim = (TextView) findViewById(R.id.dim);
        all_2 = (TextView) findViewById(R.id.all_2);
        review = (TextView) findViewById(R.id.review);
        not_review = (TextView) findViewById(R.id.not_review);
        checkBoxes[0] = (CheckBox) findViewById(R.id.know_check);
        checkBoxes[1] = (CheckBox) findViewById(R.id.incognizant_check);
        checkBoxes[2] = (CheckBox) findViewById(R.id.dim_check);
        checkBoxes[3] = (CheckBox) findViewById(R.id.all_check);
        checkBoxes[0].setOnCheckedChangeListener(this);
        checkBoxes[1].setOnCheckedChangeListener(this);
        checkBoxes[2].setOnCheckedChangeListener(this);
        checkBoxes[3].setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            for (int i = 0; i < checkBoxes.length; i++) {
                //不等于当前选中的就变成false
                if (checkBoxes[i].getText().toString().equals(buttonView.getText().toString())) {
                    checkBoxes[i].setChecked(true);
                    status = statusList[i];
                } else {
                    checkBoxes[i].setChecked(false);
                }
            }
        }
    }



    public interface OnReviewClickListener {
        void onReviewClick();
    }

    public interface OnNotReviewClickListener {
        void onNotReviewClick();
    }
}
