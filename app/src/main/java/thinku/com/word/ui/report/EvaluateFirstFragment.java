package thinku.com.word.ui.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.PkResultBeen;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 评估首页
 */

public class EvaluateFirstFragment extends BaseActivity {

    public  static void start(Context context){
        Intent intent = new Intent(context ,EvaluateFirstFragment.class);
        context.startActivity(intent);
    }
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.portrait)
    CircleImageView portrait;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.evaluate)
    TextView evaluate;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_first);
        unbinder = ButterKnife.bind(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public void initView(){
        titleT.setText("评估");
        new GlideUtils().loadCircle(this ,NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(this) , portrait);
        int evaNum = SharedPreferencesUtils.getEvaluationNum(this);
        String nametxt = SharedPreferencesUtils.getString("nickname",this);
        name.setText(nametxt);
        if (evaNum == 0) state.setText("未评估");
        else state.setText(evaNum+"");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.back)
    public void back() {
        this.finishWithAnim();
    }

    @OnClick(R.id.evaluate)
    public void evaluate(){
        addToCompositeDis(HttpUtil.evaStartObservable()
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
//                showLoadDialog();
            }
        }).subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(ResultBeen<Void> voidResultBeen) throws Exception {
                        if (getHttpResSuc(voidResultBeen.getCode())){
                            EvaWordActivity.start(EvaluateFirstFragment.this);
                        }
                    }
                }));
    }

    @OnClick(R.id.check_result)
    public void check(){
        EvaluateRankingActivity.start(EvaluateFirstFragment.this);
    }
}
