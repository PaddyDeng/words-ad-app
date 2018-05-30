package thinku.com.word.ui.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

public class UserActivity extends BaseActivity {
    private TextView content  ,title;
    private ImageView back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        title.setText("用户协议");
    }

    public void initView(){
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title_t);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

    }
}
