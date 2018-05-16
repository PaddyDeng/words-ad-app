package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class WordReportBeen {

    /**
     * code : 1
     * week : {"all":"52","knowWell":"1","know":"24","dim":"1","notKnow":"26","forget":"0"}
     * data : {"re":[{"data":{"all":"40","knowWell":"1","know":"9","dim":"16","notKnow":"14","forget":"0"},"date":"2018-05-02"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-03"},{"data":{"all":"19","knowWell":"0","know":"14","dim":"0","notKnow":"5","forget":"0"},"date":"2018-05-04"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-05"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-06"},{"data":{"all":"8","knowWell":"0","know":"7","dim":"0","notKnow":"1","forget":"0"},"date":"2018-05-07"},{"data":{"all":"38","knowWell":"0","know":"36","dim":"0","notKnow":"2","forget":"0"},"date":"2018-05-08"},{"data":{"all":"10","knowWell":"0","know":"9","dim":"0","notKnow":"1","forget":"0"},"date":"2018-05-09"},{"data":{"all":"18","knowWell":"0","know":"10","dim":"0","notKnow":"8","forget":"0"},"date":"2018-05-10"},{"data":{"all":"73","knowWell":"6","know":"27","dim":"14","notKnow":"26","forget":"0"},"date":"2018-05-11"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-12"},{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-13"},{"data":{"all":"6","knowWell":"0","know":"5","dim":"0","notKnow":"1","forget":"0"},"date":"2018-05-14"},{"data":{"all":"2","knowWell":"1","know":"1","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-15"},{"data":{"all":"1","knowWell":"0","know":"1","dim":"0","notKnow":"0","forget":"0"},"date":"2018-05-16"}],"re1":[{"data":1,"date":"2018-05-17"},{"data":2,"date":"2018-05-18"},{"data":1,"date":"2018-05-19"},{"data":0,"date":"2018-05-20"},{"data":2,"date":"2018-05-21"},{"data":1,"date":"2018-05-22"},{"data":0,"date":"2018-05-23"},{"data":0,"date":"2018-05-24"},{"data":0,"date":"2018-05-25"},{"data":0,"date":"2018-05-26"},{"data":0,"date":"2018-05-27"},{"data":0,"date":"2018-05-28"},{"data":2,"date":"2018-05-29"},{"data":1,"date":"2018-05-30"}]}
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
         * all : 52
         * knowWell : 1
         * know : 24
         * dim : 1
         * notKnow : 26
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
        private List<Re1Bean> re1;

        public List<ReBean> getRe() {
            return re;
        }

        public void setRe(List<ReBean> re) {
            this.re = re;
        }

        public List<Re1Bean> getRe1() {
            return re1;
        }

        public void setRe1(List<Re1Bean> re1) {
            this.re1 = re1;
        }

        public static class ReBean {
            /**
             * data : {"all":"40","knowWell":"1","know":"9","dim":"16","notKnow":"14","forget":"0"}
             * date : 2018-05-02
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
                 * all : 40
                 * knowWell : 1
                 * know : 9
                 * dim : 16
                 * notKnow : 14
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

        public static class Re1Bean {
            /**
             * data : 1
             * date : 2018-05-17
             */

            private int data;
            private String date;

            public int getData() {
                return data;
            }

            public void setData(int data) {
                this.data = data;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
