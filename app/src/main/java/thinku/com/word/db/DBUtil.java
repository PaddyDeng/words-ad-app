package thinku.com.word.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Iterator;
import java.util.List;

import thinku.com.word.MyApplication;
import thinku.com.word.callback.ICallBack;
import thinku.com.word.ui.personalCenter.update.localdb.LocalParseData;
import thinku.com.word.ui.personalCenter.update.localdb.LocalQuestionData;
import thinku.com.word.ui.personalCenter.update.localdb.LocalSerial;
import thinku.com.word.ui.personalCenter.update.localdb.LocalSerialTiku;
import thinku.com.word.ui.personalCenter.update.localdb.LocalTikuData;
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

    public void updateLocalDb(List<LocalQuestionData> questionDatas, List<LocalTikuData> tikuDatas, List<LocalParseData> parseDatas,
                              List<LocalSerialTiku> serialTikus, List<LocalSerial> serials, ICallBack<Integer> iCallBack) {
        sqLiteDatabase.beginTransaction();
        try {
            int progress = 0;
            progress = dealUpdateDb(questionDatas, iCallBack, progress);
            progress = dealUpdateDb(tikuDatas, iCallBack, progress);
            progress = dealUpdateDb(parseDatas, iCallBack, progress);
            progress = dealUpdateDb(serialTikus, iCallBack, progress);
            progress = dealUpdateDb(serials, iCallBack, progress);
//            if (isEmptyOrNull(questionDatas))
//                for (LocalQuestionData q : questionDatas) {
//                    updateOrInsertQuestion(q);
//                    iCallBack.onSuccess(++progress);
//                }
//            if (isEmptyOrNull(tikuDatas))
//                for (LocalTikuData ti : tikuDatas) {
//                    updateOrInsertTiku(ti);
//                    iCallBack.onSuccess(++progress);
//                }
//            if (isEmptyOrNull(parseDatas))
//                for (LocalParseData pa : parseDatas) {
//                    updateOrInsertParse(pa);
//                    iCallBack.onSuccess(++progress);
//                }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            iCallBack.onFail();
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    private int dealUpdateDb(List<?> list, ICallBack<Integer> iCallBack, int progress) {
        if (isEmptyOrNull(list)) {
//            for (Iterator it = list.iterator(); it.hasNext(); ) {
//                Object o = it.next();
//                if (o instanceof LocalQuestionData)
////                    updateOrInsertQuestion((LocalQuestionData) o);
//                else if (o instanceof LocalTikuData)
////                    updateOrInsertTiku((LocalTikuData) o);
//                else if (o instanceof LocalParseData)
////                    updateOrInsertParse((LocalParseData) o);
//                else if (o instanceof LocalSerialTiku)
//                    updateOrInsertSerialTiku((LocalSerialTiku) o);
//                else if (o instanceof LocalSerial)
//                    updateOrInsertSerial((LocalSerial) o);
//
//                iCallBack.onSuccess(++progress);
//            }
        }
        return progress;
    }

    private boolean isEmptyOrNull(List<?> list) {
        return list != null && !list.isEmpty();
    }


}
