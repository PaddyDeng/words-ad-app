package thinku.com.word.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */


    public  class EventPkListData {
        /**
         * totalId : 141
         * words : [{"answer":"n. 教友派信徒","phonetic_uk":"'kweɪkə(r)","select":"vt. 重新排定...日程\nadj. 腐蚀的, 腐蚀性的, 蚀坏的\nn. 教友派信徒\nn. 牙,暴牙,牙状物","uk_audio":"http://media.shanbay.com/audio/uk/quaker.mp3","word":"Quaker","wordsId":"74964"},{"answer":"adj. 关键的,决定性的","phonetic_uk":"[ˈkru:ʃəl]","select":"n. 种类,样子,态度\nn. 领导, 领导才干\nvt.& vi.设计； 绘制\nadj. 关键的,决定性的","uk_audio":"http://media.shanbay.com/audio/uk/crucial.mp3","word":"crucial","wordsId":"110"},{"answer":"vt. 使一致,遵守,使顺从","phonetic_uk":"[kənˈfɔ:m]","select":"n. 小礼拜堂, 礼拜仪式，私人祈祷处，唱诗班，印刷厂工会，殡仪馆\nint. 惊叹声, 停止\nvt. 使一致,遵守,使顺从\nadj. 建设性的, 构造上的, 作图的","uk_audio":"http://media.shanbay.com/audio/uk/conform.mp3","word":"conform","wordsId":"1573"},{"answer":"adj. 险峻的, 陡峭的, (价格)过高的","phonetic_uk":"[sti:p]","select":"n. 队列,排名,等级,军衔,阶级\nn. 攻击, 突袭\nadj. 险峻的, 陡峭的, (价格)过高的\nn. 革命, 旋转, 转数","uk_audio":"http://media.shanbay.com/audio/uk/steep.mp3","word":"steep","wordsId":"3175"},{"answer":"n. 背骨, 脊柱, 尖刺","phonetic_uk":"[spaɪn]","select":"n. 背骨, 脊柱, 尖刺\nadj. 过多的,过分的\nn. 撤退,退回,取消\nadj. 怀敌意的,敌对的","uk_audio":"http://media.shanbay.com/audio/uk/spine.mp3","word":"spine","wordsId":"3147"},{"answer":"adj. 固有的, 内在的","phonetic_uk":"[ɪnˈtrɪnsɪk]","select":"n. 布局, 安排, 设计\nn. 道德准则,士气,斗志\nn. 传播, 流行, 普及\nadj. 固有的, 内在的","uk_audio":"http://media.shanbay.com/audio/uk/intrinsic.mp3","word":"intrinsic","wordsId":"2314"},{"answer":"vt. 发布,公布,发表","phonetic_uk":"'prɒmlɡeɪt","select":"adj. 排泄物的, 渣滓的\nvt. 发布,公布,发表\nn. 乘法,增加,乘法表\nn. 表层土","uk_audio":"http://media.shanbay.com/audio/uk/promulgate.mp3","word":"promulgate","wordsId":"74969"},{"answer":"n. 精神病患者","phonetic_uk":"[ˈsaɪkəʊpæθ]","select":"n. 除臭剂\nn. 着色\nn. 邦联, 同(联)盟\nn. 精神病患者","uk_audio":"http://media.shanbay.com/audio/uk/psychopath.mp3","word":"psychopath","wordsId":"401"}]
         */

        private int totalId;
        private List<WordsBean> words;

        public int getTotalId() {
            return totalId;
        }

        public void setTotalId(int totalId) {
            this.totalId = totalId;
        }

        public List<WordsBean> getWords() {
            return words;
        }

        public void setWords(List<WordsBean> words) {
            this.words = words;
        }

        public static class WordsBean {
            /**
             * answer : n. 教友派信徒
             * phonetic_uk : 'kweɪkə(r)
             * select : vt. 重新排定...日程
             adj. 腐蚀的, 腐蚀性的, 蚀坏的
             n. 教友派信徒
             n. 牙,暴牙,牙状物
             * uk_audio : http://media.shanbay.com/audio/uk/quaker.mp3
             * word : Quaker
             * wordsId : 74964
             */

            private String answer;
            private String phonetic_uk;
            private String select;
            private String uk_audio;
            private String word;
            private String wordsId;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getPhonetic_uk() {
                return phonetic_uk;
            }

            public void setPhonetic_uk(String phonetic_uk) {
                this.phonetic_uk = phonetic_uk;
            }

            public String getSelect() {
                return select;
            }

            public void setSelect(String select) {
                this.select = select;
            }

            public String getUk_audio() {
                return uk_audio;
            }

            public void setUk_audio(String uk_audio) {
                this.uk_audio = uk_audio;
            }

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
        }
    }
