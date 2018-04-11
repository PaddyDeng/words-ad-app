package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;

/**
 * 单词报表
 */

public class WordReportFragment extends SupportFragment{

    private TextView total_num,familiar_num,vague_num,know_num,forget_num,date;
    private ImageView setting_date;

    public static WordReportFragment newInstance(){
        WordReportFragment wordReportFragment = new WordReportFragment();
        return wordReportFragment ;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_report,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
    }

    private void setClick() {
        setting_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findView(View v) {
        total_num = (TextView) v.findViewById(R.id.total_num);
        familiar_num = (TextView) v.findViewById(R.id.familiar_num);
        vague_num = (TextView) v.findViewById(R.id.vague_num);
        know_num = (TextView) v.findViewById(R.id.know_num);
        forget_num = (TextView) v.findViewById(R.id.forget_num);
        date = (TextView) v.findViewById(R.id.date);
        setting_date = (ImageView) v.findViewById(R.id.setting_date);
    }
}
