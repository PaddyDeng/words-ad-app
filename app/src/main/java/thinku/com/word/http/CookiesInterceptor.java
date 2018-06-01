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
    private boolean isBaidu ;
    public CookiesInterceptor(Context mContext) {
        this.mContext = mContext;
        isBaidu = false ;
    }

    public CookiesInterceptor(Context mContext , boolean isBaidu) {
        this.mContext = mContext;
        this.isBaidu = true ;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest;
        String cookie ;
        if (isBaidu){
            cookie = SharedPreferencesUtils.getCookie(mContext);
        }else{
            cookie = SharePref.getCookie(mContext);
        }
        compressedRequest = request.newBuilder()
                .header("cookie", cookie)
                .build();
        Log.e("cookie", "intercept: " + cookie );
        Response originalResponse = chain.proceed(compressedRequest);
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                if (isBaidu) {
                    SharedPreferencesUtils.setCookie(mContext ,header);
                }else{
                    SharePref.saveCookie(mContext, header);
                }
            }
        }
        return originalResponse;
    }

}
