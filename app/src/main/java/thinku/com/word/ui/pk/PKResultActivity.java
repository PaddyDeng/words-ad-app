package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.PkResultBeen;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.MeasureUtils;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

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
    ImageView userVictory ;
    @BindView(R.id.match_victory)
    ImageView matchVictory ;
    @BindView(R.id.match_image_big)
    ImageView matchBig ;
    @BindView(R.id.user_image_big)
    ImageView userBig;
    private String matchUid;
    private String totalId;

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
        setMatchVictory();
        addNet();
    }

    public void addNet() {
        addToCompositeDis(HttpUtil.pkResultBeenObservable(matchUid, totalId)
                .subscribe(new Consumer<PkResultBeen>() {
                    @Override
                    public void accept(@NonNull PkResultBeen pkResultBeen) throws Exception {
                        if (getHttpResSuc(pkResultBeen.getCode())) {
                            referUi(pkResultBeen);
                        }
                    }
                }));
    }

    public void referUi(PkResultBeen pkResultBeen) {
        if (pkResultBeen.getType() == 1) {  //  胜利
            pkSuccess();
            setUserVictory();
        } else {
            pkFailure();
            setMatchVictory();
        }

        PkResultBeen.DataBean userBeen = pkResultBeen.getData().get(0);
        userCurrentNum.setText("正确：" + userBeen.getSuccess());
        userErrorNum.setText("错误：" + userBeen.getError());
        PkResultBeen.DataBean matchBeen = pkResultBeen.getData().get(1);
        matchCurrentNum.setText("正确：" + matchBeen.getSuccess());
        matchErrorNum.setText("错误：" + matchBeen.getError());

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
        setUserVictory();
    }

    @OnClick({ R.id.back , R.id.share , R.id.user_image ,R.id.match_image})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.share:
                share();
                break;
            case R.id.back:
                this.finishWithAnim();
                break;
            case R.id.user_image :
                Log.e(TAG, "click: user " );
//                setUserVictory();
                break;
            case R.id.match_image:
//                setMatchVictory();
                break;
        }
    }

    public void setUserVictory(){
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(PKResultActivity.this), userBig);
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getPKMatchImage(PKResultActivity.this), matchImage);
        userVictory.setVisibility(View.VISIBLE);
        userBig.setVisibility(View.VISIBLE);
        matchImage.setVisibility(View.VISIBLE);
        userImage.setVisibility(View.GONE);
        matchBig.setVisibility(View.GONE);
        matchVictory.setVisibility(View.GONE);
    }


    public void setMatchVictory(){
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(PKResultActivity.this), matchBig);
        new GlideUtils().loadCircle(PKResultActivity.this, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getPKMatchImage(PKResultActivity.this), userImage);
        userVictory.setVisibility(View.GONE);
        userBig.setVisibility(View.GONE);
        matchImage.setVisibility(View.GONE);
        matchVictory.setVisibility(View.VISIBLE);
        matchBig.setVisibility(View.VISIBLE);
        userImage.setVisibility(View.VISIBLE);
    }

    public void share(){
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片文件路径
        String filePath = sdCardPath + File.separator + System.currentTimeMillis() + ".png";
        ShareUtils.shareOnlyImage(this ,filePath);
    }
}
