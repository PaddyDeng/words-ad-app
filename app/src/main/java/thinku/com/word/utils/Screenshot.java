package thinku.com.word.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 截屏功能
 */

public class Screenshot {
    public static boolean screen(Activity context, String filePath) {
        boolean success = false;
        View dView = context.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                success = true;
            } catch (Exception e) {
                success = false;
                Toast.makeText(context, "截屏失败", Toast.LENGTH_SHORT).show();
            }
        }
        return success;
    }


}
