package thinku.com.word.ui.personalCenter.bean;

/**
 * Created by Administrator on 2018/5/3.
 */

public class Clock {
    private String time ;
    private String week ;
    private boolean isCheck ;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
