package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    private Map<Integer ,Fragment> fragmentList = new HashMap<>();
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
        setClick();
        setSelect(0);
        addFragment();
    }


    public void addFragment(){
        if (findChildFragment( WordReportFragment.class )== null){
            wordReportFragment = WordReportFragment.newInstance() ;
            processFragment = ProcessFragment.newInstance();
            loadMultipleRootFragment(R.id.fl ,1 , wordReportFragment ,processFragment);
        }else{
            wordReportFragment = findChildFragment(WordReportFragment.class);
            processFragment = findChildFragment(ProcessFragment.class);
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
                EvaluateFirstFragment.start(_mActivity);
                break;
        }
    }

    private void setSelect(int i){
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        if (oldPage != -1) {
//            ft.hide(fragmentList.get(oldPage));
//        }
//        if (null != fragmentList.get(i) && fragmentList.get(i).isAdded()) {
//            ft.show(fragmentList.get(i));
//        }else {
//            if (i == 0) {
//                statistics.setSelected(false);
//                total.setSelected(true);
//                processFragment = ProcessFragment.newInstance();
//                fragmentList.put(i ,processFragment);
//                ft.add(R.id.fl,processFragment);
//            } else {
//                total.setSelected(false);
//                statistics.setSelected(true);
//                wordReportFragment = WordReportFragment.newInstance();
//                fragmentList.put(i ,wordReportFragment);
//                ft.add(R.id.fl,wordReportFragment);
//            }
//            oldPage = i ;
//        }
        if (i == 0){
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
