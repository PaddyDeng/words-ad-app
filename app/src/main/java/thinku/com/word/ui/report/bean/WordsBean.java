package thinku.com.word.ui.report.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class WordsBean  implements Parcelable{
    private List<String> words ;
    private int poistion ;
    private boolean isNewAiBinHaoSi ;
    private int tag ;
    private boolean isUpdataReview ;
    private boolean isNormal ;

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public int getPoistion() {
        return poistion;
    }

    public void setPoistion(int poistion) {
        this.poistion = poistion;
    }

    public boolean getMode() {
        return isNewAiBinHaoSi;
    }

    public void setMode(boolean mode) {
        this.isNewAiBinHaoSi = mode;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isUpdataReview() {
        return isUpdataReview;
    }

    public void setUpdataReview(boolean updataReview) {
        isUpdataReview = updataReview;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public static Creator<WordsBean> getCREATOR() {
        return CREATOR;
    }

    public WordsBean () {}

    protected WordsBean(Parcel in) {
        words = in.createStringArrayList();
        poistion = in.readInt();
        isNewAiBinHaoSi = in.readByte() != 0;
        tag = in.readInt();
        isUpdataReview = in.readByte() != 0;
        isNormal = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(words);
        dest.writeInt(poistion);
        dest.writeByte((byte) (isNewAiBinHaoSi ? 1 : 0));
        dest.writeInt(tag);
        dest.writeByte((byte) (isUpdataReview ? 1 : 0));
        dest.writeByte((byte) (isNormal ? 1 : 0));
    }
}
