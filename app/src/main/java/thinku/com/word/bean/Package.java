package thinku.com.word.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class Package {
    @JSONField(name="package")
    private List<PackData> packDatas ;
    public static class PackData{
            private String id ;
            private String catId ;
            private String uid ;
            private String planDay ;
            private String planWords ;
            private String sort ;
            private String createTime ;
            private String updateTime ;
            private String total ;
            private String userWords ;
            private String name ;

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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUserWords() {
            return userWords;
        }

        public void setUserWords(String userWords) {
            this.userWords = userWords;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<PackData> getPackDatas() {
        return packDatas;
    }

    public void setPackDatas(List<PackData> packDatas) {
        this.packDatas = packDatas;
    }
}
