package thinku.com.word.ui.report.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22/022.
 */

public class ReviewBean {

    /**
     * code : 1
     * message : 复习
     * words : ["3548","3547","3546","3545"]
     */

    private int code;
    private String message;
    private List<String> wordsId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getWords() {
        return wordsId;
    }

    public void setWords(List<String> words) {
        this.wordsId = words;
    }
}
