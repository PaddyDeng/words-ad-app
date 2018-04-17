package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/4/13.
 */

public class WordResultBeen {

    /**
     * code : 1
     * result : {"id":"4","uid":"29456","createTime":"1523606762","level":"大学四级","num":"1640","four":"29","six":"0","ielts":"0","toefl":"0","gmat":"0","gre":"0","know":"21","notKnow":"0","bit":0}
     */

    private int code;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 4
         * uid : 29456
         * createTime : 1523606762
         * level : 大学四级
         * num : 1640
         * four : 29
         * six : 0
         * ielts : 0
         * toefl : 0
         * gmat : 0
         * gre : 0
         * know : 21
         * notKnow : 0
         * bit : 0
         */

        private String id;
        private String uid;
        private String level;
        private String num;
        private String four;
        private String six;
        private String ielts;
        private String toefl;
        private String gmat;
        private String gre;
        private String know;
        private String notKnow;
        private int bit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getFour() {
            return four;
        }

        public void setFour(String four) {
            this.four = four;
        }

        public String getSix() {
            return six;
        }

        public void setSix(String six) {
            this.six = six;
        }

        public String getIelts() {
            return ielts;
        }

        public void setIelts(String ielts) {
            this.ielts = ielts;
        }

        public String getToefl() {
            return toefl;
        }

        public void setToefl(String toefl) {
            this.toefl = toefl;
        }

        public String getGmat() {
            return gmat;
        }

        public void setGmat(String gmat) {
            this.gmat = gmat;
        }

        public String getGre() {
            return gre;
        }

        public void setGre(String gre) {
            this.gre = gre;
        }

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

        public int getBit() {
            return bit;
        }

        public void setBit(int bit) {
            this.bit = bit;
        }
    }
}
