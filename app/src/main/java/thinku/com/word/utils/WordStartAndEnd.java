package thinku.com.word.utils;

/**
 *  匹配单词的开始和结尾的位置
 */

public class WordStartAndEnd {
    private int start ; // 开始位置
    private int end ;   // 结束位置

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public WordStartAndEnd(){}

    public WordStartAndEnd(int start , int end){
        setStart(start);
        setEnd(end);
    }
}
