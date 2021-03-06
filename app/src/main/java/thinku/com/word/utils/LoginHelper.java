package thinku.com.word.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import thinku.com.word.R;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.jpush.TagAliasOperatorHelper;
import thinku.com.word.ui.other.LoginActivity;
import thinku.com.word.ui.other.dialog.NeedLoginDialog;
import thinku.com.word.ui.other.dialog.callback.DialogClickListener;
import thinku.com.word.ui.personalCenter.SetNickNameActivity;


/**
 * 登录过程
 */
public class LoginHelper {
    private static final String TAG = LoginHelper.class.getSimpleName();
    private static String needLogin = "还未登录，请先登录";

    /**
     * @param context
     * @param
     */
    public static void againLoginRetrofit(final Context context, String phone, String password, final RequestCallback<UserInfo> requestCallback) {
        if (!TextUtils.isEmpty(phone)) {
            Log.e(TAG, "againLoginRetrofit: " + phone + "  " + password);
            addToCompositeDis(HttpUtil.login(phone, password)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            requestCallback.beforeRequest();
                        }
                    })
                    .subscribe(new Consumer<UserInfo>() {
                        @Override
                        public void accept(@NonNull final UserInfo userInfo) throws Exception {
                            if (userInfo != null) {
                                if (userInfo.getCode() == 1) {
                                    if (!TextUtils.isEmpty(userInfo.getNickname())) {
                                        setJpushAlians(context, userInfo.getUid());
                                        setSession(context, userInfo, new ICallBack() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                requestCallback.requestSuccess(userInfo);
                                            }

                                            @Override
                                            public void onFail() {
                                                requestCallback.requestFail("");
                                            }
                                        });
                                    } else {
                                        SharedPreferencesUtils.setLogin(context, userInfo);
                                        SetNickNameActivity.start(context, userInfo);
                                        if (!SharedPreferencesUtils.getStudyMode(context).equals(userInfo.getStudyModel())) {
                                            Log.e(TAG, "accept: " + SharedPreferencesUtils.getStudyMode(context) + "  " + userInfo.getStudyModel());
                                            updataMode(SharedPreferencesUtils.getStudyMode(context), context);
                                        }
                                    }
                                } else {
                                    requestCallback.requestFail(userInfo.getMessage());
                                }
                            } else {
                                requestCallback.requestFail(userInfo.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            requestCallback.requestFail(throwable.getMessage());
                        }
                    }));
        }
    }


    /**
     * 设置 Jpush alians
     *
     * @param context
     * @param uid
     */
    public static void setJpushAlians(Context context, String uid) {
        String sequence = "lgw" + uid;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = 2;
        tagAliasBean.alias = sequence;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, 2, tagAliasBean);
    }

    /**
     * retrofit 重置session
     *
     * @param context
     * @param mUserInfo
     */
    public static void setSession(final Context context, final UserInfo mUserInfo, final ICallBack mICallBack) {
        Log.e(TAG, "setSession: " + mUserInfo.getMessage());
        final Map param = new HashMap();
        param.put("uid", mUserInfo.getUid());
        param.put("username", mUserInfo.getUsername());
        param.put("password", mUserInfo.getPassword());
        param.put("email", mUserInfo.getEmail());
        param.put("phone", mUserInfo.getPhone());
        param.put("nickname", mUserInfo.getNickname());

        addToCompositeDis(Observable.just(1)
                .flatMap(new Function<Integer, ObservableSource<retrofit2.Response<Void>>>() {
                    @Override
                    public ObservableSource<retrofit2.Response<Void>> apply(@NonNull Integer integer) throws Exception {
                        return HttpUtil.word(param);
                    }
                })
                .flatMap(new Function<retrofit2.Response<Void>, ObservableSource<retrofit2.Response<Void>>>() {
                    @Override
                    public ObservableSource<retrofit2.Response<Void>> apply(@NonNull retrofit2.Response<Void> voidResponse) throws Exception {
                        if (voidResponse.isSuccessful()) {
                            return HttpUtil.toefl(param);
                        } else {
                            throw new IllegalArgumentException("toefl reset session fail");
                        }
                    }
                })
                .flatMap(new Function<retrofit2.Response<Void>, ObservableSource<retrofit2.Response<Void>>>() {
                    @Override
                    public ObservableSource<retrofit2.Response<Void>> apply(@NonNull retrofit2.Response<Void> voidResponse) throws Exception {
                        if (voidResponse.isSuccessful()) {
                            return HttpUtil.gmatl(param);
                        } else {
                            throw new IllegalArgumentException("gmatl reset session fail");
                        }
                    }
                })
                .flatMap(new Function<retrofit2.Response<Void>, ObservableSource<retrofit2.Response<Void>>>() {
                    @Override
                    public ObservableSource<retrofit2.Response<Void>> apply(@NonNull retrofit2.Response<Void> voidResponse) throws Exception {
                        if (voidResponse.isSuccessful()) {
                            return HttpUtil.gossip(param);
                        } else {
                            throw new IllegalArgumentException("gossip reset session fail");
                        }
                    }
                })
                .flatMap(new Function<retrofit2.Response<Void>, ObservableSource<retrofit2.Response<Void>>>() {
                    @Override
                    public ObservableSource<retrofit2.Response<Void>> apply(@NonNull retrofit2.Response<Void> voidResponse) throws Exception {
                        if (voidResponse.isSuccessful()) {
                            return HttpUtil.smartapply(param);
                        } else {
                            throw new IllegalArgumentException("smartapply reset session fail");
                        }
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mICallBack.onSuccess(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mICallBack.onFail();
                    }
                }));
    }


    public static void updataMode(final String status, final Context context) {
        Log.e(TAG, "updataMode: " + status);
        addToCompositeDis(HttpUtil.choseStudyMode(status)
                .subscribe(new Consumer<BackCode>() {
                    @Override
                    public void accept(@NonNull BackCode backCode) throws Exception {
                        if (backCode.getCode() == 1) {
                            Log.e("choseMode", "accept: " + status);
                            SharedPreferencesUtils.setStudyMode(context, status);
                        }
                    }
                }));
    }

    public static void toLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void toRequestCodeLogin(Activity context, int code) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, code);
    }

    public static void needLogin(Context context, String s) {
//        NeedLoginDialog dialog = new NeedLoginDialog(context, R.style.AlphaDialogAct);
//        dialog.setContent(s);
//        dialog.show();
        Log.e(TAG, "needLogin: " + s);
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

//    public static void needLogin(Context context, String s , int requestCode) {
//        NeedLoginDialog dialog = new NeedLoginDialog(context, R.style.AlphaDialogAct);
//        dialog.setContent(s);
//        dialog.setRequestCode(requestCode);
//        dialog.show();
//    }

    public static void needLogin(Activity context, String s, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    public static void needLogin(Context context, String s, DialogClickListener dialogClickListener) {
        NeedLoginDialog dialog = new NeedLoginDialog(context, R.style.AlphaDialogAct, dialogClickListener);
        dialog.setContent(s);
        dialog.show();
    }

    public static void initMessage(final Context context) {
        addToCompositeDis(HttpUtil.sendCode()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {

                    }
                }));
    }


    protected static void addToCompositeDis(Disposable disposable) {
//        if (mCompositeDisposable == null) {
//            mCompositeDisposable = new CompositeDisposable();
//        }
//        mCompositeDisposable.add(disposable);
    }
}
