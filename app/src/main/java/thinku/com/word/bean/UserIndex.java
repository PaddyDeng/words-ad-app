package thinku.com.word.bean;

/**
 * Created by Administrator on 2018/3/28.
 * 用户正常数据
 */
public class UserIndex {
    private String packageName ;  // 词包名字
    private String allWords ;     // 当前词包所有单词
    private String insistDay ;   //  坚持天数
    private String userPackageWords;  // 当前词包已背单词
    private String surplusDay ;    // 剩余天数
    private String userAllWords ;  // 累计背单词书
    private String toDayWords ;   // 今天已背单词
    private String userReviewWords ;  // 已复习单词
    private String userNeedReviewWords ;  // 需要复习单词
    private UserPackage userPackage ;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAllWords() {
        return allWords;
    }

    public void setAllWords(String allWords) {
        this.allWords = allWords;
    }

    public String getInsistDay() {
        return insistDay;
    }

    public void setInsistDay(String insistDay) {
        this.insistDay = insistDay;
    }

    public String getUserPackageWords() {
        return userPackageWords;
    }

    public void setUserPackageWords(String userPackageWords) {
        this.userPackageWords = userPackageWords;
    }

    public String getSurplusDay() {
        return surplusDay;
    }

    public void setSurplusDay(String surplusDay) {
        this.surplusDay = surplusDay;
    }

    public String getUserAllWords() {
        return userAllWords;
    }

    public void setUserAllWords(String userAllWords) {
        this.userAllWords = userAllWords;
    }

    public String getToDayWords() {
        return toDayWords;
    }

    public void setToDayWords(String toDayWords) {
        this.toDayWords = toDayWords;
    }

    public String getUserReviewWords() {
        return userReviewWords;
    }

    public void setUserReviewWords(String userReviewWords) {
        this.userReviewWords = userReviewWords;
    }

    public String getUserNeedReviewWords() {
        return userNeedReviewWords;
    }

    public void setUserNeedReviewWords(String userNeedReviewWords) {
        this.userNeedReviewWords = userNeedReviewWords;
    }

    public UserPackage getUserPackage() {
        return userPackage;
    }

    public void setUserPackage(UserPackage userPackage) {
        this.userPackage = userPackage;
    }

    public static class UserPackage{
        private String id ;
        private String catId ;
        private String uid ;
        private String planDay ;
        private String planWords ;
        private String sort ;
        private String createTime ;
        private String updateTime ;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPlanDay() {
            return planDay;
        }

        public void setPlanDay(String planDay) {
            this.planDay = planDay;
        }

        public String getPlanWords() {
            return planWords;
        }

        public void setPlanWords(String planWords) {
            this.planWords = planWords;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
