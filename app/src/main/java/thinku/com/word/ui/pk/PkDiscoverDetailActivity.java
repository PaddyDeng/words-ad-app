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
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import thinku.com.word.R;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.utils.GlideUtils;

public class PkDiscoverDetailActivity extends AppCompatActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.content)
    TextView content;

    private Unbinder unbinder;
    private PkWordData.DataBean dataBean;
    private Html.ImageGetter imgGetter;
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
        init();
        struct();
        initData();
    }


    public void init() {
        imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Log.i("RG", "source---?>>>" + source);
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    Log.i("RG", "url---?>>>" + url);
                    drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                Log.i("RG", "url---?>>>" + url);
                return drawable;
            }
        };
    }

    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }
    /**
     * 初始化数据
     */
    public void initData() {
        content.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        content.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        content.setText(Html.fromHtml(dataBean.getContent(), imgGetter, null));
        titleT.setText(dataBean.getTitle());
        new GlideUtils().load(PkDiscoverDetailActivity.this , NetworkTitle.WORDRESOURE + dataBean.getImage() ,image);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
