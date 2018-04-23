package thinku.com.word.db;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "word.db";
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

                is = mContext.getAssets().open("word_11.sqlite");

                fos = new FileOutputStream(dbFile);

                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }

                Log.i("DMService" , "write success");
            } catch (IOException e) {
                Log.i("DMService", "write exception");
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
