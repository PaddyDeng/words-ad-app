package thinku.com.word.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import thinku.com.word.MyApplication;
//  pk  word

public class DBUtil {
    private static SQLiteDatabase sqLiteDatabase;
    private static DBUtil instance = null;

    public static DBUtil getInstance() {
        if (instance == null) {
            synchronized (DBUtil.class) {
                if (instance == null) {
                    instance = new DBUtil(MyApplication.newInstance());
                }
            }
        }
        return instance;
    }

    private DBUtil(Context mContext) {
        sqLiteDatabase = SQLiteDatabase.openDatabase(mContext.getDatabasePath(DBManager.DB_NAME).getPath(), null, mContext.MODE_PRIVATE);
    }



}
