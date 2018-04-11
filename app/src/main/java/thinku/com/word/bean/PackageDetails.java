package thinku.com.word.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class PackageDetails  {
    private List<Word> packageDetails ;
    public static class Word implements Parcelable{
        private String id ;
        private String word ;
        private String categoryId ;
        private String objectId ;
        private String createTime ;
        private String translate ;
        private String phonetic_uk ;
        private String sentence ;
        private String phrase;
        private String phonetic_us;
        private String mnemonic ;
        private String uk_audio ;
        private String us_audio ;
        private String firstStatus ;

        public Word(){}
        protected Word(Parcel in) {
            id = in.readString();
            word = in.readString();
            categoryId = in.readString();
            objectId = in.readString();
            createTime = in.readString();
            translate = in.readString();
            phonetic_uk = in.readString();
            sentence = in.readString();
            phrase = in.readString();
            phonetic_us = in.readString();
            mnemonic = in.readString();
            uk_audio = in.readString();
            us_audio = in.readString();
            firstStatus = in.readString();
        }

        public static final Creator<Word> CREATOR = new Creator<Word>() {
            @Override
            public Word createFromParcel(Parcel in) {
                return new Word(in);
            }

            @Override
            public Word[] newArray(int size) {
                return new Word[size];
            }
        };

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

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getPhrase() {
            return phrase;
        }

        public void setPhrase(String phrase) {
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

        public String getFirstStatus() {
            return firstStatus;
        }

        public void setFirstStatus(String firstStatus) {
            this.firstStatus = firstStatus;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(word);
            dest.writeString(categoryId);
            dest.writeString(objectId);
            dest.writeString(createTime);
            dest.writeString(translate);
            dest.writeString(phonetic_uk);
            dest.writeString(sentence);
            dest.writeString(phrase);
            dest.writeString(phonetic_us);
            dest.writeString(mnemonic);
            dest.writeString(uk_audio);
            dest.writeString(us_audio);
            dest.writeString(firstStatus);
        }
    }

    public List<Word> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(List<Word> packageDetails) {
        this.packageDetails = packageDetails;
    }
}
