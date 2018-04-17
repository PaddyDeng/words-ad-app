package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class UserRankBeen {

    /**
     * code : 1
     * rank : [{"nickname":"Test222","image":"/files/upload/image5abc50f205dd8.jpg","uid":"26967","num":"3880"},{"nickname":"obelisk","image":null,"uid":"17053","num":"1840"},{"nickname":"以梦为马","image":null,"uid":"28244","num":"1680"},{"nickname":"nickw","image":null,"uid":"29456","num":"1640"}]
     */

    private int code;
    private List<RankBean> rank;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RankBean> getRank() {
        return rank;
    }

    public void setRank(List<RankBean> rank) {
        this.rank = rank;
    }

    public static class RankBean {
        /**
         * nickname : Test222
         * image : /files/upload/image5abc50f205dd8.jpg
         * uid : 26967
         * num : 3880
         */

        private String nickname;
        private String image;
        private String uid;
        private String num;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
