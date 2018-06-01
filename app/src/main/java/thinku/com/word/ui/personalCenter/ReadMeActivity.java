package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.utils.GlideUtils;

public class ReadMeActivity extends BaseActivity {
    @BindView(R.id.title_t)
    TextView title ;
    @BindView(R.id.mip)
    ImageView mip ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        ButterKnife.bind(this);
        title.setText("READ ME");
        new GlideUtils().loadResCircle(this , R.mipmap.readme ,mip);
    }

    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }
}

