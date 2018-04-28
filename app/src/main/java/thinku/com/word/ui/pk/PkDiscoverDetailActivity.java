package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HtmlUtil;

public class PkDiscoverDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.content)
    WebView content;

    private Unbinder unbinder;
    private PkWordData.DataBean dataBean;
    public static void start(Context context, PkWordData.DataBean dataBean) {
        Intent intent = new Intent(context, PkDiscoverDetailActivity.class);
        intent.putExtra("data", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_discover_detail);
        unbinder = ButterKnife.bind(this);
        try {
            dataBean = getIntent().getParcelableExtra("data");
        } catch (Exception e) {

        }
        initData();
    }



    /**
     * 初始化数据
     */
    public void initData() {
        titleT.setText(dataBean.getTitle());
        new GlideUtils().loadCircle(PkDiscoverDetailActivity.this , NetworkTitle.WORDRESOURE + dataBean.getImage() ,image);
        String s = HtmlUtil.repairContent(dataBean.getContent(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s,0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
    }

    @OnClick({R.id.back,R.id.online })
    public void click(View view ){
        switch (view.getId()){
            case R.id.back:
                finishWithAnim();
                break;
            case R.id.online:
                OnlineActivity.start(PkDiscoverDetailActivity.this);
                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
