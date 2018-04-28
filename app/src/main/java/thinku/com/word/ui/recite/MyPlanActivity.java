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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter;
import thinku.com.word.adapter.WordBagAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.Package;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.wheelview.widget.WheelView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class MyPlanActivity extends BaseActivity implements View.OnClickListener {
    private static int wordPosition = 0;
    private static final String TAG = MyPlanActivity.class.getSimpleName();
    private ImageView back;
    private TextView title_t, edit, num_of_word, num_of_day, title_right_t;
    private RecyclerView words_bag_list;
    private WheelView wheel_day ,wheel_num;
    private int total = 400;
    private int day = 1;
    private int word = 1;
    private List<String> dayList, wordList;
    private RelativeLayout wheelView_rl , wheelView_r2 ;
    private boolean isDelete;
    private WordBagAdapter adapter;
    private List<Package.PackData> packdatas;
    private String[] words;
    private String[] days;
    private String[] Ids;
    private static int isFirst = 0 ;
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
    }


    /**
     * 获取词包数据
     */
    public void initpackage() {
        addToCompositeDis(HttpUtil.changePackage()
                .subscribe(new Consumer<Package>() {
                    @Override
                    public void accept(@NonNull Package aPackages) throws Exception {

                        if (aPackages == null) {
                            LoginHelper.needLogin(MyPlanActivity.this, "你还未登录，请先登录");
                        } else {
                            packdatas = aPackages.getPackDatas();
                            words = new String[packdatas.size()];
                            days = new String[packdatas.size()];
                            Ids = new String[packdatas.size()];
                            total = Integer.parseInt(packdatas.get(0).getTotal());
                            initView();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                })
        );
    }

    private void initView() {
        adapter = new WordBagAdapter(MyPlanActivity.this, packdatas, new SelectListener() {
            @Override
            public void setListener(int position) {
                Ids[position] = packdatas.get(position).getId();
                wordPosition = position;
                total = Integer.parseInt(packdatas.get(position).getTotal());
                try {
                    day = Integer.parseInt(packdatas.get(position).getPlanDay());
                } catch (Exception e) {
                    day = 1;
                }

                try {
                    word = Integer.parseInt(packdatas.get(position).getPlanWords());
                } catch (Exception e) {
                    word = total;
                }
                setWheel(total, day, word);
            }
        });
        words_bag_list.setAdapter(adapter);
        wordPosition = 0;
        Ids[wordPosition] = packdatas.get(0).getId();
        total = Integer.parseInt(packdatas.get(0).getTotal());
        try {
            day = Integer.parseInt(packdatas.get(0).getPlanDay());
        } catch (Exception e) {
            day = 1;
        }
        try {
            word = Integer.parseInt(packdatas.get(0).getPlanWords());
        } catch (Exception e) {
            word = total;
        }
        setWheel(total, day, word);

    }

    public void setWheel(final int totals, final int dayInt, int wordInt) {
        dayList = new ArrayList<>();
        wordList = new ArrayList<>();
        for (int i = 0; i < totals; i++) {
            dayList.add(i + 1 + "天");
            wordList.add(totals - i + "个");
        }
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = getResources().getColor(R.color.mainColor);
        style.backgroundColor = getResources().getColor(R.color.gray);
        wheel_day = new WheelView(this ,style);
        wheel_day.setWheelAdapter(new MyWheelAdapter(this));
        wheel_day.setWheelSize(9);
        wheel_day.setSkin(WheelView.Skin.Common);
        wheel_day.setWheelData(dayList);
        wheel_day.setSelection(dayInt -1);
        wheel_day.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst ++ ;
                if (isFirst ==1) {
                    day2num(position);
                }else{
                    isFirst = 0 ;
                }
                days[wordPosition] = num_of_day.getText().toString().trim();
                words[wordPosition] = num_of_word.getText().toString().trim();
            }
        });
        wheelView_rl.addView(wheel_day);
        num_of_day.setText(dayList.get(dayInt - 1));

        wheel_num = new WheelView(this ,style);
        wheel_num.setWheelAdapter(new MyWheelAdapter(this));
        wheel_num.setWheelSize(9);
        wheel_num.setSkin(WheelView.Skin.Common);
        wheel_num.setWheelData(wordList);

        wheel_num.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst ++ ;
                if (isFirst ==1) {
                    num2day(position);
                }else{
                    isFirst = 0 ;
                }
                words[wordPosition] = num_of_word.getText().toString().trim();
                days[wordPosition] = num_of_day.getText().toString().trim();
            }
        });
//        wheel_num.setSelection(totals - wordInt );
        wheelView_r2.addView(wheel_num);
        num_of_word.setText(wordList.get(totals - wordInt));
        days[wordPosition] = dayList.get(dayInt - 1);
        words[wordPosition] = wordList.get(totals - wordInt);
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("我的计划");
        title_right_t = (TextView) findViewById(R.id.title_right_t);
        title_right_t.setVisibility(View.VISIBLE);
        edit = (TextView) findViewById(R.id.edit);
        words_bag_list = (RecyclerView) findViewById(R.id.words_bag_list);
        LinearLayoutManager manager = new LinearLayoutManager(MyPlanActivity.this, LinearLayoutManager.HORIZONTAL, false);
        words_bag_list.setLayoutManager(manager);
        num_of_day = (TextView) findViewById(R.id.num_of_day);
        num_of_word = (TextView) findViewById(R.id.num_of_word);
        wheelView_rl = (RelativeLayout) findViewById(R.id.wheelView_rl);
        wheelView_r2 = (RelativeLayout) findViewById(R.id.wheelView_r2);
    }

    private void day2num(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (i + 1));
//        wheel_num.(wordList, total - n);
        wheel_num.setSelection(total - n);
        num_of_day.setText(dayList.get(i));
        num_of_word.setText(wordList.get(total - n));
    }

    private void num2day(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (total - i));
//        wheel_day.setItems(dayList, n - 1);
        wheel_day.setSelection(n -1 );
        num_of_day.setText(dayList.get(n - 1));
        num_of_word.setText(wordList.get(i));
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
                jsonObject.put("planDay", StringUtils.spilitNum(days[i]));
                jsonObject.put("planWords", StringUtils.spilitNum(words[i]));
                jsonArray.add(i, jsonObject);
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
            toTast( "请为所有词包选择计划");
        } else {
            String JsonString = createJSONObject().toJSONString();
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
}

