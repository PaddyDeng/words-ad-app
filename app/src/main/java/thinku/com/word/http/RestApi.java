package thinku.com.word.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.Dictation;
import thinku.com.word.bean.Package;
import thinku.com.word.bean.PackageDetails;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.ReviewDialogBeen;
import thinku.com.word.bean.ReviewMainBeen;
import thinku.com.word.bean.SingBeen;
import thinku.com.word.bean.UserData;
import thinku.com.word.bean.UserIndex;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.bean.WordPackageBeen;
import thinku.com.word.bean.WordReviewTodayBeen;
import thinku.com.word.bean.WrongIndexBeen;

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
}
