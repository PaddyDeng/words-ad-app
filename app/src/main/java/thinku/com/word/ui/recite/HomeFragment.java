package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.view.AutoZoomTextView;
import thinku.com.word.view.ProgressView;

/**
 * 正常进入
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private AutoZoomTextView surplus_day;
    private ProgressView progress;
    private TextView days,change_plan,need_num,already_num,num,all_num,today_num,review_t,review_num,start_recite,start_review;
    private LinearLayout sign;
    private ImageView modify_word_package;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
    }

    private void setClick() {
        sign.setOnClickListener(this);
        change_plan.setOnClickListener(this);
        modify_word_package.setOnClickListener(this);
        start_recite.setOnClickListener(this);
        start_review.setOnClickListener(this);
    }

    private void findView(View view) {
        surplus_day = (AutoZoomTextView) view.findViewById(R.id.surplus_day);
        surplus_day.setIsAutoZoom(true);
        progress = (ProgressView) view.findViewById(R.id.progress);
        days = (TextView) view.findViewById(R.id.days);
        sign = (LinearLayout) view.findViewById(R.id.sign);
        change_plan = (TextView) view.findViewById(R.id.change_plan);
        need_num = (TextView) view.findViewById(R.id.need_num);
        already_num = (TextView) view.findViewById(R.id.already_num);
        modify_word_package = (ImageView) view.findViewById(R.id.modify_word_package);
        num = (TextView) view.findViewById(R.id.num);
        all_num = (TextView) view.findViewById(R.id.all_num);
        today_num = (TextView) view.findViewById(R.id.today_num);
        review_t = (TextView) view.findViewById(R.id.review_t);
        review_num = (TextView) view.findViewById(R.id.review_num);
        start_recite = (TextView) view.findViewById(R.id.start_recite);
        start_review = (TextView) view.findViewById(R.id.start_review);

        //设置进度条
//        progress.setColor(R.color.black,R.color.mainColor,R.color.blue);
//        progress.setMaxCount(100);
//        progress.setCurrentCount(50);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign:

                break;
            case R.id.change_plan:

                break;
            case R.id.modify_word_package:

                break;
            case R.id.start_recite:
                break;
            case R.id.start_review:
                break;
        }
    }
}
