package thinku.com.word.thrlib;

import android.content.Context;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

public class OCRProxy {
    private OCRProxy() {
    }

    public static void initToken(Context context) {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
//                String token = result.getAccessToken();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
//                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, context, "fBmjCWalVR0V8u628ZU8du3V", "57qubjY9BoEAA50p3cFLMWZfowlwduL8");
    }

    public static void orcRelease() {
        // 释放内存资源
        OCR.getInstance().release();
    }

}
