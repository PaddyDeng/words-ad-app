package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HtmlUtil;

/**
 * 学员评价详情
 */
public class EvaAllActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.webView)
    WebView webView;

    Unbinder unbinder;

    public static void start(Context context, RoundBean.CaseBean caseBean) {
        Intent intent = new Intent(context, EvaAllActivity.class);
        intent.putExtra("data", caseBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eva_all);
        unbinder = ButterKnife.bind(this);
        try {
           RoundBean.CaseBean caseBean = getIntent().getParcelableExtra("data");
            initData(caseBean);
        } catch (Exception e) {
            toTast(this, e.getMessage());
        }

    }


    public void initData(RoundBean.CaseBean caseBean) {
        titleT.setText("学员案列");
        name.setText(caseBean.getName());
        new GlideUtils().loadRoundCircle(this ,caseBean.getImage() ,image);
        time.setText(DateUtil.getCustonFormatTime(caseBean.getCreateTime() ,"yyyy-MM-dd"));
        String s = HtmlUtil.repairContent(caseBean.getDetails(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
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
}
