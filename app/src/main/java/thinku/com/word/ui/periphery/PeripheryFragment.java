package thinku.com.word.ui.periphery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;

/**
 * 周边
 */

public class PeripheryFragment extends BaseFragment {

    public static PeripheryFragment newInstance(){
        PeripheryFragment peripheryFragment = new PeripheryFragment();
        return peripheryFragment ;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_periphery,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
