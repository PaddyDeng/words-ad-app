package thinku.com.word.ui.report.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class WordBean implements Parcelable {


    /**
     * needReviewWords : 0
     * userNeedReviewWords : 0
     * do : 0
     * percent : 0
     * sentence : [{"id":"200209","wordsId":"73683","english":"Sensitivity is the <vocab>buzzword<\/vocab> in the beauty industry this fall","chinese":"\u201c\u2018灵敏度\u2019是今年秋天化妆品工业的专门术语\u201d","createTime":"1521770176"},{"id":"200210","wordsId":"73683","english":"Scratch the subject of defence and acronym, abbreviation, and <vocab>buzzwords<\/vocab> fly out.","chinese":"话题触及国防，缩合字，缩写字和行话就满天飞。","createTime":"1521770176"},{"id":"200211","wordsId":"73683","english":"Biodiversity was the <vocab>buzzword<\/vocab> of the Rio Earth Summit.","chinese":"生物多样性是里约地球峰会上的时髦用语。","createTime":"1521770176"}]
     * words : {"id":"73683","word":"buzzword","categoryId":"67","objectId":"5","createTime":"1521514374","translate":"强意词 buzzing。","phonetic_uk":"[ˈbʌzwɜ:d]","sentence":null,"phrase":null,"phonetic_us":"[ˈbʌzwɚd]","mnemonic":"[联想] buzz散布 被散布的词就是流行语","uk_audio":"http://media.shanbay.com/audio/uk/buzzword.mp3","us_audio":"http://media.shanbay.com/audio/us/buzzword.mp3","level":null}
     * lowSentence : [{"id":"800162","wordsId":"73683","english":"buzzword d","chinese":"时髦词语;漂亮口号","createTime":"1522030327"},{"id":"800163","wordsId":"73683","english":"buzzword jargon","chinese":"专门术语","createTime":"1522030327"},{"id":"800164","wordsId":"73683","english":"buzzword n","chinese":"内容空洞","createTime":"1522030327"},{"id":"800165","wordsId":"73683","english":"futuristic buzzword","chinese":"未来的专业术语","createTime":"1522030327"},{"id":"800166","wordsId":"73683","english":"Buzzword Compliant","chinese":"用时髦词汇描述产品或服务的行为","createTime":"1522030327"},{"id":"800167","wordsId":"73683","english":"T Buzzword Bingo","chinese":"独立车库","createTime":"1522030327"},{"id":"800168","wordsId":"73683","english":"the formation of buzzword","chinese":"新词的构成方式","createTime":"1522030327"},{"id":"800169","wordsId":"73683","english":"the source of buzzword","chinese":"新词获得的途径","createTime":"1522030327"},{"id":"800170","wordsId":"73683","english":"Management By Buzzword","chinese":"在几年的流行术语管理","createTime":"1522030327"}]
     * question : false
     */

    private int needReviewWords;
    private int userNeedReviewWords;
    @SerializedName("do")
    private int doX;
    private int percent;
    private WordsBean words;
    private boolean question;
    private List<LowSentenceBean> sentence;
    private List<LowSentenceBean> lowSentence;

    protected WordBean(Parcel in) {
        needReviewWords = in.readInt();
        userNeedReviewWords = in.readInt();
        doX = in.readInt();
        percent = in.readInt();
        question = in.readByte() != 0;
        sentence = new ArrayList<>();
        in.readTypedList(sentence, LowSentenceBean.CREATOR);
        lowSentence = new ArrayList<>();
        in.readTypedList(lowSentence , LowSentenceBean.CREATOR);
    }

    public static final Creator<WordBean> CREATOR = new Creator<WordBean>() {
        @Override
        public WordBean createFromParcel(Parcel in) {
            return new WordBean(in);
        }

        @Override
        public WordBean[] newArray(int size) {
            return new WordBean[size];
        }
    };

    public int getNeedReviewWords() {
        return needReviewWords;
    }

    public void setNeedReviewWords(int needReviewWords) {
        this.needReviewWords = needReviewWords;
    }

    public int getUserNeedReviewWords() {
        return userNeedReviewWords;
    }

    public void setUserNeedReviewWords(int userNeedReviewWords) {
        this.userNeedReviewWords = userNeedReviewWords;
    }

    public int getDoX() {
        return doX;
    }

    public void setDoX(int doX) {
        this.doX = doX;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public WordsBean getWords() {
        return words;
    }

    public void setWords(WordsBean words) {
        this.words = words;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public List<LowSentenceBean> getSentence() {
        return sentence;
    }

    public void setSentence(List<LowSentenceBean> sentence) {
        this.sentence = sentence;
    }

    public List<LowSentenceBean> getLowSentence() {
        return lowSentence;
    }

    public void setLowSentence(List<LowSentenceBean> lowSentence) {
        this.lowSentence = lowSentence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(needReviewWords);
        dest.writeInt(userNeedReviewWords);
        dest.writeInt(doX);
        dest.writeInt(percent);
        dest.writeByte((byte) (question ? 1 : 0));
    }

    public static class WordsBean {
        /**
         * id : 73683
         * word : buzzword
         * categoryId : 67
         * objectId : 5
         * createTime : 1521514374
         * translate : 强意词 buzzing。
         * phonetic_uk : [ˈbʌzwɜ:d]
         * sentence : null
         * phrase : null
         * phonetic_us : [ˈbʌzwɚd]
         * mnemonic : [联想] buzz散布 被散布的词就是流行语
         * uk_audio : http://media.shanbay.com/audio/uk/buzzword.mp3
         * us_audio : http://media.shanbay.com/audio/us/buzzword.mp3
         * level : null
         */

        private String id;
        private String word;
        private String categoryId;
        private String objectId;
        private String createTime;
        private String translate;
        private String phonetic_uk;
        private Object sentence;
        private Object phrase;
        private String phonetic_us;
        private String mnemonic;
        private String uk_audio;
        private String us_audio;
        private Object level;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTranslate() {
            return translate;
        }

        public void setTranslate(String translate) {
            this.translate = translate;
        }

        public String getPhonetic_uk() {
            return phonetic_uk;
        }

        public void setPhonetic_uk(String phonetic_uk) {
            this.phonetic_uk = phonetic_uk;
        }

        public Object getSentence() {
            return sentence;
        }

        public void setSentence(Object sentence) {
            this.sentence = sentence;
        }

        public Object getPhrase() {
            return phrase;
        }

        public void setPhrase(Object phrase) {
            this.phrase = phrase;
        }

        public String getPhonetic_us() {
            return phonetic_us;
        }

        public void setPhonetic_us(String phonetic_us) {
            this.phonetic_us = phonetic_us;
        }

        public String getMnemonic() {
            return mnemonic;
        }

        public void setMnemonic(String mnemonic) {
            this.mnemonic = mnemonic;
        }

        public String getUk_audio() {
            return uk_audio;
        }

        public void setUk_audio(String uk_audio) {
            this.uk_audio = uk_audio;
        }

        public String getUs_audio() {
            return us_audio;
        }

        public void setUs_audio(String us_audio) {
            this.us_audio = us_audio;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }
    }

    public static class LowSentenceBean implements Parcelable {
        /**
         * id : 800162
         * wordsId : 73683
         * english : buzzword d
         * chinese : 时髦词语;漂亮口号
         * createTime : 1522030327
         */

        private String id;
        private String wordsId;
        private String english;
        private String chinese;
        private String createTime;

        protected LowSentenceBean(Parcel in) {
            id = in.readString();
            wordsId = in.readString();
            english = in.readString();
            chinese = in.readString();
            createTime = in.readString();
        }

        public static final Creator<LowSentenceBean> CREATOR = new Creator<LowSentenceBean>() {
            @Override
            public LowSentenceBean createFromParcel(Parcel in) {
                return new LowSentenceBean(in);
            }

            @Override
            public LowSentenceBean[] newArray(int size) {
                return new LowSentenceBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWordsId() {
            return wordsId;
        }

        public void setWordsId(String wordsId) {
            this.wordsId = wordsId;
        }

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }

        public String getChinese() {
            return chinese;
        }

        public void setChinese(String chinese) {
            this.chinese = chinese;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(wordsId);
            dest.writeString(english);
            dest.writeString(chinese);
            dest.writeString(createTime);
        }
    }
}
