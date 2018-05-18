package thinku.com.word.ui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jude.rollviewpager.HintView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragmentActivitiy;
import thinku.com.word.ui.adapter.SplashAdapter;
import thinku.com.word.ui.call.FinshActivity;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;

/**
 * 引导页
 */

public class GuideActivity extends BaseFragmentActivitiy  {
    private RollPagerView viewPager;

    private List<Bitmap> bitmapList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SharedPreferences guide = getSharedPreferences("guide", MODE_PRIVATE);
        SharedPreferences.Editor edit = guide.edit();
        edit.putBoolean("isFirst", true);
        edit.commit();

        bitmapList = new ArrayList<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.guide_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.guide_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.guide_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.guide_4);
        bitmapList.add(bitmap1);
        bitmapList.add(bitmap2);
        bitmapList.add(bitmap3);
        bitmapList.add(bitmap4);
        viewPager = (RollPagerView) findViewById(R.id.viewPager);
        viewPager.setHintView(new ColorPointHintView(this, getResources().getColor(R.color.white), getResources().getColor(R.color.black)));
        SplashAdapter splashAdapter = new SplashAdapter(this, bitmapList);
        viewPager.setHintViewDelegate(new RollPagerView.HintViewDelegate() {
            @Override
            public void setCurrentPosition(int i, HintView hintView) {
                if (i == bitmapList.size() - 1) {
                    TypeSettingActivity.start(GuideActivity.this, true);
                    GuideActivity.this.finish();
                }
            }

            @Override
            public void initView(int i, int i1, HintView hintView) {

            }
        });
        viewPager.setAdapter(splashAdapter);

    }

}
