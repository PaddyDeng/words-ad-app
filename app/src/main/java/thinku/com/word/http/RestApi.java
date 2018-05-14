package thinku.com.word.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.Dictation;
import thinku.com.word.bean.EVAnswerBeen;
import thinku.com.word.bean.EvaWordBeen;
import thinku.com.word.bean.EventPkListData;
import thinku.com.word.bean.Package;
import thinku.com.word.bean.PackageDetails;
import thinku.com.word.bean.PkIndexBeen;
import thinku.com.word.bean.PkResultBeen;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.ReviewDialogBeen;
import thinku.com.word.bean.ReviewMainBeen;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.bean.TrackBeen;
import thinku.com.word.bean.UserData;
import thinku.com.word.bean.UserIndex;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.bean.UserRankBeen;
import thinku.com.word.bean.WordPackageBeen;
import thinku.com.word.bean.WordReportBeen;
import thinku.com.word.bean.WordReportMonthBeen;
import thinku.com.word.bean.WordResultBeen;
import thinku.com.word.bean.WordReviewTodayBeen;
import thinku.com.word.bean.WrongIndexBeen;
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.ui.personalCenter.update.bean.VersionInfo;
import thinku.com.word.ui.personalCenter.update.localdb.UpdateLocalDbData;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.ui.seacher.WordBean;

/**
 * Created by fire on 2017/5/3 15:18.
 */
public interface RestApi {


    @GET("cn/app-api/unify-login")
    Observable<Response<Void>> toefl(@QueryMap Map<String, String> info);

    //    @GET("cn/api/unify-login")
    @GET("cn/app-api/unify-login")
    Observable<Response<Void>> smartapply(@QueryMap Map<String, String> info);

    //    @FormUrlEncoded
//    @GET("index.php?web/webapi/unifyLogin")
    @GET("index.php?web/appapi/unifyLogin")
    Observable<Response<Void>> gmatl(@QueryMap Map<String, String> info);

    //    @FormUrlEncoded
    @GET("cn/app-api/unify-login")
    Observable<Response<Void>> gossip(@QueryMap Map<String, String> info);

    //    @FormUrlEncoded
    @GET("app-api/unify-login")
    Observable<Response<Void>> word(@QueryMap Map<String, String> info);

    @FormUrlEncoded   //  retrofit 2.0 必须
    @POST(NetworkChildren.LOGIN)
    Observable<UserInfo> login(@Field("userName") String userName ,@Field("userPass") String userPass);

    @POST(NetworkChildren.DCTATION_INDEX)
    Observable<Dictation> dictation_index();

    @POST(NetworkChildren.USERDETAIL)
    Observable<ResultBeen<UserData>> userData();

    @POST(NetworkChildren.USER_PAGE)
    Observable<Package> changePackage() ;

    @FormUrlEncoded
    @POST(NetworkChildren.CHOOSE_STUDY_MODE)
    Observable<BackCode> choseStudyMoed(@Field("status") String status);


    @POST(NetworkChildren.INDEX)
    Observable<UserIndex> reciteIndex();

    @FormUrlEncoded
    @POST(NetworkChildren.UPDATA_PACKAGE)
    Observable<ResultBeen<Void>> updataPackage(@Field("data") String data);

    @POST(NetworkChildren.PACKAGE_LIST)
    Observable<WordPackageBeen> wordPackList();


    @FormUrlEncoded
    @POST(NetworkChildren.PACKAGE_DETAILS)
    Observable<PackageDetails> wordDetails(@Field("catId") String catId ,@Field("page") String page);

    @FormUrlEncoded
    @POST(NetworkChildren.ADD_PACKAGE)
    Observable<ResultBeen<Void>> addPackage(@Field("packageId") String packageId);

    @POST(NetworkChildren.RECITE_WORDS)
    Observable<ResultBeen<Void>> reciteWord();

    @POST(NetworkChildren.REVIEW_CASE)
    Observable<ReviewDialogBeen> reviewCase() ;

    @POST(NetworkChildren.UPDATA_IS_REVIEW)
    Observable<ResultBeen<Void>> updataIsReview();

    @POST(NetworkChildren.RECITE_WORDS)
    Observable<RecitWordBeen> reciteWords();


    @FormUrlEncoded
    @POST(NetworkChildren.REVIEW_CASE_WORDS)
    Observable<WordReviewTodayBeen> reviewCaseWords(@Field("status") String status);

    @FormUrlEncoded
    @POST(NetworkChildren.WORD_DETAILS)
    Observable<RecitWordBeen> wordDetails(@Field("wordsId") String wordId);

    @POST(NetworkChildren.IS_REVIEW)
    Observable<ResultBeen<Void>> isReview();

    @FormUrlEncoded
    @POST(NetworkChildren.REVIEWE_UPDATA)
    Observable<ResultBeen<Void>> reviewUpdata(@Field("wordsId") String wordId , @Field("status") String status);

    @FormUrlEncoded
    @POST(NetworkChildren.UPDATA_STATUS)
    Observable<ResultBeen<Void>> updataStatus(@Field("wordsId") String wordId , @Field("status") String status);

    @POST(NetworkChildren.REVIEW_INDEX)
    Observable<ReviewMainBeen> reviewMain();


    @POST(NetworkChildren.WRONG_INDEX)
    Observable<List<WrongIndexBeen>> wrongIndex();

    @FormUrlEncoded
    @POST(NetworkChildren.WRONG_WORDS)
    Observable<List<String>> wrongWords(@Field("start") String start );

    @FormUrlEncoded
    @POST(NetworkChildren.TIME_SELECT)
    Observable<List<String>> timeSelect(@Field("start") String start , @Field("end") String end);

    @POST(NetworkChildren.DCTATION_INDEX)
    Observable<Dictation> dictationIndex();

    @FormUrlEncoded
    @POST(NetworkChildren.DICTATION_WORDS)
    Observable<List<String>> dictationWords(@Field("status") String status );

    @FormUrlEncoded
    @POST(NetworkChildren.ERROR_RECOVERY)
    Observable<ResultBeen<Void>> errorRecovery(@Field("type") String type , @Field("wordId") String wordId  ,@Field("content") String content );

    @POST(NetworkChildren.USER_SIGN)
    Observable<SingBeen> userSign();

    @POST(NetworkChildren.SIGN)
    Observable<ResultBeen<Void>> sing();

    @POST(NetworkChildren.TRACK)
    Observable<TrackBeen> track();

    @POST(NetworkChildren.EVA_START)
    Observable<ResultBeen<Void>> evaStart();

    @POST(NetworkChildren.EV_WORDS)
    Observable<EvaWordBeen> evaWord();

    @FormUrlEncoded
    @POST(NetworkChildren.EV_ANSWER)
    Observable<EVAnswerBeen> evAnswer(@Field("wordsId") String wordsId , @Field("type") String type  , @Field("answer") String answer , @Field("duration") String duration , @Field("status") String isKnow);

    @POST(NetworkChildren.EV_RESULT)
    Observable<WordResultBeen> evResult();

    @POST(NetworkChildren.EV_RANK)
    Observable<UserRankBeen> evRank();

    @POST(NetworkChildren.WORD_REPORT)
    Observable<WordReportBeen> wordReport();


    @FormUrlEncoded
    @POST(NetworkChildren.API_REPORT)
    Observable<WordReportMonthBeen> monthWordReport(@Field("month") String month);


    @POST(NetworkChildren.PK_INDEX)
    Observable<PkIndexBeen> pkIndex();

    @POST(NetworkChildren.PK_MATCHING)
    Observable<ResultBeen<Void>> pkMatching();

    @FormUrlEncoded
    @POST(NetworkChildren.PK_CHOSE)
    Observable<ResultBeen<Void>> pkChose(@Field("uid") String uid , @Field("type") String type);


    @FormUrlEncoded
    @POST(NetworkChildren.PK_ANSWER)
    Observable<ResultBeen<Void>> pkAnswer(@Field("totalId") String totalId ,@Field("wordsId") String wordsId ,
                                                 @Field("answer") String answer ,@Field("type") String type ,@Field("duration") String duration);

    @FormUrlEncoded
    @POST(NetworkChildren.PK_FINSH)
    Observable<ResultBeen<Void>> pkFinsh(@Field("uid") String uid ,@Field("totalId") String totalId );

    @FormUrlEncoded
    @POST(NetworkChildren.PK_POLL)
    Observable<ResultBeen<Void>> pkPoll(@Field("uid2") String uid ,@Field("totalId") String totalId );

    @FormUrlEncoded
    @POST(NetworkChildren.PK_RESULT)
    Observable<PkResultBeen> pkResult(@Field("uid") String uid , @Field("totalId") String totalId );


    @FormUrlEncoded
    @POST(NetworkChildren.PK_DISCOVER)
    Observable<PkWordData> pkDiscover(@Field("page") String page ,@Field("pageSize") String pageSize );


    @Multipart
    @POST(NetworkChildren.USER_IMAGE)
    Observable<ImageBean> replaceHeader(@Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST(NetworkChildren.CHANGE_NICKNAME)
    Observable<ResultBeen<Void>> setNickName(@Field("nickname") String nickName  );


    @POST(NetworkChildren.SEND_CODE)
    Observable<ResultBeen<Void>> sendCode();

    @FormUrlEncoded
    @POST(NetworkChildren.PHONE_CODE)
    Observable<ResultBeen<Void>> phoneCode(@Field("phoneNum") String phoneNum ,@Field("type") String type  );

    @FormUrlEncoded
    @POST(NetworkChildren.EMAIL_CODE)
    Observable<ResultBeen<Void>> emailCode(@Field("email") String phoneNum ,@Field("type") String type  );


    @FormUrlEncoded
    @POST(NetworkChildren.UPDATE_USER)
    Observable<ResultBeen<Void>> updatePhone(@Field("uid") String uid  ,@Field("phone") String phone ,@Field("code") String code  );

    @FormUrlEncoded
    @POST(NetworkChildren.UPDATE_USER)
    Observable<ResultBeen<Void>> updateEmail(@Field("uid") String uid  ,@Field("email") String email ,@Field("code") String code  );

    @FormUrlEncoded
    @POST(NetworkChildren.UPDATE_USER)
    Observable<ResultBeen<Void>> updatePassword(@Field("uid") String uid  ,@Field("oldPass") String olbPassword ,@Field("newPass") String newPassword  );

    @GET
    Observable<UpdateLocalDbData> updateLocalData(@Url String url);

    @GET("index.php?web/appapi/versions")
    Observable<VersionInfo> getUpdate();

    @FormUrlEncoded
    @POST(NetworkChildren.FEED_BACK)
    Observable<ResultBeen<Void>> feedback(@Field("content") String content);

    @POST(NetworkChildren.RIM)
    Observable<RoundBean> roundHome();

    @POST(NetworkChildren.CASE_LIST)
    Observable<List<RoundBean.CaseBean>> caseList();

    @FormUrlEncoded
    @POST(NetworkChildren.COURSE_LIST)
    Observable<List<CourseBean>> courseList(@Field("type") int  type);

    @FormUrlEncoded
    @POST(NetworkChildren.SEARCH_WORDS)
    Observable<List<WordBean>> search(@Field("str") String str);
}
