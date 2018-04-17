package thinku.com.word.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WordPackageBeen {
    @SerializedName("package")
    public List<WordPackage> packages;
    public static  class WordPackage {
        private String id;
        private String name;
        private String pid;
        private String image;
        private String type;
        private List<WordPackageChildeBeen> child;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<WordPackageChildeBeen> getChild() {
            return child;
        }

        public void setChild(List<WordPackageChildeBeen> data) {
            this.child = data;
        }
    }

    public List<WordPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<WordPackage> packages) {
        this.packages = packages;
    }
}
