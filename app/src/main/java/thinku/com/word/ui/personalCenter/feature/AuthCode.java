package thinku.com.word.ui.personalCenter.feature;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.ui.call.IDestoryView;


public class AuthCode extends CountDownTimer implements IDestoryView {

    private TextView mAuthCode;
    private Context mContext;

    private int mRetryResId;
    private int mShowResId;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public AuthCode(long millisInFuture, long countDownInterval, Context context, TextView sendAuthCode) {
        super(millisInFuture, countDownInterval);
        this.mAuthCode = sendAuthCode;
        this.mContext = context;
        this.mRetryResId = R.drawable.auth_code_bg;
        this.mShowResId = R.drawable.counter_down_bg;
    }

    public AuthCode(long millisInFuture, long countDownInterval, Context context, TextView sendAuthCode, @DrawableRes int defaltRes) {
        super(millisInFuture, countDownInterval);
        this.mAuthCode = sendAuthCode;
        this.mContext = context;
        this.mRetryResId = defaltRes;
        this.mShowResId = defaltRes;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        setAuthCodeClickable(mAuthCode, false, millisUntilFinished);
    }

    @Override
    public void onFinish() {
        setAuthCodeClickable(mAuthCode, true, 0);
    }

    public void setAuthCodeClickable(TextView view, boolean isRetry, long millisUntilFinished) {
        if (isRetry) {
            cancel();
            // 显示再次发送
            view.setClickable(true);
            view.setText("获取验证码");
            view.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            view.setBackgroundResource(mRetryResId);
        } else {
            // 显示还剩多少秒
            view.setClickable(false);
            view.setText(millisUntilFinished / 1000 + "秒");
            view.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            view.setBackgroundResource(R.drawable.white_10round);
        }
    }

    /**
     * 显示再次发送情况
     */
    public void sendAgain() {
        setAuthCodeClickable(mAuthCode, true, 0);
    }


    @Override
    public void destory() {
        cancel();
        mContext = null;
    }
}
