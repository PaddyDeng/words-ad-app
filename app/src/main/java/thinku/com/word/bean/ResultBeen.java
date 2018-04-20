package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/9.
 * 后台返回数据
 */

public class ResultBeen<T> {

    private int code ;
    private String message ;
    private T data ;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
