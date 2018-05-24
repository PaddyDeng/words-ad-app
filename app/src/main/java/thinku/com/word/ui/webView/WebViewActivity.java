package thinku.com.word.ui.webView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.deal_progress_bar)
    ProgressBar dealProgressBar;

    private String url ;
    public static void start(Context c , String url){
        Intent intent = new Intent(c ,WebViewActivity.class);
        intent.putExtra("url" ,url);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null){
            url =  intent.getStringExtra("url");
        }
        initWeb();
    }

    public void initWeb() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle: " + title );
                  titleT.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (dealProgressBar == null) return;
                if (newProgress == 100) {
                    dealProgressBar.setVisibility(View.GONE);
                } else {
                    dealProgressBar.setVisibility(View.VISIBLE);
                    dealProgressBar.setProgress(newProgress);
                }
            }
        });
        webView.loadUrl(url);
        Log.e(TAG, "initWeb: " + url );
    }



    protected boolean preBackExitPage() {
        if (null != webView && webView.canGoBack()) {
            webView.goBack();
            return true ;
        }
        return super.preBackExitPage();
    }

    @OnClick(R.id.back)
    public void back(){
        this.finishWithAnim();
    }

    @Override
    protected void onDestroy() {
        if (null != webView) {
            if (null != webView.getParent()) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            webView.stopLoading();
            webView.destroy();
        }
        super.onDestroy();
    }
}
