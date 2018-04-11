package thinku.com.word.http;

/**
 * Created by Administrator on 2018/2/5.
 */

public class NetworkChildren {
    public static final String LOGIN="check-login";//正常登录
    public static final String TOEFL="app-api/unify-login";//托福
    public static final String GMAT="index.php?web/appapi/unifyLogin";//GMAT
    public static final String MESSAGEINIT="phone-request";//短信初始化
    public static final String PHONECODE="phone-code";//发送短信
    public static final String MAILCODE="send-mail";//发送邮箱验证
    public static final  String REGISTER="register";//注册
    public static final String FindPass="find-pass";//找回密码
    public static final String USERDETAIL="app-api/user-info";//用户信息
    public static final String CHOOSE_STUDY_MODE = "app-api/update-model";  //  选择学习模式
    public static final String UPDATA_PACKAGE    =  "app-api/update-package";  //  上传词包
    public static final String PACKAGE_LIST = "app-api/package-list" ;  // 获取词包
    public static final String PACKAGE_DETAILS = "app-api/package-details"; // 获取词包详情
    public static final String ADD_PACKAGE = "app-api/add-package" ;  //  添加词包
    public static final String USER_PAGE   = "app-api/user-package" ;   //  用户词包
    public static final String INDEX  = "app-api/index" ;    //  选择词包和计划用户进入
    public static final String RECITE_WORDS = "app-api/recite-words"; // 复习单词
    public static final String UPDATA_STATUS = "app-api/update-status" ;  //  上传单词状态
    public static final String IS_REVIEW = "app-api/is-review" ;  // 是否为复习
    public static final String REVIEW_CASE = "app-api/review-case" ;  //  复习情况
    public static final String UPDATA_IS_REVIEW = "app-api/update-is-review" ; // 是否复习
    public static final String REVIEW_CASE_WORDS = "app-api/review-case-words"; // 复习单词
    public static final String WORD_DETAILS = "app-api/get-words-details" ;   //  根据id 获取word详情

    /**
     *  复习部分接口
     */
    public static final String REVIEW_INDEX = "app-api/review-index";   //  复习
    public static final String WRONG_INDEX = "app-api/wrong-index" ;  //  错题列表
    public static final String WRONG_WORDS  = "app-api/wrong-words" ;  //  错题words
    public static final String REVIEWE_UPDATA = "app-api/review-update";  //  复习情况下上传单词状态
    public static final String TIME_SELECT = "app-api/time-select";  //   按时间复习中时间选择
    public static final String DICTATION_WORDS = "app-api/dictation-words";  // 听写练习分类获取words
    public static final String DCTATION_INDEX = "app-api/dictation-index" ;   // 听写练习首页
    public static final String ERROR_RECOVERY = "app-api/error-recovery" ; //  报错功能
    public static final String USER_SIGN = "app-api/user-sign" ;  //  用户签到
}
