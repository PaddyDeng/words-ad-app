package thinku.com.word.ui.periphery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordAdapter;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/5/7.
 * round  公开课轮播图
 */

public class RecentClassAdapter extends LoopPagerAdapter {
    private static final String TAG = ReciteWordAdapter.class.getSimpleName();
    private List<RoundBean.RecentClassBean> recentClassBeanList;
    private Context context;
    public SelectListener selectListener ;
    public RecentClassAdapter(Context context, RollPagerView viewPager, List<RoundBean.RecentClassBean> recentClassBeanList) {
        super(viewPager);
        this.recentClassBeanList = recentClassBeanList;
        this.context = context;
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener ;
    }

    @Override
    public View getView(ViewGroup viewGroup,final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_round_roll, viewGroup, false);
        RoundBean.RecentClassBean recentClassBean = recentClassBeanList.get(i);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.date_and_time);
        TextView inquire = (TextView) view.findViewById(R.id.inquire);
        GlideUtils.load(context, NetworkTitle.OPENRESOURE + recentClassBean.getImage(), imageView);
        textView.setText(recentClassBean.getCnName());
        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(i);
            }
        });
        return view;
    }

    @Override
    public int getRealCount() {
        return recentClassBeanList == null ? 0 : recentClassBeanList.size();
    }
}
