package thinku.com.word.ui.periphery.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class RoundBean {
    private List<RecentClassBean> recentClass;
    private List<LivePreviewBean> livePreview;
    private List<ChoicenessBean> choiceness;
    @SerializedName("case")
    private List<CaseBean> caseX;

    public List<RecentClassBean> getRecentClass() {
        return recentClass;
    }

    public void setRecentClass(List<RecentClassBean> recentClass) {
        this.recentClass = recentClass;
    }

    public List<LivePreviewBean> getLivePreview() {
        return livePreview;
    }

    public void setLivePreview(List<LivePreviewBean> livePreview) {
        this.livePreview = livePreview;
    }

    public List<ChoicenessBean> getChoiceness() {
        return choiceness;
    }

    public void setChoiceness(List<ChoicenessBean> choiceness) {
        this.choiceness = choiceness;
    }

    public List<CaseBean> getCaseX() {
        return caseX;
    }

    public void setCaseX(List<CaseBean> caseX) {
        this.caseX = caseX;
    }

    public static class RecentClassBean implements Parcelable {
        /**
         * id : 3356
         * pid : 0
         * catId : 218
         * name : 雷哥GRE阅读长难句5天训练营
         * title : 雷哥GRE阅读长难句5天训练营
         * image : /files/attach/images/20180424/1524559739383172.png
         * createTime : 2018-04-24 16:51:34
         * sort : 3356
         * userId : 1
         * viewCount : 55
         * show : 1
         * catName : 公开课
         * duration :
         * cnName : 2018-05-02 16:50:20
         * sentenceNumber : <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">雷哥GRE名师sharron精选长难句；</section><p><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">和众多杀G战友一起互助互帮，共同进步；</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">在贴心的小G君的督促下，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">每日打卡完成任务，保持学习动力；</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">5天的长难句集中训练，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">阅读习惯彻底优化，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">掌握长难句阅读技巧，</section></p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">阅读速度、精度突飞猛进！</p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">真正的提速提分提能力！<br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"/></p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">助你轻松突破GRE330+！</p>
         * numbering : 39
         */

        private String name;
        private String title;
        private String image;
        private String cnName;
        private String sentenceNumber;
        private String viewCount ;


        public RecentClassBean(){}

        protected RecentClassBean(Parcel in) {
            name = in.readString();
            title = in.readString();
            image = in.readString();
            cnName = in.readString();
            sentenceNumber = in.readString();
            viewCount = in.readString();
        }


        public static final Creator<RecentClassBean> CREATOR = new Creator<RecentClassBean>() {
            @Override
            public RecentClassBean createFromParcel(Parcel in) {
                return new RecentClassBean(in);
            }

            @Override
            public RecentClassBean[] newArray(int size) {
                return new RecentClassBean[size];
            }
        };


        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getSentenceNumber() {
            return sentenceNumber;
        }

        public void setSentenceNumber(String sentenceNumber) {
            this.sentenceNumber = sentenceNumber;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(title);
            dest.writeString(image);
            dest.writeString(cnName);
            dest.writeString(sentenceNumber);
            dest.writeString(viewCount);

        }
    }

    public static class LivePreviewBean {
        /**
         * date : 2018-05
         * data : [{"id":"3356","pid":"0","catId":"218","name":"雷哥GRE阅读长难句5天训练营","title":"雷哥GRE阅读长难句5天训练营","image":"/files/attach/images/20180424/1524559739383172.png","createTime":"2018-04-24 16:51:34","sort":"3356","userId":"1","viewCount":"55","show":"1","catName":"公开课","duration":"","alternatives":"25个典型长难句机经练习，\r\n每日定量练习长难句，\r\n配合直播精准解析，\r\n5天掌握做题技巧，\r\n尽在雷哥GRE\r\n阅读经典长难句训练营！\r\n这里有什么\r\n\r\n为期5天，每天5个精选长难句，\r\n对句子结构、关键点提取、\r\n名词指代、逻辑关系等进行全方位训练；\r\n先做题后解析，有备考战友互相讨论，\r\n有贴心小G君严格监督，\r\n有专业老师答疑解惑。\r\n\r\n能给你什么\r\n\r\n雷哥GRE名师sharron精选长难句；\r\n和众多杀G战友一起互助互帮，共同进步；\r\n在贴心的小G君的督促下，\r\n每日打卡完成任务，保持学习动力；\r\n5天的长难句集中训练，\r\n阅读习惯彻底优化，\r\n掌握长难句阅读技巧，\r\n阅读速度、精度突飞猛进！\r\n真正的提速提分提能力！\r\n助你轻松突破GRE330+！\r\n\r\n什么时候开始\r\n\r\n2018年5月2日 - 5月6日","article":"/files/attach/file/20180424/1524559764697839.png","listeningFile":"sharron","cnName":"2018-05-02 16:50:20","sentenceNumber":"<section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">雷哥GRE名师sharron精选长难句；<\/section><p><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">和众多杀G战友一起互助互帮，共同进步；<\/section><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">在贴心的小G君的督促下，<\/section><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">每日打卡完成任务，保持学习动力；<\/section><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">5天的长难句集中训练，<\/section><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">阅读习惯彻底优化，<\/section><section style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">掌握长难句阅读技巧，<\/section><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">阅读速度、精度突飞猛进！<\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">真正的提速提分提能力！<br style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;\"/><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);\">助你轻松突破GRE330+！<\/p>","numbering":"39"}]
         */

        private String date;
        private List<DataBean> data;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Parcelable {
            /**
             * id : 3356
             * pid : 0
             * catId : 218
             * name : 雷哥GRE阅读长难句5天训练营
             * title : 雷哥GRE阅读长难句5天训练营
             * image : /files/attach/images/20180424/1524559739383172.png
             * createTime : 2018-04-24 16:51:34
             * sort : 3356
             * userId : 1
             * viewCount : 55
             * show : 1
             * catName : 公开课
             * duration :
             * alternatives : 25个典型长难句机经练习，
             每日定量练习长难句，
             配合直播精准解析，
             5天掌握做题技巧，
             尽在雷哥GRE
             阅读经典长难句训练营！
             这里有什么

             为期5天，每天5个精选长难句，
             对句子结构、关键点提取、
             名词指代、逻辑关系等进行全方位训练；
             先做题后解析，有备考战友互相讨论，
             有贴心小G君严格监督，
             有专业老师答疑解惑。

             能给你什么

             雷哥GRE名师sharron精选长难句；
             和众多杀G战友一起互助互帮，共同进步；
             在贴心的小G君的督促下，
             每日打卡完成任务，保持学习动力；
             5天的长难句集中训练，
             阅读习惯彻底优化，
             掌握长难句阅读技巧，
             阅读速度、精度突飞猛进！
             真正的提速提分提能力！
             助你轻松突破GRE330+！

             什么时候开始

             2018年5月2日 - 5月6日
             * article : /files/attach/file/20180424/1524559764697839.png
             * listeningFile : sharron
             * cnName : 2018-05-02 16:50:20
             * sentenceNumber : <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">雷哥GRE名师sharron精选长难句；</section><p><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">和众多杀G战友一起互助互帮，共同进步；</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">在贴心的小G君的督促下，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">每日打卡完成任务，保持学习动力；</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">5天的长难句集中训练，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">阅读习惯彻底优化，</section><section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">掌握长难句阅读技巧，</section></p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">阅读速度、精度突飞猛进！</p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; clear: both; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">真正的提速提分提能力！<br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"/></p><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; min-height: 1em; color: rgb(62, 62, 62); font-family: &#39;Helvetica Neue&#39;, Helvetica, &#39;Hiragino Sans GB&#39;, &#39;Microsoft YaHei&#39;, Arial, sans-serif; font-size: 14px; line-height: 42px; text-align: center; white-space: normal; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">助你轻松突破GRE330+！</p>
             * numbering : 39
             */

            private String name;
            private String title;
            private String image;
            private String alternatives;
            private String article;
            private String listeningFile;
            private String cnName;
            private String sentenceNumber;
            private String catName ;
            private String isTitle ;
            private String viewCount ;

            protected DataBean(Parcel in) {
                name = in.readString();
                title = in.readString();
                image = in.readString();
                alternatives = in.readString();
                article = in.readString();
                listeningFile = in.readString();
                cnName = in.readString();
                sentenceNumber = in.readString();
                catName = in.readString();
                isTitle = in.readString();
                viewCount = in.readString();
            }

            public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
                @Override
                public DataBean createFromParcel(Parcel in) {
                    return new DataBean(in);
                }

                @Override
                public DataBean[] newArray(int size) {
                    return new DataBean[size];
                }
            };

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }

            public String getIsTitle() {
                return isTitle;
            }

            public void setIsTitle(String isTitle) {
                this.isTitle = isTitle;
            }

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getAlternatives() {
                return alternatives;
            }

            public void setAlternatives(String alternatives) {
                this.alternatives = alternatives;
            }

            public String getArticle() {
                return article;
            }

            public void setArticle(String article) {
                this.article = article;
            }

            public String getListeningFile() {
                return listeningFile;
            }

            public void setListeningFile(String listeningFile) {
                this.listeningFile = listeningFile;
            }

            public String getCnName() {
                return cnName;
            }

            public void setCnName(String cnName) {
                this.cnName = cnName;
            }

            public String getSentenceNumber() {
                return sentenceNumber;
            }

            public void setSentenceNumber(String sentenceNumber) {
                this.sentenceNumber = sentenceNumber;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeString(title);
                dest.writeString(image);
                dest.writeString(alternatives);
                dest.writeString(article);
                dest.writeString(listeningFile);
                dest.writeString(cnName);
                dest.writeString(sentenceNumber);
                dest.writeString(catName);
                dest.writeString(isTitle);
                dest.writeString(viewCount);
            }
        }
    }

    public static class ChoicenessBean implements Parcelable {
        /**
         * id : 2
         * categoryId : 1
         * name : 雷哥GMAT经典强化方法课
         * relationId : 394
         * url : K4wTImcBxP
         * selected : 1
         * createTime : 2018-04-11 14:35:04
         * content : <p style="padding: 0px; border: 0px currentColor; text-align: left; color: rgb(102, 102, 102); line-height: 22px; text-indent: 2em; font-family: "Microsoft yahei"; font-size: 12px; margin-bottom: 0px; list-style-type: none; border-image-source: none;"><br style="text-align: left; text-indent: 2em;"/></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>一、适用人群</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、有一定语言基础，初次备考GMAT或是考试成绩不理想；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、希望系统全面学习GMAT核心考点，GMAT考察内容，GMAT正确商科思维；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">3、备考时间不足，希望在雷哥GMAT名师指点下通过精准、高效复习一次拿下GMAT高分；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">4．学习自觉性不高，希望通过雷哥GMAT一对一学管老师监督、高效完成学习任务，快速出分。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>二、授课内容</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、雷哥GMAT学管扫盲1课时+强化核心方法课30课时+精讲习题课50课时。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、全面讲解GMAT 考试商科思维、提分解题思路、答题方法与核心技巧，并能够熟练运用于实战。提高GMAT考试的应试能力，实现系统学习与高效提分。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">3、雷哥网专业留学规划师一对一留学规划服务一次。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>三、雷哥GMAT上课学习备考流程</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">你来参加雷哥GMAT 经典强化方法课，九大经典方案给你GMAT满满的信心：</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第一步、课前扫盲\学习资料发送</strong></span></p><p><span style="line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">        1、学管老师一对一快速帮助GMAT小白扫盲，根据学生实际基础和备考时间制定课前预习计划；针对考过GMAT学员，成绩和备考情况分析，</span><span style="line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, "Microsoft YaHei";"> </span><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">给出合理备考规划建议；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、寄送教材及发送GMAT备考所需要资料大礼包电子档给学生，赠送雷豆10000个用于雷哥网模考查看LGSR；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br/></span></strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">第二步、课前预习</span></strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">学管老师一对一了解学生基础情况，深度扫盲和制定的个性化预习计划进行课前预习。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"> </span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第三步、强化核心方法课</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、30课时强化核心方法课程（SC+CR+RC+数学+写作），全面讲解GMAT 正确商科思维、提分解题思路、答题方法与核心技巧，并能够熟练运用于实战，</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">提高GMAT考试的应试能力，<span style="line-height: 1.75em; text-indent: 2em; font-size: 14px;">实现系统学习与高效提分。</span></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、全程小班实时仿真直播教室，课堂可看见老师视频、课件，也可以和老师充分互动答疑。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong><br style="text-align: left; text-indent: 2em;"/></strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第四步、回看视频巩固学习</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">直播结束，在做题练习过程中，可回看全程视频巩固学习，整理笔记，总结方法，视频3个月有效期。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第五步、智能系统刷题练习+精讲习题课</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、32课时真题精讲习题课，题目来源包括OG+PREP+IR50题+作文七宗罪，课前发送题目，完成后上课讲解难题，考前必备练习课程；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、6课时经典模考习题课，精选模考套题，考前模拟分析；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">3、12课时点题课；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">4、雷哥GMAT智能题库（网站+APP）刷题练习，6000+题目，可实现按照考点刷题、难度刷题、单项刷题、书本序号刷题，可查看做题数据报告，了解备考真实状态；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第六步、在线系统答疑</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">如遇到不懂的题目，可以在APP备考八卦、雷哥GMAT学习群、学员专属答疑论坛提问，多种方式快速解决疑难题目；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第七步、一对一个性化复习方案</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"> 一对一学管老师全程监督辅导学习，解决备考问题，制定预复习计划，从成为雷哥GMAT学员那一刻直到考出满意分数，学管老师一路陪伴；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第八步、雷哥网模考</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">雷哥网187套全真模考，分语文、数学、全套模考，模拟真实考试环境，考前模拟考试，分析LGSR，找出问题，针对性复习提高；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><br style="text-align: left; text-indent: 2em;"/></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #1F497D; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>第九步、参加考试拿高分</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">集中备考2~3个月，准备充分，及时报名参加GMAT考试。</span></p><p style="padding: 0px; border: 0px currentColor; text-align: left; color: rgb(102, 102, 102); line-height: 22px; text-indent: 2em; font-family: "Microsoft yahei"; font-size: 12px; margin-bottom: 0px; list-style-type: none; border-image-source: none;"><br style="text-align: left; text-indent: 2em;"/></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="color: #000000; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong>四、【特别提示】</strong></span></p><p style="text-align: left; line-height: 1.75em;"><span style="color: #000000; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">上课详情请咨询老师：</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、请联系官方QQ：2095453331；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、请联系官方微信：1746295647；</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">3、请咨询官方电话：4001816180；</span></p><p style="text-align: left; line-height: 1.75em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong style="line-height: 21px; font-family: tahoma, arial, 宋体, sans-serif;"><span style="margin: 0px; padding: 0px; border: 0px currentColor; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px; border-image-source: none;"><br style="text-align: left; text-indent: 2em;"/></span></strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong style="line-height: 21px; font-family: tahoma, arial, 宋体, sans-serif;"><span style="margin: 0px; padding: 0px; border: 0px currentColor; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px; border-image-source: none;">五【使用提示】</span></strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="line-height: 21px; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">1、本产品在线使用，联网即可，不限地域，全球均可购买。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;">2、本课程仅供本人学习使用，不得转给其他人，一旦发现必追究法律责任。</span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><br style="text-align: left; text-indent: 2em;"/><span style="line-height: 21px; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px;"><strong style="color: rgb(31, 73, 125); line-height: 21px; font-family: 微软雅黑, "Microsoft YaHei";">获取更多GMAT信息，请关注雷哥GMAT微信公众号：LGclub</strong></span></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="margin: 0px; padding: 0px; border: 0px currentColor; color: #000000; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px; border-image-source: none;"><strong>      </strong></span></p><p style="text-align: center;"><img title="1470378854931790.jpg" alt="雷哥微信二维码.jpg" src="http://www.gmatonline.cn/files/attach/images/20160805/1470378854931790.jpg"/></p><p style="text-align: left; line-height: 1.75em; text-indent: 2em;"><span style="margin: 0px; padding: 0px; border: 0px currentColor; color: #000000; font-family: 微软雅黑, "Microsoft YaHei"; font-size: 14px; border-image-source: none;"></span></p>
         * image : http://www.gmatonline.cn/files/attach/images/20170726/1501053858830270.jpg
         * view : 29795
         */

        private String id;
        private String categoryId;
        private String name;
        private String relationId;
        private String url;
        private String createTime;
        private String content;
        private String image;

        public ChoicenessBean(){}

        protected ChoicenessBean(Parcel in) {
            id = in.readString();
            categoryId = in.readString();
            name = in.readString();
            relationId = in.readString();
            url = in.readString();
            createTime = in.readString();
            content = in.readString();
            image = in.readString();
        }

        public static final Creator<ChoicenessBean> CREATOR = new Creator<ChoicenessBean>() {
            @Override
            public ChoicenessBean createFromParcel(Parcel in) {
                return new ChoicenessBean(in);
            }

            @Override
            public ChoicenessBean[] newArray(int size) {
                return new ChoicenessBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(categoryId);
            dest.writeString(name);
            dest.writeString(relationId);
            dest.writeString(url);
            dest.writeString(createTime);
            dest.writeString(content);
            dest.writeString(image);
        }
    }

    public static class CaseBean implements Parcelable {
        /**
         * id : 5246
         * name : 恭喜雷哥网lgw的z同学继BC之后 又拿到了华威大学金融专业的offer~~
         * image : http://www.smartapply.cn/files/attach/images/20180427/1524797083313962.jpg
         * view : 651
         * content : 恭喜雷哥网lgw的z同学继BC之后 又拿到了华威大学金融专业的offer~~
         * details : <p style="text-align: center;"><img src="http://www.smartapply.cn/files/attach/images/20180427/1524797113180814.jpg" title="1524797113180814.jpg" alt="留学offer捷报宣传图-改1.jpg" width="433" height="904" style="width: 433px; height: 904px;"/></p><p><br/></p><p>恭喜雷哥网lgw的z同学继BC之后 又拿到了华威大学金融专业的offer~~</p><p><br/></p><p>川大 gpa:3.7 gmat：770 托福：100+</p><p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p>
         * createTime : 1524797223
         */

        private String name;
        private String image;
        private String content;
        private String details;
        private long createTime ;




        protected CaseBean(Parcel in) {
            name = in.readString();
            image = in.readString();
            content = in.readString();
            details = in.readString();
            createTime = in.readLong();
        }

        public static final Creator<CaseBean> CREATOR = new Creator<CaseBean>() {
            @Override
            public CaseBean createFromParcel(Parcel in) {
                return new CaseBean(in);
            }

            @Override
            public CaseBean[] newArray(int size) {
                return new CaseBean[size];
            }
        };

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(image);
            dest.writeString(content);
            dest.writeString(details);
            dest.writeLong(createTime);
        }
    }
}
