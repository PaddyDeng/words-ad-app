package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;
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
    private Map< Integer,SupportFragment> fragments ;
    private int oldPage = -1 ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragments = new HashMap<>();
        return inflater.inflate(R.layout.fragment_pk,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setSelect(0);
        setClick();
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

        }else{
            pk.setSelected(false);
            find.setSelected(true);
            showHideFragment(pkWordFragment);
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
                    PKPageFragment pkPageFragment = PKPageFragment.newInstance();
                    fragments.put(tag, pkPageFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    break;
                case 1://单词报表
                    PkWordFragment pkWordFragment = PkWordFragment.newInstance();
                    fragments.put(tag, pkWordFragment);
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
