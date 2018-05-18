package thinku.com.word.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/5/18.
 */

public class SplashAdapter extends StaticPagerAdapter {

    public List<Bitmap> bitmapList;
    public Context context;

    private int[] imgs = {
            R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3,
            R.mipmap.guide_4,
    };

    public SplashAdapter(Context context, List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_splash, viewGroup, false);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image);
//        imageView.setImageBitmap(bitmapList.get(i));
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
//    }
//        return view;

    }

    @Override
    public int getCount() {
        return bitmapList == null ? 0 : bitmapList.size();
    }
}
