package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/20.
 */

public class EventPkData {
    /**
     * user2 : {"uid":"29456","image":null,"lose":"20","win":"25","words":"120","nickname":"nickw"}
     * user1 : {"uid":"26967","image":"/files/upload/image5abc50f205dd8.jpg","lose":"26","win":"20","words":"93","nickname":"Test222"}
     */

    private UserBean user2;
    private UserBean user1;

    public UserBean getUser2() {
        return user2;
    }

    public void setUser2(UserBean user2) {
        this.user2 = user2;
    }

    public UserBean getUser1() {
        return user1;
    }

    public void setUser1(UserBean user1) {
        this.user1 = user1;
    }

    public static class UserBean {
        /**
         * uid : 29456
         * image : null
         * lose : 20
         * win : 25
         * words : 120
         * nickname : nickw
         */

        private String uid;
        private String image;
        private String lose;
        private String win;
        private String words;
        private String nickname;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
