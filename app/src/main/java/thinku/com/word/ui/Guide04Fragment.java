package thinku.com.word.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.ui.personalCenter.TypeSettingActivity;
import thinku.com.word.utils.RxHelper;


/**
 * Created by Administrator on 2018/2/2.
 */

public class Guide04Fragment extends BaseFragment {
    private ImageView iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_guide04,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       iv= (ImageView) view.findViewById(R.id.iv);
        Glide.with(getActivity()).load(R.mipmap.guide_4).into(iv);
        RxHelper.delay(1000)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        TypeSettingActivity.start(getActivity() ,true);
                        getActivity().finish();
                    }
                });

    }

}
