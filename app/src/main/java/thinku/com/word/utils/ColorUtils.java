package thinku.com.word.utils;

import android.content.Context;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ColorUtils {

    public static int  getProgressColor( Context context ,float total , float userWords){
        float desc = userWords / total ;
        if (desc > 0 && desc <= 0.3f){
            return context.getResources().getColor(R.color.color_progress_low);
        }else if (desc <= 0.6f){
            return context.getResources().getColor(R.color.color_progress_middle);
        }else if (desc <= 1f){
            return context.getResources().getColor(R.color.color_progress_high);
        }else{
            return context.getResources().getColor(R.color.progress_bg);
        }
    }
}
