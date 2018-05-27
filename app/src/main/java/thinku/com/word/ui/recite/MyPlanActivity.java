package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter;
import thinku.com.word.adapter.WordBagAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.Package;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.callback.DeleteListener;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class MyPlanActivity extends BaseActivity implements View.OnClickListener, DeleteListener {
    private static final int REQUEST_CODE = 10;
    private static int wordPosition = 0;  //  现在在那个package 位置
    private static final String TAG = MyPlanActivity.class.getSimpleName();
    private ImageView back;
    private TextView title_t, edit, num_of_word, num_of_day, title_right_t;
    private RecyclerView words_bag_list;
    private WheelView wheel_day, wheel_num;
    private int total = 400;
    private int day = 1;
    private int word = 1;
    private List<String> dayList = new ArrayList<>(), wordList = new ArrayList<>();
    private RelativeLayout wheelView_rl, wheelView_r2;
    private boolean isDelete;
    private WordBagAdapter adapter;
    private List<Package.PackData> packdatas;
    private String[] words;
    private String[] days;
    private String[] Ids;
    private int nowPackage = 0;
    private int isFirst = 0;   //    0  代表手机滑动wheelView   1 代表没有滑动WheelView
    private boolean isInit = false;
    private LinearLayoutManager manager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyPlanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        findView();
        setClick();
        initpackage();
    }

    private void setClick() {
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        title_right_t.setOnClickListener(this);
        initView();
    }

    /**
     * 获取词包数据
     */
    public void initpackage() {
        addToCompositeDis(HttpUtil.changePackage()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<Package>() {
                    @Override
                    public void accept(@NonNull Package aPackages) throws Exception {
                        dismissLoadDialog();
                        int nowPackId = aPackages.getNowPackage();
                        packdatas.clear();
                        packdatas.addAll(aPackages.getPackDatas());
                        initWheelPackData(packdatas, nowPackId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                })
        );
    }

    public void initWheelPackData(List<Package.PackData> packDatas, int nowPackId) {
        isInit = true;
        words = new String[packdatas.size()];
        days = new String[packdatas.size()];
        Ids = new String[packdatas.size()];
        for (int i = 0; i < packDatas.size(); i++) {
            Package.PackData packData = packDatas.get(i);
            words[i] = packData.getPlanWords();
            days[i] = packData.getPlanDay();
            Ids[i] = packData.getCatId();
            if (nowPackId == Integer.parseInt(packData.getCatId())) {
                nowPackage = packDatas.indexOf(packData);
            }
        }
        total = Integer.parseInt(packdatas.get(nowPackage).getTotal());
        words_bag_list.setAdapter(adapter);
        manager.scrollToPositionWithOffset(nowPackage, 0);
        adapter.setSelectP(nowPackage);
        wordPosition = nowPackage;
        try {
            day = Integer.parseInt(packdatas.get(nowPackage).getPlanDay()) -1;
        } catch (Exception e) {
            Log.e(TAG, "initWheelPackData: " + e.getMessage());
            day = 0;
        }
        try {
            word = Integer.parseInt(packdatas.get(nowPackage).getPlanWords()) -1;
        } catch (Exception e) {
            word = total -1;
        }
        setWheel(total, day, word);
    }

    //  初始化adapter
    private void initView() {
        packdatas = new ArrayList<>();
        adapter = new WordBagAdapter(MyPlanActivity.this, packdatas, new SelectListener() {

            @Override
            public void setListener(final int position) {
                addToCompositeDis(HttpUtil.updataNowPackage(packdatas.get(position).getCatId() + "")
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                showLoadDialog();
                            }
                        })
                        .subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                                isInit = false;
                                dismissLoadDialog();
                                if (getHttpResSuc(voidResultBeen.getCode())) {
                                    Ids[position] = packdatas.get(position).getCatId();
                                    wordPosition = position;
                                    total = Integer.parseInt(packdatas.get(position).getTotal());
                                    try {
                                        day = Integer.parseInt(packdatas.get(position).getPlanDay()) -1;
                                    } catch (Exception e) {
                                        day = 0;
                                    }

                                    try {
                                        word = Integer.parseInt(packdatas.get(position).getPlanWords()) -1;
                                    } catch (Exception e) {
                                        word = total  -1;
                                    }
                                    setWheel(total, day, word);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                dismissLoadDialog();
                            }
                        }));
            }
        });
        adapter.setDeleteListener(this);
    }
    public void setWheel(final int totals, final int dayInt, int wordInt) {
        Log.e(TAG, "setWheel: " + totals +"  " + dayInt + "  " + wordInt );
        isFirst = 0 ;
        wheelView_r2.removeAllViews();
        wheelView_rl.removeAllViews();
        wheel_day = new WheelView(this);
        wheel_num = new WheelView(this);
        wheel_num.setWheelAdapter(new MyWheelAdapter(this));
        wheel_day.setWheelAdapter(new MyWheelAdapter(this));

        if (dayList != null && dayList.size() > 0) {
            dayList.clear();
        }
        if (wordList != null && wordList.size() > 0) {
            wordList.clear();
        }
        for (int i = 0; i < totals; i++) {
            dayList.add(i + 1 + "天");
            wordList.add(i + 1+ "个");
        }
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = getResources().getColor(R.color.mainColor);
        style.backgroundColor = getResources().getColor(R.color.gray);
        wheel_day.setStyle(style);
        wheel_num.setStyle(style);
        wheel_day.setSkin(WheelView.Skin.Common);
        wheel_num.setSkin(WheelView.Skin.Common);
        wheel_day.setWheelData(dayList);
        wheel_num.setWheelData(wordList);
        wheel_day.setWheelSize(7);
        wheel_num.setWheelSize(7);
        wheel_day.setSelection(dayInt );
//        wheel_num.setSelection(wordInt );
        num_of_day.setText(dayList.get(dayInt));
        num_of_word.setText(wordList.get(wordInt));
        wheel_day.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                  //  为0 表示没有联动滑动 ， 所有联动滑动  ，如果为1 表明已经联动滑动了，禁止联动滑动
                if (isFirst == 0) {
                    day2num(position);
                    isFirst++;
                } else {
                    isFirst = 0;
                }
                words[wordPosition] = num_of_word.getText().toString().trim();
                days[wordPosition] = num_of_day.getText().toString().trim();

                days[wordPosition] = dayList.get(position);
                words[wordPosition] = wordList.get(position);
            }
        });

        wheel_num.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                if (isFirst == 0) {
//                    num2day(position);
                    isFirst++;
                } else {
                    isFirst = 0;
                }

                words[wordPosition] = num_of_word.getText().toString().trim();
                days[wordPosition] = num_of_day.getText().toString().trim();
            }
        });
        wheelView_rl.addView(wheel_day);
        wheelView_r2.addView(wheel_num);
        days[wordPosition] = dayList.get(dayInt - 1);
        words[wordPosition] = wordList.get(wordInt - 1);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("我的计划");
        title_right_t = (TextView) findViewById(R.id.title_right_t);
        title_right_t.setVisibility(View.VISIBLE);
        edit = (TextView) findViewById(R.id.edit);
        words_bag_list = (RecyclerView) findViewById(R.id.words_bag_list);
        num_of_day = (TextView) findViewById(R.id.num_of_day);
        num_of_word = (TextView) findViewById(R.id.num_of_word);
        wheelView_rl = (RelativeLayout) findViewById(R.id.wheelView_rl);
        wheelView_r2 = (RelativeLayout) findViewById(R.id.wheelView_r2);
        manager = new LinearLayoutManager(MyPlanActivity.this, LinearLayoutManager.HORIZONTAL, false);
        words_bag_list.setLayoutManager(manager);
    }

    private void day2num(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (i + 1));
        wheel_num.setSelection(n  - 1);
//        num_of_day.setText(dayList.get(i));
        num_of_word.setText(wordList.get(n  - 1));
        Log.e(TAG, "day2num: " + n  + "  " + i );
    }

    private void num2day(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (i + 1));
        wheel_day.setSelection(n -1  );
        num_of_day.setText(dayList.get(n  -1 ));
//        num_of_word.setText(wordList.get(i  ));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                isDelete = !isDelete;
                if (isDelete) {
                    edit.setText("确定");
                } else {
                    edit.setText("编辑");
                }
                adapter.setDelete(isDelete);
                break;
            case R.id.title_right_t:
                updataPlan();
                break;
        }
    }

    /**
     * 将请求变为json 字符串
     *
     * @return
     */
    private JSONArray createJSONObject() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < packdatas.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", Ids[i]);
                jsonObject.put("planDay", StringUtils.getStringNum(words[i]));
                jsonObject.put("planWords", StringUtils.getStringNum(days[i]));
                jsonArray.add(jsonObject);
            }
            // 返回一个JSONArray对象
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传计划
     */
    public void updataPlan() {
        if (!checkPlan()) {
            toTast("请为所有词包选择计划");
        } else {
            String JsonString = createJSONObject().toJSONString();
            Log.e(TAG, "updataPlan: " + JsonString);
            showLoadDialog();
            addToCompositeDis(HttpUtil.updataPackage(JsonString)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<ResultBeen<Void>>() {
                        @Override
                        public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                            dismissLoadDialog();
                            if (getHttpResSuc(voidResultBeen.getCode())) {
                                toTast(MyPlanActivity.this, "修改成功");
                            } else {
                                toTast(MyPlanActivity.this, "修改失败");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            toTast(MyPlanActivity.this, throwable.getMessage());
                        }
                    }));
        }
    }


    public boolean checkPlan() {
        boolean isPlan = true;
        for (int i = 0; i < packdatas.size(); i++) {
            if (TextUtils.isEmpty(words[i])) {
                isPlan = false;
                break;
            }
            if (TextUtils.isEmpty(days[i])) {
                isPlan = false;
                break;
            }
        }
        return isPlan;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            MainActivity.toMain(MyPlanActivity.this);
        }
    }

    @Override
    public void delete(final RecyclerView.ViewHolder viewHolder, final int poisition) {
        Package.PackData packData = packdatas.get(poisition);
        addToCompositeDis(HttpUtil.deletePackageObservable(packData.getId())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        dismissLoadDialog();
                        if (getHttpResSuc(voidResultBeen.getCode())) {
                            if (poisition != adapter.getSelectP()) {
                                packdatas.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            } else {
                                if (packdatas.size() > 0) {
                                    packdatas.remove(viewHolder.getAdapterPosition());
                                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                    adapter.setSelectP(0);
                                    manager.scrollToPositionWithOffset(viewHolder.getAdapterPosition(), 0);
                                }
                            }
                        } else {
                            toTast(MyPlanActivity.this, voidResultBeen.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));

    }
}

