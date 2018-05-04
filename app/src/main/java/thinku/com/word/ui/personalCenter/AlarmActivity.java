package thinku.com.word.ui.personalCenter;

import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.LongClickLisetener;
import thinku.com.word.callback.SelectRlClickListener;
import thinku.com.word.callback.SetSwitchChangeListener;
import thinku.com.word.db.ClockDao;
import thinku.com.word.db.bean.Clock;
import thinku.com.word.ui.personalCenter.adapter.ClockAdapter;
import thinku.com.word.utils.clock.AlarmManagerUtil;

public class AlarmActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.no_alarm)
    TextView noAlarm;
    @BindView(R.id.alarm)
    RecyclerView alarm;


    private ClockAdapter clockAdapter;
    private List<Clock> clocks;
    private ClockDao clockDao;

    private AlarmManager alarmManager;

    public static void toAlarm(Context context) {
        Intent intent = new Intent(context, AlarmActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        initView();
        init();
    }

    public void init() {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
    }

    /**
     * 设置闹钟
     */
    private void setClock(Clock clock) {
        String time = clock.getTime();
        String cycle = clock.getWeek();
        if (!TextUtils.isEmpty(time)) {
            String[] times = time.split(":");
            if ("每天".equals(cycle)) {//是每天的闹钟
                String id = times[0] + times[1] + 0 ;
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), Integer.parseInt(id), 1, "该复习单词了", 2);
                clock.setC_id(id);
//            } if(cycle == -1){//是只响一次的闹钟
//                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
//                        (times[1]), 0, 0, "该复习单词了", 2);
            } else {//多选，周几的闹钟
                String[] weeks = cycle.split("\\t");
                String id = "";
                for (int i = 0; i < weeks.length; i++) {
                    int ring = parseRepeat(weeks[i]);
                    String o_id = times[0] + times[1] + ring ;
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), Integer.parseInt(o_id), ring, "该复习单词了", 2);
                    id=o_id+",";
                    Log.e(TAG, "setClock: " + o_id +  "   "+ Integer.parseInt(o_id) );
                }
                clock.setC_id(id);
            }
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }

    public int parseRepeat(String week) {
        int weekNum = -1;
        switch (week) {
            case "星期一":
                weekNum = 1;
                break;
            case "星期二":
                weekNum = 2;
                break;
            case "星期三":
                weekNum = 3;
                break;
            case "星期四":
                weekNum = 4;
                break;
            case "星期五":
                weekNum = 5;
                break;
            case "星期六":
                weekNum = 6;
                break;
            case "星期天":
                weekNum = 7;
                break;
            default:
                break;
        }
        return weekNum;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initView() {
        titleT.setText("闹钟");
        titleIv.setBackgroundResource(R.mipmap.alarm_add);
        clocks = new ArrayList<>();
        clockAdapter = new ClockAdapter(this, clocks);
        clockAdapter.setSelectRlClickListener(new SelectRlClickListener() {
            @Override
            public void setClickListener(int position, RecyclerView.ViewHolder viewHolder, View view) {
                    Clock clock = clocks.get(position);
                   AlarmAddActivity.toAddAlarm(AlarmActivity.this ,clock);
            }
        });
        clockAdapter.setLongClickLisetener(new LongClickLisetener() {
            @Override
            public void LongClickListener(int position, RecyclerView.ViewHolder hodler, View view) {
                Clock clock = clocks.get(position);
                if (clock.isClock()){
                    cancelClock(clock);
                }
                deleteClock(position);
            }
        });
        clockAdapter.SetSwitchChangeListener(new SetSwitchChangeListener() {
            @Override
            public void toggleToOn(SwitchView view, int position) {
                Clock clock = clocks.get(position);
                setClock(clock);
                clock.setClock(true);
                clockDao.updateClock(clock);
                view.setOpened(true);
            }

            @Override
            public void toggleToOff(SwitchView view, int position) {
                Clock clock = clocks.get(position);
                cancelClock(clock);
                view.setOpened(false);
                clock.setClock(false);
                clockDao.updateClock(clock);
            }
        });
        alarm.setLayoutManager(new LinearLayoutManager(this));
        alarm.setAdapter(clockAdapter);
    }

    /**
     * 取消闹钟
     * @param clock
     */
    public void cancelClock(Clock clock){
        String c_id  = clock.getC_id();
        Log.e(TAG, "cancelClock: "  + c_id );
        String c_ids[] = c_id.split(",");
        if (c_ids.length > 0) {
            for (int i = 0; i < c_ids.length; i++) {
                Log.e(TAG, "cancelClock: " + c_ids );
                AlarmManagerUtil.cancelAlarm(this, Integer.parseInt(c_ids[i]));
            }
        }
    }

    public void deleteClock(final int position) {
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setMessage("是否删除闹钟")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clockDao.delete(clocks.get(position));
                        clocks.remove(position);
                        clockAdapter.notifyItemRemoved(position);
                        clockAdapter.notifyItemRangeChanged(position, clocks.size());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(true).create();
        builder.show();
    }

    public void initData() {
        clockDao = new ClockDao(this);
        Observable<List<Clock>> observable = Observable.create(new ObservableOnSubscribe<List<Clock>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Clock>> e) throws Exception {
                e.onNext(clockDao.getAllClock());
                e.onComplete();
            }
        });
        Observer<List<Clock>> observer = new Observer<List<Clock>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Clock> clockList) {
                clocks.clear();
                clocks.addAll(clockList);
                clockAdapter.notifyDataSetChanged();
                if (clockList != null && clockList.size() > 0) {
                    noAlarm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @OnClick({R.id.title_iv, R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_iv:
                AlarmAddActivity.start(this);
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        clockDao.close();
//        clockDao = null;
    }
}
