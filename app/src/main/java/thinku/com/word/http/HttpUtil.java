package thinku.com.word.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.Dictation;
import thinku.com.word.bean.EVAnswerBeen;
import thinku.com.word.bean.EvaWordBeen;
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
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.ui.pk.been.PkWordData;

public class HttpUtil {
    private HttpUtil() {
    }

    private static RestApi getRestApi(@HostType.HostTypeChecker int hostType) {
        return RetrofitProvider.getInstance(hostType).create(RestApi.class);
    }


    //重置session
    //================================================================
    public static Observable<Response<Void>> toefl(Map<String, String> userInfo) {
//        return getRestApi(HostType.TOEFL_URL_HOST).toefl(uid, userName, password, email, phone).compose(new SchedulerTransformer<>());
        return getRestApi(HostType.TOEFL_URL_HOST).toefl(userInfo).compose(new SchedulerTransformer<Response<Void>>());
    }

    public static Observable<Response<Void>> smartapply(Map<String, String> userInfoe) {
        return getRestApi(HostType.SMARTAPPLY_URL_HOST).smartapply(userInfoe).compose(new SchedulerTransformer<Response<Void>>());
    }

    public static Observable<Response<Void>> gmatl(Map<String, String> userInfoee) {
        return getRestApi(HostType.BASE_URL_HOST).gmatl(userInfoee).compose(new SchedulerTransformer<Response<Void>>());
    }

    public static Observable<Response<Void>> gossip(Map<String, String> userInfoe) {
        return getRestApi(HostType.GOSSIP_URL_HOST).gossip(userInfoe).compose(new SchedulerTransformer<Response<Void>>());
    }

    public static Observable<Response<Void>> word(Map<String, String> userInfo) {
        return getRestApi(HostType.WORDS_URL_HOST).word(userInfo).compose(new SchedulerTransformer<Response<Void>>());
    }

    //  login   先loign 获取userInfo  然后 获取session

    public static Observable<UserInfo> login(String userName, String userPass) {
        return getRestApi(HostType.LOGIN_REGIST_HOST).login(userName, userPass).compose(new SchedulerTransformer<UserInfo>());
    }



    public static Observable<Dictation> dictation(){
        return getRestApi(HostType.WORDS_URL_HOST).dictation_index().compose(new SchedulerTransformer<Dictation>());
    }

    /**
     *   背单词接口
     */
    //  获取用户信息

    public static Observable<ResultBeen<UserData>> getUserData(){
        return getRestApi(HostType.WORDS_URL_HOST).userData().compose(new SchedulerTransformer<ResultBeen<UserData>>());
    }

    //  修改单词计划
    public static Observable<Package> changePackage(){
        return getRestApi(HostType.WORDS_URL_HOST).changePackage().compose(new SchedulerTransformer<Package>());
    }

    //  修改学习模式
    public static Observable<BackCode> choseStudyMode(String status){
        return getRestApi(HostType.WORDS_URL_HOST).choseStudyMoed(status).compose(new SchedulerTransformer<BackCode>());
    }
    //   选择计划和词包的用户首页

    public static Observable<UserIndex> reciteIndex(){
        return getRestApi(HostType.WORDS_URL_HOST).reciteIndex().compose(new SchedulerTransformer<UserIndex>());
    }

    //  修改词包计划

    public static Observable<ResultBeen<Void>> updataPackage(String data){
        return getRestApi(HostType.WORDS_URL_HOST).updataPackage(data).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  获取词包计划包
    public static Observable<WordPackageBeen> wordPackList(){
        return getRestApi(HostType.WORDS_URL_HOST).wordPackList().compose(new SchedulerTransformer<WordPackageBeen>());
    }

    //  词包详情
    public static Observable<PackageDetails> packageDetailsObservable(String catId ,String page){
        return getRestApi(HostType.WORDS_URL_HOST).wordDetails(catId ,page).compose(new SchedulerTransformer<PackageDetails>());
    }

    // 添加词包
    public static Observable<ResultBeen<Void>> addPackageObservable(String packageId){
        return getRestApi(HostType.WORDS_URL_HOST).addPackage(packageId).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  开始背单词
    public static Observable<ResultBeen<Void>> reciteWordObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reciteWord().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    // 背单词弹窗
    public static Observable<ReviewDialogBeen> reviewCaseObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reviewCase().compose(new SchedulerTransformer<ReviewDialogBeen>());
    }

    // 复习单词
    public static Observable<ResultBeen<Void>> updataIsReviewObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).updataIsReview().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    // 复习单词详情
    public static Observable<RecitWordBeen> reciteWordsObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reciteWords().compose(new SchedulerTransformer<RecitWordBeen>());
    }

    //复习单词
    public static Observable<WordReviewTodayBeen> wordReviewTodayBeenObservable(String status){
        return getRestApi(HostType.WORDS_URL_HOST).reviewCaseWords(status).compose(new SchedulerTransformer<WordReviewTodayBeen>());
    }

    //单词详情
    public static Observable<RecitWordBeen> wordDetailsObservable(String wordId){
        return getRestApi(HostType.WORDS_URL_HOST).wordDetails(wordId).compose(new SchedulerTransformer<RecitWordBeen>());
    }

    //根据复习模式后台返回改复习情况
    public static Observable<ResultBeen<Void>> isReviewObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).isReview().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //背单词上传对单词认识状态
    public static Observable<ResultBeen<Void>> reviewUpdataObservable(String wordId ,String status){
        return getRestApi(HostType.WORDS_URL_HOST).reviewUpdata(wordId, status).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //复习时上传对单词认识状态
    public static Observable<ResultBeen<Void>> updataStatus(String wordId ,String status){
        return getRestApi(HostType.WORDS_URL_HOST).updataStatus(wordId, status).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //复习主界面
    public static Observable<ReviewMainBeen> reviewMainObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reviewMain().compose(new SchedulerTransformer<ReviewMainBeen>());
    }

    //复习主界面
    public static Observable<List<WrongIndexBeen>> wrongIndexObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).wrongIndex().compose(new SchedulerTransformer<List<WrongIndexBeen>>());
    }

    //复习主界面
    public static Observable<List<String>> wrongIndexObservable(String start){
        return getRestApi(HostType.WORDS_URL_HOST).wrongWords( start).compose(new SchedulerTransformer<List<String>>());
    }

    //时间复习
    public static Observable<List<String>> timeSelectObservable(String start,String end){
        return getRestApi(HostType.WORDS_URL_HOST).timeSelect( start ,end).compose(new SchedulerTransformer<List<String>>());
    }

    //听写练习主界面
    public static Observable<Dictation> dictationObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).dictationIndex().compose(new SchedulerTransformer<Dictation>());
    }

    //听写练习单词详情
    public static Observable<List<String>> dictationWordsObservable(String status){
        return getRestApi(HostType.WORDS_URL_HOST).dictationWords( status).compose(new SchedulerTransformer<List<String>>());
    }

    //报错提交
    public static Observable<ResultBeen<Void>> errorRecoveryObservable(String type ,String wordId ,String content){
        return getRestApi(HostType.WORDS_URL_HOST).errorRecovery( type ,wordId ,content).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //用户签到首页
    public static Observable<SingBeen> userSingObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).userSign().compose(new SchedulerTransformer<SingBeen>());
    }

    //用户签到
    public static Observable<ResultBeen<Void>> singObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).sing().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //单词报告轨迹
    public static Observable<TrackBeen> trackObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).track().compose(new SchedulerTransformer<TrackBeen>());
    }

    //单词报告轨迹
    public static Observable<ResultBeen<Void>> evaStartObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).evaStart().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //单词报告轨迹
    public static Observable<EvaWordBeen> evaWordBeenObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).evaWord().compose(new SchedulerTransformer<EvaWordBeen>());
    }

    //提交单词答案
    public static Observable<EVAnswerBeen> evAnseerObservable(String wordIds , String type , String answer , String duration , String isKnow){
        return getRestApi(HostType.WORDS_URL_HOST).evAnswer( wordIds ,type ,answer ,duration ,isKnow).compose(new SchedulerTransformer<EVAnswerBeen>());
    }

    //提交单词答案
    public static Observable<WordResultBeen> evResultObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).evResult().compose(new SchedulerTransformer<WordResultBeen>());
    }

    //获取rank排名
    public static Observable<UserRankBeen> evRankObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).evRank().compose(new SchedulerTransformer<UserRankBeen>());
    }

    //获取单词报表数据
    public static Observable<WordReportBeen> wordReportBeenObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).wordReport().compose(new SchedulerTransformer<WordReportBeen>());
    }

    //月报切换数据
    public static Observable<WordReportMonthBeen> wordMonthReportObservable(String month){
        return getRestApi(HostType.WORDS_URL_HOST).monthWordReport(month).compose(new SchedulerTransformer<WordReportMonthBeen>());
    }

    //PKIndex
    public static Observable<PkIndexBeen> pkIndexObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).pkIndex().compose(new SchedulerTransformer<PkIndexBeen>());
    }

    //PKIndex
    public static Observable<ResultBeen<Void>> pkMatchObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).pkMatching().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //PKIndex
    public static Observable<ResultBeen<Void>> pkChoseObservable(String uid ,String type){
        return getRestApi(HostType.WORDS_URL_HOST).pkChose(uid ,type).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //PK 回答问题
    public static Observable<ResultBeen<Void>> pkAnswerObservable(String totalId ,String wordsId ,String answer ,String type ,String duration){
        return getRestApi(HostType.WORDS_URL_HOST).pkAnswer(totalId ,wordsId ,answer  ,type ,duration).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //PK 完成请求结果
    public static Observable<ResultBeen<Void>> pkFinshObservable(String uid ,String totalId){
        return getRestApi(HostType.WORDS_URL_HOST).pkFinsh(uid,totalId ).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //PK 完成请求结果
    public static Observable<ResultBeen<Void>> pkPollObservable(String uid ,String totalId){
        return getRestApi(HostType.WORDS_URL_HOST).pkPoll(uid,totalId ).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //PK 结果
    public static Observable<PkResultBeen> pkResultBeenObservable(String uid , String totalId){
        return getRestApi(HostType.WORDS_URL_HOST).pkResult(uid,totalId ).compose(new SchedulerTransformer<PkResultBeen>());
    }

    //PK 子页
    public static Observable<PkWordData> pkDiscoverObservable(String page ,String pageSize){
        return getRestApi(HostType.WORDS_URL_HOST).pkDiscover(page ,pageSize).compose(new SchedulerTransformer<PkWordData>());
    }


    // 更换头像
    public static Observable<ImageBean> uploadHeader(MultipartBody.Part file){
        return getRestApi(HostType.WORDS_URL_HOST).replaceHeader(file).compose(new SchedulerTransformer<ImageBean>());
    }


    //PK 子页
    public static Observable<ResultBeen<Void>> newModifyName(String nickName){
        return getRestApi(HostType.LOGIN_REGIST_HOST).setNickName(nickName).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

}
