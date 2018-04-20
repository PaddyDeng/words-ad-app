package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JPushData<T> {
    private int type ;
    private T message ;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
