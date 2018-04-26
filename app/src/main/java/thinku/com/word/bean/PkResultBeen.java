package thinku.com.word.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class PkResultBeen {

    /**
     * code : 1
     * type : 1
     * data : [{"true":"5","false":3},{"true":"5","false":3}]
     */

    private int code;
    private int type;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("true")
        private String success;
        @SerializedName("false")
        private int error;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }
    }
}
