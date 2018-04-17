package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ReciteWordParent {

    private String name;
    private List<RecitWordBeen.LowSentenceBean> sentenceList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecitWordBeen.LowSentenceBean> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<RecitWordBeen.LowSentenceBean> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
