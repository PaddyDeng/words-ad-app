package thinku.com.word.ui.report.bean;

/**
 * Created by Administrator on 2018/5/30/030.
 */

public class WordEva {
    private String content ;
    private boolean isAnswer ;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public WordEva(String contnet ,boolean isAnswer){
        this.setContent(contnet);
        this.setAnswer(isAnswer);
    }
}
