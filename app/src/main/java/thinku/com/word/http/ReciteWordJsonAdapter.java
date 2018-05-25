package thinku.com.word.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.ui.report.bean.QuestionBean;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ReciteWordJsonAdapter extends TypeAdapter<QuestionBean> {
    @Override
    public void write(JsonWriter out, QuestionBean value) throws IOException {

    }

    @Override
    public QuestionBean read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.BOOLEAN){
            in.nextBoolean();
            return null;
        }
        QuestionBean recitWordBeen = new Gson().fromJson(in ,QuestionBean.class);
        return recitWordBeen ;
    }
}
