package thinku.com.word.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.WaitUtils;


/**
 * Created by dasu on 2016/9/27.
 * <p>
 * Fragment基类，封装了懒加载的实现
 * <p>
 * 1、Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义新的回调方法，当fragment可见状态改变时会触发的回调方法，和 Fragment 第一次可见时会回调的方法
 *
 * @see #onFragmentFirstVisible()
 */
public abstract class BaseFragment extends SupportFragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View rootView;

    protected Context mContext;
    private ImmersionBar immersionBar;

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    protected void addToCompositeDis(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    /**
     * 判断是否请求成功
     *
     * @param code
     * @return
     */
    protected boolean getHttpResSuc(int code) {
        if (HttpUtils.getHttpMsgSu(code)) {
            return true;
        }
        return false;
    }

    //  toast
    public void toTast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 调起加载dialog
     */
    public void showLoadDialog(Context context, String tag) {
        WaitUtils.show(mContext, tag);
    }


    public void dismissLoadDialog(String tag) {
        if (WaitUtils.isRunning(tag)) {
            WaitUtils.dismiss(tag);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        RefWatcher refWatcher = MyApplication.getRefWatcher(_mActivity);
//        if (null != refWatcher)  refWatcher.watch(_mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }


    public void toLogin() {
        LoginHelper.needLogin(_mActivity, "您还未登陆，请先登陆");
    }

    public void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            _mActivity.finish();
        }
        return true;
    }

    /**
     * session 失效重新登录
     */
    public void login() {
        UserInfo userInfo = SharedPreferencesUtils.getUserInfo(_mActivity);
        if (userInfo != null & !TextUtils.isEmpty(userInfo.getPhone()) & !"".equals(userInfo.getPhone())) {
            LoginHelper.setSession(_mActivity, userInfo, new ICallBack() {
                @Override
                public void onSuccess(Object o) {

                }

                @Override
                public void onFail() {

                }
            });
        }
    }

}
