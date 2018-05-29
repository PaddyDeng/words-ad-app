package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;
import thinku.com.word.utils.C;
import thinku.com.word.utils.LoginHelper;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 未选择记忆模式和设置词包进入该页
 */

public class HomeFirstFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = HomeFirstFragment.class.getSimpleName();
    private TextView now_type,word_depot;
    private LinearLayout change_type;
    private Observable<Boolean> referObservable ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_first,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
        init();
        referObservable = RxBus.get().register(C.RXBUS_REFER_HOMEFIRST ,Boolean.class);
        referObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                init();
            }
        });
    }



    private void setClick() {
        change_type.setOnClickListener(this);
        word_depot.setOnClickListener(this);
    }

    private void findView(View view) {
        now_type = (TextView) view.findViewById(R.id.now_type);
        change_type = (LinearLayout) view.findViewById(R.id.change_type);
        word_depot = (TextView) view.findViewById(R.id.word_depot);
    }

    public void init(){
        Log.e(TAG, "init: " );
        String mode = ""  ;
        String initMode = SharedPreferencesUtils.getStudyMode(_mActivity);
        switch (initMode){
            case "1":
                mode = "艾宾浩斯记忆法（科学记忆）";
                break;
            case "2":
                mode = "复习记忆法（快速巩固）";
                break;
            case "3":
                mode = "只背新单词（快速记忆）";
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(mode)) {
            now_type.setText("你正在使用" + mode + "记忆单词");
        }else{
            now_type.setText("你还未选择记忆模式");
        }
        LoginHelper.needLogin(_mActivity ,"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_type:
                TypeSettingActivity.start(getActivity());
                break;
            case R.id.word_depot:
                WordPackageActivity.start(getActivity());
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("homefirst", "onHiddenChanged: ");
//        if (!hidden) {
//            init();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(C.RXBUS_REFER_HOMEFIRST ,referObservable);
    }
}
