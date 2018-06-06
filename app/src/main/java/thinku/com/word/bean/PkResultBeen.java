package thinku.com.word.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class PkResultBeen {


    /**
     * code : 1
     * type : 0
     * data : [{"true":"1","false":7},{"true":"1","false":7}]
     * questionInfo : [{"words":"policy","answer":"n. 政策, 方针, 保险单","self":1,"peer":1},{"words":"delicate","answer":"adj. 细致优雅的,微妙的,美味的","self":0,"peer":0},{"words":"phase","answer":"n. 相, 相位,时期,局面,阶段","self":0,"peer":0},{"words":"symbol","answer":"n. 符号, 标志, 象征","self":0,"peer":0},{"words":"sponge","answer":"n. 海绵, 海绵状的东西","self":0,"peer":0},{"words":"release","answer":"n. 释放, 让渡, 发行","self":0,"peer":0},{"words":"metric","answer":"adj. 公制的,米制的,十进制的","self":0,"peer":0},{"words":"leap","answer":"n. 跳跃,剧增,急变","self":0,"peer":0}]
     */

    private int code;
    private int type;
    private List<DataBean> data;
    private List<QuestionInfoBean> questionInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<QuestionInfoBean> getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(List<QuestionInfoBean> questionInfo) {
        this.questionInfo = questionInfo;
    }

    public static class DataBean {
        @SerializedName("true")
        private String success; // FIXME check this code
        @SerializedName("false")
        private int error; // FIXME check this code

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }
    }

    public static class QuestionInfoBean {
        /**
         * words : policy
         * answer : n. 政策, 方针, 保险单
         * self : 1
         * peer : 1
         */

        private String words;
        private String answer;
        private int self;
        private int peer;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getSelf() {
            return self;
        }

        public void setSelf(int self) {
            this.self = self;
        }

        public int getPeer() {
            return peer;
        }

        public void setPeer(int peer) {
            this.peer = peer;
        }
    }
}
