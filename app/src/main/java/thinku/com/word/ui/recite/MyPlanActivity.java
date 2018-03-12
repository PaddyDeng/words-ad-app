package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.WordBagAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.view.wheelView.WheelView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class MyPlanActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title_t,edit,num_of_word,num_of_day,title_right_t;
    private RecyclerView words_bag_list;
    private WheelView wheel_day,wheel_num;
    private int total=1000;
    private List<String> dayList,wordList;
    private boolean isDelete;
    private WordBagAdapter adapter;

    public static void start(Context context){
        Intent intent =new Intent(context,MyPlanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        findView();
        initView();
        setClick();
    }

    private void setClick() {
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        title_right_t.setOnClickListener(this);
    }

    private void initView() {
        List<String> list =new ArrayList<>();
        list.add("测试");
        list.add("测试1");
        list.add("测试2");
        adapter =new WordBagAdapter(MyPlanActivity.this, list, new SelectListener() {
            @Override
            public void setListener(int position) {

            }
        });
        words_bag_list.setAdapter(adapter);

        dayList =new ArrayList<>();
        wordList =new ArrayList<>();
        for (int i = 0; i < total; i++) {
            dayList.add(i+1+"天");
            wordList.add(total-i+"个");
        }
        wheel_day.setItems(dayList,0);
        wheel_num.setItems(wordList,0);

        wheel_day.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                day2num(selectedIndex);
            }
        });
        wheel_num.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                num2day(selectedIndex);
            }
        });
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("我的计划");
        title_right_t = (TextView) findViewById(R.id.title_right_t);
        title_right_t.setVisibility(View.VISIBLE);
        edit = (TextView) findViewById(R.id.edit);
        words_bag_list = (RecyclerView) findViewById(R.id.words_bag_list);
        LinearLayoutManager manager =new LinearLayoutManager(MyPlanActivity.this,LinearLayoutManager.HORIZONTAL,false);
        words_bag_list.setLayoutManager(manager);
        num_of_day = (TextView) findViewById(R.id.num_of_day);
        wheel_day = (WheelView) findViewById(R.id.wheel_day);
        num_of_word = (TextView) findViewById(R.id.num_of_word);
        wheel_num = (WheelView) findViewById(R.id.wheel_num);
    }

    private void day2num(int i){
        int n = (int) Math.ceil(Double.valueOf(total)/(i+1));
        wheel_num.setItems(wordList,total-n);
        num_of_day.setText(dayList.get(i));
        num_of_word.setText(wordList.get(total-n));
    }
    private void num2day(int i){
        int n = (int) Math.ceil(Double.valueOf(total)/(total-i));
        wheel_day.setItems(dayList,n-1);
        num_of_day.setText(dayList.get(n-1));
        num_of_word.setText(wordList.get(i));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                isDelete=!isDelete;
                if(isDelete){
                    edit.setText("确定");
                }else{
                    edit.setText("编辑");
                }
                adapter.setDelete(isDelete);
                break;
            case R.id.title_right_t:

                break;
        }
    }
}
