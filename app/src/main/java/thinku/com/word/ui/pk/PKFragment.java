package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;

/**
 *PK
 */

public class PKFragment extends BaseFragment implements View.OnClickListener {

    public static PKFragment newInstance(){
        PKFragment pkFragment = new PKFragment();
        return pkFragment ;
    }

    private PKPageFragment pkPageFragment ;
    private PkWordFragment pkWordFragment ;
    private TextView pk, find;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pk,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        init();
        setSelect(0);
        setClick();
    }

    public  void init(){
        if (findChildFragment(PKPageFragment.class) == null){
            pkPageFragment = PKPageFragment.newInstance();
            pkWordFragment = PkWordFragment.newInstance();
        }else {
            pkPageFragment = findFragment(PKPageFragment.class);
            pkWordFragment = findFragment(PkWordFragment.class);
        }
        loadMultipleRootFragment(R.id.fl  ,2,pkPageFragment , pkWordFragment);
    }

    private void setClick() {
        pk.setOnClickListener(this);
        find.setOnClickListener(this);
    }

    private void findView(View view) {
        pk = (TextView) view.findViewById(R.id.pk);
        find = (TextView) view.findViewById(R.id.find);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pk:
                setSelect(0);
                break;
            case R.id.find:
                setSelect(1);
                break;
        }
    }
    private void setSelect(int i){
        if(i==0){
            find.setSelected(false);
            pk.setSelected(true);
            showHideFragment(pkPageFragment);
        }else{
            pk.setSelected(false);
            find.setSelected(true);
            showHideFragment(pkWordFragment);
        }
    }
}
