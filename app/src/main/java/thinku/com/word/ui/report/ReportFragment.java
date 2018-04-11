package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.recite.ReciteFragment;

/**
 * 单词报告（主）
 */

public class ReportFragment extends BaseFragment implements View.OnClickListener {

    private TextView total,statistics ,evaluate;
    private ProcessFragment processFragment  ;  // 背单词轨迹fragment
    private WordReportFragment wordReportFragment ; //  单词报表fragment
    public static ReportFragment newInstance(){
        ReportFragment reportFragment = new ReportFragment() ;
        return reportFragment ;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        addFragment();
        setClick();
        setSelect(0);
    }

    public void addFragment(){
        processFragment = findFragment(ProcessFragment.class);
        if (processFragment == null ) {
            processFragment = ProcessFragment.newInstance();
            wordReportFragment = WordReportFragment.newInstance();
            loadMultipleRootFragment(R.id.fl, 2, processFragment, wordReportFragment);
        }else{
            processFragment = findFragment(ProcessFragment.class);
            wordReportFragment = findFragment(WordReportFragment.class);
        }
    }
    private void setClick() {
        total.setOnClickListener(this);
        statistics.setOnClickListener(this);
        evaluate.setOnClickListener(this);
    }

    private void findView(View view) {
        total = (TextView) view.findViewById(R.id.total);
        statistics = (TextView) view.findViewById(R.id.statistics);
        evaluate = (TextView) view.findViewById(R.id.evaluate);
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
            case R.id.evaluate:
                start(EvaluateFirstFragment.newInstance());
                break;
        }
    }

    private void setSelect(int i){
        if(i==0){
            statistics.setSelected(false);
            total.setSelected(true);
            showHideFragment(processFragment , wordReportFragment);
        }else{
            total.setSelected(false);
            statistics.setSelected(true);
            showHideFragment(wordReportFragment , processFragment);
        }
    }
}
