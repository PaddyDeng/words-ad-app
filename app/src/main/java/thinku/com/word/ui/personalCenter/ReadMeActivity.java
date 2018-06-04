package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.utils.GlideUtils;

public class ReadMeActivity extends BaseActivity {
    @BindView(R.id.title_t)
    TextView title;
    SubsamplingScaleImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        ButterKnife.bind(this);
        title.setText("READ ME");
        imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        imageView.setImage(ImageSource.asset("readme.jpg"));
//        new GlideUtils().load(this, R.mipmap.readme, mip);
//        mip.setImageBitmap(bitmap);
    }

    @OnClick(R.id.back)
    public void back() {
        this.finishWithAnim();
    }


    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
           opt.inPreferredConfig = Bitmap.Config.RGB_565;
       opt.inPurgeable = true;
           opt.inInputShareable = true;
             //获取资源图片
          InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is,null,opt);
         }
}

