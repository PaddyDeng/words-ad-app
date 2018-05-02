package thinku.com.word.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.font.ControlTextView;
import thinku.com.word.font.FontControlContainer;
import thinku.com.word.utils.SharedPreferencesUtils;

public class FontSizeSettingActivity extends BaseActivity implements FontControlContainer.FontSizeChangeListener {

    @BindView(R.id.change_font_size_container)
    FontControlContainer changeFontSize;
    @BindView(R.id.simple_word)
    ControlTextView mControlTextView;
    @BindView(R.id.title_t)
    TextView titleT;

    public static void start(Context context) {
        Intent intent = new Intent(context, FontSizeSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected void initView() {
        titleT.setText("字体大小");
        int index = SharedPreferencesUtils.getFontSize(mContext);
        changeFontSize.setThumbLeftMargin(index);
        mControlTextView.setFontSize(index);
    }

    protected void initData() {
        changeFontSize.setFontSizeChangeListener(this);
    }

    @Override
    public void fontSize(int index) {
        SharedPreferencesUtils.saveFontSize(mContext, index);
        mControlTextView.setFontSize(index);
//        RxBus.get().post(C.FONT_SIEZ_CHANGE, true);
    }

    @OnClick(R.id.back)
    public void back(){
        FontSizeSettingActivity.this.finishWithAnim();
    }
}
