package thinku.com.word.utils;

import android.content.Context;
import android.util.Log;

import thinku.com.word.R;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ColorUtils {

    public static int  getProgressColor( Context context ,float total , float userWords){
        float desc = userWords / total ;
        if (desc > 0 && desc <= 0.3f){
            return context.getResources().getColor(R.color.circle_red);
        }else if (desc <= 0.6f){
            return context.getResources().getColor(R.color.color_progress_middle);
        }else if (desc <= 1f){
            return context.getResources().getColor(R.color.color_progress_high);
        }else{
            return context.getResources().getColor(R.color.tittle);
        }
    }


    public static int getPackDetailsColor(Context context ,String status){
        if (status == null) {
          return R.color.gray ;
        }
        int color  ;
        switch (status){
            case "":
                color = R.color.word_none;
                Log.e("tag", "word_none: "  );
                break;
            case "3":
                color = R.color.word_not_know;
                Log.e("tag", "word_not_know: "  );
                break;
            case "1":
                color = R.color.word_know;
                Log.e("tag", "word_know: "  );
                break;
            case "2":
                color = R.color.word_know;
                Log.e("tag", "word_know: "  );
                break;
            case "4":
                color = R.color.word_dim;
                Log.e("tag", "word_dim: "  );
                break;
            default:
                color = R.color.word_none;
                Log.e("tag", "word_none: "  );
                break;
        }
        return  color ;
    }
}
