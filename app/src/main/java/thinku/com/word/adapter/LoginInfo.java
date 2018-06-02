package thinku.com.word.adapter;

/**
 * Created by Administrator on 2018/6/2.
 */

public class LoginInfo {
    private String num ;
    private String pass ;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LoginInfo(String num , String pass){
        setNum(num);
        setPass(pass);
    }

    public LoginInfo(){

    }
}
