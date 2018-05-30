package thinku.com.word.ui.personalCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

public class ReadMeActivity extends BaseActivity {
    @BindView(R.id.title_t)
    TextView title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        ButterKnife.bind(this);
        title.setText("READ ME");
    }

    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }
}

