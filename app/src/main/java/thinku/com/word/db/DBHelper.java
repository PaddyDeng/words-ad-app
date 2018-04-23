package thinku.com.word.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DBHELPER_NAME = "word.db";

    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DBHELPER_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MigrationHelper.getInstance().onCreate(db, false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i <newVersion ; i++) {
            switch (i){
                case 1:
                    MigrationHelper.getInstance().onCreate(db, true);//如果表不存在，就创建表
                    boolean b = checkColumnExist1(db, PracticeTable.PK_NAME, PracticeTable.WORDSID);//判断字段是否存在
                    if(!b)db.execSQL("ALTER TABLE " + PracticeTable.PK_NAME + " ADD " + PracticeTable.WORDSID + " int");//升级
                    break;
            }
        }
    }




    /**
     * 检查某表列是否存在
     * @param db
     * @param tableName 表名
     * @param columnName 列名
     * @return
     */
    private boolean checkColumnExist1(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0"
                    , null );
            result = (cursor != null && cursor.getColumnIndex(columnName) != -1) ;
        }catch (Exception e){

        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }
        return result ;
    }
}
