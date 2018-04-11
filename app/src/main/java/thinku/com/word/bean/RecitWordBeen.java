package thinku.com.word.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class RecitWordBeen implements Parcelable {
    private int code;
    private String message;
    @JSONField(name = "do")
    private String item;
    private String question;
    private String planWords;
    private String consoleWords;
    private PackageDetails.Word words;
    private List<Sentence> sentence;   //  例句
    private List<Sentence> lowSentence;  //  短句
    private String tag ;   //  获取单词的方式的状态


    public RecitWordBeen(){

    }

    protected RecitWordBeen(Parcel in) {
        code = in.readInt();
        message = in.readString();
        item = in.readString();
        question = in.readString();
        planWords = in.readString();
        consoleWords = in.readString();
        sentence = in.createTypedArrayList(Sentence.CREATOR);
        lowSentence = in.createTypedArrayList(Sentence.CREATOR);
        words = in.readParcelable(PackageDetails.Word.class.getClassLoader());
    }

    public static final Creator<RecitWordBeen> CREATOR = new Creator<RecitWordBeen>() {
        @Override
        public RecitWordBeen createFromParcel(Parcel in) {
            return new RecitWordBeen(in);
        }

        @Override
        public RecitWordBeen[] newArray(int size) {
            return new RecitWordBeen[size];
        }
    };

    public String getPlanWords() {
        return planWords;
    }

    public void setPlanWords(String planWords) {
        this.planWords = planWords;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getplanWords() {
        return planWords;
    }

    public void setplanWords(String planWords) {
        this.planWords = planWords;
    }

    public String getConsoleWords() {
        return consoleWords;
    }

    public void setConsoleWords(String consoleWords) {
        this.consoleWords = consoleWords;
    }

    public PackageDetails.Word getWords() {
        return words;
    }

    public void setWords(PackageDetails.Word words) {
        this.words = words;
    }

    public List<Sentence> getSentence() {
        return sentence;
    }

    public void setSentence(List<Sentence> sentence) {
        this.sentence = sentence;
    }

    public List<Sentence> getLowSentence() {
        return lowSentence;
    }

    public void setLowSentence(List<Sentence> lowSentence) {
        this.lowSentence = lowSentence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(item);
        dest.writeString(question);
        dest.writeString(planWords);
        dest.writeString(consoleWords);
        dest.writeTypedList(sentence);
        dest.writeTypedList(lowSentence);
        dest.writeParcelable(words ,flags);
    }

    public static class Sentence implements Parcelable {
        private String id;
        private String wordsId;
        private String english;
        private String chinese;

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

        }

        public Sentence(){}

        public Sentence(Parcel in) {
            id = in.readString();
            wordsId = in.readString();
            english = in.readString();
            chinese = in.readString();
        }

        public static final Parcelable.Creator<Sentence> CREATOR = new Creator<Sentence>() {
            @Override
            public Sentence[] newArray(int size) {
                return new Sentence[size];
            }

            @Override
            public Sentence createFromParcel(Parcel in) {
                return new Sentence(in);
            }
        };
    }
}
