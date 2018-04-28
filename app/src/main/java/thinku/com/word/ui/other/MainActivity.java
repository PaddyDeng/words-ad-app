package thinku.com.word.ui.other;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.callback.PermissionCallback;
import thinku.com.word.callback.ReferImage;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.fparent.PKParentFragment;
import thinku.com.word.ui.fparent.PeripheryParentFragment;
import thinku.com.word.ui.fparent.ReportParentFragment;
import thinku.com.word.ui.fparent.WordParentFragment;
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

public class MainActivity extends BaseFragmentActivitiy implements View.OnClickListener {
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout recite_ll, report_ll, pk_ll, periphery_ll;
    private FrameLayout fl;
    private ImageView recite_iv, report_iv, pk_iv, periphery_iv;
    private TextView recite_tv, report_tv, pk_tv, periphery_tv;
    private int oldPage = 0;
    private boolean isSelectOther = false;
    private List<ImageView> ivs;
    private List<TextView> tvs;
    private List<LinearLayout> lls;
    private List<SupportFragment> fragments;
    private WordParentFragment reciteFragment;
    private ReportParentFragment reportFragment;
    private PKParentFragment pkFragment;
    private PeripheryParentFragment peripheryFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
        findView();
        setClick();
        // 获取权限
        getPermission(permissions, 1, "需要文件读写权限", 2, new PermissionCallback() {
            @Override
            public void onSuccessful() {
                initView();
            }

            @Override
            public void onFailure() {
                toTast("获取权限失败");
                finish();
            }
        });
    }


    /**
     * session 失效重新登录
     */
    public void login(){
        UserInfo userInfo = SharedPreferencesUtils.getUserInfo(MainActivity.this);
        if (userInfo != null & !TextUtils.isEmpty(userInfo.getPhone())& !"".equals(userInfo.getPhone()) ){
            LoginHelper.setSession(MainActivity.this, userInfo, new ICallBack() {
                @Override
                public void onSuccess(Object o) {

                }

                @Override
                public void onFail() {

                }
            });
        }
    }

    private void setClick() {
        recite_ll.setOnClickListener(this);
        report_ll.setOnClickListener(this);
        pk_ll.setOnClickListener(this);
        periphery_ll.setOnClickListener(this);
    }

    private void initView() {
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
        fragments = new ArrayList<>();
        reciteFragment = findFragment(WordParentFragment.class);
        if (reciteFragment == null) {
            reciteFragment = WordParentFragment.newInstance();
            reportFragment = ReportParentFragment.newInstance();
            pkFragment = PKParentFragment.newInstance();
            peripheryFragment = PeripheryParentFragment.newInstance();
            loadMultipleRootFragment(R.id.fl, 1,
                    reciteFragment,
                    reportFragment,
                    pkFragment,
                    peripheryFragment);
        } else {
            reciteFragment = findFragment(WordParentFragment.class);
            reportFragment = findFragment(ReportParentFragment.class);
            peripheryFragment = findFragment(PeripheryParentFragment.class);
            pkFragment = findFragment(PKParentFragment.class);
        }
        fragments.add(reciteFragment);
        fragments.add(reportFragment);
        fragments.add(pkFragment);
        fragments.add(peripheryFragment);
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
        if (oldPage != i || !isSelectOther) {//不是点当前选择，或者未选择过
            isSelectOther = true;
            ivs.get(oldPage).setSelected(false);
            lls.get(oldPage).setSelected(false);
            tvs.get(oldPage).setTextColor(getResources().getColor(R.color.main_title_color));
            ivs.get(i).setSelected(true);
            lls.get(i).setSelected(true);
            tvs.get(i).setTextColor(getResources().getColor(R.color.white));
            chooseFragment(i);
            oldPage = i;
        }
    }

    private void chooseFragment(int i) {
        showHideFragment(fragments.get(i));
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
    }
}
