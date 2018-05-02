package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.MyApplication;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserData;
import thinku.com.word.callback.ReferImage;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.http.SchedulerTransformer;
import thinku.com.word.ui.personalCenter.PersonalCenterActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.RxHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 背单词
 */

public class ReciteFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = ReciteFragment.class.getSimpleName();
    private CircleImageView portrait;
    private LinearLayout input_lookup;
    private ImageView speech_lookup, photo_lookup;
    private SparseArray<Fragment> fragments;
    private int oldPage = -1;

    public static ReciteFragment newInstance() {
        ReciteFragment reciteFragment = new ReciteFragment();
        return reciteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
    }




    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    /**
     * 是否选择记忆模式
     *
     * @return
     */
    public boolean isChooseMemeryMode() {
        MyApplication.MemoryMode = SharedPreferencesUtils.getMemoryMode(_mActivity);
        if (MyApplication.MemoryMode != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void initView() {
        addToCompositeDis(HttpUtil.getUserData()
                .subscribe(new Consumer<ResultBeen<UserData>>() {
            @Override
            public void accept(@NonNull ResultBeen<UserData> been) throws Exception {
                if (getHttpResSuc(been.getCode())) {
                    UserData userData = been.getData();
                    if (userData != null && !TextUtils.isEmpty(userData.getPassword())) {
                        SharedPreferencesUtils.setPlanWords(_mActivity, userData.getPlanWords());
                        new GlideUtils().loadCircle(_mActivity , NetworkTitle.WORDRESOURE + userData.getImage(),portrait);
                        SharedPreferencesUtils.setImage(_mActivity ,userData.getImage());
                        if (TextUtils.isEmpty(userData.getPlanWords())) {
                            setFragment(0);
                        } else {
                            setFragment(1);
                        }
                    }
                }else {
                    setFragment(0);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
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
                break;
            case R.id.speech_lookup:
                break;
            case R.id.take_photo:
                break;
        }
    }

    public void setFragment(int tag) {
        if (tag ==1){
            portrait.setVisibility(View.VISIBLE);
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (oldPage != -1) {
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
                    portrait.setVisibility(View.GONE);
                    break;
                case 1://选好了的
                    HomeFragment homeFragment = new HomeFragment();
                    fragments.put(tag, homeFragment);
                    ft.add(R.id.fl, fragments.get(tag));
                    portrait.setVisibility(View.VISIBLE);
                    break;
            }
        }
        oldPage = tag;
        try {
            ft.commit();
        } catch (Exception e) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

}
