package thinku.com.word.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import thinku.com.word.MyApplication;
import thinku.com.word.adapter.LoginInfo;
import thinku.com.word.bean.EventPkData;
import thinku.com.word.bean.UserInfo;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SharedPreferencesUtils {
    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();

    protected static String PREFS_NAME = "UserInfo";
    private static SharedPreferences sp ;
    private final static String PREFS_KEY_USER_PWD = "prefs_key_user_pwd";
    private final static String PREFS_KEY_MEMORY_MODE = "prefs_key_memory_mode";
    private final static String PREFS_KEY_PALN_WORDS = "prefs_key_plan_words" ;
    private final static String PREFS_KEY_WINDOW = "prefs_key_window" ;  //  是否弹窗
    private final static String PREFS_KEY_REVIEWE_MODE = "prefs_key_chose_mode" ;
    private final static String PREFS_KEY_RANK_SCORE = "prefs_key_rank_score" ;  //  评估排名
    private final static String PREFS_KEY_RANK_NUM = "prefs_key_rank_num" ;   //  评估数量
    private final static String PREFS_KEY_IMAGE = "prefs_key_user_img";  //  自己头像

    //  PK
    private final static String PREFS_PK_NAME = "prefs_pk";   //  pk
    private final static String PERFS_PK_IMAGE = "prefs_pk_match_image" ;  //  对手头像
    private final static String PERFS_PK_NAME = "prefs_pk_match_name" ;   // 对手名字
    private final static String PERFS_PK_UID = "prefs_pk_match_uid" ;
    private final static String PLAY_MUSCI = "prefs_play_music";  //   播放音效设置

    private final static String LOGO = "prefs_logo" ;  //  logo 图片

    private static SharedPreferences getSharePreferences(Context context){
        if (sp == null){
            sp = context.getSharedPreferences(PREFS_PK_NAME ,context.MODE_PRIVATE);
        }
        return sp ;
    }

    /**
     * 存储PK对手信息
     * @param context
     * @param userBean
     */
    public static void setPk(Context context , EventPkData.UserBean userBean){
        SharedPreferences sp = context.getSharedPreferences(PREFS_PK_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PERFS_PK_IMAGE,userBean.getImage());
        editor.putString(PERFS_PK_NAME , userBean.getNickname());
        editor.putString(PERFS_PK_UID ,userBean.getUid());
        editor.commit();
    }


    public void clearUserInfo(Context context){
        SharedPreferences sp = getSharePreferences(context);
    }


    public static void setPlayMusic(Context context ,boolean isPlay){
        setBoolean(PLAY_MUSCI ,context ,isPlay);
    }

    public static boolean getPlayMusic(Context context ){
        return getBoolean(PLAY_MUSCI ,context);
    }

    /**
     * 获取对手头像
     * @param context
     * @return
     */
    public static String getPKMatchImage(Context context ){
        return context.getSharedPreferences(PREFS_PK_NAME ,Context.MODE_PRIVATE).getString(PERFS_PK_IMAGE,"");
    }

    /**
     * 获取对手名字
     * @param context
     * @return
     */
    public static String getPKMatchName(Context context ){
        return context.getSharedPreferences(PREFS_PK_NAME ,Context.MODE_PRIVATE).getString(PERFS_PK_NAME,"");
    }

    /**
     * 获取对手名字
     * @param context
     * @return
     */
    public static String getPKMatchUid(Context context ){
        return context.getSharedPreferences(PREFS_PK_NAME ,Context.MODE_PRIVATE).getString(PERFS_PK_UID,"");
    }

    public  static void setLogo(Context context ,Boolean value){
        setBoolean(LOGO ,context ,value);
    }

    public static Boolean getLogo(Context context){
        return getBoolean(LOGO ,context);
    }




    /**
     * 用户信息
     * @param context
     * @param login
     */
    public static void setLogin(Context context, UserInfo login) {
        SharedPreferences sp = getSharePreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uid", login.getUid());
        edit.putString("username", login.getUsername());
        edit.putString("password", login.getPassword());
        edit.putString("email", login.getEmail());
        edit.putString("phone", login.getPhone());
        edit.putString("nickname", login.getNickname());
        edit.commit();
    }

    /**
     * 用户信息
     * @param context
     */
    public static void clearLogin(Context context) {
        SharedPreferences sp =getSharePreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uid", "");
        edit.putString("username", "");
        edit.putString("password", "");
        edit.putString("email", "");
        edit.putString("phone", "");
        edit.putString("nickname", "");
        edit.commit();
    }

    public static void setLoginInfo(Context context , LoginInfo loginInfo){
        SharedPreferences sp = context.getSharedPreferences("login" ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("num" , loginInfo.getNum());
        editor.putString("pass" ,loginInfo.getPass());
        editor.commit();
    }

    public static LoginInfo getLoginInfo(Context context){
        LoginInfo loginInfo  = new LoginInfo() ;
        SharedPreferences sp = context.getSharedPreferences("login" ,Context.MODE_PRIVATE);
        loginInfo.setNum(sp.getString("num",""));
        loginInfo.setPass(sp.getString("pass" ,""));
        return loginInfo;
    }

    public static void clearMatch(Context context){
        SharedPreferences sp =context.getSharedPreferences(PREFS_PK_NAME ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PERFS_PK_IMAGE,"");
        editor.putString(PERFS_PK_NAME , "");
        editor.putString(PERFS_PK_UID ,"");
        editor.commit();
    }


    public static UserInfo getUserInfo(Context context) {
        UserInfo login = new UserInfo();
        SharedPreferences sp = getSharePreferences(context);
        login.setUid(sp.getString("uid", ""));
        login.setUsername(sp.getString("username", ""));
        login.setEmail(sp.getString("email", ""));
        login.setPhone(sp.getString("phone", ""));
        login.setNickname(sp.getString("nickname", ""));
        login.setPassword(sp.getString("password", ""));
        return login;
    }

    public static void setStudyMode(Context context , String mode){
        Log.e(TAG, "setStudyMode: " + mode );
        setString("studyMode",context ,mode);
    }

    public static String getStudyMode(Context context){
        Log.e(TAG, "getStudyMode: "  +getSharePreferences(context).getString("studyMode" ,"") );
        return  getSharePreferences(context).getString("studyMode" ,"");
    }
    public static String getNickName(Context context){
        return getSharePreferences(context).getString("nickname" ,"");
    }

    public static String getUid (Context context){
        return  getSharePreferences(context).getString("uid" ,"");
    }

    public static String getImage(Context context){
        return getString(PREFS_KEY_IMAGE ,context);
    }

    public static void setImage(Context context ,String image){
         setString(PREFS_KEY_IMAGE ,context , image);
    }

    public static void setEmail(Context context ,String email){
        setString("email",context ,email);
    }

    public static void setPhone(Context context ,String phone){
        setString("phone",context ,phone);
    }

    public static void setPassword(Context context ,String password){
        setString("password" ,context ,password);
    }

    public static void saveFontSize(Context context ,int size){
        setInt("font" ,context ,size);
    }

    public static int getFontSize(Context context){
       return  getInt( "font",context);
    }

    public static void setNickName(Context context ,String name){
        SharedPreferences sp =getSharePreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickname" ,name);
        editor.commit();
    }
    /**
     *
     */



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
    public  static void saveMemoryMode(Context context, String value) {
        setString(PREFS_KEY_MEMORY_MODE, context, value);
    }

    /**
     * 获取记忆模式
     *
     * @return
     */
    public static String getMemoryMode(Context context) {
        String mode = getString(PREFS_KEY_MEMORY_MODE , context);
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
        return  getSharePreferences(c).getString(key, "");
    }

    public static void setString(String key, Context c, String value) {
        SharedPreferences.Editor editor =  getSharePreferences(c).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, Context c) {
        return getSharePreferences(c).getInt(key, 0);
    }

    public  static  void setInt(String key, Context c, int value) {
        SharedPreferences.Editor editor = getSharePreferences(c).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public  static  void setBoolean(String key, Context c, boolean value) {
        SharedPreferences.Editor editor = getSharePreferences(c).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(String key ,Context context){
        return getSharePreferences(context).getBoolean(key, false);
    }


    public static boolean getFirstOpen(Context context){
        SharedPreferences sp =context.getSharedPreferences("firstOpen",Context.MODE_PRIVATE);
        return sp.getBoolean("isfirst",true);
    }

    public static void setFirstOpen(Context context ){
        SharedPreferences sp =context.getSharedPreferences("firstOpen",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isfirst" ,false);
        editor.commit();
    }


    public static void setCookie(Context context , String cookie){
        SharedPreferences sp =context.getSharedPreferences("baidu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cookie" ,cookie);
    }

    public static String getCookie(Context context){
        SharedPreferences sp =context.getSharedPreferences("baidu",Context.MODE_PRIVATE);
        return sp.getString("cookie" ,"");
    }


}
