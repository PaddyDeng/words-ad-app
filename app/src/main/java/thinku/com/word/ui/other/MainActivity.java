
package thinku.com.word.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.thrlib.OCRProxy;
import thinku.com.word.ui.fparent.PKParentFragment;
import thinku.com.word.ui.fparent.PeripheryParentFragment;
import thinku.com.word.ui.fparent.WordParentFragment;
import thinku.com.word.ui.report.ReportFragment;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

public class MainActivity extends BaseFragmentActivitiy implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private LinearLayout recite_ll, report_ll, pk_ll, periphery_ll;
    private FrameLayout fl;
    private ImageView recite_iv, report_iv, pk_iv, periphery_iv;
    private TextView recite_tv, report_tv, pk_tv, periphery_tv;
    private int oldPage = -1;
    private boolean isSelectOther = false;
    private List<ImageView> ivs;
    private List<TextView> tvs;
    private List<LinearLayout> lls;
    private SupportFragment[] fragments = new SupportFragment[4];
    private WordParentFragment reciteFragment ;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );

    }


    private void setClick() {
        recite_ll.setOnClickListener(this);
        report_ll.setOnClickListener(this);
        pk_ll.setOnClickListener(this);
        periphery_ll.setOnClickListener(this);
    }

    private void initView() {
        initBootom();
        reciteFragment = findFragment(WordParentFragment.class);
        if (reciteFragment == null) {
            fragments[FIRST] = WordParentFragment.newInstance();
            fragments[SECOND] = ReportFragment.newInstance();
            fragments[THIRD] = PKParentFragment.newInstance();
            fragments[FOURTH] = PeripheryParentFragment.newInstance();
            loadMultipleRootFragment(R.id.fl, 1,
                    fragments[FIRST],
                    fragments[SECOND],
                    fragments[THIRD],
                    fragments[FOURTH]);
        } else {
            fragments[FIRST] = findFragment(WordParentFragment.class);
            fragments[SECOND]  = findFragment(ReportFragment.class);
            fragments[THIRD]  = findFragment(PeripheryParentFragment.class);
            fragments[FOURTH]  = findFragment(PKParentFragment.class);
        }
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
                chooseFragment(i );
                oldPage = i;
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
            chooseFragment(i );
            oldPage = i;
        }
    }

    private void chooseFragment(int i ) {
        if (oldPage != -1) {
            showHideFragment(fragments[i], fragments[oldPage]);
        }else{
            showHideFragment(fragments[i]);
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
            if (MyApplication.mediaPlayer.isPlaying()) MyApplication.mediaPlayer.stop();
            MyApplication.mediaPlayer.release();
        }
        OCRProxy.orcRelease();
    }

}