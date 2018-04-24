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


}
