package thinku.com.word.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/4.
 */
@DatabaseTable(tableName = "tb_clock")
public class Clock implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "time")
    private String time;
    @DatabaseField(columnName = "week")
    private String week;
    @DatabaseField(columnName = "isClock")
    private boolean isClock;
    @DatabaseField(columnName = "c_id")
    private String c_id;

    public Clock() {

    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public Clock(String time, String week, boolean isClock) {
        this.time = time;
        this.week = week;
        this.isClock = isClock;
    }

    public boolean isClock() {
        return isClock;
    }

    public void setClock(boolean clock) {
        isClock = clock;
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
