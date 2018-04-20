package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class PkIndexBeen {

    /**
     * user : {"uid":"29456","username":"lgw29456","email":"","phone":"18782084792","createTime":"1522029771","nickname":"nickw","password":"nickw0312","image":null,"studyModel":"1","startTime":null,"nowPackage":"6","lastSign":"2018-04-12","continuousNum":"0","isReview":"2018-04-13","isNum":"5","pk":"0","pkTime":null,"pkSure":"0","win":"25","lose":"20","words":"119"}
     * rankingList : [{"nickname":"obelisk","image":null,"uid":"17053","percent":"1.0000","win":"1","lose":"0","userWords":"26"},{"nickname":"nickw","image":null,"uid":"29456","percent":"0.5435","win":"25","lose":"20","userWords":"119"},{"nickname":"以梦为马","image":null,"uid":"28244","percent":"0.5000","win":"1","lose":"1","userWords":"41"},{"nickname":"Test222","image":"/files/upload/image5abc50f205dd8.jpg","uid":"26967","percent":"0.4255","win":"20","lose":"26","userWords":"92"}]
     */

    private UserBean user;
    private List<RankingListBean> rankingList;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<RankingListBean> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingListBean> rankingList) {
        this.rankingList = rankingList;
    }

    public static class UserBean {
        /**
         * uid : 29456
         * username : lgw29456
         * email :
         * phone : 18782084792
         * createTime : 1522029771
         * nickname : nickw
         * password : nickw0312
         * image : null
         * studyModel : 1
         * startTime : null
         * nowPackage : 6
         * lastSign : 2018-04-12
         * continuousNum : 0
         * isReview : 2018-04-13
         * isNum : 5
         * pk : 0
         * pkTime : null
         * pkSure : 0
         * win : 25
         * lose : 20
         * words : 119
         */

        private String uid;
        private String username;
        private String email;
        private String phone;
        private String createTime;
        private String nickname;
        private String password;
        private Object image;
        private String studyModel;
        private Object startTime;
        private String nowPackage;
        private String lastSign;
        private String continuousNum;
        private String isReview;
        private String isNum;
        private String pk;
        private Object pkTime;
        private String pkSure;
        private String win;
        private String lose;
        private String words;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getStudyModel() {
            return studyModel;
        }

        public void setStudyModel(String studyModel) {
            this.studyModel = studyModel;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public String getNowPackage() {
            return nowPackage;
        }

        public void setNowPackage(String nowPackage) {
            this.nowPackage = nowPackage;
        }

        public String getLastSign() {
            return lastSign;
        }

        public void setLastSign(String lastSign) {
            this.lastSign = lastSign;
        }

        public String getContinuousNum() {
            return continuousNum;
        }

        public void setContinuousNum(String continuousNum) {
            this.continuousNum = continuousNum;
        }

        public String getIsReview() {
            return isReview;
        }

        public void setIsReview(String isReview) {
            this.isReview = isReview;
        }

        public String getIsNum() {
            return isNum;
        }

        public void setIsNum(String isNum) {
            this.isNum = isNum;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public Object getPkTime() {
            return pkTime;
        }

        public void setPkTime(Object pkTime) {
            this.pkTime = pkTime;
        }

        public String getPkSure() {
            return pkSure;
        }

        public void setPkSure(String pkSure) {
            this.pkSure = pkSure;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }

    public static class RankingListBean {
        /**
         * nickname : obelisk
         * image : null
         * uid : 17053
         * percent : 1.0000
         * win : 1
         * lose : 0
         * userWords : 26
         */

        private String nickname;
        private Object image;
        private String uid;
        private String percent;
        private String win;
        private String lose;
        private String userWords;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getUserWords() {
            return userWords;
        }

        public void setUserWords(String userWords) {
            this.userWords = userWords;
        }
    }
}
