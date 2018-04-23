package thinku.com.word.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;

import thinku.com.word.MyApplication;
import thinku.com.word.bean.EventPkListData;

public class PracticeManager {
    private static DBHelper mDBHelper = null;
    private static PracticeManager instance;
    private static SQLiteDatabase writableDatabase;
    private static SQLiteDatabase readableDatabase;

    public static PracticeManager getInstance() {
        if (null == instance) {
            synchronized (PracticeManager.class) {
                if (null == instance) {
                    instance = new PracticeManager();
                }
            }
        }
        return instance;
    }

    private PracticeManager() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(MyApplication.newInstance());
            writableDatabase = mDBHelper.getWritableDatabase();
            readableDatabase = mDBHelper.getReadableDatabase();
        }
    }

    public EventPkListData.WordsBean queryWordsBeen(String wordsId) {
        EventPkListData.WordsBean wordsBean = queryWordsBeen(wordsId);
        //存储或更新数据库
        insertOrUpdateData(wordsBean, wordsId);
        return wordsBean;
    }

    public EventPkListData.WordsBean queryRandomNumber(String wordsId) {  // 查询数据
        EventPkListData.WordsBean wordsBean = new EventPkListData.WordsBean();
        Cursor cursor = readableDatabase.query(PracticeTable.PK_NAME, null, PracticeTable.WORDSID + "=?",
                new String[]{wordsId}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                wordsBean.setAnswer(cursor.getString(cursor.getColumnIndex(PracticeTable.ANSWER)));
                wordsBean.setPhonetic_uk(cursor.getString(cursor.getColumnIndex(PracticeTable.PHONETIC_UK)));
                wordsBean.setSelect(cursor.getString(cursor.getColumnIndex(PracticeTable.SELECT)));
                wordsBean.setUk_audio(cursor.getString(cursor.getColumnIndex(PracticeTable.UK_AUDIO)));
                wordsBean.setWord(cursor.getString(cursor.getColumnIndex(PracticeTable.WORD)));
                wordsBean.setWordsId(cursor.getString(cursor.getColumnIndex(PracticeTable.WORDSID)));
            }
            cursor.close();
        }
        return wordsBean;
    }


    public void insertOrUpdateData(EventPkListData.WordsBean  wordsBean, String wordsId) {
        ContentValues values = new ContentValues();
        values.put(PracticeTable.ANSWER, wordsBean.getAnswer());
        values.put(PracticeTable.PHONETIC_UK, wordsBean.getPhonetic_uk());
        values.put(PracticeTable.SELECT, wordsBean.getSelect());
        values.put(PracticeTable.UK_AUDIO, wordsBean.getUk_audio());
        values.put(PracticeTable.WORD, wordsBean.getWord());
        values.put(PracticeTable.WORDSID ,wordsBean.getWordsId());
        int update = writableDatabase.update(PracticeTable.PK_NAME, values, PracticeTable.WORDSID + "=?",
                new String[]{wordsId});
        if (update != 1) {
            writableDatabase.insert(PracticeTable.PK_NAME, null, values);
        }
    }

}
