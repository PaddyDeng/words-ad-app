package thinku.com.word.ui.recite.bean;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PackJsonBean {
    public String id ;
    public String planDay ;
    public String planWords ;

    public PackJsonBean(){}

    public PackJsonBean(String id , String planDay ,String planWords){
        this.id = id ;
        this.planDay = planDay ;
        this.planWords = planWords ;
    }
}
