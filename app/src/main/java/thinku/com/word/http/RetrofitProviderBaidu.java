package thinku.com.word.http;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import thinku.com.word.BuildConfig;
import thinku.com.word.MyApplication;
import thinku.com.word.ui.report.bean.QuestionBean;

//链接
public class RetrofitProviderBaidu {

    public static String BAIDU = "https://aip.baidubce.com/";
    private static SparseArray<Retrofit> sparseArray = new SparseArray<>();

    private static SparseArray<Retrofit> sparseArrayWord = new SparseArray<>();
    private Context context ;
    private RetrofitProviderBaidu() {
    }

    public static Retrofit getInstance(@HostType.HostTypeChecker int hostType ) {
        Retrofit instance = sparseArray.get(hostType);
        if (instance == null) {
            synchronized (RetrofitProviderBaidu.class) {
                if (instance == null) {
                    instance = SingletonHolder.create(hostType);
                    sparseArray.put(hostType, instance);
                }
            }
        }
        return instance;
    }

    private static class SingletonHolder {

        private static Retrofit create(@HostType.HostTypeChecker int type) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            String url = null;
            switch (type) {
                case HostType.BAIDU_URL_HOST :
                    url = BAIDU ;
            }
            builder.networkInterceptors().add(new CookiesInterceptor(MyApplication.getInstance() , true));

            return new Retrofit.Builder()
                    .baseUrl(url)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }

        private static Retrofit createWord(@HostType.HostTypeChecker int type) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            String url = null;
            switch (type) {

            }
            builder.networkInterceptors().add(new CookiesInterceptor(MyApplication.getInstance()));
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(QuestionBean.class ,new ReciteWordJsonAdapter())
                    .create();
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


        }
    }


}
