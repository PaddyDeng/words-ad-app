package thinku.com.word.ui.personalCenter.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.C;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.personalCenter.BaseDialog;
import thinku.com.word.ui.personalCenter.feature.AuthCode;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RegexValidateUtil;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.utils.Utils;

/**
 * 修改电话或邮箱后都要重新登录，修改成功，自动就登录了。
 */
public class ModifyPhoneOrEmailDialog extends BaseDialog {
    private final static String TAG = ModifyPhoneOrEmailDialog.class.getSimpleName();
    private static ICallBack<String> mCallBack;
    private static final String KEY_TYPE = "modify_email";

    public static ModifyPhoneOrEmailDialog getInstance(boolean modifyEmail, ICallBack<String> callBack) {
        ModifyPhoneOrEmailDialog simpleEditDialog = new ModifyPhoneOrEmailDialog();
        Bundle b = new Bundle();
        b.putBoolean(KEY_TYPE, modifyEmail);
        simpleEditDialog.setArguments(b);
        simpleEditDialog.mCallBack = callBack;
        return simpleEditDialog;
    }

    @BindView(R.id.modify_dialog_tv_title)
    TextView title;
    @BindView(R.id.modify_acc_et_content)
    EditText et;
    @BindView(R.id.modify_dialog_et_auth)
    EditText etAuthCode;
    @BindView(R.id.modify_authcode)
    TextView authCode;
    @BindView(R.id.dialog_simple_btn_confirm)
    TextView confirm;
    @BindView(R.id.dialog_simple_btn_cancel)
    TextView cancel;
    private AuthCode mAuthCode;
    private boolean modifyEmail;

    @Override
    protected void getArgs() {
        super.getArgs();
        Bundle arguments = getArguments();
        if (arguments != null) {
            modifyEmail = arguments.getBoolean(KEY_TYPE);
        }
    }

    @Override
    protected int getContentViewLayId() {
        return R.layout.modify_phone_or_email_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (modifyEmail) {
            title.setText("修改邮箱");
            et.setHint("请填写邮箱");
        }
        mAuthCode = new AuthCode(60 * 1000, 1000, getActivity(), authCode, R.drawable.modify_p_or_e_auth_code_gb);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onFail();
                    mCallBack = null;
                }
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check(et) && checkAuthCode(etAuthCode)) {
                    modify(et);
                }
            }
        });
        authCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAuthCode();
            }
        });
    }

    private void getAuthCode() {
        addToCompositeDis(HttpUtil.sendCode()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        if (voidResultBeen.getCode() == 1) {
                            if (modifyEmail) {
                                sendAuthCode(HttpUtil.emailCodeObservable(getEditTxt(et)));
                            } else {
                                sendAuthCode(HttpUtil.phoneCodeObservable(getEditTxt(et)));
                            }
                        } else {
                            toastShort(voidResultBeen.getMessage());
                        }
                    }
                }));
    }


    private void sendAuthCode(Observable<ResultBeen<Void>> observable) {
        if (check(et)) {
            mAuthCode.start();
            addToCompositeDis(observable.subscribe(new Consumer<ResultBeen<Void>>() {
                @Override
                public void accept(@NonNull ResultBeen<Void> o) throws Exception {
                    if (o.getCode() != 1) {
                        mAuthCode.sendAgain();
                    } else {
                        toastShort(o.getMessage());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    mAuthCode.sendAgain();
                }
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAuthCode != null)
            mAuthCode.destory();
        if (mCallBack != null)
            mCallBack = null;
    }

    private Observable<ResultBeen<Void>> getModifyType() {
        UserInfo userInfo = SharedPreferencesUtils.getUserInfo(getActivity());
        if (modifyEmail) {
            return HttpUtil.updateEmailObservable(userInfo.getUid(), getEditTxt(et), getEditTxt(etAuthCode));
        }
        return HttpUtil.updatePhoneObservable(userInfo.getUid(), getEditTxt(et), getEditTxt(etAuthCode));
    }

    private void modify(final EditText et) {
        showLoadDialog();
        addToCompositeDis(getModifyType().doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                })
                        .subscribe(new Consumer<ResultBeen<Void>>() {
                            @Override
                            public void accept(@NonNull final ResultBeen<Void> bean) throws Exception {
                                String account = getEditTxt(et);
                                if (getHttpCodeSucc(bean.getCode())) {
                                    if (mCallBack != null) {
                                        mCallBack.onSuccess(account);
                                        mCallBack = null;
                                    }
                                    saveUserInfo(account);
//                                    //重新登录
                                    String password = SharedPreferencesUtils.getPassword(getActivity());
                                    if (TextUtils.isEmpty(password)) {
                                        password = C.DEFAULT_PWD;
                                    }
                                    LoginHelper.againLoginRetrofit(getActivity(), account, password, new RequestCallback<UserInfo>() {
                                        @Override
                                        public void beforeRequest() {
                                            showLoadDialog();
                                        }

                                        @Override
                                        public void requestFail(String msg) {
                                            toastShort(msg);
                                            dismissLoadDialog();
                                            dismiss();
                                        }

                                        @Override
                                        public void requestSuccess(UserInfo userInfo) {
                                            dismissLoadDialog();
                                            UserInfo user = userInfo;
                                            SharedPreferencesUtils.setPassword(getActivity(), TextUtils.isEmpty(user.getPhone()) ? user.getEmail() : user.getPhone(), user.getPassword());
                                            SharedPreferencesUtils.setLogin(getActivity(), user);
                                            dismiss();
                                        }

                                        @Override
                                        public void otherDeal(UserInfo userInfo) {

                                        }

                                    });
                                } else {
                                    toastShort(bean.getMessage());
                                    dismissLoadDialog();
                                }
                            }
                        })
        );
    }

    private void saveUserInfo(String account) {
        if (!TextUtils.isEmpty(account)) {
            if (modifyEmail) {
                SharedPreferencesUtils.setEmail(getActivity(), account);
            } else {
                SharedPreferencesUtils.setPhone(getActivity(), account);
            }
        }
    }


    private boolean checkAuthCode(EditText etAuthCode) {
        String authCode = Utils.getEditTextString(etAuthCode);
        if (TextUtils.isEmpty(authCode)) {
            toastShort("请输入验证码");
            return false;
        }
        return true;
    }

    private boolean check(final EditText editText) {
        String acc = Utils.getEditTextString(editText);
        if (modifyEmail) {
            if (checkEmail(acc)) return false;
        } else {
            if (checkPhone(acc)) return false;
        }
        return true;
    }

    private boolean checkEmail(String acc) {
        if (TextUtils.isEmpty(acc)) {
            toastShort("请填写邮箱");
            return true;
        } else if (!RegexValidateUtil.checkEmail(acc)) {
            toastShort("邮箱格式错误");
            return true;
        } else if (acc.equals(SharedPreferencesUtils.getUserInfo(getActivity()).getEmail())) {
            toastShort("与当前邮箱一致，无须修改");
            return true;
        }
        return false;
    }

    private boolean checkPhone(String acc) {
        if (TextUtils.isEmpty(acc)) {
            toastShort("请填写手机号");
            return true;
        } else if (!RegexValidateUtil.checkPhoneNumber(acc)) {
            toastShort("手机号格式错误");
            return true;
        } else if (acc.equals(SharedPreferencesUtils.getUserInfo(getActivity()).getPhone())) {
            toastShort("与当前手机号一致，无须修改");
            return true;
        }
        return false;
    }
}
