package thinku.com.word.ui.recite;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.report.*;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.PopHelper;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.view.wheelview.widget.WheelView;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ReviewTimeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.start_time)
    TextView startTime;
    WheelView wheelStart;
    @BindView(R.id.end_time)
    TextView endTime;
    WheelView wheelEnd;
    @BindView(R.id.start_rl)
    RelativeLayout startRl;
    @BindView(R.id.end_rl)
    RelativeLayout endRl;
    @BindView(R.id.center)
    TextView center;

    private PopHelper popHelper;
    private WheelView.WheelViewStyle style;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_time);
        ButterKnife.bind(this);
        findView();
        initPopWindow();
        initData();
    }

    public void initWheelView() {
        style = new WheelView.WheelViewStyle();
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = getResources().getColor(R.color.mainColor);
        style.backgroundColor = getResources().getColor(R.color.gray);
        wheelStart = new WheelView(this ,style);
        wheelEnd = new WheelView(this ,style);
        wheelStart.setWheelAdapter(new MyWheelAdapter(this));
        wheelStart.setWheelSize(7);
        wheelEnd.setWheelAdapter(new MyWheelAdapter(this));
        wheelEnd.setWheelSize(7);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initPopWindow() {
        final ArrayList<String> names = new ArrayList<>();
        names.add("中英");
        names.add("英中");
        names.add("听写");
        popHelper = PopHelper.create(ReviewTimeActivity.this);
        popHelper.setData(names);
        popHelper.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                choseTxt.setText(names.get(position));
                SharedPreferencesUtils.setChoseMode(ReviewTimeActivity.this, names.get(position));
                popHelper.dismiss();
            }
        });
        popHelper.initRecyclerView();
    }

    public void showPopWindow() {
        popHelper.show(titleIv);
    }

    private void initData() {
        Observable.just("")
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull String s) throws Exception {
                        List<String> startList;
                        try {
                            long start = DateUtil.dateToLong(DateUtil.stringToDate("2018-03-01", "yyyy-MM-dd"));
                            long now = System.currentTimeMillis();
                            startList = DateUtil.betweenDays(start, now);
                        } catch (Exception e) {
                            startList = new ArrayList<>();
                        }
                        return startList;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull final List<String> strings) throws Exception {
                        initWheelView();
                        wheelStart.setSkin(WheelView.Skin.Common);
                        wheelStart.setWheelData(strings);
                        wheelStart.setSelection(strings.size() - 1);
                        wheelEnd.setSkin(WheelView.Skin.Common);
                        wheelEnd.setWheelData(strings);
                        wheelEnd.setSelection(strings.size() - 1);
                        wheelStart.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                            @Override
                            public void onItemSelected(int position, Object o) {
                                if (position < strings.size())
                                    startTime.setText(strings.get(position));
                            }
                        });

                        wheelEnd.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                            @Override
                            public void onItemSelected(int position, Object o) {
                                if (position < strings.size())
                                    endTime.setText(strings.get(position));
                            }
                        });
                        startRl.addView(wheelStart);
                        endRl.addView(wheelEnd);
                    }
                });
    }


    private void findView() {
        titleT.setText("按时间复习");
        titleIv.setBackgroundResource(R.mipmap.wrong_chose);
        String choseMode = SharedPreferencesUtils.getChoseMode(ReviewTimeActivity.this);
        if (!TextUtils.isEmpty(choseMode)) choseTxt.setText(choseMode);
    }

    @OnClick({R.id.back, R.id.title_iv, R.id.center})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_iv:
                showPopWindow();
                break;
            case R.id.center:
                try {
                    long start = DateUtil.stringToLong(startTime.getText().toString().trim(), "yyyy-MM-dd");
                    long end = DateUtil.stringToLong(endTime.getText().toString().trim(), "yyyy-MM-dd");
                    if (start >= end) {
                        toTast(ReviewTimeActivity.this, "开始日期不能大于截止日期");
                    } else {
                        updateTime();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    /**
     * 时间
     */
    public void updateTime() {
        addToCompositeDis(HttpUtil.timeSelectObservable(startTime.getText().toString().trim(), endTime.getText().toString().trim())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        dismissLoadDialog();
                        if (strings != null && strings.size() == 0) {
                            toTast(ReviewTimeActivity.this, "该时间段没有复习单词");
                        } else {
                            showReviewDialog((ArrayList<String>) strings);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        toTast(ReviewTimeActivity.this, throwable.getMessage());
                    }
                }));
    }


    public void showReviewDialog(final ArrayList<String> wordIds) {
        View view = LayoutInflater.from(ReviewTimeActivity.this).inflate(R.layout.dialog_review_time, null, false);
        final Dialog dialog = new AlertDialog.Builder(ReviewTimeActivity.this)
                .setView(view)
                .setCancelable(true)
                .create();
        TextView num = (TextView) view.findViewById(R.id.num);
        TextView review = (TextView) view.findViewById(R.id.review);
        TextView not_review = (TextView) view.findViewById(R.id.not_review);
        num.setText(wordIds.size() + "");
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thinku.com.word.ui.report.WordEvaluateFragment.start(ReviewTimeActivity.this, wordIds);
                dialog.dismiss();
            }
        });
        not_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
