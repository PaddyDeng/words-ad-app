package thinku.com.word.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import thinku.com.word.bean.BackCode;
import thinku.com.word.bean.BaiduBean;
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
import thinku.com.word.ui.periphery.bean.CourseBean;
import thinku.com.word.ui.periphery.bean.RoundBean;
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.ui.personalCenter.bean.SignBean;
import thinku.com.word.ui.personalCenter.update.bean.VersionInfo;
import thinku.com.word.ui.personalCenter.update.localdb.UpdateLocalDbData;
import thinku.com.word.ui.pk.been.PkWordData;
import thinku.com.word.ui.report.bean.ReviewBean;
import thinku.com.word.ui.report.bean.ReviewCaseBean;
import thinku.com.word.ui.seacher.WordBean;
import thinku.com.word.ui.seacher.WordListBean;

public class HttpUtil {
    private HttpUtil() {
    }

    private static RestApi getRestApi(@HostType.HostTypeChecker int hostType) {
        return RetrofitProvider.getInstance(hostType).create(RestApi.class);
    }

    private static RestApi getRestApiReciteWord(@HostType.HostTypeChecker int hostType){
        return  RetrofitProvider.getInstance1(hostType).create(RestApi.class);
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

    //  login   先loign 获取userInfo  然后 获取session

    public static Observable<ResultBeen<Void>> findPass(String type,String num, String userPass , String code) {
        return getRestApi(HostType.LOGIN_REGIST_HOST).findPass( type,num, userPass , code).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }



    public static Observable<Dictation> dictation(){
        return getRestApi(HostType.WORDS_URL_HOST).dictation_index().compose(new SchedulerTransformer<Dictation>());
    }

    /**
     * 注册
     * @param type
     * @param registerStr
     * @param pass
     * @param code
     * @param userName
     * @param source
     * @return
     */
    public static Observable<ResultBeen<Void>> register(String type, String registerStr, String pass, String code,
                                                  String userName, String source) {
        return getRestApi(HostType.LOGIN_REGIST_HOST).register(type, registerStr, pass, code, userName, source, "2").
                compose(new SchedulerTransformer<ResultBeen<Void>>());
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
        return getRestApi(HostType.WORDS_URL_HOST).wordDetails(catId ,page ,"20").compose(new SchedulerTransformer<PackageDetails>());
    }

    // 添加词包
    public static Observable<ResultBeen<Void>> addPackageObservable(String packageId){
        return getRestApi(HostType.WORDS_URL_HOST).addPackage(packageId).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    // 添加词包
    public static Observable<ResultBeen<Void>> addPackageObservableOther(String packageId , String planDay ,String planWords,String rank){
        return getRestApi(HostType.WORDS_URL_HOST).addPackageOther(packageId ,planDay , planWords , rank).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  开始背单词
    public static Observable<ResultBeen<Void>> reciteWordObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reciteWord().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    // 背单词弹窗
    public static Observable<ResultBeen<List<String>>> reviewCaseObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reviewCase().compose(new SchedulerTransformer<ResultBeen<List<String>>>());
    }

    // 复习单词
    public static Observable<ResultBeen<Void>> updataIsReviewObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).updataIsReview().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    // 复习单词详情
    public static Observable<RecitWordBeen> reciteWordsObservable(){
        return getRestApiReciteWord(HostType.WORDS_URL_HOST).reciteWords().compose(new SchedulerTransformer<RecitWordBeen>());
    }

    //复习单词
    public static Observable<ReviewBean> wordReviewTodayBeenObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).reviewCaseWords().compose(new SchedulerTransformer<ReviewBean>());
    }

    //单词详情
    public static Observable<RecitWordBeen> wordDetailsObservable(String wordId){
        return getRestApiReciteWord(HostType.WORDS_URL_HOST).wordDetails(wordId).compose(new SchedulerTransformer<RecitWordBeen>());
    }


    //根据复习模式后台返回改复习情况
    public static Observable<ReviewCaseBean> isReviewObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).isReview().compose(new SchedulerTransformer<ReviewCaseBean>());
    }

    //背单词上传对单词认识状态
    public static Observable<ResultBeen<Void>> reviewUpdataObservable(String wordId ,String status , String type){
        return getRestApi(HostType.WORDS_URL_HOST).reviewUpdata(wordId, status , type).compose(new SchedulerTransformer<ResultBeen<Void>>());
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
    public static Observable<List<String>> dictationWordsObservable(String status , String start){
        return getRestApi(HostType.WORDS_URL_HOST).dictationWords( status ,start).compose(new SchedulerTransformer<List<String>>());
    }

    //报错提交
    public static Observable<ResultBeen<Void>> errorRecoveryObservable(String type ,String wordId ,String content){
        return getRestApi(HostType.WORDS_URL_HOST).errorRecovery( type ,wordId ,content ,"android").compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //用户签到首页
    public static Observable<SingBeen> userSingObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).userSign().compose(new SchedulerTransformer<SingBeen>());
    }

    //用户签到
    public static Observable<SignBean> singObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).sing().compose(new SchedulerTransformer<SignBean>());
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
    public static Observable<UserRankBeen> evRankObservable(String page ,String size){
        return getRestApi(HostType.WORDS_URL_HOST).evRank(page ,size).compose(new SchedulerTransformer<UserRankBeen>());
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

    //  获取验证码之前调用
    public static Observable<ResultBeen<Void>> sendCode(){
        return getRestApi(HostType.LOGIN_REGIST_HOST).sendCode().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  手机获取验证码
    public static Observable<ResultBeen<Void>> phoneCodeObservable(String phoneNum ,String type){
        return getRestApi(HostType.LOGIN_REGIST_HOST).phoneCode(phoneNum ,type).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  邮箱获取验证码
    public static Observable<ResultBeen<Void>> emailCodeObservable(String email , String type ){
        return getRestApi(HostType.LOGIN_REGIST_HOST).emailCode(email ,type).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  更新邮箱
    public static Observable<ResultBeen<Void>> updateEmailObservable(String uid ,String email , String code){
        return getRestApi(HostType.LOGIN_REGIST_HOST).updateEmail(uid  , email ,code).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  更新手机号
    public static Observable<ResultBeen<Void>> updatePhoneObservable(String uid ,String phone , String code){
        return getRestApi(HostType.LOGIN_REGIST_HOST).updatePhone(uid  , phone ,code).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  更换密码
    public static Observable<ResultBeen<Void>> updatePasswordObservable(String uid ,String oldPassword , String newPassword){
        return getRestApi(HostType.LOGIN_REGIST_HOST).updatePassword(uid  , oldPassword ,newPassword).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  更新版本
    public static Observable<UpdateLocalDbData> updateLocalData(String url) {
        return getRestApi(HostType.BASE_URL_HOST).updateLocalData(url).compose(new SchedulerTransformer<UpdateLocalDbData>());
    }

    //  获取服务器版本信息
    public static Observable<VersionInfo> getUpdate() {
        return getRestApi(HostType.WORDS_URL_HOST).getUpdate().compose(new SchedulerTransformer<VersionInfo>());
    }


    //  意见反馈信息
    public static Observable<ResultBeen<Void>> feedBackObservable(String content) {
        return getRestApi(HostType.WORDS_URL_HOST).feedback(content).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  周边首页数据
    public static Observable<RoundBean> roundBeanObservable() {
        return getRestApi(HostType.WORDS_URL_HOST).roundHome().compose(new SchedulerTransformer<RoundBean>());
    }

    //  案列列表
    public static Observable<List<RoundBean.CaseBean>> caseBeanObservable() {
        return getRestApi(HostType.WORDS_URL_HOST).caseList().compose(new SchedulerTransformer<List<RoundBean.CaseBean>>());
    }
    //  课程列表
    public static Observable<List<CourseBean>> courseBeanObservable(int type) {
        return getRestApi(HostType.WORDS_URL_HOST).courseList(type).compose(new SchedulerTransformer<List<CourseBean>>());
    }

    //  搜索单词
    public static Observable<List<WordBean>> seacherWordObservable(String words) {
        return getRestApi(HostType.WORDS_URL_HOST).search(words).compose(new SchedulerTransformer<List<WordBean>>());
    }

    // 学习中

    public static Observable<ResultBeen<Void>> updataNowPackage(String catId){
        return getRestApi(HostType.WORDS_URL_HOST).updataNowPackage(catId).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  直播预告
    public static Observable<List<RoundBean.LivePreviewBean.DataBean>> liveListen(String page , String pageSize){
        return getRestApi(HostType.WORDS_URL_HOST).liveList(page ,pageSize).compose(new SchedulerTransformer<List<RoundBean.LivePreviewBean.DataBean>>());
    }

    //  背单词接口
    public static Observable<ResultBeen<Void>> isReciteWordsObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).isReciteWords().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  背单词接口
    public static Observable<ResultBeen<Void>> nowFinshObservable(){
        return getRestApi(HostType.WORDS_URL_HOST).nowFinsh().compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

    //  背单词接口
    public static Observable<List<WrongIndexBeen>> dictionGroupObservable(String status){
        return getRestApi(HostType.WORDS_URL_HOST).dictionGroup(status).compose(new SchedulerTransformer<List<WrongIndexBeen>>());
    }

    //  背单词接口
    public static Observable<ResultBeen<Void>> deletePackageObservable(String id){
        return getRestApi(HostType.WORDS_URL_HOST).deletePackage(id).compose(new SchedulerTransformer<ResultBeen<Void>>());
    }

}
