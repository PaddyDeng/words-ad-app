package thinku.com.word.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/4/13.
 */

public class EvaWordBeen implements Parcelable {


    /**
     * code : 1
     * words : {"word":"along","wordsId":"74993","phonetic_uk":"ə'lɒŋ","uk_audio":"http://media.shanbay.com/audio/uk/along.mp3","select":"n. 暴风雨\nn. 重要人物\nadv. 向前,(与某人)一道\nn. 乘坐,乘车,搭便车","answer":"adv. 向前,(与某人)一道"}
     */

    private int code;
    private WordsBean words;

    public EvaWordBeen() {}

    protected EvaWordBeen(Parcel in) {
        code = in.readInt();
        words = in.readParcelable(WordsBean.class.getClassLoader());
    }

    public static final Creator<EvaWordBeen> CREATOR = new Creator<EvaWordBeen>() {
        @Override
        public EvaWordBeen createFromParcel(Parcel in) {
            return new EvaWordBeen(in);
        }

        @Override
        public EvaWordBeen[] newArray(int size) {
            return new EvaWordBeen[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WordsBean getWords() {
        return words;
    }

    public void setWords(WordsBean words) {
        this.words = words;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeParcelable((Parcelable) WordsBean.class.getClassLoader(),flags);
    }

    public static class WordsBean  implements Parcelable{
        /**
         * word : along
         * wordsId : 74993
         * phonetic_uk : ə'lɒŋ
         * uk_audio : http://media.shanbay.com/audio/uk/along.mp3
         * select : n. 暴风雨
         n. 重要人物
         adv. 向前,(与某人)一道
         n. 乘坐,乘车,搭便车
         * answer : adv. 向前,(与某人)一道
         */

        private String word;
        private String wordsId;
        private String phonetic_uk;
        private String uk_audio;
        private String select;
        private String answer;

        public WordsBean(){}
        protected WordsBean(Parcel in) {
            word = in.readString();
            wordsId = in.readString();
            phonetic_uk = in.readString();
            uk_audio = in.readString();
            select = in.readString();
            answer = in.readString();
        }

        public static final Creator<WordsBean> CREATOR = new Creator<WordsBean>() {
            @Override
            public WordsBean createFromParcel(Parcel in) {
                return new WordsBean(in);
            }

            @Override
            public WordsBean[] newArray(int size) {
                return new WordsBean[size];
            }
        };

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getWordsId() {
            return wordsId;
        }

        public void setWordsId(String wordsId) {
            this.wordsId = wordsId;
        }

        public String getPhonetic_uk() {
            return phonetic_uk;
        }

        public void setPhonetic_uk(String phonetic_uk) {
            this.phonetic_uk = phonetic_uk;
        }

        public String getUk_audio() {
            return uk_audio;
        }

        public void setUk_audio(String uk_audio) {
            this.uk_audio = uk_audio;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(word);
            dest.writeString(wordsId);
            dest.writeString(phonetic_uk);
            dest.writeString(uk_audio);
            dest.writeString(select);
            dest.writeString(answer);
        }
    }
}
