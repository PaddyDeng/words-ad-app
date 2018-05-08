package thinku.com.word.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2017/8/16.
 */

public class DateUtil {
    private static final String TAG = DateUtil.class.getSimpleName();
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static String getCurrentYearAndMonth() {
        Calendar a = Calendar.getInstance();
        int year = a.get(Calendar.YEAR);
        int month = a.get(Calendar.MONTH) + 1;
        return year + "-" + month + "-" + getToday() ;
    }

    public static int getFirstDayOfMonth() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DAY_OF_MONTH, 1);
        int i = a.get(Calendar.DAY_OF_WEEK);
        return i;
    }

    public static int getToday() {
        Calendar a = Calendar.getInstance();
        int today = a.get(Calendar.DAY_OF_MONTH);
        return today;
    }


    /**
     * 计算两个日期之间的日期
     *
     * @param startTime
     * @param endTime
     */
    public static List<String> betweenDays(long startTime, long endTime) {
        List<String> datas = new ArrayList<>();
        Date date_start = new Date(startTime);
        Date date_end = new Date(endTime);
        //计算日期从开始时间于结束时间的0时计算
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (ONE_DAY_MS));
        if (s > 0) {
            for (int i = 0; i <= s; i++) {
                long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
                /**
                 * yyyy-MM-dd E :2012-09-01
                 */
                datas.add(getCustonFormatTime(todayDate ,"yyyy-MM-dd"));
            }
        } else {//此时在同一天之内
            datas.add(getCustonFormatTime(startTime ,"yyyy-MM-dd"));
        }
        return datas ;
    }


    /**
     * date2比date1多的天数
     * @return
     */
    public static int differentDays(int id)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());

        Calendar cal2 = Calendar.getInstance();
        String time = "2018-04-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = (Date) sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return (timeDistance + (day1-day2) * 50 -id + 234 ) ;
        }
        else    //不同年
        {
            return (day1-day2) * 50 - id + 234;
        }
    }


    /**
     * 格式化传入的时间
     *
     * @param time      需要格式化的时间
     * @param formatStr 格式化的格式
     * @return
     */
    public static String getCustonFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = new Date(time);
        return format.format(d1);
    }



    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String dateToString(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }


    /**
     * 比较两日期的大小
     * @param currentTime
     * @param liveTime
     * @return
     */
    public static boolean compare(String currentTime ,String liveTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try{
            Date currentDate = simpleDateFormat.parse(currentTime);
            Date liveDate = simpleDateFormat.parse(liveTime);
            if (currentDate.getTime() > liveDate.getTime()){
                return false ;
            }else{
                return true ;
            }
        }catch (ParseException e){
            Log.i("date" ,e.getMessage());
            return false ;
        }
    }

    /**
     * 比较两日期的大小
     * @param currentTime
     * @param liveTime
     * @return
     */
    public static boolean compare(long currentTime ,String liveTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try{
            Date liveDate = simpleDateFormat.parse(liveTime);
            if (currentTime > liveDate.getTime()){
                return false ;
            }else{
                return true ;
            }
        }catch (ParseException e){
            Log.i("date" ,e.getMessage());
            return false ;
        }
    }
}
