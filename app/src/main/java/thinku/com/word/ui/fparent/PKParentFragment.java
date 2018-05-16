package thinku.com.word.ui.fparent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.pk.PKFragment;
import thinku.com.word.ui.recite.ReciteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PKParentFragment extends BaseFragment {

    public static PKParentFragment newInstance(){
        PKParentFragment wordReportFragment = new PKParentFragment();
        return wordReportFragment ;
    }
    public PKParentFragment() {
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
        if (findChildFragment(PKFragment.class) == null){
            loadRootFragment(R.id.parent , PKFragment.newInstance());
        }
    }
}
