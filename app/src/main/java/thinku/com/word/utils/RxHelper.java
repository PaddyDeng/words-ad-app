package thinku.com.word.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import thinku.com.word.http.SchedulerTransformer;

/**
 * Created by Administrator on 2018/4/8.
 */

public class RxHelper {

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

}
