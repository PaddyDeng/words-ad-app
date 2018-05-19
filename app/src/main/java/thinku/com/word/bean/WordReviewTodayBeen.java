package thinku.com.word.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/30.
 */

public class WordReviewTodayBeen {
    private String code;
    private String message;
    private String all;   // 全部
    @SerializedName("do")
    private String now;   //  现在
    private String wordsId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getWordsId() {
        return wordsId;
    }

    public void setWordsId(String wordsId) {
        this.wordsId = wordsId;
    }
}
