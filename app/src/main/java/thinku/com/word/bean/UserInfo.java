package thinku.com.word.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class UserInfo implements Parcelable {
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

    public UserInfo() {}
    protected UserInfo(Parcel in) {
        code = in.readInt();
        message = in.readString();
        uid = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        nickname = in.readString();
        nowPackage = in.readString();
        studyModel = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(nickname);
        dest.writeString(nowPackage);
        dest.writeString(studyModel);
    }
}
