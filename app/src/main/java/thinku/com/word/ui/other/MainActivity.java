package thinku.com.word.ui.other;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.periphery.PeripheryFragment;
import thinku.com.word.ui.pk.PKFragment;
import thinku.com.word.ui.recite.ReciteFragment;
import thinku.com.word.ui.report.ReportFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout recite_ll,report_ll,pk_ll,periphery_ll;
    private FrameLayout fl;
    private ImageView recite_iv,report_iv,pk_iv,periphery_iv;
    private TextView recite_tv,report_tv,pk_tv,periphery_tv;
    private int oldPage=0;
    private boolean isSelectOther=false;
    private List<ImageView> ivs;
    private List<TextView> tvs;
    private List<LinearLayout> lls;
    private List<BaseFragment> fragments;
    private ReciteFragment reciteFragment;
    private ReportFragment reportFragment;
    private PKFragment pkFragment;
    private PeripheryFragment peripheryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        setClick();
    }

    private void setClick() {
        recite_ll.setOnClickListener(this);
        report_ll.setOnClickListener(this);
        pk_ll.setOnClickListener(this);
        periphery_ll.setOnClickListener(this);
    }

    private void initView() {
        ivs= new ArrayList<>();
        ivs.add(recite_iv);
        ivs.add(report_iv);
        ivs.add(pk_iv);
        ivs.add(periphery_iv);
        tvs=new ArrayList<>();
        tvs.add(recite_tv);
        tvs.add(report_tv);
        tvs.add(pk_tv);
        tvs.add(periphery_tv);
        lls=new ArrayList<>();
        lls.add(recite_ll);
        lls.add(report_ll);
        lls.add(pk_ll);
        lls.add(periphery_ll);
        fragments =new ArrayList<>();
        reciteFragment = new ReciteFragment();
        reportFragment = new ReportFragment();
        pkFragment = new PKFragment();
        peripheryFragment = new PeripheryFragment();
        fragments.add(reciteFragment);fragments.add(reportFragment);
        fragments.add(pkFragment);fragments.add(peripheryFragment);
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl,reciteFragment).add(R.id.fl,reportFragment).add(R.id.fl,pkFragment).add(R.id.fl,peripheryFragment);
        ft.hide(reportFragment).hide(pkFragment).hide(peripheryFragment);
        ft.commit();

        setSelect(0);
    }

    private void findView() {
        recite_ll = (LinearLayout) findViewById(R.id.recite_ll);
        report_ll = (LinearLayout) findViewById(R.id.report_ll);
        pk_ll = (LinearLayout) findViewById(R.id.pk_ll);
        periphery_ll = (LinearLayout) findViewById(R.id.periphery_ll);
        fl = (FrameLayout) findViewById(R.id.fl);
        recite_iv = (ImageView) findViewById(R.id.recite_iv);
        report_iv = (ImageView) findViewById(R.id.report_iv);
        pk_iv = (ImageView) findViewById(R.id.pk_iv);
        periphery_iv = (ImageView) findViewById(R.id.periphery_iv);
        recite_tv = (TextView) findViewById(R.id.recite_tv);
        report_tv = (TextView) findViewById(R.id.report_tv);
        pk_tv = (TextView) findViewById(R.id.pk_tv);
        periphery_tv = (TextView) findViewById(R.id.periphery_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recite_ll:
                setSelect(0);
                break;
            case R.id.report_ll:
                setSelect(1);
                break;
            case R.id.pk_ll:
                setSelect(2);
                break;
            case R.id.periphery_ll:
                setSelect(3);
                break;
        }
    }

    private void setSelect(int i){
        if(oldPage!=i||!isSelectOther){//不是点当前选择，或者未选择过
            isSelectOther=true;
            ivs.get(oldPage).setSelected(false);
            lls.get(oldPage).setSelected(false);
            tvs.get(oldPage).setTextColor(getResources().getColor(R.color.black));
            ivs.get(i).setSelected(true);
            lls.get(i).setSelected(true);
            tvs.get(i).setTextColor(getResources().getColor(R.color.white));
            chooseFragment(i);
            oldPage=i;
        }
    }

    private void chooseFragment(int i) {
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        BaseFragment fragment =fragments.get(i);
        if (!fragment.isAdded()){
            ft.hide(fragments.get(oldPage)).show(fragments.get(i));
        }else{
            ft.hide(fragments.get(oldPage)).show(fragments.get(i));
        }
        ft.commit();
    }
}
