package thinku.com.word.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/28.
 */

public class StringUtils {


    /**
     * 获取 类似2015-04-04的当月日期数
     * @param s
     * @return
     */
    public static String spiltDay(String s){
            String[] strings = s.split("-");
            if (strings != null){
                return strings[strings.length -1];
            }else {
                return "";
            }
        }

    /**
     * 以 -  分割字符串
     * @param s
     * @return
     */
    public static List<String> spiltInt(String s){
        List<String> stringList = new ArrayList<>();
        String[] strings = s.split("-");
        if (strings.length > 0){
            for (int i = 0 ; i< strings.length ;i++){
                stringList.add(strings[i]);
            }
        }
        return stringList;
    }

    /**
     * 以 //n  分割字符串
      * @param s
     * @return
     */
    public static List<String> spiltString(String s){
        List<String> stringList = new ArrayList<>();
        String[] strings = s.split("\\n");
        if (strings.length > 0){
            for (int i = 0 ; i< strings.length ;i++){
                stringList.add(strings[i]);
            }
            Collections.reverse(stringList);
        }
        return stringList;
    }
    /**
     * 以空行分割字符串
     * @param s
     * @return
     */
    public static String spilt(String s ){
        String[] strings = s.split("\\n");
        if (strings!= null){
            return strings[0];
        }else{
            return "";
        }
    }

    /**
     *  获取String 中的数字
     * @param s
     * @return
     */
    public static String spilitNum(String  s){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        m.find();
        return m.group() ;
    }


    public static String getStringNum(String s){
        String regEx="[^0-9]";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(s);
        return mat.replaceAll("").trim() ;
    }

    /**
     *   随机生成答案个数
     * @param s
     * @return
     *   s.length < 4   拆分成字母个数
     *   s.length == 5   随机拆分成4 或5 个
     *   s.length < 24   随机拆分成 4 或 5 或6 个
     *   s.length > 24   随机拆分成6个
     */
    public static List<String> splitString(String s) {
        List<String> words;
        if (!TextUtils.isEmpty(s)) {
            int length = s.length();
            if (length <= 4) {
                words = getListNumFromLength(s.length(), s.length(), s);
            } else if (length == 5) {
                int randomNum = random(4, 5);
                if (randomNum == 5) {
                    words = getListNumFromLength(5, 5, s);
                } else {
                    words = getListNumFromLength(randomNum, s);
                }
            } else if (length <= 24) {
                int randomNum = random(4, 6);
                words = getListNumFromLength(randomNum, s);
            } else {
                words = getListNumFromLength(6, s);
            }
            Collections.shuffle(words);
            return words;
        }
        return null ;
    }


    /**
     *
     * @param sLength    string的length
     * @param spiltLength   string的spilt的数目
     * @param s  string
     */
    public static List<String> getListNumFromLength(int sLength ,int spiltLength ,String s){
        List<String> stringList = new ArrayList<>();
        if (sLength == spiltLength){
            for (int i = 0 ;i < sLength ; i++){
                String unitS = s.substring(i ,(i+1));
                stringList.add(unitS);
            }
        }
        return stringList;
    }


    /**
     *
     * @param spiltLength   分割的字符串字数
     * @param s   string
     * @return
     */
    public static List<String> getListNumFromLength(int spiltLength ,String s ){
        boolean isMoreWord = true ;
        int unit = s.length() / spiltLength;
        int moreNum = s.length() % spiltLength;
        List<Integer> moreTotal = randomInt(spiltLength, moreNum);
        if (moreTotal == null || moreTotal.size() == 0)  isMoreWord = false ;
        List<String> stringList = new ArrayList<>();
        int moreIndex = 0 ;
        int sIndex = 0 ;
        for (int i = 0 ;i < spiltLength ;i ++){
            if (isMoreWord  && moreIndex <moreTotal.size() && i == moreTotal.get(moreIndex)) {
                stringList.add(s.substring(sIndex, (sIndex + unit + 1)));
                sIndex = (sIndex + unit + 1);
                moreIndex++;
            }else {
                stringList.add(s.substring(sIndex, (sIndex + unit)));
                sIndex = (sIndex + unit);
            }
        }
        return stringList;
    }



    /**
     * 在0 - maxNum 中随机选出total 个不相同的数字
     * @param maxNum
     * @param total
     * @return
     */
    public  static List<Integer> randomInt(int maxNum ,int total ){
        if (maxNum < total) return null;
        List<Integer> moreNum = new ArrayList<>();
        Random random = new Random();
        int i = 0 ;
         while ( i < total){
            int more = random.nextInt(maxNum);
            if (moreNum.indexOf(more) == -1){
                moreNum.add(more);
                i++ ;
            }
        }
        Collections.sort(moreNum);
        return moreNum ;
    }

    /**
     * 产生随机整数，包含下限值，包括上限值
     * @param {Number} lower 下限
     * @param {Number} upper 上限
     * @return {Number} 返回在下限到上限之间的一个随机整数
     */
    public  static  int  random(int lower,int upper) {
        return (int) (Math.floor(Math.random() * (upper - lower+1)) + lower);
    }



}
