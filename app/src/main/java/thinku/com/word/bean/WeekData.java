package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class WeekData {
    private String name ;
    private float value ;

    public WeekData(){}
    public WeekData(String name ,float Value){
        this.setName(name);
        this.setValue(Value);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
