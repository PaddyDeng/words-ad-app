package thinku.com.word.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.AdapterDate;
import thinku.com.word.adapter.AdapterWeek;
import thinku.com.word.utils.DateUtil;

/**
 * Created by Administrator on 2017/8/16.
 */

public class SignDate extends LinearLayout {

    private TextView tvYear;
    private InnerGridView gvWeek;
    private InnerGridView gvDate;
    private AdapterDate adapterDate;

    public SignDate(Context context) {
        super(context);
        init();
    }

    public SignDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignDate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(), R.layout.layout_signdate,this);
        tvYear = (TextView) view.findViewById(R.id.tvYear);
        gvWeek = (InnerGridView) view.findViewById(R.id.gvWeek);
        gvDate = (InnerGridView) view.findViewById(R.id.gvDate);
        tvYear.setText(DateUtil.getCurrentYearAndMonth());
        gvWeek.setAdapter(new AdapterWeek(getContext()));
        adapterDate = new AdapterDate(getContext());
        gvDate.setAdapter(adapterDate);
    }

    public void setSign(List<Integer> list){
        adapterDate.setStatus(list);
    }
}
