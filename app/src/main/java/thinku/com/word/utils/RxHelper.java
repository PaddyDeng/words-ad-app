package thinku.com.word.utils;

import android.util.Log;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import thinku.com.word.db.bean.Clock;
import thinku.com.word.http.SchedulerTransformer;

/**
 * Created by Administrator on 2018/4/8.
 */

public class RxHelper {

    private static void log(String msg) {
        Log.i(RxHelper.class.getSimpleName(), msg);
    }
    static {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof InterruptedException) {
                    log("Thread interrupted");
                } else if (throwable instanceof InterruptedIOException) {
                    log("Io interrupted");
                } else if (throwable instanceof SocketException) {
                    log("Socket error");
                } else {
                    throwable.printStackTrace();
                }
            }
        });
    }
    public static Observable<Integer> countDown(final int time) {
        return Observable
                .interval(1, TimeUnit.SECONDS)
                .take(time + 1)
                .flatMap(new Function<Long, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Long aLong) throws Exception {
                        return Observable.just(time - aLong.intValue());
                    }
                })
                .compose(new SchedulerTransformer<Integer>());
    }

    public static Observable<Integer> delay(final int time) {
        return Observable
                .just(time)
                .delay(time, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Integer>());
    }


}
