package thinku.com.word.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TrackBeen {

    /**
     * know : 79
     * notKnow : 40
     * insistDay : 7
     * new : 17
     * review : 2
     * package : [{"id":"90","catId":"6","uid":"29456","name":"GMAT-OG逻辑+语法、生词","all":"560","console":119},{"id":"97","catId":"9","uid":"29456","name":"GMAT-词汇精选","all":"2270","console":0},{"id":"98","catId":"20","uid":"29456","name":"GMAT-航天艺术政治其他","all":"65","console":0},{"id":"101","catId":"7","uid":"29456","name":"GMAT-OG数学教材、生词","all":"237","console":0},{"id":"111","catId":"16","uid":"29456","name":"GAMT-物理+农业","all":"67","console":0},{"id":"114","catId":"24","uid":"29456","name":"GMAT-社会事务4","all":"70","console":0}]
     * userAllWords : 119
     * data : {"num":0,"rank":0}
     * rank : [{"nickname":"Test222","image":"/files/upload/image5abc50f205dd8.jpg","uid":"26967","num":"3880"},{"nickname":"obelisk","image":null,"uid":"17053","num":"1840"},{"nickname":"以梦为马","image":null,"uid":"28244","num":"1680"}]
     */

    private String know;
    private String notKnow;
    private int insistDay;
    @SerializedName("new")
    private int newX;
    private int review;
    private String userAllWords;
    private DataBean data;
    @SerializedName("package")
    private List<PackageBean> packageX;
    private List<RankBean> rank;

    public String getKnow() {
        return know;
    }

    public void setKnow(String know) {
        this.know = know;
    }

    public String getNotKnow() {
        return notKnow;
    }

    public void setNotKnow(String notKnow) {
        this.notKnow = notKnow;
    }

    public int getInsistDay() {
        return insistDay;
    }

    public void setInsistDay(int insistDay) {
        this.insistDay = insistDay;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getUserAllWords() {
        return userAllWords;
    }

    public void setUserAllWords(String userAllWords) {
        this.userAllWords = userAllWords;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<PackageBean> getPackageX() {
        return packageX;
    }

    public void setPackageX(List<PackageBean> packageX) {
        this.packageX = packageX;
    }

    public List<RankBean> getRank() {
        return rank;
    }

    public void setRank(List<RankBean> rank) {
        this.rank = rank;
    }

    public static class DataBean {
        /**
         * num : 0
         * rank : 0
         */

        private int num;
        private int rank;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class PackageBean {
        /**
         * id : 90
         * catId : 6
         * uid : 29456
         * name : GMAT-OG逻辑+语法、生词
         * all : 560
         * console : 119
         */

        private String id;
        private String catId;
        private String name;
        private String all;
        private int console;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public int getConsole() {
            return console;
        }

        public void setConsole(int console) {
            this.console = console;
        }
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
