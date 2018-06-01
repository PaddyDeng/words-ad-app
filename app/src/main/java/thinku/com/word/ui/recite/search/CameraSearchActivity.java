package thinku.com.word.ui.recite.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import thinku.com.word.R;
import thinku.com.word.base.BaseNoImmActivity;
import thinku.com.word.thrlib.RecognizeService;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.ui.seacher.PicSearchActivity;
import thinku.com.word.utils.FileUtil;
import thinku.com.word.utils.Utils;

public class CameraSearchActivity extends BaseNoImmActivity {


    @BindView(R.id.camera_crop_iv)
    ImageView cameraIv;
    @BindView(R.id.camera_result_tv)
    TextView recognResultTv;
    @BindView(R.id.recognize_camera_pb)
    ProgressBar mProgressBar;
    @BindView(R.id.question_search_input)
    EditText questionEt;
    private String content;
    private Unbinder unbinder ;
    public static void startAct(Context c, String content) {
        Intent intent = new Intent(c, CameraSearchActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_layout);
        unbinder = ButterKnife.bind(this);
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        initView();
    }

    @Override
    public BaseNoImmActivity.AnimType getAnimType() {
        return BaseNoImmActivity.AnimType.ANIM_TYPE_UP_IN;
    }

    protected void initView() {
        cameraIv.setImageBitmap(BitmapFactory.decodeFile(FileUtil.getSaveFile(mContext).getAbsolutePath()));
        initRecognizeResult();
        questionEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Utils.controlTvFocus(cameraIv);
                    PicSearchActivity.startAct(mContext, content.trim());
                }
            }
        });
        Utils.controlTvFocus(cameraIv);
    }

    private void initRecognizeResult() {

        RecognizeService.recGeneral(FileUtil.getSaveFile(mContext).getAbsolutePath(),
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Utils.setGone(mProgressBar);
                        toTast(CameraSearchActivity.this ,result);
                        content = result;
                        recognResultTv.setText(content);
                        questionEt.setText(content);
                        if (!TextUtils.isEmpty(content) && !TextUtils.equals("[283504] Network error", content)) {
                            PicSearchActivity.startAct(mContext, content.trim());
                        }
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.question_search_cancel_btn, R.id.go_on_camera, R.id.camera_crop_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_crop_iv:
                Utils.setVisible(mProgressBar);
                initRecognizeResult();
                break;
            case R.id.go_on_camera:
                finish();
                break;
            // 点击“取消”按钮，及半透明背景，均退出页面
            case R.id.question_search_cancel_btn:
                MainActivity.toMain(this);
                break;
        }
    }

    @Override
    protected boolean preBackExitPage() {
        MainActivity.toMain(this);
        return super.preBackExitPage();
    }
}
