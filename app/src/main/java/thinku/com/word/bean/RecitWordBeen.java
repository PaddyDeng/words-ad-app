package thinku.com.word.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class RecitWordBeen implements Parcelable {

    /**
     * code : 1
     * message : 成功
     * do : 0
     * words : {"id":"120","word":"cynical","categoryId":"6","objectId":"1","createTime":"1509072420","translate":" adj. 愤世嫉俗的,吹毛求疵的","phonetic_uk":"[ˈsɪnɪkəl]","sentence":"","phrase":"","phonetic_us":"[ˈsɪnɪkəl]","mnemonic":null,"uk_audio":"http://media.shanbay.com/audio/uk/cynical.mp3","us_audio":"http://media.shanbay.com/audio/us/cynical.mp3"}
     * sentence : []
     * lowSentence : [{"id":"172238","wordsId":"120","english":"Cynical electioneering","chinese":"讽刺对手的竞选方法","createTime":"1515660141"},{"id":"172239","wordsId":"120","english":"cynical a","chinese":"愤世嫉俗的","createTime":"1515660141"},{"id":"172240","wordsId":"120","english":"Cynical Remark","chinese":"怪话","createTime":"1515660141"},{"id":"172241","wordsId":"120","english":"cynical spirits","chinese":"玩世精神","createTime":"1515660141"},{"id":"172242","wordsId":"120","english":"Cynical Dog2","chinese":"愤怒的小狗","createTime":"1515660141"},{"id":"172243","wordsId":"120","english":"cynical remarks","chinese":"挖苦话","createTime":"1515660141"},{"id":"172244","wordsId":"120","english":"cynical ideology","chinese":"犬儒主义意识形态","createTime":"1515660141"},{"id":"172245","wordsId":"120","english":"C cynical","chinese":"犬儒主义的","createTime":"1515660141"},{"id":"172246","wordsId":"120","english":"Cynical Lies","chinese":"吹毛求疵谎言","createTime":"1515660141"}]
     * question : {"questionid":"8528","questiontype":"1","question":"<p>The plot of The Bostonians centers on the <span style=\"text-decoration:underline;\">rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom<\/span>, when they find themselves drawn to the same radiant young woman whose talent for public speaking has won her an ardent following.<\/p>","questiontitle":"<p>The plot of The Bostonians centers on the <span style=\"text-decoration:underline;\">rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom<\/span>, when they find themselves drawn to the same radiant young woman whose talent for public speaking has won her an ardent following.<\/p>","questionuserid":"106","questionusername":"chris","questionlastmodifyuser":"","questionselect":"&lt;p&gt;(A)&amp;nbsp;rivalry&amp;nbsp;between&amp;nbsp;Olive&amp;nbsp;Chancellor,&amp;nbsp;an&amp;nbsp;active&amp;nbsp;feminist,&amp;nbsp;with&amp;nbsp;her&amp;nbsp;charming&amp;nbsp;and&amp;nbsp;cynical&amp;nbsp;cousin,&amp;nbsp;Basil&amp;nbsp;Ransom,&lt;/p&gt;&lt;p&gt;(B)&amp;nbsp;rivals&amp;nbsp;Olive&amp;nbsp;Chancellor,&amp;nbsp;an&amp;nbsp;active&amp;nbsp;feminist,&amp;nbsp;against&amp;nbsp;her&amp;nbsp;charming&amp;nbsp;and&amp;nbsp;cynical&amp;nbsp;cousin,&amp;nbsp;Basil&amp;nbsp;Ransom,&lt;/p&gt;&lt;p&gt;(C)&amp;nbsp;rivalry&amp;nbsp;that&amp;nbsp;develops&amp;nbsp;between&amp;nbsp;Olive&amp;nbsp;Chancellor,&amp;nbsp;an&amp;nbsp;active&amp;nbsp;feminist,&amp;nbsp;and&amp;nbsp;Basil&amp;nbsp;Ransom,&amp;nbsp;her&amp;nbsp;charming&amp;nbsp;and&amp;nbsp;cynical&amp;nbsp;cousin,&lt;/p&gt;&lt;p&gt;(D)&amp;nbsp;developing&amp;nbsp;rivalry&amp;nbsp;between&amp;nbsp;Olive&amp;nbsp;Chancellor,&amp;nbsp;an&amp;nbsp;active&amp;nbsp;feminist,&amp;nbsp;with&amp;nbsp;Basil&amp;nbsp;Ransom,&amp;nbsp;her&amp;nbsp;charming&amp;nbsp;and&amp;nbsp;cynical&amp;nbsp;cousin,&lt;/p&gt;&lt;p&gt;(E)&amp;nbsp;active&amp;nbsp;feminist,&amp;nbsp;Olive&amp;nbsp;Chancellor,&amp;nbsp;and&amp;nbsp;the&amp;nbsp;rivalry&amp;nbsp;with&amp;nbsp;her&amp;nbsp;charming&amp;nbsp;and&amp;nbsp;cynical&amp;nbsp;cousin&amp;nbsp;Basil&amp;nbsp;Ransom,&lt;/p&gt;","questionselectnumber":"5","questionanswer":"C","questiondescribe":"","questionknowsid":"a:1:{i:0;a:2:{s:7:\"knowsid\";s:2:\"26\";s:5:\"knows\";s:6:\"用词\";}}","questioncreatetime":"1429695956","questionstatus":"1","questionhtml":"","questionparent":"0","questionsequence":"0","questionlevel":"1","oneobjecttype":"4","twoobjecttype":"1","subjecttype":"5","sectiontype":"6","questionarticle":"","articletitle":"","mathfoundation":"","views":"1231","comments":"0","discusstime":"2017-12-12 18:43:07","discussmark":"0","readArticleId":"0","articleContent":null,"questionNumber":"0","questionNumber1":null,"offer":"xCUPFpap","isoffer":null,"isOG":"0","num":"0","qslctarr":[{"name":"A","select":"rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom,"},{"name":"B","select":"rivals Olive Chancellor, an active feminist, against her charming and cynical cousin, Basil Ransom,"},{"name":"C","select":"rivalry that develops between Olive Chancellor, an active feminist, and Basil Ransom, her charming and cynical cousin,"},{"name":"D","select":"developing rivalry between Olive Chancellor, an active feminist, with Basil Ransom, her charming and cynical cousin,"},{"name":"E","select":"active feminist, Olive Chancellor, and the rivalry with her charming and cynical cousin Basil Ransom,"}],"level_s":"","qtitle":"The plot of The Bostonians centers on the rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom, when they find themselves drawn to the same radiant young woman whose talent for public speaking has won her an ardent following."}
     * planWords : 70
     * consoleWords : 0
     */

    private int code;
    private String message;
    @SerializedName("do")
    private String doX;
    private WordsBean words;
    private QuestionBean question;
    private String planWords;
    private String consoleWords;
    private List<LowSentenceBean> sentence;
    private List<LowSentenceBean> lowSentence;
    private String tag ;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public RecitWordBeen (){}
    protected RecitWordBeen(Parcel in) {
        code = in.readInt();
        message = in.readString();
        doX = in.readString();
        planWords = in.readString();
        consoleWords = in.readString();
        tag = in.readString();
        words =  in.readParcelable(WordsBean.class.getClassLoader());
        question = in.readParcelable(QuestionBean.class.getClassLoader());
//        sentence = in.readArrayList(LowSentenceBean.class.getClassLoader() );
//        lowSentence = in.readArrayList(LowSentenceBean.class.getClassLoader());
        sentence = new ArrayList<>();
        in.readTypedList(sentence, LowSentenceBean.CREATOR);
        lowSentence = new ArrayList<>();
        in.readTypedList(lowSentence ,LowSentenceBean.CREATOR);
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

    public String getDoX() {
        return doX;
    }

    public void setDoX(String doX) {
        this.doX = doX;
    }

    public WordsBean getWords() {
        return words;
    }

    public void setWords(WordsBean words) {
        this.words = words;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public String getPlanWords() {
        return planWords;
    }

    public void setPlanWords(String planWords) {
        this.planWords = planWords;
    }

    public String getConsoleWords() {
        return consoleWords;
    }

    public void setConsoleWords(String consoleWords) {
        this.consoleWords = consoleWords;
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
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(doX);
        dest.writeString(planWords);
        dest.writeString(consoleWords);
        dest.writeString(tag);
        dest.writeParcelable(words ,flags);
        dest.writeParcelable(question ,flags);
        dest.writeTypedList(sentence);
        dest.writeTypedList(lowSentence);
    }

    public static class WordsBean implements Parcelable {
        /**
         * id : 120
         * word : cynical
         * categoryId : 6
         * objectId : 1
         * createTime : 1509072420
         * translate :  adj. 愤世嫉俗的,吹毛求疵的
         * phonetic_uk : [ˈsɪnɪkəl]
         * sentence :
         * phrase :
         * phonetic_us : [ˈsɪnɪkəl]
         * mnemonic : null
         * uk_audio : http://media.shanbay.com/audio/uk/cynical.mp3
         * us_audio : http://media.shanbay.com/audio/us/cynical.mp3
         */

        private String id;
        private String word;
        private String categoryId;
        private String objectId;
        private String createTime;
        private String translate;
        private String phonetic_uk;
        private String sentence;
        private String phrase;
        private String phonetic_us;
        private String mnemonic;
        private String uk_audio;
        private String us_audio;

        public WordsBean(){}
        protected WordsBean(Parcel in) {
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
            uk_audio = in.readString();
            us_audio = in.readString();
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
            dest.writeString(uk_audio);
            dest.writeString(us_audio);
        }
    }

    public static class QuestionBean  implements Parcelable{
        /**
         * questionanswer : C
         * qtitle : The plot of The Bostonians centers on the rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom, when they find themselves drawn to the same radiant young woman whose talent for public speaking has won her an ardent following.
         */

        private String questionanswer;
        private List<QslctarrBean> qslctarr;

        public QuestionBean(){}
        protected QuestionBean(Parcel in) {
            questionanswer = in.readString();
        }

        public static final Creator<QuestionBean> CREATOR = new Creator<QuestionBean>() {
            @Override
            public QuestionBean createFromParcel(Parcel in) {
                return new QuestionBean(in);
            }

            @Override
            public QuestionBean[] newArray(int size) {
                return new QuestionBean[size];
            }
        };


        public String getQuestionanswer() {
            return questionanswer;
        }

        public void setQuestionanswer(String questionanswer) {
            this.questionanswer = questionanswer;
        }

        public List<QslctarrBean> getQslctarr() {
            return qslctarr;
        }

        public void setQslctarr(List<QslctarrBean> qslctarr) {
            this.qslctarr = qslctarr;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(questionanswer);
        }

        public static class QslctarrBean implements Parcelable {
            /**
             * name : A
             * select : rivalry between Olive Chancellor, an active feminist, with her charming and cynical cousin, Basil Ransom,
             */

            private String name;
            private String select;

            public QslctarrBean (){}
            protected QslctarrBean(Parcel in) {
                name = in.readString();
                select = in.readString();
            }

            public static final Creator<QslctarrBean> CREATOR = new Creator<QslctarrBean>() {
                @Override
                public QslctarrBean createFromParcel(Parcel in) {
                    return new QslctarrBean(in);
                }

                @Override
                public QslctarrBean[] newArray(int size) {
                    return new QslctarrBean[size];
                }
            };

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSelect() {
                return select;
            }

            public void setSelect(String select) {
                this.select = select;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeString(select);
            }
        }
    }

    public static class LowSentenceBean implements Parcelable {
        /**
         * id : 172238
         * wordsId : 120
         * english : Cynical electioneering
         * chinese : 讽刺对手的竞选方法
         * createTime : 1515660141
         */

        private String id;
        private String wordsId;
        private String english;
        private String chinese;
        private String createTime;

        public LowSentenceBean(){}

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
