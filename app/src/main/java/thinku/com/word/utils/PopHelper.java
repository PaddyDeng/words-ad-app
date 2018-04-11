package thinku.com.word.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import thinku.com.word.R;
import thinku.com.word.adapter.WrongChoseAdapter;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.ui.recite.ReviewErrorActivity;

/**
 * Created by Administrator on 2018/4/2.
 */

public class PopHelper {
    private View mView ;
    private Context context ;
    private PopupWindow mPopupWindow ;
    private SelectListener selectListener ;
    private static PopHelper popHelper ;
    private List<String> names ;
    public PopHelper(Context context){
        this.context = context ;
        mView = LayoutInflater.from(context).inflate(R.layout.wrong_chose_popwindow,null ,false);
        mPopupWindow = new PopupWindow(mView , WindowManager.LayoutParams.WRAP_CONTENT , WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true); //该值为false时，点击弹窗框外面window不会消失，即使设置了背景也无效，只能由dismiss()关闭
        mPopupWindow.setOutsideTouchable(true); //只有该值设置为true时，外层点击才有效
        mPopupWindow.update();
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//只有设置背景之后在focsable为true时点击弹出框外面才会消失，
    }
    public static  PopHelper create(Context context ){
        if (popHelper == null){
            popHelper = new PopHelper(context);
        }
        return popHelper ;
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener ;
    }

    public void show(View view){
        if (mPopupWindow != null){
            mPopupWindow.showAsDropDown(view);
        }
    }

    public void setData(List<String > names){
        this.names = names ;
    }

    public  void initRecyclerView(){
        if (mView == null ) return;
        if (selectListener == null) return ;
        if (names == null) return ;
        RecyclerView choses = (RecyclerView) mView.findViewById(R.id.wrong_chose);
        WrongChoseAdapter wrongChoseAdapter = new WrongChoseAdapter(context, names);
        wrongChoseAdapter.setSelectListener(selectListener);
        choses.setLayoutManager(new LinearLayoutManager(context));
        choses.setAdapter(wrongChoseAdapter);
    }


    public void dismiss(){
        if (mPopupWindow != null) mPopupWindow.dismiss();
    }
}
