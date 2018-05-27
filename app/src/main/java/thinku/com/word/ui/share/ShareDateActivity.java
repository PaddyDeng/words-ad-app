package thinku.com.word.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.bean.UserIndex;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.ShareUtils;
import thinku.com.word.utils.StringUtils;
import thinku.com.word.view.SignDate;

public class ShareDateActivity extends BaseActivity  implements PlatformActionListener{

    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.calendar)
    SignDate calendar;
    @BindView(R.id.day_num)
    TextView dayNum;
    @BindView(R.id.word_num)
    TextView wordNum;

    private List<Integer> list ;

    public static void start(Context c ){
        Intent intent = new Intent(c,ShareDateActivity.class);
        c.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_date);
        ButterKnife.bind(this);

        initData();
        addIndex();
    }


    public AnimType getAnimType() {
        return AnimType.ANIM_TYPE_UP_IN;
    }


    public void initData(){
        addToCompositeDis(HttpUtil.userSingObservable()
                .subscribe(new Consumer<SingBeen>() {
                    @Override
                    public void accept(SingBeen singBeen) throws Exception {
                        if (singBeen != null){
                            referUi(singBeen);
                        }
                    }
                }));
    }

    /**
     *   刷新UI
     * @param singBeen
     */
    public void referUi(SingBeen singBeen){
        List<SingBeen.SignData> signDates = singBeen.getData();
        list = new ArrayList<>();
        if (signDates!= null){
            for (SingBeen.SignData data : signDates) {
                list.add(Integer.parseInt(StringUtils.spiltDay(data.getCreateDay())));
            }
            calendar.setSign(list);
        }
    }

    public void addIndex (){
        addToCompositeDis(HttpUtil.reciteIndex()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
        .subscribe(new Consumer<UserIndex>() {
            @Override
            public void accept(@NonNull UserIndex userIndex) throws Exception {
                if (userIndex != null) {
                    dayNum.setText(userIndex.getInsistDay());
                    wordNum.setText(userIndex.getToDayWords());
                    String content = getResources().getString(R.string.share_content);
                    ShareUtils.shareContent(ShareDateActivity.this , String.format(content ,userIndex.getInsistDay() ,userIndex.getToDayWords() ,userIndex.getUserAllWords() ) ,ShareDateActivity.this);
                }
            }
        }));
    }


    @OnClick(R.id.cancel)
    public void cancel(){
        MainActivity.toMain(this);
        this.finishWithAnim();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.e(TAG, "onComplete: " );
        this.finishWithAnim();
        MainActivity.toMain(this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e(TAG, "onError: " );
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e(TAG, "onCancel: " );
    }
}
