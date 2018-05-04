package thinku.com.word.db;

import android.content.Context;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import thinku.com.word.utils.Utils;

public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private final int BUFFER_SIZE = 1024;
    public static final String DB_OLD_NAME = "gmat13.db";
    public static final String DB_NAME = "word_version.db";
    private Context mContext;

    public DBManager(Context context) {
        this.mContext = context;
    }

    public boolean copyDatabase() {
        //数据库路径
        File dbFile = mContext.getDatabasePath(DB_NAME);
        //true 未复制，false需要复制
        if (!dbFile.exists()) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();

                is = mContext.getAssets().open("gmat_13.sqlite");

                fos = new FileOutputStream(dbFile);

                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }

                Utils.logh("DBService", "copy success");
            } catch (IOException e) {
                Utils.logh("DMService", "write exception");
                return false;
            } finally {
                try {
                    if (null != fos) {
                        fos.close();
                    }
                    if (null != is) {
                        is.close();
                    }

                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

}
