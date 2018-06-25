package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.PkResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.pk.adapter.ResultAdapter;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.view.FullLinearLayoutManager;

public class PKResultActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.match_image)
    CircleImageView matchImage;
    @BindView(R.id.pk_vs)
    ImageView pkVs;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_current_num)
    TextView userCurrentNum;
    @BindView(R.id.user_error_num)
    TextView userErrorNum;
    @BindView(R.id.match_name)
    TextView matchName;
    @BindView(R.id.match_current_num)
    TextView matchCurrentNum;
    @BindView(R.id.match_error_num)
    TextView matchErrorNum;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.pk_result_bg)
    ImageView pkResultBg;
    @BindView(R.id.image_rl)
    RelativeLayout imageRl;
    @BindView(R.id.user_victory)
    ImageView userVictory;
    @BindView(R.id.match_victory)
    ImageView matchVictory;
    @BindView(R.id.match_image_big)
    ImageView matchBig;
    @BindView(R.id.user_image_big)
    ImageView userBig;
    @BindView(R.id.word_list)
    RecyclerView wordList;
    @BindView(R.id.again_pk)
    TextView againPk;
    @BindView(R.id.scroll)
    NestedScrollView scrollView ;
    private String matchUid;
    private String totalId;
    private MediaPlayer pk_success;
    private MediaPlayer pk_failure;
    private boolean pkResult ;
    private ResultAdapter resultAdapter;
    private List<PkResultBeen.QuestionInfoBean> questionInfoBeanList;

    public static void start(Context context, String matchUid, String totalId) {
        Intent intent = new Intent(context, PKResultActivity.class);
        intent.putExtra("matchUid", matchUid);
        intent.putExtra("totalId", totalId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkresult);
        ButterKnife.bind(this);
        try {
            matchUid = getIntent().getStringExtra("matchUid");
            totalId = getIntent().getStringExtra("totalId");
        } catch (Exception e) {

        }
        init();
        addNet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pkResult = SharedPreferencesUtils.getPkResultMusic(this);
    }

    public void addNet() {
        addToCompositeDis(HttpUtil.pkResultBeenObservable(matchUid, totalId)
                .subscribe(new Consumer<PkResultBeen>() {
                    @Override
                    public void accept(@NonNull PkResultBeen pkResultBeen) throws Exception {
                        if (getHttpResSuc(pkResultBeen.getCode())) {
                            if (MyApplication.mediaPlayer != null) {
                                if (MyApplication.mediaPlayer.isPlaying())
                                    MyApplication.mediaPlayer.stop();
                            }
                            referUi(pkResultBeen);

                        }
                    }
                }));
    }

    public void initPkSuccessMedia() {
        if (pkResult) {
        if (pk_success == null) {
            pk_success = MediaPlayer.create(this, R.raw.pk_success);

        }
            pk_success.start();
        }
    }

    public void initPkFailureMedia() {
        if (pkResult) {
        if (pk_failure == null) {
            pk_failure = MediaPlayer.create(this, R.raw.pk_faliure);
        }

            pk_failure.start();
        }
    }

    public void referUi(PkResultBeen pkResultBeen) {
        if (pkResultBeen.getType() == 1) {  //  胜利
            pkSuccess();
            setUserVictory();
            initPkSuccessMedia();
        } else {
            pkFailure();
            setMatchVictory();
            initPkFailureMedia();
        }

        PkResultBeen.DataBean userBeen = pkResultBeen.getData().get(0);
        userCurrentNum.setText("正确：" + userBeen.getSuccess());
        userErrorNum.setText("错误：" + userBeen.getError());
        PkResultBeen.DataBean matchBeen = pkResultBeen.getData().get(1);
        matchCurrentNum.setText("正确：" + matchBeen.getSuccess());
        matchErrorNum.setText("错误：" + matchBeen.getError());
        if (pkResultBeen.getQuestionInfo() != null && pkResultBeen.getQuestionInfo().size() > 0) {
            questionInfoBeanList.addAll(pkResultBeen.getQuestionInfo());
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * pk 胜利
     */
    public void pkSuccess() {
        pkResultBg.setImageResource(R.mipmap.pk_success);
    }

    /**
     * pk失败
     */
    public void pkFailure() {
        pkResultBg.setImageResource(R.mipmap.pk_failure);
    }

    public void init() {
        userCurrentNum.getBackground().setAlpha(50);
        userErrorNum.getBackground().setAlpha(50);
        matchCurrentNum.getBackground().setAlpha(50);
        matchErrorNum.getBackground().setAlpha(50);
        userName.setText(SharedPreferencesUtils.getNickName(PKResultActivity.this));
        matchName.setText(SharedPreferencesUtils.getPKMatchName(PKResultActivity.this));
        questionInfoBeanList = new ArrayList<>();
        resultAdapter = new ResultAdapter(this, questionInfoBeanList);
        wordList.setLayoutManager(new LinearLayoutManager(this));
        wordList.setAdapter(resultAdapter);
//        setUserVictory();
    }

    @OnClick({R.id.back, R.id.share, R.id.user_image, R.id.match_image,R.id.again_pk})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.share:
                share();
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
            case R.id.user_image:
                Log.e(TAG, "click: user ");
//                setUserVictory();
                break;
            case R.id.match_image:
//                setMatchVictory();
                break;
            case R.id.again_pk:
                this.finishWithAnim();
                PKHomeActivity.start(this);
                break;
        }
    }

    public void setUserVictory() {
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(PKResultActivity.this), userBig);
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getPKMatchImage(PKResultActivity.this), matchImage);
        userVictory.setVisibility(View.VISIBLE);
        userBig.setVisibility(View.VISIBLE);
        matchImage.setVisibility(View.VISIBLE);
        userImage.setVisibility(View.GONE);
        matchBig.setVisibility(View.GONE);
        matchVictory.setVisibility(View.GONE);
    }


    public void setMatchVictory() {
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(PKResultActivity.this), userImage);
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getPKMatchImage(PKResultActivity.this), matchBig);
        userVictory.setVisibility(View.GONE);
        userBig.setVisibility(View.GONE);
        matchImage.setVisibility(View.GONE);
        matchVictory.setVisibility(View.VISIBLE);
        matchBig.setVisibility(View.VISIBLE);
        userImage.setVisibility(View.VISIBLE);
    }

    public void share() {
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片文件路径
        String filePath = sdCardPath + File.separator + System.currentTimeMillis() + ".png";
        ShareUtils.ShareOnlyScrollViewImage(this,scrollView, filePath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pk_failure != null) {
            if (pk_failure.isPlaying()) pk_failure.stop();
            pk_failure.release();
        }

        if (pk_success != null) {
            if (pk_success.isPlaying()) pk_success.stop();
            pk_success.release();
        }
    }
}
