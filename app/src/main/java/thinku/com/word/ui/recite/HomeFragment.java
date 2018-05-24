package thinku.com.word.ui.recite;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.ReviewDialogBeen;
import thinku.com.word.bean.UserIndex;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.dialog.callback.DialogClickListener;
import thinku.com.word.ui.personalCenter.SignActivity;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;
import thinku.com.word.ui.personalCenter.dialog.load.WaitDialog;
import thinku.com.word.ui.report.*;
import thinku.com.word.utils.C;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.WaitUtils;
import thinku.com.word.view.AutoZoomTextView;
import thinku.com.word.view.CompleteDialog;
import thinku.com.word.view.LoadingCustomView;
import thinku.com.word.view.ReviewDialog;

import static thinku.com.word.view.LoadingCustomView.HOLLOW;

/**
 * 正常进入
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.days)
    TextView days;
    @BindView(R.id.surplus_day)
    AutoZoomTextView surplusDay;
    @BindView(R.id.day_ll)
    LinearLayout dayLl;
    @BindView(R.id.sign)
    LinearLayout sign;
    @BindView(R.id.change_plan)
    TextView changePlan;
    @BindView(R.id.need_num)
    TextView needNum;
    @BindView(R.id.already_num)
    TextView alreadyNum;
    @BindView(R.id.top_rl)
    RelativeLayout topRl;
    @BindView(R.id.modify_word_package)
    ImageView modifyWordPackage;
    @BindView(R.id.progress)
    LoadingCustomView progress;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.all_num)
    TextView allNum;
    @BindView(R.id.middle)
    RelativeLayout middle;
    @BindView(R.id.today_num)
    TextView todayNum;
    @BindView(R.id.review_t)
    TextView reviewT;
    @BindView(R.id.review_num)
    TextView reviewNum;
    @BindView(R.id.start_recite)
    TextView startRecite;
    @BindView(R.id.start_review)
    TextView startReview;
    Unbinder unbinder;
    @BindView(R.id.name)
    TextView name;

    private String name_text;
    private SimpleDateFormat simpleDateFormat;

    private Observable<Boolean> observable ;
    private MediaPlayer recitePlayer ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initData();
        observable = RxBus.get().register(C.RXBUS_LOGIN_BACKMAIN ,Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                initData();
            }
        });
        recitePlayer = MediaPlayer.create(_mActivity ,R.raw.start_recite);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void init() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        surplusDay.setIsAutoZoom(true);
        progress.setProgressBankgroundColor(_mActivity.getResources().getColor(R.color.color_progress_side));
        progress.setProgressBarBankgroundStyle(HOLLOW);
        progress.setProgressColor(_mActivity.getResources().getColor(R.color.progress_clolor1));
        progress.setProgressBarFrameHeight(3);
    }


    public void initData() {
        addToCompositeDis(HttpUtil.reciteIndex()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        WaitUtils.show(_mActivity,"word");
                    }
                }).subscribe(new Consumer<UserIndex>() {
                    @Override
                    public void accept(@NonNull UserIndex userIndex) throws Exception {
                        if(WaitUtils.isRunning("word")){
                            WaitUtils.dismiss("word");
                        }
                        days.setText("已坚持" + userIndex.getInsistDay() + "天");
                        surplusDay.setText(userIndex.getSurplusDay());
                        needNum.setText(userIndex.getUserPackage().getPlanWords());
                        alreadyNum.setText(userIndex.getUserAllWords());
                        num.setText(userIndex.getUserPackageWords());
                        allNum.setText("/" + userIndex.getAllWords());
                        name.setText(userIndex.getPackageName());
                        name_text = userIndex.getPackageName();
                        progress.setProgress((Float.parseFloat(userIndex.getUserPackageWords()) * 100) / Float.parseFloat(userIndex.getAllWords()));
                        todayNum.setText(userIndex.getToDayWords());
                        reviewNum.setText(userIndex.getUserReviewWords() + "/" + userIndex.getUserNeedReviewWords());
                        MyApplication.task = Integer.parseInt(userIndex.getTask());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if(WaitUtils.isRunning("word")){
                            WaitUtils.dismiss("word");
                        }
                    }
                })
        );
    }


    @OnClick({R.id.change_plan, R.id.modify_word_package, R.id.start_recite, R.id.start_review, R.id.sign})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign:  // 打卡
                SignActivity.start(_mActivity);
                break;
            case R.id.change_plan:
                MyPlanActivity.start(_mActivity);
                break;
            case R.id.modify_word_package:
                WordPackageActivity.start(_mActivity);
                break;
            case R.id.start_recite:  // 背单词
                reciteWord();
                break;
            case R.id.start_review:  // 复习
                ReviewActivity.start(_mActivity);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister(C.RXBUS_LOGIN_BACKMAIN ,observable);
        if (recitePlayer != null){
            if (recitePlayer.isPlaying()) recitePlayer.stop();
            recitePlayer.release();
        }
    }


    /**
     *  背单词
     *  依据task 进行判断 if  task  =1   弹窗  是否继续  先调用接口
     *  task  =2  正常进入
     *  task  = 3  老艾宾浩斯调用   review -casewords  code == 2  老艾宾浩斯  弹分享
     *  task  = 4 提示  词包已经背完
     */
    public void reciteWord() {
        if (recitePlayer != null && !recitePlayer.isPlaying()) recitePlayer.start();
        if (MyApplication.task == 1){
            showCompeleteDialog();
        }else if (MyApplication.task ==2 ){
            //  背单词进入
            thinku.com.word.ui.report.WordEvaluateFragment.start(_mActivity,C.NORMAL);
        }else if (MyApplication.task ==3 ){
              // 背单词进入
            thinku.com.word.ui.report.WordEvaluateFragment.start(_mActivity,C.NORMAL);
        }else {
            toTast(_mActivity,"该词包已背完，请选择另一个词包");
        }
    }


    public void showCompeleteDialog(){
        CompleteDialog completeDialog = new CompleteDialog(_mActivity);
        completeDialog.setDialogClickListener(new DialogClickListener() {
            @Override
            public void clickTrue() {
                // 背单词进入
                addToCompositeDis(HttpUtil.isReciteWordsObservable()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        thinku.com.word.ui.report.WordEvaluateFragment.start(_mActivity,C.NORMAL);
                    }
                }));
            }

            @Override
            public void clickFalse() {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) initData();
    }
}
