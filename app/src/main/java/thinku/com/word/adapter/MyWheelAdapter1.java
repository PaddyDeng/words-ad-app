package thinku.com.word.adapter;

/**
 * Created by Administrator on 2018/4/11.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.view.wheelview.adapter.BaseWheelAdapter;

/**
 * Demo
 *
 * @author venshine
 */
public class MyWheelAdapter1 extends BaseWheelAdapter<String> {

    private Context mContext;

    public MyWheelAdapter1(Context context) {
        mContext = context;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list1, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }

}
