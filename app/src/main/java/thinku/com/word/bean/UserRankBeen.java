package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class UserRankBeen {


    /**
     * code : 1
     * rank : [{"nickname":"我要背单词","image":"/files/upload/image5af3d8ab88ff6.jpg","uid":"31869","num":"4480"},{"nickname":"牛小刀","image":"/files/upload/image5af2547b89513.jpg","uid":"31619","num":"3640"},{"nickname":"obelisk","image":"/files/upload/image5af269ef60210.jpg","uid":"17053","num":"1840"},{"nickname":"以梦为马","image":"/files/upload/image5aded8caa9551.jpg","uid":"28244","num":"1680"},{"nickname":"恩蜗居拉黑","image":"/files/upload/image5abc50f205dd8.jpg","uid":"26967","num":"1440"},{"nickname":"fasfasas","image":"/files/upload/image5afd602ef2ea2.jpg","uid":"29456","num":"240"}]
     * data : {"num":"240","rank":6}
     */

    private int code;
    private DataBean data;
    private List<RankBean> rank;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<RankBean> getRank() {
        return rank;
    }

    public void setRank(List<RankBean> rank) {
        this.rank = rank;
    }

    public static class DataBean {
        /**
         * num : 240
         * rank : 6
         */

        private String num;
        private int rank;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class RankBean {
        /**
         * nickname : 我要背单词
         * image : /files/upload/image5af3d8ab88ff6.jpg
         * uid : 31869
         * num : 4480
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
