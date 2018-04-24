package thinku.com.word.adapter.been;

/**
 * Created by Administrator on 2018/4/24.
 */

public class PKSelctBeen {
    private String select ;
    private boolean isAnswer ;
    private boolean isChose ;

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }
}
