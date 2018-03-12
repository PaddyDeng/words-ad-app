package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.HashMap;
import java.util.Map;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.BackCode;
import thinku.com.word.http.NetworkChildren;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.utils.LogUtils;
import thinku.com.word.utils.SharedPreferencesUtils;

/**
 *背单词
 */

public class ReciteFragment extends BaseFragment implements View.OnClickListener {

    private ImageView portrait;
    private LinearLayout input_lookup;
    private ImageView speech_lookup,photo_lookup;
    private Map<String,Fragment> fragments;
    private int oldPage=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recite,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        initView();
        setClick();
    }

    private void initView() {
        fragments=new HashMap<>();
        String session= SharedPreferencesUtils.getSession(getActivity(),1);
        if(!TextUtils.isEmpty(session)){
            Request<String>req =NoHttp.createStringRequest(NetworkTitle.WORD+ NetworkChildren.USERDETAIL, RequestMethod.POST);
            req.setHeader("Cookie","PHPSESSID="+session);
            ((BaseActivity)getActivity()).request(0, req, new SimpleResponseListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    if(response.isSucceed()){
                        try {
                            BackCode backCode=JSON.parseObject(response.get(), BackCode.class);
                            if(backCode.getCode()==1){//成功


                            }else{
                                setFragment(1);
                            }
                        }catch (JSONException e){
                            LogUtils.log(response.get());
                        }
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    LogUtils.log(response.get());
                }
            });
        }else{
            setFragment(0);
        }
    }

    private void setClick() {
        portrait.setOnClickListener(this);
        input_lookup.setOnClickListener(this);
        speech_lookup.setOnClickListener(this);
        photo_lookup.setOnClickListener(this);
    }
    private void findView(View view) {
        portrait = (ImageView) view.findViewById(R.id.portrait);
        input_lookup = (LinearLayout) view.findViewById(R.id.input_lookup);
        speech_lookup = (ImageView) view.findViewById(R.id.speech_lookup);
        photo_lookup = (ImageView) view.findViewById(R.id.photo_lookup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.portrait:

                break;
            case R.id.input_lookup:
                break;
            case R.id.speech_lookup:
                break;
            case R.id.photo_lookup:
                break;
        }
    }

    public void setFragment(int tag){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if(oldPage!=-1){
            ft.hide(fragments.get(oldPage+""));
        }
        if(null!=fragments.get(tag+"")&&fragments.get(tag+"").isAdded()){
            ft.show(fragments.get(tag+""));
        }else {
            switch (tag) {
                case 0://没选择
                    HomeFirstFragment homeFirstFragment = new HomeFirstFragment();
                    fragments.put(tag + "", homeFirstFragment);
                    ft.add(R.id.fl, fragments.get(tag + ""));
                    break;
                case 1://选好了的
                    HomeFragment homeFragment =new HomeFragment();
                    fragments.put(tag+"",homeFragment);
                    ft.add(R.id.fl,fragments.get(tag+""));
                    break;
            }
        }
        oldPage=tag;
        ft.commit();
    }

}
