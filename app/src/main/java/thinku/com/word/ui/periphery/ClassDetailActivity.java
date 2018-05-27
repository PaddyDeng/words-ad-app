package thinku.com.word.ui.periphery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import thinku.com.word.utils.DateUtil;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HtmlUtil;
import thinku.com.word.utils.LoginHelper;

public class ClassDetailActivity extends BaseActivity {

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
    @BindView(R.id.listen)
    AutoLinearLayout listen;
    @BindView(R.id.aa)
    AutoRelativeLayout aa;
    @BindView(R.id.content)
    WebView content;
    @BindView(R.id.online)
    TextView online;

    private Unbinder unbinder;
    private RoundBean.RecentClassBean recentClassBean;
    private RoundBean.ChoicenessBean choicenessBean;
    private RoundBean.LivePreviewBean.DataBean dataBean;
    private String url;
    private boolean hasAsyncFree = false;
    private String contentTxt;
    private CourseBean courseBean ;
    public static void start(Context context, RoundBean.RecentClassBean recentClassBean) {
        Intent intent = new Intent(context, ClassDetailActivity.class);
        intent.putExtra("data", recentClassBean);
        context.startActivity(intent);
    }

    public static void start(Context context , CourseBean courseBean){
        Intent intent = new Intent(context, ClassDetailActivity.class);
        intent.putExtra("data", courseBean);
        context.startActivity(intent);
    }

    public static void start(Context context, RoundBean.ChoicenessBean choicenessBean) {
        Intent intent = new Intent(context, ClassDetailActivity.class);
        intent.putExtra("data", choicenessBean);
        context.startActivity(intent);
    }

    public static void start(Context context, RoundBean.LivePreviewBean.DataBean dataBean) {
        Intent intent = new Intent(context, ClassDetailActivity.class);
        intent.putExtra("data", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        unbinder = ButterKnife.bind(this);
        try {
            recentClassBean = getIntent().getParcelableExtra("data");
            init(recentClassBean);
            hasAsyncFree = true;
        } catch (Exception e) {

        }

        try {
            choicenessBean = getIntent().getParcelableExtra("data");
            init(choicenessBean);
            hasAsyncFree = true;
        } catch (Exception e) {

        }

        try {
            dataBean = getIntent().getParcelableExtra("data");
            init(dataBean);
            hasAsyncFree = true;
        } catch (Exception e) {

        }

        try{
            courseBean = (CourseBean) getIntent().getSerializableExtra("data");
            init(courseBean);
            hasAsyncFree = true;
        }catch (Exception e){

        }
    }

    public void init(RoundBean.RecentClassBean recentClassBean) {
        titleT.setText("课程详情");
        GlideUtils.load(this, NetworkTitle.OPENRESOURE + recentClassBean.getImage(), courseImg);
        name.setText(recentClassBean.getName());
        people.setText(recentClassBean.getViewCount() + "人已加入");
        String s = HtmlUtil.repairContent(recentClassBean.getSentenceNumber(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        listen.setVisibility(View.GONE);
    }

    public void init(RoundBean.ChoicenessBean choicenessBean) {
        titleT.setText("课程详情");
        GlideUtils.load(this, choicenessBean.getImage(), courseImg);
        name.setText(choicenessBean.getName());
        people.setText(DateUtil.differentDays(Integer.parseInt(choicenessBean.getId())) + "人已加入");
        String s = HtmlUtil.repairContent(choicenessBean.getContent(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        listen.setVisibility(View.VISIBLE);
        url = choicenessBean.getUrl();
        contentTxt = choicenessBean.getContent();
    }

    public void init(CourseBean courseBean){
        titleT.setText("课程详情");
        GlideUtils.load(this, courseBean.getImage(), courseImg);
        name.setText(courseBean.getName());
        people.setText(DateUtil.differentDays(Integer.parseInt(courseBean.getId())) + "人已加入");
        String s = HtmlUtil.repairContent(courseBean.getContent(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        listen.setVisibility(View.VISIBLE);
        url = courseBean.getUrl();
        contentTxt = courseBean.getContent();
    }

    public void init(RoundBean.LivePreviewBean.DataBean dataBean) {
        titleT.setText("课程详情");
        GlideUtils.load(this, NetworkTitle.OPENRESOURE + dataBean.getImage(), courseImg);
        name.setText(dataBean.getName());
        people.setText(dataBean.getViewCount() + "人已加入");
        String s = HtmlUtil.repairContent(dataBean.getSentenceNumber(), NetworkTitle.DomainSmartApplyResourceNormal);
        String html = HtmlUtil.getHtml(s, 0);
        content.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        listen.setVisibility(View.GONE);
    }


    @OnClick({R.id.back, R.id.online, R.id.listen})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finishWithAnim();
                break;
            case R.id.online:
                OnlineActivity.start(this);
                break;
            case R.id.listen:
                PlayActivity.start(this, contentTxt, name.getText().toString().trim(), url);
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
