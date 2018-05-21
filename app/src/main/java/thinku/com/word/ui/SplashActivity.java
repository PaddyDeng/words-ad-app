package thinku.com.word.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {
    private boolean isFirst = false;
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkMyPermission();
    }

    public void checkMyPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 1);
                }
            }
        }else{
            login();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                login();
            }
        }else{
            finish();
            toTast(this ,"获取权限失败");
        }
    }

    public void init(){
        if (SharedPreferencesUtils.getFirstOpen(this)) {
            isFirst = true;
            SharedPreferencesUtils.setFirstOpen(this);
            forword(GuideActivity.class);
            this.finish();
        } else {
            toMain();
            this.finish();
        }
    }

    private void toMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * session 失效重新登录
     */
    public void login() {
        UserInfo userInfo = SharedPreferencesUtils.getUserInfo(SplashActivity.this);
        if (userInfo != null & !TextUtils.isEmpty(userInfo.getPhone()) & !"".equals(userInfo.getPhone())) {
            LoginHelper.setSession(SplashActivity.this, userInfo, new ICallBack() {
                @Override
                public void onSuccess(Object o) {
                    init();
                }

                @Override
                public void onFail() {
                    init();
                }
            });
        }else{
            init();
        }
    }


}
