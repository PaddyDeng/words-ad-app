package thinku.com.word.ui.report;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.adapter.EVWordResultAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.WordResultBeen;
import thinku.com.word.bean.WrodRateData;
import thinku.com.word.callback.ISHCallBack;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.Screenshot;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.FuckView;

/**
 * 测评结果
 */

public class EvaluateResultActivity extends BaseActivity implements View.OnClickListener, ISHCallBack {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.chose_txt)
    TextView choseTxt;
    @BindView(R.id.title_iv)
    ImageView titleIv;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.know_num)
    TextView knowNum;
    @BindView(R.id.know_rl)
    RelativeLayout knowRl;
    @BindView(R.id.know_list)
    RecyclerView knowList;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.unknow_num)
    TextView unknowNum;
    @BindView(R.id.ranking)
    TextView ranking;
    Unbinder unbinder;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.head_image)
    CircleImageView headImage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.recent)
    TextView recent;
    @BindView(R.id.down_title)
    ImageView downTitle;
    @BindView(R.id.result)
    FuckView result;

    private EVWordResultAdapter evWordResultAdapter;
    private List<WrodRateData> wrodRateDataList;

    private ObjectAnimator objectAnimatorShow;
    private ObjectAnimator objectAnimatorHide;

    private MyApplication myApplication;

    public static void start(Context context) {
        Intent intent = new Intent(context, EvaluateResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_result);
        unbinder = ButterKnife.bind(this);
        findView();
        initRecy();
        netData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        myApplication = MyApplication.newInstance();
    }

    public void initRecy() {
        knowList.setLayoutManager(new LinearLayoutManager(this));
        wrodRateDataList = new ArrayList<>();
        evWordResultAdapter = new EVWordResultAdapter(this, wrodRateDataList);
        knowList.setAdapter(evWordResultAdapter);
    }


    public void netData() {
        addToCompositeDis(HttpUtil.evResultObservable()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                }).subscribe(new Consumer<WordResultBeen>() {
                    @Override
                    public void accept(WordResultBeen wordResultBeen) throws Exception {
                        dismissLoadDialog();
                        if (getHttpResSuc(wordResultBeen.getCode())) {
                            referUi(wordResultBeen);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        toTast(throwable.getMessage());
                    }
                }));
    }

    public void referUi(WordResultBeen wordResultBeen) {
        WordResultBeen.ResultBean resultBeen = wordResultBeen.getResult();
        if (resultBeen != null) {
            result.setFuckText("词汇量    ：     "+resultBeen.getNum());
            Log.e(TAG, "referUi: " + SharedPreferencesUtils.getImage(this));
            new GlideUtils().loadCircle(EvaluateResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(this), headImage);
            name.setText(SharedPreferencesUtils.getString("nickname", EvaluateResultActivity.this));
            level.setText(resultBeen.getLevel());
            recent.setText((int) (resultBeen.getBit() * 100) + "%");
            knowNum.setText("（" + resultBeen.getKnow() + "）");
            unknowNum.setText("（" + resultBeen.getNotKnow() + "）");
            wrodRateDataList.clear();

            if (choseAddItem(resultBeen.getFour()))
                wrodRateDataList.add(new WrodRateData("四级", resultBeen.getFour() + "%"));
            if (choseAddItem(resultBeen.getSix()))
                wrodRateDataList.add(new WrodRateData("六级", resultBeen.getSix() + "%"));
            if (choseAddItem(resultBeen.getIelts()))
                wrodRateDataList.add(new WrodRateData("雅思", resultBeen.getIelts() + "%"));
            if (choseAddItem(resultBeen.getToefl()))
                wrodRateDataList.add(new WrodRateData("托福", resultBeen.getToefl() + "%"));
            if (choseAddItem(resultBeen.getGmat()))
                wrodRateDataList.add(new WrodRateData("GMAT", resultBeen.getGmat() + "%"));
            if (choseAddItem(resultBeen.getGre()))
                wrodRateDataList.add(new WrodRateData("GRE", resultBeen.getGre() + "%"));
            evWordResultAdapter.notifyDataSetChanged();
            referShowHideUi(knowList);
        }
    }

    private void findView() {
        titleT.setText("测评结果");
        titleIv.setBackgroundResource(R.mipmap.share);
        objectAnimatorShow = ObjectAnimator.ofFloat(downTitle, "rotation", 0f, 180f);
        objectAnimatorHide = ObjectAnimator.ofFloat(downTitle, "rotation", 180f, 360f);
        objectAnimatorHide.setDuration(300);
        objectAnimatorShow.setDuration(300);
    }

    @OnClick({R.id.back, R.id.title_iv, R.id.know_rl, R.id.ranking})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                myApplication.finishAllActivity();
                break;
            case R.id.title_iv:
                share();
                break;
            case R.id.know_rl:
                showHideClick(knowList);
                break;
            case R.id.ranking:
                EvaluateRankingActivity.start(EvaluateResultActivity.this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showHideClick(ViewGroup recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            referShowHideUi(recyclerView);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            referShowHideUi(recyclerView);
        }

    }

    public void referShowHideUi(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            objectAnimatorShow.start();
        } else {
            objectAnimatorHide.start();
        }
    }

    /**
     * 当rate大于0 时  添加  否则不添加
     */
    public boolean choseAddItem(String value) {
        boolean isAdd = false;
        try {
            if (Integer.parseInt(value) > 0) isAdd = true;
        } catch (Exception e) {

        }
        return isAdd;
    }


    public void share(){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片文件路径
        String filePath = sdCardPath + File.separator + System.currentTimeMillis() + ".png";
        ShareUtils.shareOnlyImage(this ,filePath);
    }
}
