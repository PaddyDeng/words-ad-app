package thinku.com.word.utils;

/**
 * Created by Administrator on 2018/3/27.
 */

public class C {
    public static final int LGWordStatusNone = 0 ; // 没背， 复习模块中 0 表示全部
    public static final int LGWordStatusFamiliar = 1  ; // 熟识
    public static final int LGWordStatusKnow = 2 ; // 认识
    public static final int LGWordStatusIncoginzance = 3  ; // 不认识
    public static final int LGWordStatusVague = 4 ; // 模糊
    public static final int LGWordStatusForget = 5  ; // 忘记

    public static final int NORMAL_RECITE = 100 ;   //  正常背单词

    public static final String NORMAL = "normal";
    public static final String TAGS = "tags" ;
    public static final String LISTWORD = "list_word" ;  //  已知list数组 获取word详情


    public static final int PK_MATCH_SUCCESS = 1 ;   //  匹配成功
    public static final int PK_READY_SUCCESS = 2 ;  //准备好
    public static final int PK_MATCH_CANCLE = 3 ;  //  对手取消匹配
    public static final int PKING = 4 ; //  正在PK


    public static final int PK_AGREE = 1 ;  // 同意PK
    public static final int PK_CANCEL = 2 ;  // 取消PK


    public static final int PK_TYPE_ERROR = 0 ; // 答案错误
    public static final int PK_TYPE_CURRENT = 1 ;   // 答案正确


    public static final String CENTER_CHANGE = "center_change";
    public static final int HEADER_CHANGE = 500;

    public  static int LOGIN_REQUEST_CODE = 100;
    public  static int COM_REQUEST_CODE = LOGIN_REQUEST_CODE + 1;
    public  static int REMARK_RELEASE_REQUEST_CODE = COM_REQUEST_CODE + 1;
    public  static int MAKE_REQUEST_CODE = REMARK_RELEASE_REQUEST_CODE + 1;
    public  static int SETTING_RESET_TXT_CODE = MAKE_REQUEST_CODE + 1;
    public  static int SET_NICK_REQUEST_CODE = SETTING_RESET_TXT_CODE + 1;

    public static int TRIAL_COURSE = 1;
    public static int INTRO_COURSE = TRIAL_COURSE + 1;
    public static int PUBLIC_COURSE = INTRO_COURSE + 1;
    public static int HOT_COURSE = PUBLIC_COURSE + 1;
    public static int TEACHER_COURSE = HOT_COURSE + 1;
    public static int MAKE_TEST_PERSON = TEACHER_COURSE + 1;
    public static int KNOW_POINT_TYPE = MAKE_TEST_PERSON + 1;
    public static int KNOW_POINT_TYPE_ITEM = KNOW_POINT_TYPE + 1;
    public static int REQUST_CODE_UPDATE = KNOW_POINT_TYPE_ITEM + 1;



    public int ERR_UN_INVOKE_GETOBJECT = -201;    //没有调用getVodObject
    public int ERR_VOD_INTI_FAIL = 14;                    //点播初始化失败
    public int ERR_VOD_NUM_UNEXIST = 15;            //点播编号不存在或点播不存在
    public int ERR_VOD_PWD_ERR = 16;                //点播密码错误
    public int ERR_VOD_ACC_PWD_ERR = 17;                 //帐号或帐号密码错误
    public int ERR_UNSURPORT_MOBILE = 18;         //不支持移动设备
    public int ERR_DOMAIN = -100;                                // domain不正确
    public int ERR_TIME_OUT = -101;                              // 超时
    public int ERR_UNKNOWN = -102;                                    // 未知错误
    public int ERR_SITE_UNUSED = -103;                       // 站点不可用
    public int ERR_UN_NET = -104;                                 // 无网络
    public int ERR_DATA_TIMEOUT = -105;                     // 数据过期
    public int ERR_SERVICE = -106;                               // 服务不正确
    public int ERR_PARAM = -107;                                  // 参数不正确
    public int ERR_THIRD_CERTIFICATION_AUTHORITY = -108; //第三方认证失败


    /**
     * Rxbus  tag
     */
    public static final int RXBUS_HEAD_IMAGE  = 1 ;
    public static final int RXBUS_LOGIN = 2 ;
    public static final int RXBUS_LOGIN_BACKMAIN = 3 ;
    public static final int RXBUS_EXLOING = 4 ;

}
