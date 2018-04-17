package thinku.com.word.http;


import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import thinku.com.word.MyApplication;
import thinku.com.word.utils.SharePref;
import thinku.com.word.utils.SharedPreferencesUtils;


public class CookiesInterceptor implements Interceptor {

    private Context mContext;

    public CookiesInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest;
        compressedRequest = request.newBuilder()
                .header("cookie", SharePref.getCookie(mContext))
                .build();
        Response originalResponse = chain.proceed(compressedRequest);
        Log.e("tag", "intercept: " + SharePref.getCookie(mContext) );
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                SharePref.saveCookie(mContext, header);
            }
        }
        return originalResponse;
    }
}
