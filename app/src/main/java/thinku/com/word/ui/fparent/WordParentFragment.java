package thinku.com.word.ui.fparent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.callback.ReferImage;
import thinku.com.word.ui.recite.ReciteFragment;
import thinku.com.word.ui.report.WordReportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordParentFragment extends BaseFragment  {

    public static WordParentFragment newInstance(){
        WordParentFragment wordReportFragment = new WordParentFragment();
        return wordReportFragment ;
    }
    public WordParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_parent, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(ReciteFragment.class) == null){
            loadRootFragment(R.id.parent , ReciteFragment.newInstance());
        }
    }
}
