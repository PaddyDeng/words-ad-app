package thinku.com.word.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import thinku.com.word.MyApplication;
import thinku.com.word.bean.PersonalDetail;
import thinku.com.word.bean.UserInfo;

import static thinku.com.word.utils.SharePref.PREFS_NAME;
import static thinku.com.word.utils.SharePref.PREFS_STR_INVALID;
import static thinku.com.word.utils.SharePref.isInvalidPrefString;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SharedPreferencesUtils {
    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();

    private final static String PREFS_KEY_USER_PWD = "prefs_key_user_pwd";
    private final static String PREFS_KEY_MEMORY_MODE = "prefs_key_memory_mode";
    private final static String PREFS_KEY_PALN_WORDS = "prefs_key_plan_words" ;
    private final static String PREFS_KEY_WINDOW = "prefs_key_window" ;  //  是否弹窗
    private final static String PREFS_KEY_REVIEWE_MODE = "prefs_key_chose_mode" ;
    private final static String PREFS_KEY_RANK_SCORE = "prefs_key_rank_score" ;  //  评估排名
    private final static String PREFS_KEY_RANK_NUM = "prefs_key_rank_num" ;   //  评估数量
    public static void setPersonal(Context context, PersonalDetail personal) {
        SharedPreferences sp = context.getSharedPreferences("Personal", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("id", personal.getId());
        edit.putString("userName", personal.getUserName());
        edit.putString("userPass", personal.getUserPass());
        edit.putString("nickname", personal.getNickname());
        edit.putString("phone", personal.getPhone());
        edit.putString("school", null == personal.getSchool() ? "" : personal.getSchool());
        edit.putString("major", null == personal.getMajor() ? "" : personal.getMajor());
        edit.putString("grade", null == personal.getGrade() ? "" : personal.getGrade());
        edit.putString("email", null == personal.getEmail() ? "" : personal.getEmail());
        edit.putString("createTime", personal.getCreateTime());
        edit.putString("image", null == personal.getImage() ? "" : personal.getImage());
        edit.putString("remark", null == personal.getRemark() ? "" : personal.getRemark());
        edit.putString("uid", personal.getUid());
        edit.putString("follow", personal.getFollow());
        edit.putString("fans", personal.getFans());
        edit.putString("questionNum", personal.getQuestionNum());
        edit.putString("answerNum", personal.getAnswerNum());
        edit.commit();
    }

    public static void exitLogin(Context context) {
        context.getSharedPreferences("Personal", Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences("Sessions", Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static PersonalDetail getPersonalDetail(Context context) {
        PersonalDetail personalDetail = new PersonalDetail();
        SharedPreferences sp = context.getSharedPreferences("Personal", Context.MODE_PRIVATE);
        personalDetail.setId(sp.getString("id", ""));
        personalDetail.setUserName(sp.getString("userName", ""));
        personalDetail.setUserPass(sp.getString("userPass", ""));
        personalDetail.setNickname(sp.getString("nickname", ""));
        personalDetail.setPhone(sp.getString("phone", ""));
        personalDetail.setSchool(sp.getString("school", ""));
        personalDetail.setMajor(sp.getString("major", ""));
        personalDetail.setGrade(sp.getString("grade", ""));
        personalDetail.setEmail(sp.getString("email", ""));
        personalDetail.setCreateTime(sp.getString("createTime", ""));
        personalDetail.setImage(sp.getString("image", ""));
        personalDetail.setRemark(sp.getString("remark", ""));
        personalDetail.setUid(sp.getString("uid", ""));
        personalDetail.setFollow(sp.getString("follow", ""));
        personalDetail.setFans(sp.getString("fans", ""));
        personalDetail.setQuestionNum(sp.getString("questionNum", ""));
        personalDetail.setAnswerNum(sp.getString("answerNum", ""));
        return personalDetail;
    }

    //保存用户登录信息
    public static void setLogin(Context context, UserInfo login) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uid", login.getUid());
        edit.putString("username", login.getUsername());
        edit.putString("password", login.getPassword());
        edit.putString("email", login.getEmail());
        edit.putString("phone", login.getPhone());
        edit.putString("nickname", login.getNickname());
        edit.commit();
    }

    public static UserInfo getUserInfo(Context context) {
        UserInfo login = new UserInfo();
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        login.setUid(sp.getString("uid", ""));
        login.setUsername(sp.getString("username", ""));
        login.setEmail(sp.getString("email", ""));
        login.setPhone(sp.getString("phone", ""));
        login.setNickname(sp.getString("nickname", ""));
        login.setPassword(sp.getString("password", ""));
        return login;
    }


    public static void setSession(Context context, int i, String session) {
        SharedPreferences sp = context.getSharedPreferences("Sessions", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        String key = "";
        switch (i) {
            case 0:
                key = "toefl";
                break;
            case 1:
                key = "smartapply";
                break;
            case 2:
                key = "gmat";
                break;
            case 3:
                key = "bbs";
                break;
            case 4:
                key = "word";
                MyApplication.session = session ;
                break;
        }
        edit.putString(key, session);
        edit.commit();
    }

    public static String getSession(Context context, int i) {
        String key = "";
        switch (i) {
            case 0:
                key = "toefl";
                break;
            case 1:
                key = "smartapply";
                break;
            case 2:
                key = "gmat";
                break;
            case 3:
                key = "bbs";
                break;
            case 4:
                key = "word";
                break;
        }
        SharedPreferences sp = context.getSharedPreferences("Sessions", Context.MODE_PRIVATE);
        String session = sp.getString(key, "");
        return session;
    }


    //验证是否登录过
    public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String uid = sp.getString("uid", "");
        if (!TextUtils.isEmpty(uid)) {
            return true;
        }
        return false;
    }


    //保存用户账户密码
    public static void setPassword(Context context, String phone, String pass) {
        SharedPreferences sp = context.getSharedPreferences("PhoneAndPass", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (!TextUtils.isEmpty(phone)) edit.putString("phone", phone);
        if (!TextUtils.isEmpty(pass)) edit.putString("pass", pass);
        edit.commit();
    }

    // 存储评估量
    public static void setEvaluationNum(Context context ,int  num ){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sp.edit().putInt("evaluationNum" ,num).commit();
    }
    //  获取评估量
    public static int getEvaluationNum(Context context ){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
       return sp.getInt("evaluationNum",0);
    }
    public static String getPassword(Context context ){
        SharedPreferences sp = context.getSharedPreferences("PhoneAndPass", Context.MODE_PRIVATE);
        return sp.getString("pass","");
    }

    public static String getPhone(Context context ){
        SharedPreferences sp = context.getSharedPreferences("PhoneAndPass", Context.MODE_PRIVATE);
        return sp.getString("phone","");
    }

    public static void setWindow( Context context,String value){
        setString(PREFS_KEY_WINDOW ,context ,value);
    }

    public static String getWindow( Context context){
        return getString(PREFS_KEY_WINDOW ,context);
    }
//    public static String getPassword(Context c) {
//        String pwd = getString(PREFS_KEY_USER_PWD, c);
//        if (isInvalidPrefString(pwd)) {
//            return PREFS_STR_INVALID;
//        }
//        pwd = new String(Base64.decode(pwd.getBytes(),
//                Base64.DEFAULT));
//        return pwd;
//    }

    /**
     * 保存记忆模式
     *
     * @return
     */
    public  static void saveMemoryMode(Context context, int value) {
        setInt(PREFS_KEY_MEMORY_MODE, context, value);
    }

    /**
     * 获取记忆模式
     *
     * @return
     */
    public static int getMemoryMode(Context context) {
        int mode = getInt(PREFS_KEY_MEMORY_MODE , context);
        return mode;
    }


    /**
     *  计划单词数
     * @param context
     * @return
     */
    public static String getPlanWords(Context context){
        return getString(PREFS_KEY_PALN_WORDS ,context) ;
    }

    /**
     *  计划单词数
     * @param context
     * @return
     */
    public static void setPlanWords(Context context ,String value){
        setString(PREFS_KEY_PALN_WORDS ,context ,value);
    }

    /**
     *  计划单词数
     * @param context
     * @return
     */
    public static String getChoseMode(Context context){
        return getString(PREFS_KEY_REVIEWE_MODE ,context) ;
    }

    /**
     *  计划单词数
     * @param context
     * @return
     */
    public static void setChoseMode(Context context ,String value){
        setString(PREFS_KEY_REVIEWE_MODE ,context ,value);
    }

    /**
     *  评估排名
     * @param context
     * @return
     */
    public static void setRankScore(Context context ,String value){
        setString(PREFS_KEY_RANK_SCORE ,context ,value);
    }
    public static String getRankScore(Context context){
        return getString(PREFS_KEY_RANK_SCORE , context);
    }

    /**
     *  评估数量
     * @param context
     * @return
     */
    public static void setRankNum(Context context ,String value){
        setString(PREFS_KEY_RANK_NUM ,context ,value);
    }
    public static String getRankNum(Context context){
        return getString(PREFS_KEY_RANK_NUM , context);
    }

    public static String getString(String key, Context c) {
        return c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public static void setString(String key, Context c, String value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, Context c) {
        return c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(key, 0);
    }

    public  static  void setInt(String key, Context c, int value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }



}
