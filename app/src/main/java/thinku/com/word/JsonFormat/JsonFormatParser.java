package thinku.com.word.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import thinku.com.word.bean.EventPkData;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.JPushData;
import thinku.com.word.bean.PkingData;

import static thinku.com.word.utils.C.PKING;
import static thinku.com.word.utils.C.PK_MATCH_CANCLE;
import static thinku.com.word.utils.C.PK_MATCH_SUCCESS;
import static thinku.com.word.utils.C.PK_READY_SUCCESS;

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
            case PK_MATCH_SUCCESS:
                jPushData.setMessage(fromJsonObject(message , EventPkData.class));
                jPushData.setType(type);
                break;
            case PK_MATCH_CANCLE:
                jPushData.setMessage(null);
                jPushData.setType(type);
                break;
            case PK_READY_SUCCESS:
                if (jsonObject.get("message").isJsonObject()&& !jsonObject.get("message").isJsonNull()){
                    jPushData.setMessage(fromJsonObject(message , EventPkListData.class));
                    jPushData.setType(type);
                }
                break;
            case PKING:
                if (jsonObject.get("message").isJsonObject()&& !jsonObject.get("message").isJsonNull()){
                    jPushData.setMessage(fromJsonObject(message , PkingData.class));
                    jPushData.setType(type);
                }
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

    /**
     * 用来解析集合
     */
    private <T> ArrayList<T> fromJsonArray(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
