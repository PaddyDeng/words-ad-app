package thinku.com.word.ui.periphery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/5/7.
 */

public class CourseAdapter extends RecyclerView.Adapter {
    private List<CourseBean> courseBeanList ;
    private Context context ;
    private SelectListener selectListener ;
    private List<RoundBean.LivePreviewBean.DataBean > liveList ;
    private SelectRlClickListener selectRlClickListener ;

    public CourseAdapter(Context context  ){
        this.context = context ;
    }
    public CourseAdapter(Context context , List<CourseBean> courseBeanList ){
        this.context = context ;
        this.courseBeanList = courseBeanList ;
    }

    public void setData(List<RoundBean.LivePreviewBean.DataBean > liveList){
        this .liveList = liveList ;
        notifyDataSetChanged();
    }

    public void setSelectRlClickListener(SelectRlClickListener selectRlClickListener){
        this. selectRlClickListener  = selectRlClickListener ;
    }
    public void  setSelectListener(SelectListener selectListener){
        this.selectListener  = selectListener ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CourseHolder courseHolder = new CourseHolder(LayoutInflater.from(context).inflate(R.layout.item_round_course ,parent ,false));
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CourseHolder courseHolder = (CourseHolder) holder;
        if (courseBeanList != null && courseBeanList.size() > 0) {
            CourseBean courseBean = courseBeanList.get(position);
            courseHolder.name.setText(courseBean.getName());
            courseHolder.people.setText(courseBean.getView() + "人已加入");
            courseHolder.listen.setVisibility(View.VISIBLE);
            new GlideUtils().load(context, courseBean.getImage(), courseHolder.course_img);
            courseHolder.listen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectListener.setListener(position);
                }
            });
            courseHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   selectRlClickListener.setClickListener(position ,holder ,v);
                }
            });
        }else if (liveList != null && liveList.size() > 0){
            RoundBean.LivePreviewBean.DataBean dataBean = liveList.get(position);
            courseHolder.name.setText(dataBean.getName());
            courseHolder.people.setText(dataBean.getViewCount() + "人已加入");
            courseHolder.listen.setVisibility(View.GONE);
            new GlideUtils().load(context , NetworkTitle.OPENRESOURE +  dataBean.getImage()   , courseHolder.course_img);
            courseHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectListener.setListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (courseBeanList != null) {
            return courseBeanList == null ? 0 : courseBeanList.size();
        }else if (liveList != null){
            return liveList == null ? 0 : liveList.size();
        }else{
            return 0 ;
        }
    }

     class CourseHolder extends RecyclerView.ViewHolder{
         private TextView name ,people ;
         private ImageView course_img ;
         private LinearLayout listen ;
         private AutoRelativeLayout rl ;
         public CourseHolder(View itemView) {
             super(itemView);
             name = (TextView) itemView.findViewById(R.id.name);
             people = (TextView) itemView.findViewById(R.id.people);
             listen = (LinearLayout) itemView.findViewById(R.id.listen);
             course_img = (ImageView) itemView.findViewById(R.id.course_img);
             rl = (AutoRelativeLayout) itemView.findViewById(R.id.rl);
         }
     }
}
