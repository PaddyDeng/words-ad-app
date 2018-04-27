package thinku.com.word.ui.pk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 在线咨询
 */

public class OnlineActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.web)
    WebView web;
    private boolean isClose = false;

    public static void start(Context context){
        Intent intent = new Intent(context ,OnlineActivity.class);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        ButterKnife.bind(this);
        titleT.setText("在线咨询");
        initWeb();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClose) {
                    finish();
                } else {
                    web.loadUrl("");
                    //处理断开链接
                    isClose = true;
                }
            }
        });

    }

    public void initWeb(){
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        String url = "http://p.qiao.baidu.com/im/index?siteid=7905926&ucid=18329536&cp=&cr=&cw=";
        Map<String, String> header = new HashMap<>();
        header.put("Referer", "http://www.gmatonline.cn/");
        web.loadUrl(url, header);
    }
}
