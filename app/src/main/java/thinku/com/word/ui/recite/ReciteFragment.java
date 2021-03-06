package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserData;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.ocr.camera.CameraActivity;
import thinku.com.word.ui.personalCenter.PersonalCenterActivity;
import thinku.com.word.ui.seacher.SearchQuestionActivity;
import thinku.com.word.ui.seacher.TopicSearchActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.FileUtil;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.Utils;

import static thinku.com.word.R.id.userInfo;

/**
 * 背单词
 */

public class ReciteFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_GENERAL = 105;
    private static final String TAG = ReciteFragment.class.getSimpleName();
    private CircleImageView portrait;
    private LinearLayout input_lookup;
    private ImageView speech_lookup, photo_lookup;
    private SparseArray<Fragment> fragments;
    private int oldPage = -1;
    private Observable<String> observable;
    private Observable<Boolean> booleanObservable;
    private Observable<Boolean> exitLoginObservable;

    public static ReciteFragment newInstance() {
        ReciteFragment reciteFragment = new ReciteFragment();
        return reciteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
        initView();
        new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(_mActivity), portrait);
        observable = RxBus.get().register(C.RXBUS_HEAD_IMAGE, String.class);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + s, portrait);
            }
        });

        booleanObservable = RxBus.get().register(C.RXBUS_LOGIN, Boolean.class);
        booleanObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(_mActivity), portrait);
//                initView();
            }
        });
        exitLoginObservable = RxBus.get().register(C.RXBUS_EXLOING, Boolean.class);
        exitLoginObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
//                initView();
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_HEAD_IMAGE, observable);
        RxBus.get().unregister(C.RXBUS_LOGIN, booleanObservable);
        RxBus.get().unregister(C.RXBUS_EXLOING, exitLoginObservable);
    }

    private void initView() {
        addToCompositeDis(HttpUtil.getUserData()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<ResultBeen<UserData>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<UserData> been) throws Exception {

                        if (getHttpResSuc(been.getCode())) {
                            UserData userData = been.getData();
                            if (userData != null && !TextUtils.isEmpty(userData.getUid())) {
                                SharedPreferencesUtils.setPlanWords(_mActivity, userData.getPlanWords());
                                MyApplication.signTime = been.getData().getLastSign();
                                new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE + userData.getImage(), portrait);
                                SharedPreferencesUtils.setImage(_mActivity, userData.getImage());
//                                SharedPreferencesUtils.setStudyMode(_mActivity, userData.getStudyModel());
                                updateStudyMode(userData.getStudyModel());
                                if (TextUtils.isEmpty(userData.getPlanWords())) {
                                    setFragment(0);
                                } else {
                                    setFragment(1);
                                }
                            }else{
                                setFragment(0);
                            }
                        } else if (been.getCode() == 98){
                            setFragment(0);
                            new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE , portrait);
                        } else {
                            new GlideUtils().loadCircle(_mActivity, NetworkTitle.WORDRESOURE , portrait);
                            setFragment(0);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        toTast(_mActivity, HttpUtils.onError(throwable));
                    }
                }));
    }


    public void updateStudyMode(String mode ){
        if (!SharedPreferencesUtils.getStudyMode(_mActivity).equals(mode)) {
            updataMode(SharedPreferencesUtils.getStudyMode(_mActivity), _mActivity);
        }
    }


    public  void updataMode(final String status , final Context context){
        Log.e(TAG, "updataMode: " + status);
        addToCompositeDis(HttpUtil.choseStudyMode(status)
                .subscribe(new Consumer<BackCode>() {
                    @Override
                    public void accept(@NonNull BackCode backCode) throws Exception {
                        if (backCode.getCode() == 1){
                            Log.e("choseMode", "accept: " + status );
                            SharedPreferencesUtils.setStudyMode(context , status);
                        }
                    }
                }));
    }
    private void setClick() {
        portrait.setOnClickListener(this);
        input_lookup.setOnClickListener(this);
        speech_lookup.setOnClickListener(this);
        photo_lookup.setOnClickListener(this);
    }

    private void findView(View view) {
        portrait = (CircleImageView) view.findViewById(R.id.portrait);
        input_lookup = (LinearLayout) view.findViewById(R.id.input_lookup);
        speech_lookup = (ImageView) view.findViewById(R.id.speech_lookup);
        photo_lookup = (ImageView) view.findViewById(R.id.take_photo);
        fragments = new SparseArray<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.portrait:
                PersonalCenterActivity.start(_mActivity);
                break;
            case R.id.input_lookup:
                startActivity(new Intent(getActivity(), SearchQuestionActivity.class));
                break;
            case R.id.speech_lookup:
                TopicSearchActivity.start(_mActivity);
                break;
            case R.id.take_photo:
                picSearch();
                break;
        }
    }

    private void picSearch() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getActivity()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_GENERAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setFragment(int tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (oldPage != -1 && oldPage != tag) {
            ft.hide(fragments.get(oldPage));
        }

        if (null != fragments.get(tag) && fragments.get(tag).isAdded()) {
            ft.show(fragments.get(tag));
        } else {
            switch (tag) {
                case 0://没选择
                    HomeFirstFragment homeFirstFragment = new HomeFirstFragment();
                    fragments.put(tag, homeFirstFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    break;
                case 1://选好了的
                    HomeFragment homeFragment = new HomeFragment();
                    fragments.put(tag, homeFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    break;
            }
        }
        oldPage = tag;
        Log.e(TAG, "setFragment: " + tag );
        if (tag==0) {
            RxBus.get().post(C.RXBUS_REFER_HOMEFIRST, true);
        } else {
            RxBus.get().post(C.RXBUS_REFER_HOME, true);
        }
        try {
            ft.commit();
        } catch (Exception e) {

        }
    }


}
