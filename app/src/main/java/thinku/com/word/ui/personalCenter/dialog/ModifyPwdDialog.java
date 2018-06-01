package thinku.com.word.ui.personalCenter.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.callback.RequestCallback;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.personalCenter.BaseDialog;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.SharedPreferencesUtils;

public class ModifyPwdDialog extends BaseDialog {

    private static ICallBack<String> mCallBack;

    public static ModifyPwdDialog getInstance(ICallBack<String> callBack) {
        ModifyPwdDialog simpleEditDialog = new ModifyPwdDialog();
        simpleEditDialog.mCallBack = callBack;
        return simpleEditDialog;
    }

    @BindView(R.id.modify_old_pwd)
    EditText oldPwd;
    @BindView(R.id.modify_new_pwd)
    EditText newPwd;
    @BindView(R.id.dialog_simple_btn_confirm)
    TextView confirm;
    @BindView(R.id.dialog_simple_btn_cancel)
    TextView cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyPwd();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCallBack != null)
            mCallBack = null;
    }

    @Override
    protected int getContentViewLayId() {
        return R.layout.modify_pwd_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                if (check(newPwd, oldPwd)) {
                    updatePassword();
                }
            }
        });
    }

    private void modifyPwd() {
        addToCompositeDis(HttpUtil.sendCode()
                .subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull ResultBeen<Void> voidResultBeen) throws Exception {
                        if (voidResultBeen.getCode() == 1) {
                            updatePassword();
                        } else {
                            toastShort(voidResultBeen.getMessage());
                        }
                    }
                }));
        showLoadDialog();
    }


    public void updatePassword(){
        UserInfo data = SharedPreferencesUtils.getUserInfo(getActivity());
        addToCompositeDis(HttpUtil.updatePasswordObservable(data.getUid(), getEditTxt(oldPwd), getEditTxt(newPwd))
                .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissLoadDialog();
            }
        }).subscribe(new Consumer<ResultBeen<Void>>() {
                    @Override
                    public void accept(@NonNull final ResultBeen<Void> bean) throws Exception {
                        String newPwdStr = getEditTxt(newPwd);
                        if (getHttpCodeSucc(bean.getCode())) {
                            if (mCallBack != null) {
                                mCallBack.onSuccess(newPwdStr);
                                mCallBack = null;
                            }
                            SharedPreferencesUtils.setPassword(getActivity(), newPwdStr);
                            //重新登录
                            String account = SharedPreferencesUtils.getPhone(getActivity());
                            LoginHelper.againLoginRetrofit(getActivity(), account, newPwdStr, new RequestCallback<UserInfo>() {
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
                                    UserInfo user = userInfo;
                                    dismissLoadDialog();
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                        dismiss();
                    }
                }));
    }

    private boolean check(EditText newPwd, EditText olePwd) {
        String newStr = getEditTxt(newPwd);
        String oldStr = getEditTxt(olePwd);
        if (TextUtils.isEmpty(oldStr)) {
            toastShort("请填写原密码");
            return false;
        } else if (TextUtils.isEmpty(newStr)) {
            toastShort("请填写新密码");
            return false;
        } else if (TextUtils.equals(newStr, oldStr)) {
            toastShort("新旧密码一致，无须修改");
            return false;
        }
        return true;
    }
}
