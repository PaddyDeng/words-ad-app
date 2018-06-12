package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.MyWheelAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.view.wheelview.widget.WheelView;

import static thinku.com.word.R.id.title_t;

public class AddMyPlanActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(title_t)
    TextView titleT;
    @BindView(R.id.title_iv)
    TextView titleIv;
    @BindView(R.id.day)
    TextView daytext;
    @BindView(R.id.num)
    TextView numtext;
    TextView numOfDay;
    @BindView(R.id.wheelView_rl)
    RelativeLayout wheelViewRl;
    @BindView(R.id.num_of_word)
    TextView numOfWord;
    @BindView(R.id.wheelView_r2)
    RelativeLayout wheelViewR2;
    @BindView(R.id.word_order_group)
    RadioGroup wordGroup ;
    @BindView(R.id.positive_word)
    RadioButton positive_word ;
    @BindView(R.id.disorder)
    RadioButton disorder ;
    Unbinder unbinder;
    @BindView(R.id.name)
    TextView name;
    private WheelView wheel_day, wheel_num;
    private int day = 1;
    private int word = 1;
    private static int isFirst = 0;
    private List<String> dayList, wordList;
    private String words;
    private String days;
    private String packId;
    private int total;
    private String nameTxt;
    private int sort = 2  ;
    private Drawable wordChoseDrawable ;  //  选择
    private Drawable wordUnchoseDrawable ;  // 未选择
    public static void start(Context context, String packId, String total, String name) {
        Intent intent = new Intent(context, AddMyPlanActivity.class);
        intent.putExtra("packId", packId);
        intent.putExtra("total", total);
        intent.putExtra("name", name);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_plan);
        unbinder = ButterKnife.bind(this);
        numOfDay = (TextView) findViewById(R.id.num_of_day);
        Intent intent = getIntent();
        if (intent != null) {
            packId = intent.getStringExtra("packId");
            total = Integer.parseInt(intent.getStringExtra("total"));
            nameTxt = intent.getStringExtra("name");
            referUi();
            init();
        }

    }


    public void referUi() {
        setWheel(total, 1, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    public void init() {
        wordChoseDrawable = getResources().getDrawable(R.mipmap.word_chose);
        wordUnchoseDrawable = getResources().getDrawable(R.mipmap.word_unchose);
        wordChoseDrawable.setBounds(0,0,wordChoseDrawable.getMinimumWidth() ,wordChoseDrawable.getMinimumHeight());
        wordUnchoseDrawable.setBounds(0,0,wordUnchoseDrawable.getMinimumWidth() , wordUnchoseDrawable.getMinimumHeight());
        titleT.setText("我的计划");
        titleIv.setText("确定");
        setTextFlag(daytext);
        setTextFlag(numtext);
        name.setText(nameTxt);
        wordGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.positive_word:
                         positive_word.setChecked(true);
                         disorder.setChecked(false);
                         sort = C.LGAddPlanSortOrder;
                        break;
                    case R.id.disorder:
                        positive_word.setChecked(false);
                        disorder.setChecked(true);
                        sort = C.LGAddPlanSortRandom;
                        break;
                }
            }
        });
    }


    /**
     * 设置下划线
     *
     * @param textView
     */
    public void setTextFlag(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
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
        wheel_day = new WheelView(this, style);
        wheel_day.setWheelAdapter(new MyWheelAdapter(this));
        wheel_day.setWheelSize(7);
        wheel_day.setSkin(WheelView.Skin.Common);
        wheel_day.setWheelData(dayList);
        wheel_day.setSelection(dayInt - 1);
        wheel_day.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst++;
                if (isFirst == 1) {
                    day2num(position);
                } else {
                    isFirst = 0;
                }
                days = numOfDay.getText().toString().trim();
                words = numOfWord.getText().toString().trim();
                daytext.setText(days.substring(0, days.length() - 1));
                numtext.setText(words.substring(0, words.length() - 1));

            }
        });
        wheelViewRl.addView(wheel_day);
        numOfDay.setText(dayList.get(dayInt - 1));
        wheel_num = new WheelView(this, style);
        wheel_num.setWheelAdapter(new MyWheelAdapter(this));
        wheel_num.setWheelSize(7);
        wheel_num.setSkin(WheelView.Skin.Common);
        wheel_num.setWheelData(wordList);

        wheel_num.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                isFirst++;
                if (isFirst == 1) {
                    num2day(position);
                } else {
                    isFirst = 0;
                }
                words = numOfWord.getText().toString().trim();
                days = numOfDay.getText().toString().trim();
                daytext.setText(days.substring(0, days.length() - 1));
                numtext.setText(words.substring(0, words.length() - 1));
            }
        });
        wheelViewR2.addView(wheel_num);
        numOfWord.setText(wordList.get(totals - wordInt));
        days = dayList.get(dayInt - 1);
        words = wordList.get(totals - wordInt);
    }


    private void day2num(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (i + 1));
        wheel_num.setSelection(total - n);
        numOfDay.setText(dayList.get(i));
        numOfWord.setText(wordList.get(total - n));
    }

    private void num2day(int i) {
        int n = (int) Math.ceil(Double.valueOf(total) / (total - i));
        wheel_day.setSelection(n - 1);
        numOfDay.setText(dayList.get(n - 1));
        numOfWord.setText(wordList.get(i));
    }

    @OnClick({R.id.back, R.id.title_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishWithAnim();
                break;
            case R.id.title_iv:
                addPack(packId, daytext.getText().toString().trim(), numtext.getText().toString().trim());
                break;
        }
    }

    public void addPack(final String id, String day, String word) {
        addToCompositeDis(HttpUtil.addPackageObservableOther(id, day, word ,sort+"")
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
                            //  添加词包成功
                            updataNowPack(id);
                        }else{
                            toTast(AddMyPlanActivity.this ,"添加词包成功");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        toTast(AddMyPlanActivity.this, "添加词包失败");
                    }
                }));
    }


    public void updataNowPack(String id ){
        addToCompositeDis(HttpUtil.updataNowPackage( id)
        .subscribe(new Consumer<ResultBeen<Void>>() {
            @Override
            public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                    MainActivity.toMain(AddMyPlanActivity.this);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                toTast(AddMyPlanActivity.this ,throwable.getMessage());
            }
        }));
    }
}
