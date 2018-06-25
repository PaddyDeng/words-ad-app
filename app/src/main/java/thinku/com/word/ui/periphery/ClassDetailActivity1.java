package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.ui.pk.OnlineActivity;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HtmlUtil;

public class ClassDetailActivity1 extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.course_img)
    ImageView courseImg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.people_img)
    ImageView peopleImg;
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.aa)
    AutoRelativeLayout aa;
    @BindView(R.id.content)
    WebView content;
    @BindView(R.id.online)
    TextView online;

    private Unbinder unbinder;
    private RoundBean.RecentClassBean recentClassBean;
    private RoundBean.LivePreviewBean.DataBean dataBean;
    public static void start(Context context, RoundBean.RecentClassBean recentClassBean) {
        Intent intent = new Intent(context, ClassDetailActivity1.class);
        intent.putExtra("data", recentClassBean);
        context.startActivity(intent);
    }

    public static void start(Context context, RoundBean.LivePreviewBean.DataBean dataBean) {
        Intent intent = new Intent(context, ClassDetailActivity1.class);
        intent.putExtra("data", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail1);
        unbinder = ButterKnife.bind(this);
        try {
            recentClassBean = getIntent().getParcelableExtra("data");
            init(recentClassBean);
        } catch (Exception e) {

        }

        try {
            dataBean = getIntent().getParcelableExtra("data");
            init(dataBean);
        } catch (Exception e) {

        }
    }

    public void init(RoundBean.RecentClassBean recentClassBean) {
        titleT.setText("课程详情");
        GlideUtils.load(this, NetworkTitle.OPENRESOURE + recentClassBean.getImage(), courseImg);
        name.setText(recentClassBean.getName());
        people.setText(recentClassBean.getViewCount() + "人已加入");
        String s = HtmlUtil.repairContent(recentClassBean.getSentenceNumber(), NetworkTitle.OPEN);
        String html = HtmlUtil.getHtml(s, 0);
        Log.e(TAG, "init: " + html );
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
    }

    public void init(RoundBean.LivePreviewBean.DataBean dataBean) {
        titleT.setText("课程详情");
        GlideUtils.load(this, NetworkTitle.OPENRESOURE + dataBean.getImage(), courseImg);
        name.setText(dataBean.getName());
        people.setText(dataBean.getViewCount() + "人已加入");
        String s = HtmlUtil.repairContent(dataBean.getSentenceNumber(), NetworkTitle.OPEN);
        String html = HtmlUtil.getHtml(s, 0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
    }


    @OnClick({R.id.back, R.id.online})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finishWithAnim();
                break;
            case R.id.online:
                OnlineActivity.start(this);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
