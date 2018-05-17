package thinku.com.word.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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


}
