package thinku.com.word.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import thinku.com.word.R;

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

    public static void downLogo(final Context context , final int bitmapId){
        Flowable.just(bitmapId).map(new Function<Integer, Bitmap>() {
            @Override
            public Bitmap apply(@NonNull Integer integer) throws Exception {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources() ,bitmapId);
                return bitmap ;
            }
        }).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(@NonNull Bitmap bitmap) throws Exception {
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                String filePath = sdCardPath + File.separator  + "logo.png";
                try {
                    File file = new File(filePath);
                    FileOutputStream os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {

                }
        }
        });

    }

}
