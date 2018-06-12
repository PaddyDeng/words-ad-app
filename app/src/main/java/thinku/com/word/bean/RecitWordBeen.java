package thinku.com.word.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class RecitWordBeen {

    /**
     * needReviewWords : 0
     * userNeedReviewWords : 0
     * do : 0
     * percent : 25
     * sentence : [{"id":"204064","wordsId":"71","english":"The lawyer <vocab>cited<\/vocab> a previous case to support his argument.","chinese":"律师引用了以前的案例来支持他的论点。","createTime":"1524449043"},{"id":"204065","wordsId":"71","english":"He was <vocab>cited<\/vocab> for contempt of court.","chinese":"他因蔑视法庭而被传讯。","createTime":"1524449043"},{"id":"204066","wordsId":"71","english":"The soldier was <vocab>cited<\/vocab> by the king for his bravery.","chinese":"这士兵由于英勇而受到国王的褒扬。","createTime":"1524449043"}]
     * words : {"id":"71","word":"cite","categoryId":"6","objectId":"1","createTime":"1509072420","translate":" vt. 引用,引证,传讯,嘉奖","phonetic_uk":"[saɪt]","sentence":"","phrase":"","phonetic_us":"[saɪt]","mnemonic":"[谐音] \u201csay it \u201d 说它 要引用","uk_audio":"http://media.shanbay.com/audio/uk/cite.mp3","us_audio":"http://media.shanbay.com/audio/us/cite.mp3","level":null}
     * lowSentence : [{"id":"171835","wordsId":"71","english":"CITE COFFEE","chinese":"西堤岛咖啡;西堤岛咖啡厅;名咖啡品牌;法国西堤岛咖啡义乌店","createTime":"1515660129"},{"id":"171836","wordsId":"71","english":"Cite Group","chinese":"隶属于城邦集团","createTime":"1515660129"},{"id":"171837","wordsId":"71","english":"Cite Administrative","chinese":"市政中心","createTime":"1515660129"},{"id":"171838","wordsId":"71","english":"to cite","chinese":"引征","createTime":"1515660129"},{"id":"171839","wordsId":"71","english":"Yong Cite","chinese":"雍城","createTime":"1515660129"},{"id":"171840","wordsId":"71","english":"cite function","chinese":"表彰性功能","createTime":"1515660129"},{"id":"171841","wordsId":"71","english":"quote cite","chinese":"引用前人事例或着作作为明证","createTime":"1515660129"},{"id":"171842","wordsId":"71","english":"cite quote","chinese":"引用","createTime":"1515660129"},{"id":"171843","wordsId":"71","english":"cite e","chinese":"呼叫","createTime":"1515660129"}]
     * question : {"questionid":"13314","questiontype":"1","question":"<p><img src=\"/files/attach/images/content/20150508/1431074978223980.jpg\" title=\"1431074978223980.jpg\" alt=\"QQ截图20150508164829.jpg\"/><\/p><p>The&nbsp;table&nbsp;gives&nbsp;three&nbsp;factors&nbsp;to&nbsp;be&nbsp;considered&nbsp;when&nbsp;choosing&nbsp;an&nbsp;Internet&nbsp;service&nbsp;provider&nbsp;and&nbsp;the&nbsp;percent&nbsp;of&nbsp;the&nbsp;1,200&nbsp;respondents&nbsp;to&nbsp;a&nbsp;survey&nbsp;who&nbsp;cited&nbsp;that&nbsp;factor&nbsp;as&nbsp;important.&nbsp;&nbsp;If&nbsp;30&nbsp;percent&nbsp;of&nbsp;the&nbsp;respondents&nbsp;cited&nbsp;both&nbsp;\u201cuser-friendly\u201d&nbsp;and&nbsp;\u201cfast&nbsp;response&nbsp;time,\u201d&nbsp;what&nbsp;is&nbsp;the&nbsp;maximum&nbsp;possible&nbsp;number&nbsp;of&nbsp;respondents&nbsp;who&nbsp;cited&nbsp;\u201cbargain&nbsp;prices,\u201d&nbsp;but&nbsp;neither&nbsp;\u201cuser-friendly\u201d&nbsp;nor&nbsp;\u201cfast&nbsp;response&nbsp;time?\u201d<\/p><p>&nbsp;<\/p><p><br/><\/p><p><br/><\/p>","questiontitle":""}
     */
    private int code ;
    private String message ;
    private String tag ;

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

    private int needReviewWords;
    private int userNeedReviewWords;
    @SerializedName("do")
    private int doX;
    private String percent;
    private WordsBean words;
    private thinku.com.word.ui.report.bean.QuestionBean question;
    private List<LowSentenceBean> sentence;
    private List<LowSentenceBean> lowSentence;
    private List<SimilarWords> similarWords ;

    public List<SimilarWords> getSimilarWords() {
        return similarWords;
    }

    public void setSimilarWords(List<SimilarWords> similarWords) {
        this.similarWords = similarWords;
    }

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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public WordsBean getWords() {
        return words;
    }

    public void setWords(WordsBean words) {
        this.words = words;
    }

    public thinku.com.word.ui.report.bean.QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(thinku.com.word.ui.report.bean.QuestionBean question) {
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

    /**
     * 相似词
     */
    public  static class SimilarWords{
        private String word ;
        private String id ;
        private int num ;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class WordsBean {
        /**
         * id : 71
         * word : cite
         * categoryId : 6
         * objectId : 1
         * createTime : 1509072420
         * translate :  vt. 引用,引证,传讯,嘉奖
         * phonetic_uk : [saɪt]
         * sentence :
         * phrase :
         * phonetic_us : [saɪt]
         * mnemonic : [谐音] “say it ” 说它 要引用
         * uk_audio : http://media.shanbay.com/audio/uk/cite.mp3
         * us_audio : http://media.shanbay.com/audio/us/cite.mp3
         * level : null
         */

        private String id;
        private String word;
        private String createTime;
        private String translate;
        private String phonetic_uk;
        private String sentence;
        private String phonetic_us;
        private String mnemonic;
        private String uk_audio;
        private String us_audio;
        private String level ;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

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
    }


    public static class LowSentenceBean {
        /**
         * id : 171835
         * wordsId : 71
         * english : CITE COFFEE
         * chinese : 西堤岛咖啡;西堤岛咖啡厅;名咖啡品牌;法国西堤岛咖啡义乌店
         * createTime : 1515660129
         */

        private String english;
        private String chinese;
        private String id;
        private String word ;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
