package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PkingData {

            /**
             * user2 : {"num":"6","uid":26967,"accuracy":0}
             * user1 : {"num":"0","uid":29456,"accuracy":0}
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
                 * num : 6
                 * uid : 26967
                 * accuracy : 0
                 */

                private String num;
                private int uid;
                private int accuracy;

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public int getAccuracy() {
                    return accuracy;
                }

                public void setAccuracy(int accuracy) {
                    this.accuracy = accuracy;
                }
            }
        }