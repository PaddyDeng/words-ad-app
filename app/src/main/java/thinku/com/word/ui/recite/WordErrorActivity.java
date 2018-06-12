package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.ErrorTypeAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;

/**
 * Created by Administrator on 2018/4/10.
 */

public class WordErrorActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.check_1)
    ImageView check1;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.check_2)
    ImageView check2;
    @BindView(R.id.r2)
    RelativeLayout r2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.check_3)
    ImageView check3;
    @BindView(R.id.r3)
    RelativeLayout r3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.check_4)
    ImageView check4;
    @BindView(R.id.r4)
    RelativeLayout r4;


    private static String wrongId;
    private static String wrongContent;
    private List<ImageView> checks ;
    private static String wordId ;
    public static void start(Context context , String wordId){
        Intent intent = new Intent(context ,WordErrorActivity.class);
        intent.putExtra("wordId" , wordId);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_correction);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null){
            wordId = getIntent().getStringExtra("wordId");
        }
        initView();
    }

    public void initView() {
        titleT.setText("单词纠错");
        checks = new ArrayList<>();
        checks.add(check1);
        checks.add(check2);
        checks.add(check3);
        checks.add(check4);
    }

    @OnClick({R.id.back , R.id.rl , R.id.r2 , R.id.r3, R.id.r4 ,R.id.cancel ,R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl:
                checkable(0);
                break;
            case R.id.r2:
                checkable(1);
                break;
            case R.id.r3:
                checkable(2);
                break;
            case R.id.r4:
                checkable(3);
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.submit:
                errorRecovery();
                break;
        }
    }

    public void errorRecovery(){
        String content = et.getText().toString().trim();
        addToCompositeDis(HttpUtil.errorRecoveryObservable(wrongId ,wordId ,content)
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                showLoadDialog();
            }
        })
        .subscribe(new Consumer<ResultBeen<Void>>() {
            @Override
            public void accept(ResultBeen<Void> voidResultBeen) throws Exception {
                dismissLoadDialog();
                if (getHttpResSuc(voidResultBeen.getCode())){
                    toTast(WordErrorActivity.this ,"谢谢提醒");
                    WordErrorActivity.this.finishWithAnim();
                }else{
                    toTast(WordErrorActivity.this ,"提交失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadDialog();
                toTast(WordErrorActivity.this ,throwable.getMessage());
            }
        }));
    }

    public void checkable(int poisition){
        for (int i = 0 ; i < checks.size() ;i ++){
            if (i == poisition) {
                checks.get(poisition).setSelected(true);
                wrongId = ""+poisition + 1 ;
            }
            else checks.get(i).setSelected(false);
        }
    }
}
