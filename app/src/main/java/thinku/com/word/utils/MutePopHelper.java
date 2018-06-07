package thinku.com.word.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ch.ielse.view.SwitchView;
import thinku.com.word.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class MutePopHelper {
    private View mView ;
    private Context context ;
    private PopupWindow mPopupWindow ;
    private MuteOpenListener muteOpenListener ;
    private volatile  static MutePopHelper popHelper ;
    public MutePopHelper(Context context){
        this.context = context ;
        mView = LayoutInflater.from(context).inflate(R.layout.pop_music,null ,false);
        mPopupWindow = new PopupWindow(mView , MeasureUtils.dp2px(context ,200), WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true); //该值为false时，点击弹窗框外面window不会消失，即使设置了背景也无效，只能由dismiss()关闭
        mPopupWindow.setOutsideTouchable(true); //只有该值设置为true时，外层点击才有效
        mPopupWindow.update();
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//只有设置背景之后在focsable为true时点击弹出框外面才会消失，
    }
    public static  MutePopHelper create(Context context ){
        if (popHelper == null){
            synchronized (MutePopHelper.class) {
                popHelper = new MutePopHelper(context);
            }
        }
        return popHelper ;
    }

    public void setSelectListener(MuteOpenListener muteOpenListener){
        this.muteOpenListener = muteOpenListener ;
    }

    public void show(View view){
        if (mPopupWindow != null){
            muteOpenListener.onShowListener();
            mPopupWindow.showAsDropDown(view);
        }
    }
    public  void init(){
        if (mView == null ) return;
        if (muteOpenListener == null) return ;
        boolean isPlay = SharedPreferencesUtils.getPlayMusic(context);
        final TextView muteTxt  = (TextView) mView.findViewById(R.id.mute_text);
        final SwitchView switchView = (SwitchView) mView.findViewById(R.id.switch_button);
        Log.e("tttt", "init: " + isPlay );
        if (isPlay) {
            muteTxt.setText("(关闭)");
        }
        else {
            muteTxt.setText("(开启)");
        }
        final RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(R.id.error_rel);
        switchView.setOpened(!isPlay);
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                muteOpenListener.closeMusic();
                muteTxt.setText("(开启)");
                view.setOpened(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                muteOpenListener.openMusic();
                muteTxt.setText("(关闭)");
                view.setOpened(false);
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                muteOpenListener.onDismissListener();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muteOpenListener.toError();
            }
        });
    }


    public void dismiss(){
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public void onDestory(){
        if (popHelper != null){
            popHelper = null ;
        }
    }


    public interface MuteOpenListener{
        void openMusic() ;
        void closeMusic();
        void onDismissListener();
        void onShowListener() ;
        void toError();
    }
}
