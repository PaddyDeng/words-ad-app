
package thinku.com.word.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.thrlib.OCRProxy;
import thinku.com.word.ui.fparent.WordParentFragment;
import thinku.com.word.ui.periphery.RoundFragment;
import thinku.com.word.ui.personalCenter.update.SimpleUpdateApk;
import thinku.com.word.ui.pk.PKFragment;
import thinku.com.word.ui.recite.ReciteFragment;
import thinku.com.word.ui.report.ReportFragment;

public class MainActivity extends BaseFragmentActivitiy implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private RelativeLayout recite_ll, report_ll, pk_ll, periphery_ll;
    private FrameLayout fl;
    private ImageView recite_iv, report_iv, pk_iv, periphery_iv;
    private TextView recite_tv, report_tv, pk_tv, periphery_tv;
    private int oldPage = -1;
    private boolean isSelectOther = false;
    private List<ImageView> ivs;
    private List<TextView> tvs;
    private List<RelativeLayout> lls;
    private SupportFragment[] fragments = new SupportFragment[4];
    private Map< Integer,SupportFragment> fragmentList = new ArrayMap<>();
    private WordParentFragment reciteFragment ;
    private SimpleUpdateApk simpleUpdateApk ;
    public static void toMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setClick();
        initView();
        OCRProxy.initToken(mContext);
        simpleUpdateApk = new SimpleUpdateApk(this , false);
        simpleUpdateApk.checkVersionUpdate();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.mediaPlayer != null && MyApplication.mediaPlayer.isPlaying()){
            MyApplication.mediaPlayer.stop();
        }
    }


    private void setClick() {
        recite_ll.setOnClickListener(this);
        report_ll.setOnClickListener(this);
        pk_ll.setOnClickListener(this);
        periphery_ll.setOnClickListener(this);
    }

    private void initView() {
        initBootom();
        setSelect(0);
    }
    
    private void initBootom() {
        ivs = new ArrayList<>();
        ivs.add(recite_iv);
        ivs.add(report_iv);
        ivs.add(pk_iv);
        ivs.add(periphery_iv);
        tvs = new ArrayList<>();
        tvs.add(recite_tv);
        tvs.add(report_tv);
        tvs.add(pk_tv);
        tvs.add(periphery_tv);
        lls = new ArrayList<>();
        lls.add(recite_ll);
        lls.add(report_ll);
        lls.add(pk_ll);
        lls.add(periphery_ll);
    }

    private void findView() {
        recite_ll = (RelativeLayout) findViewById(R.id.recite_ll);
        report_ll = (RelativeLayout) findViewById(R.id.report_ll);
        pk_ll = (RelativeLayout) findViewById(R.id.pk_ll);
        periphery_ll = (RelativeLayout) findViewById(R.id.periphery_ll);
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
        switch (v.getId()) {
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

    private void setSelect(int i) {
        if (i == -1) {
            return ;
        }else{
            if (oldPage == -1){
                isSelectOther = true;
                ivs.get(i).setSelected(true);
                lls.get(i).setSelected(true);
                tvs.get(i).setTextColor(getResources().getColor(R.color.white));
                setFragment(0);
            }
        }
        if (oldPage != i || !isSelectOther) {//不是点当前选择，或者未选择过
            isSelectOther = true;
            ivs.get(oldPage).setSelected(false);
            lls.get(oldPage).setSelected(false);
            tvs.get(oldPage).setTextColor(getResources().getColor(R.color.main_title_color));
            ivs.get(i).setSelected(true);
            lls.get(i).setSelected(true);
            tvs.get(i).setTextColor(getResources().getColor(R.color.white));
            Log.e(TAG, "setSelect: " + i);
            setFragment(i);
        }
    }


    public void setFragment(int tag) {
        Log.e(TAG, "setFragment: " + tag);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (oldPage != -1) {
            ft.hide(fragmentList.get(oldPage));
        }
        if (null != fragmentList.get(tag) && fragmentList.get(tag).isAdded()) {
            ft.show(fragmentList.get(tag));
        } else {
            switch (tag) {
                case 0: // 单词轨迹
                    ReciteFragment pkPageFragment = ReciteFragment.newInstance();
                    fragmentList.put(tag, pkPageFragment);
                    ft.add(R.id.fl, fragmentList.get(tag));
                    break;
                case 1://单词报表
                    ReportFragment pkWordFragment = ReportFragment.newInstance();
                    fragmentList.put(tag, pkWordFragment);
                    ft.add(R.id.fl, fragmentList.get(tag));
                    break;
                case 2: // 单词轨迹
                    PKFragment pkFragment = PKFragment.newInstance();
                    fragmentList.put(tag, pkFragment);
                    ft.add(R.id.fl, fragmentList.get(tag));
                    break;
                case 3://单词报表
                    RoundFragment roundFragment = RoundFragment.newInstance();
                    fragmentList.put(tag, roundFragment);
                    ft.add(R.id.fl, fragmentList.get(tag));
                    break;
            }
        }
        oldPage = tag;
        try {
            ft.commit();
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (MyApplication.mediaPlayer != null){
            if (MyApplication.mediaPlayer.isPlaying()) {
                MyApplication.mediaPlayer.stop();
                try {
                    MyApplication.mediaPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        OCRProxy.orcRelease();
    }

}