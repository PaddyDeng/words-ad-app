package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class SingBeen {
    private int code ;
    private int type ;  //  是否打卡     0 为否  1 为打卡
    private List<SignData> data ;   //  打卡时间
    private String integral ;     //  雷豆数量
    private String num ;  //打卡多少天

    public SingBeen(){}

    public static class SignData{
        private String id ;
        private String uid ;
        private String createDay ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreateDay() {
            return createDay;
        }

        public void setCreateDay(String createDay) {
            this.createDay = createDay;
        }
    }
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

    public List<SignData> getData() {
        return data;
    }

    public void setData(List<SignData> data) {
        this.data = data;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
