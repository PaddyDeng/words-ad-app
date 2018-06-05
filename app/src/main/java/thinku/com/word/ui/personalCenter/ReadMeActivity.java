package thinku.com.word.ui.personalCenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

public class ReadMeActivity extends BaseActivity {
    @BindView(R.id.title_t)
    TextView title;
    SubsamplingScaleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        ButterKnife.bind(this);
        title.setText("READ ME");
        float initImageScale = getInitImageScale();
        imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START);
//        imageView.setMinScale(0.1F);//最小显示比例
        imageView.setMaxScale(initImageScale + 2.0f );//最大显示比例
        imageView.setImage(ImageSource.resource(R.mipmap.readme), new ImageViewState(initImageScale, new PointF(0, 0), 0));


    }


    /**
     * 计算出图片初次显示需要放大倍数
     */
    public float getInitImageScale(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.mipmap.readme);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }

    @OnClick(R.id.back)
    public void back() {
        this.finishWithAnim();
    }

}

