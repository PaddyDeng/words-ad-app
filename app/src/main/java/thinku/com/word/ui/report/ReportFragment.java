package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.recite.HomeFirstFragment;
import thinku.com.word.ui.recite.HomeFragment;
import thinku.com.word.ui.recite.ReciteFragment;

/**
 * 单词报告（主）
 */

public class ReportFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ReportFragment.class.getSimpleName();
    private TextView total,statistics ,evaluate;
    private int oldPage = -1 ;
    private Map< Integer,SupportFragment> fragments ;
    public static ReportFragment newInstance(){
        ReportFragment reportFragment = new ReportFragment() ;
        return reportFragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  =inflater.inflate(R.layout.fragment_report,container,false);
        fragments = new HashMap<>();
        return view ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
        setSelect(0);
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
        if (i == 0){
            statistics.setSelected(false);
            total.setSelected(true);
        }else{
            total.setSelected(false);
            statistics.setSelected(true);
        }
        setFragment(i);
    }

    public void setFragment(int tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (oldPage != -1) {
            ft.hide(fragments.get(oldPage));
        }
        if (null != fragments.get(tag) && fragments.get(tag).isAdded()) {
            ft.show(fragments.get(tag));
        } else {
            switch (tag) {
                case 0: // 单词轨迹
                    ProcessFragment processFragment = ProcessFragment.newInstance();
                    fragments.put(tag, processFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    break;
                case 1://单词报表
                    WordReportFragment wordReportFragment = thinku.com.word.ui.report.WordReportFragment.newInstance();
                    fragments.put(tag, wordReportFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    break;
            }
        }
        oldPage = tag;
        try {
            ft.commit();
        } catch (Exception e) {

        }
    }

}
