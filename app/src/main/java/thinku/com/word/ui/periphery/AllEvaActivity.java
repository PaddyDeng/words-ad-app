package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.EvaAdapter;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.HttpUtils;

public class AllEvaActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.eva_all)
    RecyclerView evaAll;

    private EvaAdapter evaAdapter ;
    private List<RoundBean.CaseBean> caseBeanList ;

    public static void start(Context context){
        Intent intent = new Intent(context ,AllEvaActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_eva);
        ButterKnife.bind(this);
        init();
        initAdapter();
        initData();
    }


    public void initData(){
        if (HttpUtils.isConnected(this)) {
            addToCompositeDis(HttpUtil.caseBeanObservable()
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            showLoadDialog();
                        }
                    }).subscribe(new Consumer<List<RoundBean.CaseBean>>() {
                        @Override
                        public void accept(@NonNull List<RoundBean.CaseBean> caseBeans) throws Exception {
                            dismissLoadDialog();
                            if (caseBeans != null && caseBeans.size() > 0){
                                caseBeanList.addAll(caseBeans);
                                evaAdapter.notifyDataSetChanged();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            toTast(AllEvaActivity.this, throwable.getMessage());
                        }
                    }));
        }else{
            toTast(this ,"请联网");
        }
    }


    public void init(){
        titleT.setText("案列列表");
    }

    public void initAdapter(){
        caseBeanList = new ArrayList<>();
        evaAdapter = new EvaAdapter(this ,caseBeanList);
        evaAdapter.setSelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                EvaAllActivity.start(AllEvaActivity.this, caseBeanList.get(position));
            }
        });
        evaAll.setLayoutManager(new LinearLayoutManager(this));
        evaAll.setAdapter(evaAdapter);
    }


    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }
}
