package thinku.com.word.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
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
 */

public class BaseActivity extends AutoLayoutActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;
    private RequestQueue mRequestQueue;
    private int code,tag;
    private String[] permission;
    private PermissionCallback mCallback;
    private String hint;
    private ImmersionBar immersionBar;
    protected RxPermissions mRxPermissions;

    protected ConcurrentMap<String, CompositeDisposable> mConcurrentMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 创建请求队列, 默认并发3个请求, 传入数字改变并发数量: NoHttp.newRequestQueue(5);
        mRequestQueue = NoHttp.newRequestQueue(20);
        immersionBar = ImmersionBar.with(this);
        immersionBar.init();
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
     *调起加载dialog
     */
    public void showLoadDialog() {
        WaitUtils.show(mContext,getClass().getSimpleName());
    }
    public void showLoadDialog(String tag){
        WaitUtils.show(mContext,tag);
    }

    public void showLoadDialog(String hint ,String tag){
        WaitUtils.show(mContext ,tag);
        WaitUtils.setHint(tag ,hint);
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
        if(WaitUtils.isRunning(getClass().getSimpleName())){
            WaitUtils.dismiss(getClass().getSimpleName());
        }
    }

    public void dismissLoadDialog(String tag){
        if(WaitUtils.isRunning(tag)){
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
        if(WaitUtils.isRunning(getClass().getSimpleName())){
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
            if(WaitUtils.isRunning(getClass().getSimpleName())){
                dismissLoadDialog();
            }else {
                finishWithAnim();
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(); // 退出页面时时取消所有请求。
        mRequestQueue.stop(); // 退出时销毁队列，回收资源。
        if(null!=immersionBar)immersionBar.destroy();
    }
    /**
     * 发起一个请求。
     *
     * @param what     what.
     * @param request  请求对象。
     * @param listener 结果监听。
     * @param <T>      要请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        mRequestQueue.add(what, request, listener);
    }

    protected boolean getHttpResSuc(int code) {
        if (HttpUtils.getHttpMsgSu(code)) {
            return true;
        }
        return false;
    }

    public void getPermission(String[] permission, int code,String hint,int tag,PermissionCallback callback) {
        this.code =code;
        this.permission =permission;
        this.mCallback=callback;
        this.tag=tag;
        this.hint=hint;
        AndPermission.with(this).requestCode(code).permission(permission).rationale(new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                AndPermission.rationaleDialog(BaseActivity.this, rationale).show();
            }
        }).callback(callListener).start();
    }

    private PermissionListener callListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            mCallback.onSuccessful();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 是否有不再提示并拒绝的权限。
            if(tag==1) {
                if (AndPermission.hasAlwaysDeniedPermission(BaseActivity.this, deniedPermissions)) {
                    AndPermission.defaultSettingDialog(BaseActivity.this, requestCode)
                            .setTitle("权限申请失败")
                            .setMessage(hint)
                            .setPositiveButton("去设置")
                            .show();
                }
                mCallback.onFailure();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==code){
            if(AndPermission.hasPermission(BaseActivity.this,permission)) {
                mCallback.onSuccessful();
            } else {
                AndPermission.defaultSettingDialog(BaseActivity.this, requestCode)
                        .setTitle("权限申请失败")
                        .setMessage(hint)
                        .setPositiveButton("去设置")
                        .show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadDialog();
    }

    //  toast
    public void toTast( String content){
        Toast.makeText(mContext,content ,Toast.LENGTH_SHORT).show();
    }

    public void toTast( Context context,String content){
        Toast.makeText(context,content ,Toast.LENGTH_SHORT).show();
    }

    public void toTast( int contentId){
        Toast.makeText(mContext,mContext.getString(contentId) ,Toast.LENGTH_SHORT).show();
    }

}
