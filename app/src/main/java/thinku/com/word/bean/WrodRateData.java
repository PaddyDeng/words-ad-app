package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/13.
 */

public class WrodRateData {
    private String name ;
    private String rate ;

    public WrodRateData(){}
    public WrodRateData(String name ,String rate){
        this.setName(name);
        this.setRate(rate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
