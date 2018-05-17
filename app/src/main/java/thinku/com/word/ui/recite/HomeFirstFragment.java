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

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 * 未选择记忆模式和设置词包进入该页
 */

public class HomeFirstFragment extends BaseFragment implements View.OnClickListener {

    private TextView now_type,word_depot;
    private LinearLayout change_type;

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
        String mode = SharedPreferencesUtils.getMemoryMode(_mActivity);
        if (!TextUtils.isEmpty(mode)) {
            now_type.setText("你正在使用" + mode + "记忆单词");
        }else{
            now_type.setText("你还未选择记忆模式");
        }
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
}
