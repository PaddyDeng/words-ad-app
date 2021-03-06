package thinku.com.word.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import thinku.com.word.R;
import thinku.com.word.callback.PermissionCallback;
import thinku.com.word.permission.RxPermissions;
import thinku.com.word.utils.HttpUtils;
import thinku.com.word.utils.WaitUtils;


/**
 * Created by Administrator on 2017/11/2.
 * 没有沉浸式的
 */

public class BaseNoImmActivity extends AutoLayoutActivity {
    public static final String TAG = BaseNoImmActivity.class.getSimpleName();

    protected Context mContext;
    private int code, tag;
    private String[] permission;
    private PermissionCallback mCallback;
    private String hint;
    protected RxPermissions mRxPermissions;

    protected ConcurrentMap<String, CompositeDisposable> mConcurrentMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 创建请求队列, 默认并发3个请求, 传入数字改变并发数量: NoHttp.newRequestQueue(5);
        mRxPermissions = new RxPermissions(this);

    }


    //网络请求
    protected void addToCompositeDis(Disposable disposable) {
        CompositeDisposable compositeDisposable = mConcurrentMap.get(TAG);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            mConcurrentMap.put(TAG, compositeDisposable);
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        switch (getAnimType()) {
            case ANIM_TYPE_RIGHT_IN:
                overridePendingTransition(R.anim.ac_slide_right_in, R.anim.ac_slide_left_out);
                break;
            case ANIM_TYPE_UP_IN:
                overridePendingTransition(R.anim.ac_slide_up_in, 0);
                break;
            case ANIM_TYPE_DOWN_IN:
                overridePendingTransition(R.anim.ac_slide_down_in, 0);
                break;
            case ANIM_TYPE_SCALE_CENTER:
                overridePendingTransition(R.anim.ac_scale_magnify_center, 0);
                break;
            default:
                break;
        }
    }


    /**
     * 调起加载dialog
     */
    public void showLoadDialog() {
        WaitUtils.show(mContext, getClass().getSimpleName());
    }

    public void showLoadDialog(String tag) {
        WaitUtils.show(mContext, tag);
    }

    public void showLoadDialog(String hint, String tag) {
        WaitUtils.show(mContext, tag);
        WaitUtils.setHint(tag, hint);
    }

//    public boolean isShow(){
//        if(WaitUtils.isRunning(getClass().getSimpleName()))return true;
//        else return false;
//    }

    /**
     * 关闭加载dialog
     */
    public void dismissLoadDialog() {
//        if (isShow()) {
//            WaitDialog01.getInstance(mContext).dismissWaitDialog();
//        }
        if (WaitUtils.isRunning(getClass().getSimpleName())) {
            WaitUtils.dismiss(getClass().getSimpleName());
        }
    }

    public void dismissLoadDialog(String tag) {
        if (WaitUtils.isRunning(tag)) {
            WaitUtils.dismiss(tag);
        }
    }

    public void finishWithAnim() {
        switch (getAnimType()) {
            case ANIM_TYPE_RIGHT_IN:
                finishWithAnimRightOut();
                break;
            case ANIM_TYPE_UP_IN:
                finishWithAnimDownOut();
                break;
            case ANIM_TYPE_DOWN_IN:
                finishWithAnimUpOut();
                break;
            case ANIM_TYPE_SCALE_CENTER:
                finishWithAnimScaleCenter();
                break;
            default:
                finish();
                break;
        }
    }

    private void finishWithAnimRightOut() {
        finish();
        overridePendingTransition(R.anim.ac_slide_left_in, R.anim.ac_slide_right_out);
    }

    private void finishWithAnimUpOut() {
        finish();
        overridePendingTransition(0, R.anim.ac_slide_up_out);
    }

    private void finishWithAnimDownOut() {
        finish();
        overridePendingTransition(0, R.anim.ac_slide_down_out);
    }

    private void finishWithAnimScaleCenter() {
        finish();
        overridePendingTransition(0, R.anim.ac_scale_shrink_center);
    }

    public enum AnimType {
        ANIM_TYPE_DOWN_IN,
        ANIM_TYPE_RIGHT_IN, // 右侧滑动进入
        ANIM_TYPE_UP_IN, //
        ANIM_TYPE_SCALE_CENTER // 中心缩放显示/隐藏
    }

    public AnimType getAnimType() {
        return AnimType.ANIM_TYPE_RIGHT_IN;
    }

    /**
     * 退出之前，如果需要额外处理，调用此方法
     *
     * @return {@link #onKeyDown(int, KeyEvent) onKeyDown}后续执行
     * true：	直接返回，停留在当前页面；
     * false：	继续执行退出后续操作。
     */
    protected boolean preBackExitPage() {
        if (WaitUtils.isRunning(getClass().getSimpleName())) {
            dismissLoadDialog();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (preBackExitPage()) {
                return true;
            }
            if (WaitUtils.isRunning(getClass().getSimpleName())) {
                dismissLoadDialog();
            } else {
                finishWithAnim();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
//        if(refWatcher != null) refWatcher.watch(this);
    }


    protected boolean getHttpResSuc(int code) {
        if (HttpUtils.getHttpMsgSu(code)) {
            return true;
        }
        return false;
    }

    public void getPermission(String[] permission, int code, String hint, int tag, PermissionCallback callback) {
        this.code = code;
        this.permission = permission;
        this.mCallback = callback;
        this.tag = tag;
        this.hint = hint;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadDialog();
    }

    //  toast
    public void toTast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    public void toTast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public void toTast(int contentId) {
        Toast.makeText(mContext, mContext.getString(contentId), Toast.LENGTH_SHORT).show();
    }

}
