package thinku.com.word.view.wheelView;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Common {

    public static final Date dateFromCommonStr(String stringDate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date =sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  date;
    }

    public static final String timeToStr(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }

    public static final Date timeFromCNStr(String stringTime){
        SimpleDateFormat sdf=new SimpleDateFormat("H点m分");
        Date time = null;
        try {
            time =sdf.parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  time;
    }


    public static final String dateToStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    public static final Date dateTimeFromStr(String stringDateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time =sdf.parse(stringDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  time;
    }
    public static final String dateTimeToStr(Date dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(dateTime);
    }

    public static final Date dateTimeFromCustomStr(String date, String time){
        Date dateTime = new Date();
        Calendar calendar = Calendar.getInstance();
        //设置day
        if(date.equals("今天")){

        } else if(date.equals("明天")){
            calendar.add(Calendar.DATE,1);
        }
        else if(date.equals("后天")){
            calendar.add(Calendar.DATE,2);
        }else{
            dateTime = dateFromCommonStr(date);
            calendar.setTime(dateTime);
        }

        //设置小时分钟
        Date timeFormat = timeFromCNStr(time);
        try{
            calendar.set(Calendar.HOUR_OF_DAY,timeFormat.getHours());
            calendar.set(Calendar.MINUTE,timeFormat.getMinutes());
            dateTime = calendar.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String showScore(float score) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(score);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }




    public static ArrayList buildNomalHourList() {
        ArrayList hourList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            hourList.add(i + "点");
        }

        return hourList;
    }

    public static ArrayList buildNomalMinuteList() {
        ArrayList minuteList = new ArrayList<>();

        for (int i = 0; i < 60; i += 10) {
            minuteList.add(i + "分");
        }

        return minuteList;
    }

    //判断两个日期是否在同一天
    public static boolean isInSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
                .get(Calendar.DAY_OF_MONTH);
    }

    //判断两个日期是否位于同一小时
    public static boolean isInSameHour(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                && calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY);

    }
}
