package thinku.com.word.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import thinku.com.word.db.bean.Clock;

/**
 * Created by Administrator on 2018/5/4.
 */

public class ClockDao {
    private Context context;
    private Dao<Clock, Integer> clockDaoOpo;
    private DatabaseHelper helper;

    public ClockDao(Context context) {
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            clockDaoOpo = helper.getDao(Clock.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  增加用户
     */

    public int addClock(Clock clock){
        try{
            clockDaoOpo.createOrUpdate(clock);
            return (int) clockDaoOpo.countOf();
        } catch (SQLException e) {
            Log.e("Ba", "addClock: " + e.getMessage() );
            e.printStackTrace();
            return -1 ;
        }

    }

    /**
     * 删除用户
     * @param clock
     */
    public int  delete(Clock clock){
        try {
            clockDaoOpo.delete(clock);
            return (int) clockDaoOpo.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return - 1 ;
        }
    }

    /**
     * 删除全部
     */
    public int deleteById(int id){
        try {
            clockDaoOpo.deleteById(id);
            return (int) clockDaoOpo.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1 ;
        }
    }

    /**
     * 更新闹钟
     * @param clock
     */
    public int  updateClock(Clock clock){
        try {
            clockDaoOpo.createOrUpdate(clock);
            return (int) clockDaoOpo.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1 ;
        }
    }

    public List<Clock> getAllClock(){
        try {
            return clockDaoOpo.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public void close(){
        helper.close();
    }

}
