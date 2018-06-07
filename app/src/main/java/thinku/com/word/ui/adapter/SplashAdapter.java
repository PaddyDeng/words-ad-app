package thinku.com.word.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.DynamicPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/5/18.
 */

public class SplashAdapter extends PagerAdapter {
    public Context context;

    private List<ImageView> imageViews;

    public SplashAdapter( Context context,List<ImageView> imageViews) {
        this.imageViews = imageViews;
        this.context = context ;
    }


    /**
     * 当前要显示的对象（图片）
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position % imageViews.size()));
        return imageViews.get(position);
    }


    /**
     * 从ViewGroup中移除当前对象（图片）
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }
    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
