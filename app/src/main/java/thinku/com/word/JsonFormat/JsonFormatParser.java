package thinku.com.word.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import thinku.com.word.bean.EventPkData;
import thinku.com.word.bean.JPushData;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JsonFormatParser implements JsonDeserializer<JPushData> {
    private Class mClass;

    public JsonFormatParser(Class tClass) {
        this.mClass = tClass;
    }


    @Override
    public JPushData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JPushData jPushData = new JPushData();
        int type = jsonObject.get("type").getAsInt();
        String message = jsonObject.get("message").toString();
        switch (type){
            case 1:
                jPushData.setMessage(fromJsonObject(message , EventPkData.class));
                jPushData.setType(type);
                break;
            case 3:
                jPushData.setMessage(null);
                jPushData.setType(type);
                break;
        }
        return jPushData;
    }

    /**
     * 用来解析对象
     */
    private <T> T fromJsonObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
