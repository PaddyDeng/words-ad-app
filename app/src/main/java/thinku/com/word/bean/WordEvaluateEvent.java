package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/3/29.
 */

public class WordEvaluateEvent {
    private String wordId ;
    private String status ;
    private String tag ;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public WordEvaluateEvent(){}
    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WordEvaluateEvent(String wordId ,String status ,String tag){
        this.setStatus(status);
        this.setWordId(wordId);
        this.setTag(tag);
    }
}
