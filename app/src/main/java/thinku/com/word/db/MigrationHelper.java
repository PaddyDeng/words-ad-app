package thinku.com.word.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fire on 2017/9/12  16:37.
 */
public class MigrationHelper {

    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    //升级表
    public void upgradeTables(SQLiteDatabase db, String[] tableName, String[] columns) {
        try {
            db.beginTransaction();
            int size = tableName.length;
            String[] tempTable = new String[size];
            //1 将表A重命名，改为_temp临时表
            for (int i = 0; i < size; i++) {
                tempTable[i] = tableName[i] + "_temp";
                String sql = "alter table " + tableName[i] + " RENAME TO " + tempTable[i];
                db.execSQL(sql);
            }
            //2 创建新表
            onCreate(db, true);

            //3 将临时表的数据添加新建表中
            for (int i = 0; i < size; i++) {
                String sql = "INSERT INTO " + tableName[i] +
                        " (" + columns[i] + ") " +
                        " SELECT " + columns[i] + " FROM " + tempTable[i];
                db.execSQL(sql);

                //4 将临时表删除
                db.execSQL("drop table " + tempTable[i]);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //ifNotExists是否需要检查表是否存在
    public void onCreate(SQLiteDatabase db, boolean ifNotExists) {
        String cond = ifNotExists ? "IF NOT EXISTS " : " ";
        String wordsSql = "CREATE TABLE " + cond + PracticeTable.PK_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PracticeTable.ANSWER + " text,"
                + PracticeTable.PHONETIC_UK + " text,"
                + PracticeTable.SELECT + " text,"
                + PracticeTable.UK_AUDIO + " text,"
                + PracticeTable.WORD + " text ,"
                + PracticeTable.WORDSID + "text"
                + ");";
        db.execSQL(wordsSql);
    }

}
