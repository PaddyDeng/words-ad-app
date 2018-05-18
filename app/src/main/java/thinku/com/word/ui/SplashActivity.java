package thinku.com.word.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.thrlib.OCRProxy;
import thinku.com.word.ui.other.MainActivity;
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
        checkPermission();
    }


    public void init(){
        if (SharedPreferencesUtils.getFirstOpen(this)) {
            isFirst = true;
            SharedPreferencesUtils.setFirstOpen(this);
            forword(GuideActivity.class);
            this.finishWithAnim();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            this.finishWithAnim();
        }
    }


    public void checkPermission() {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            init();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast("获取权限失败");
                        finish();
                    }
                });
    }


}
