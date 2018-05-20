package thinku.com.word.ui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;
import thinku.com.word.ui.adapter.SplashAdapter;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;

/**
 * 引导页
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private SplashAdapter vpAdapter;
    private static int[] imgs = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4};
    private ArrayList<ImageView> imageViews;
    private CircleImageView point1 , point2 ,point3 ,point4 ;
    private ArrayList<CircleImageView> points ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SharedPreferences guide = getSharedPreferences("guide", MODE_PRIVATE);
        SharedPreferences.Editor edit = guide.edit();
        edit.putBoolean("isFirst", true);
        edit.commit();
        initView();
        initImages();
        viewPager = (ViewPager) findViewById(R.id.guide_ViewPager);
        vpAdapter = new SplashAdapter(this, imageViews);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    public void initView() {
        points =  new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.guide_ViewPager);
        point1 = (CircleImageView) findViewById(R.id.point1);
        point2 = (CircleImageView) findViewById(R.id.point2);
        point3 = (CircleImageView) findViewById(R.id.point3);
        point4 = (CircleImageView) findViewById(R.id.point4);
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
    }

    public void initImages() {
        imageViews = new ArrayList<>();
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imgs[i]);
            imageView.setLayoutParams(mParams);
            imageViews.add(imageView);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面跳转时，设置小圆点的margin

        for (int i = 0 ; i < points.size() ; i++) {
            if (i == position ) {
                points.get(i).setImageResource(R.color.mainColor);
                points.get(i).invalidate();
            } else {
                points.get(i).setImageResource(R.color.gray_white);
                points.get(i).invalidate();
            }
        }
        if (position == points.size() -1 ){
            TypeSettingActivity.start(this ,true);
            this.finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
