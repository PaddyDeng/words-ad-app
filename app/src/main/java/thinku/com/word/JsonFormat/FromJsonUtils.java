package thinku.com.word.JsonFormat;

import com.google.gson.GsonBuilder;

import thinku.com.word.bean.JPushData;

/**
 * Created by Administrator on 2018/4/20.
 */

public class FromJsonUtils {
    public static JPushData fromJson(String json, Class clazz) {
        return new GsonBuilder()
                .registerTypeAdapter(JPushData.class, new JsonFormatParser(clazz))
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create()
                .fromJson(json, JPushData.class);
    }
}
