package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;

/**
 * 单词报告（主）
 */

public class ReportFragment extends BaseFragment implements View.OnClickListener {

    private TextView total,statistics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setSelect(0);
        setClick();
    }

    private void setClick() {
        total.setOnClickListener(this);
        statistics.setOnClickListener(this);
    }

    private void findView(View view) {
        total = (TextView) view.findViewById(R.id.total);
        statistics = (TextView) view.findViewById(R.id.statistics);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.total:
                setSelect(0);
                break;
            case R.id.statistics:
                setSelect(1);
                break;
        }
    }

    private void setSelect(int i){
        if(i==0){
            statistics.setSelected(false);
            total.setSelected(true);
        }else{
            total.setSelected(false);
            statistics.setSelected(true);
        }
    }
}
