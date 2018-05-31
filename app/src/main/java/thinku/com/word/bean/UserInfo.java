package thinku.com.word.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class UserInfo implements Serializable {
    private int code;
    private String message;
    private String uid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private String nowPackage ;
    private String studyModel ;

    public String getNowPackage() {
        return nowPackage;
    }

    public void setNowPackage(String nowPackage) {
        this.nowPackage = nowPackage;
    }

    public String getStudyModel() {
        return studyModel;
    }

    public void setStudyModel(String studyModel) {
        this.studyModel = studyModel;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
