package thinku.com.word.ui.periphery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/5/7.
 */

public class CourseAdapter extends RecyclerView.Adapter {
    private List<CourseBean> courseBeanList ;
    private Context context ;

    public CourseAdapter(Context context ,List<CourseBean> courseBeanList ){
        this.context = context ;
        this.courseBeanList = courseBeanList ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CourseHolder courseHolder = new CourseHolder(LayoutInflater.from(context).inflate(R.layout.item_round_course ,parent ,false));
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseHolder courseHolder = (CourseHolder) holder;
        CourseBean courseBean = courseBeanList.get(position);
        courseHolder.name.setText(courseBean.getName());
        courseHolder.people.setText(courseBean.getView()+"人已加入");
        new GlideUtils().load(context ,courseBean.getImage() , courseHolder.course_img);

    }

    @Override
    public int getItemCount() {
        return courseBeanList == null? 0 : courseBeanList.size();
    }

     class CourseHolder extends RecyclerView.ViewHolder{
         private TextView name ,people ;
         private ImageView course_img ;
         private LinearLayout listen ;
         public CourseHolder(View itemView) {
             super(itemView);
             AutoUtils.autoSize(itemView);
             name = (TextView) itemView.findViewById(R.id.name);
             people = (TextView) itemView.findViewById(R.id.people);
             listen = (LinearLayout) itemView.findViewById(R.id.listen);
             course_img = (ImageView) itemView.findViewById(R.id.course_img);
         }
     }
}
