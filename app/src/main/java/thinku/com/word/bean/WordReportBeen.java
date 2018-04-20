package thinku.com.word.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/17.
 */

public class WordReportBeen {

    /**
     * code : 1
     * week : {"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"}
     * data : {"1":{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.01","end":"04.01"},"2":{"data":{"all":"34","knowWell":"1","know":"3","dim":"0","notKnow":"27","forget":"0"},"start":"04.02","end":"04.08"},"3":{"data":{"all":"16","knowWell":"1","know":"5","dim":"0","notKnow":"10","forget":"0"},"start":"04.09","end":"04.15"},"4":{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.16","end":"04.22"},"5":{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.23","end":"04.29"},"6":{"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.30","end":"04.30"}}
     */

    private int code;
    private WeekBean week;
    private DataBeanMonth data;

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

    public DataBeanMonth getData() {
        return data;
    }

    public void setData(DataBeanMonth data) {
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

    public static class DataBeanMonth {
        /**
         * 1 : {"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.01","end":"04.01"}
         * 2 : {"data":{"all":"34","knowWell":"1","know":"3","dim":"0","notKnow":"27","forget":"0"},"start":"04.02","end":"04.08"}
         * 3 : {"data":{"all":"16","knowWell":"1","know":"5","dim":"0","notKnow":"10","forget":"0"},"start":"04.09","end":"04.15"}
         * 4 : {"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.16","end":"04.22"}
         * 5 : {"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.23","end":"04.29"}
         * 6 : {"data":{"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"},"start":"04.30","end":"04.30"}
         */

        @SerializedName("1")
        private MonthWeekBean _$1;
        @SerializedName("2")
        private MonthWeekBean _$2;
        @SerializedName("3")
        private MonthWeekBean _$3;
        @SerializedName("4")
        private MonthWeekBean _$4;
        @SerializedName("5")
        private MonthWeekBean _$5;
        @SerializedName("6")
        private MonthWeekBean _$6;

        public MonthWeekBean get_$1() {
            return _$1;
        }

        public void set_$1(MonthWeekBean _$1) {
            this._$1 = _$1;
        }

        public MonthWeekBean get_$2() {
            return _$2;
        }

        public void set_$2(MonthWeekBean _$2) {
            this._$2 = _$2;
        }

        public MonthWeekBean get_$3() {
            return _$3;
        }

        public void set_$3(MonthWeekBean _$3) {
            this._$3 = _$3;
        }

        public MonthWeekBean get_$4() {
            return _$4;
        }

        public void set_$4(MonthWeekBean _$4) {
            this._$4 = _$4;
        }

        public MonthWeekBean get_$5() {
            return _$5;
        }

        public void set_$5(MonthWeekBean _$5) {
            this._$5 = _$5;
        }

        public MonthWeekBean get_$6() {
            return _$6;
        }

        public void set_$6(MonthWeekBean _$6) {
            this._$6 = _$6;
        }

        public static class MonthWeekBean {
            /**
             * data : {"all":"0","knowWell":"0","know":"0","dim":"0","notKnow":"0","forget":"0"}
             * start : 04.01
             * end : 04.01
             */

            private DataBean data;
            private String start;
            private String end;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
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
