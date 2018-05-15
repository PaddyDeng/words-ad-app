package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class WordReportBeen {

    /**
     * code : 1
     * week : {"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"}
     * data : {"re":[{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-04-30"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-01"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-02"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-03"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-04"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-05"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-06"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-07"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-08"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-09"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-10"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-11"},{"data":{"all":"5","knowWell":"1","know":"3","dim":"1","notKnow":"0","forget":"0"},"date":"2018-05-12"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-13"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-14"}],"re1":[{"data":5,"date":"2018-05-15"},{"data":0,"date":"2018-05-16"},{"data":0,"date":"2018-05-17"},{"data":5,"date":"2018-05-18"},{"data":0,"date":"2018-05-19"},{"data":0,"date":"2018-05-20"},{"data":0,"date":"2018-05-21"},{"data":0,"date":"2018-05-22"},{"data":0,"date":"2018-05-23"},{"data":0,"date":"2018-05-24"},{"data":0,"date":"2018-05-25"},{"data":5,"date":"2018-05-26"},{"data":0,"date":"2018-05-27"},{"data":0,"date":"2018-05-28"}]}
     */

    private int code;
    private WeekBean week;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WeekBean getWeek() {
        return week;
    }

    public void setWeek(WeekBean week) {
        this.week = week;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class WeekBean {
        /**
         * all : 0
         * knowWell : 0
         * know : 0
         * dim : 0
         * notKnow : 0
         * forget : 0
         */

        private String all;
        private String knowWell;
        private String know;
        private String dim;
        private String notKnow;
        private String forget;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getKnowWell() {
            return knowWell;
        }

        public void setKnowWell(String knowWell) {
            this.knowWell = knowWell;
        }

        public String getKnow() {
            return know;
        }

        public void setKnow(String know) {
            this.know = know;
        }

        public String getDim() {
            return dim;
        }

        public void setDim(String dim) {
            this.dim = dim;
        }

        public String getNotKnow() {
            return notKnow;
        }

        public void setNotKnow(String notKnow) {
            this.notKnow = notKnow;
        }

        public String getForget() {
            return forget;
        }

        public void setForget(String forget) {
            this.forget = forget;
        }
    }

    public static class DataBeanX {
        private List<ReBean> re;
        private List<ReBean> re1;

        public List<ReBean> getRe() {
            return re;
        }

        public void setRe(List<ReBean> re) {
            this.re = re;
        }

        public List<ReBean> getRe1() {
            return re1;
        }

        public void setRe1(List<ReBean> re1) {
            this.re1 = re1;
        }

        public static class ReBean {
            /**
             * data : {"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"}
             * date : 2018-04-30
             */

            private DataBean data;
            private String date;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public static class DataBean {
                /**
                 * all : 0
                 * knowWell : 0
                 * know : 0
                 * dim : 0
                 * notKnow : 0
                 * forget : 0
                 */

                private String all;
                private String knowWell;
                private String know;
                private String dim;
                private String notKnow;
                private String forget;

                public String getAll() {
                    return all;
                }

                public void setAll(String all) {
                    this.all = all;
                }

                public String getKnowWell() {
                    return knowWell;
                }

                public void setKnowWell(String knowWell) {
                    this.knowWell = knowWell;
                }

                public String getKnow() {
                    return know;
                }

                public void setKnow(String know) {
                    this.know = know;
                }

                public String getDim() {
                    return dim;
                }

                public void setDim(String dim) {
                    this.dim = dim;
                }

                public String getNotKnow() {
                    return notKnow;
                }

                public void setNotKnow(String notKnow) {
                    this.notKnow = notKnow;
                }

                public String getForget() {
                    return forget;
                }

                public void setForget(String forget) {
                    this.forget = forget;
                }
            }
        }

    }
}
