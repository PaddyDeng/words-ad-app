package thinku.com.word.ui.personalCenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import ch.ielse.view.SwitchView;
import thinku.com.word.R;
import thinku.com.word.callback.LongClickLisetener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.callback.SetSwitchChangeListener;
import thinku.com.word.db.bean.Clock;

/**
 * Created by Administrator on 2018/5/3.
 */

public class ClockAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Clock> clocks;
    private SelectRlClickListener selectRlClickListener;
    private LongClickLisetener longClickLisetener;
    private SetSwitchChangeListener setSwitchChangeListener;

    public ClockAdapter(Context context, List<Clock> clocks) {
        this.context = context;
        this.clocks = clocks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClockHolder clockHolder = new ClockHolder(LayoutInflater.from(context).inflate(R.layout.item_clock, parent, false));
        return clockHolder;
    }

    public void setSelectRlClickListener(SelectRlClickListener selectRlClickListener) {
        this.selectRlClickListener = selectRlClickListener;
        notifyDataSetChanged();
    }

    public void setLongClickLisetener(LongClickLisetener longClickLisetener) {
        this.longClickLisetener = longClickLisetener;
    }

    public void SetSwitchChangeListener(SetSwitchChangeListener setSwitchChangeListener) {
        this.setSwitchChangeListener = setSwitchChangeListener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ClockHolder clockHolder = (ClockHolder) holder;
        Clock clock = clocks.get(position);
        clockHolder.time.setText(clock.getTime());
        clockHolder.week.setText(clock.getWeek());
        clockHolder.switchView.setOpened(clock.isClock());
        clockHolder.switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                setSwitchChangeListener.toggleToOn(view, position);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                setSwitchChangeListener.toggleToOff(view, position);
            }
        });
        clockHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRlClickListener.setClickListener(position, clockHolder, v);
            }
        });
        clockHolder.rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickLisetener.LongClickListener(position, clockHolder, v);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return clocks == null ? 0 : clocks.size();
    }

    public class ClockHolder extends RecyclerView.ViewHolder {
        private TextView time, week;
        private SwitchView switchView;
        private RelativeLayout rl;

        public ClockHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            time = (TextView) itemView.findViewById(R.id.time);
            week = (TextView) itemView.findViewById(R.id.week);
            switchView = (SwitchView) itemView.findViewById(R.id.switch_button);
        }
    }
}
