package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ReciteWordParent {

    private String name;
    private List<RecitWordBeen.Sentence> sentenceList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecitWordBeen.Sentence> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<RecitWordBeen.Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
