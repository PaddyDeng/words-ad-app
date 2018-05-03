package thinku.com.word.ui.personalCenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import ch.ielse.view.SwitchView;
import thinku.com.word.R;
import thinku.com.word.ui.personalCenter.bean.Clock;

/**
 * Created by Administrator on 2018/5/3.
 */

public class ClockAdapter  extends RecyclerView.Adapter{

    private Context context ;
    private List<Clock> clocks ;

    public ClockAdapter(Context context ,List<Clock> clocks){
        this.context = context ;
        this.clocks = clocks ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClockHolder clockHolder = new ClockHolder(LayoutInflater.from(context).inflate(R.layout.item_clock ,parent ,false));
        return clockHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClockHolder clockHolder = (ClockHolder) holder;
        Clock clock = clocks.get(position);
        clockHolder.time.setText(clock.getTime());
        clockHolder.week.setText(clock.getWeek());
        clockHolder.switchView.setOpened(clock.isCheck());
    }

    @Override
    public int getItemCount() {
        return clocks == null ? 0: clocks.size();
    }

    public class ClockHolder extends RecyclerView.ViewHolder{
        private TextView time ,week ;
        private SwitchView switchView ;
        public ClockHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            week = (TextView) itemView.findViewById(R.id.week);
            switchView = (SwitchView) itemView.findViewById(R.id.switch_button);
        }
    }
}
