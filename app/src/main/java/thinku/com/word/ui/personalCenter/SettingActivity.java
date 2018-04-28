package thinku.com.word.ui.personalCenter;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.ResultBeen;
import thinku.com.word.bean.UserInfo;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.http.NetworkTitle;
import thinku.com.word.permission.RxPermissions;
import thinku.com.word.photo.ClipImageActivity;
import thinku.com.word.ui.personalCenter.bean.ImageBean;
import thinku.com.word.utils.C;
import thinku.com.word.utils.GlideUtils;
import thinku.com.word.utils.ImageUtil;
import thinku.com.word.utils.RxBus;
import thinku.com.word.utils.SharedPreferencesUtils;
import thinku.com.word.version.VersionInfo;

/**
 * Created by Administrator on 2018/2/7.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();
    private ImageView back;
    private CircleImageView portrait ;
    private TextView title_t,name,nick,phone,email,pass,version,font;
    private LinearLayout personal_detail;

    private RelativeLayout portrait_rl,name_rl,nick_rl,phone_rl,email_rl,pass_rl,version_rl,font_rl;

    private UserInfo userInfo ;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //  剪裁图片
    private static final int CROP_PHOTO = 103 ;
    //调用照相机返回图片临时文件
    private File tempFile;

    private Uri imageUri ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView();
        setClick();
        init();
    }

    public static void start(Context context){
        Intent intent =new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 初始化用户信息
     */
    public void init(){
        userInfo = SharedPreferencesUtils.getUserInfo(SettingActivity.this);
        new GlideUtils().loadCircle(SettingActivity.this , NetworkTitle.WORDRESOURE + SharedPreferencesUtils.getImage(SettingActivity.this),portrait);
        name.setText(userInfo.getPhone());
        nick.setText(userInfo.getNickname());
        phone.setText(userInfo.getPhone());
        email.setText(userInfo.getEmail());
        pass.setText(userInfo.getPhone());
        version.setText(VersionInfo.versionName(SettingActivity.this));
    }


    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("账号设置");
        personal_detail = (LinearLayout) findViewById(R.id.personal_detail);
        portrait_rl = (RelativeLayout) findViewById(R.id.portrait_rl);
        portrait = (CircleImageView) findViewById(R.id.portrait);
        name_rl = (RelativeLayout) findViewById(R.id.name_rl);
        name = (TextView) findViewById(R.id.name);
        nick_rl = (RelativeLayout) findViewById(R.id.nick_rl);
        nick = (TextView) findViewById(R.id.nick);
        phone_rl = (RelativeLayout) findViewById(R.id.phone_rl);
        phone = (TextView) findViewById(R.id.phone);
        email_rl = (RelativeLayout) findViewById(R.id.email_rl);
        email = (TextView) findViewById(R.id.email);
        pass_rl = (RelativeLayout) findViewById(R.id.pass_rl);
        pass = (TextView) findViewById(R.id.pass);
        version_rl = (RelativeLayout) findViewById(R.id.version_rl);
        version = (TextView) findViewById(R.id.version);
        font_rl = (RelativeLayout) findViewById(R.id.font_rl);
        font = (TextView) findViewById(R.id.font);
    }
    private void setClick() {
        back.setOnClickListener(this);
        portrait_rl.setOnClickListener(this);
        name_rl.setOnClickListener(this);
        nick_rl.setOnClickListener(this);
        phone_rl.setOnClickListener(this);
        email_rl.setOnClickListener(this);
        pass_rl.setOnClickListener(this);
        version_rl.setOnClickListener(this);
        font_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finishWithAnim();
                break;
            case R.id.portrait_rl:
                headerChoose();
                break;
            case R.id.name_rl:
                toTast(R.string.str_set_modify_user_name_fail_tip);
                break;
            case R.id.nick_rl:
                modifyNickName();
                break;
            case R.id.phone_rl:
                break;
            case R.id.email_rl:
                break;
            case R.id.pass_rl:
                break;
            case R.id.version_rl:
                break;
            case R.id.font_rl:
                break;
        }
    }


    private void modifyNickName() {
        Intent intent = new Intent(mContext, SetNickNameActivity.class);
        startActivityForResult(intent, C.SET_NICK_REQUEST_CODE);
    }

    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, CROP_PHOTO);
    }
    /**
     * 修改相册弹窗
     */
    private void headerChoose() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    gotoCarema();
                                } else {
                                    toTast(SettingActivity.this,SettingActivity.this.getResources().getString(R.string.str_camera_no_permisson));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                            }
                        });
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    gotoPhoto();
                                } else {
                                    toTast(SettingActivity.this ,SettingActivity.this.getResources().getString(R.string.str_read_external_no_permisson));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        });
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 跳转到照相机
     */
    private void gotoCarema() {
            //獲取系統版本
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            // 激活相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 判断存储卡是否可以用，可用进行存储
            if (hasSdcard()) {
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        filename + ".jpg");
                if (currentapiVersion < 24) {
                    // 从文件中创建uri
                    imageUri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    //兼容android7.0 使用共享文件的形式
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                    //检查是否有存储权限，以免崩溃
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        Toast.makeText(this,"请开启存储权限",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                }
            }
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, REQUEST_CAPTURE);
        }


    /*
    * 判断sdcard是否被挂载
    */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.str_please_choose_pic)), REQUEST_PICK);
    }

    /**
     * 上传图片
     * @param cropImagePath
     */
    private void uploadHeader(String cropImagePath) {
        File file = new File(cropImagePath);
        if (!file.exists()) {
            return;
        }
        RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mp = MultipartBody.Part.createFormData("upload", file.getName(), rb);
        addToCompositeDis(HttpUtil.uploadHeader(mp)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadDialog();
                    }
                })
                .subscribe(new Consumer<ImageBean>() {
                    @Override
                    public void accept(@NonNull ImageBean imageBeanResultBeen) throws Exception {
                        dismissLoadDialog();
                        toTast( SettingActivity.this,imageBeanResultBeen.getMessage());
                        if (getHttpResSuc(imageBeanResultBeen.getCode())) {
                            savePhoto(imageBeanResultBeen.getImage());
                            EventBus.getDefault().post(imageBeanResultBeen);
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadDialog();
                    }
                }));
    }


    public void savePhoto(String  imageUrl){
        SharedPreferencesUtils.setImage(SettingActivity.this ,imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 105:
                nick.setText(SharedPreferencesUtils.getNickName(this));
                //这里并不是退出登录，只是发送广播通知个人中心界面更新ui
//                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(C.EXIT_LOGIN_ACTION));

                break;
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(imageUri);
                }
                break;
            case REQUEST_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = ImageUtil.getRealFilePathFromUri(getApplicationContext(), uri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath , options);
                    portrait.setImageBitmap(bitMap);
                    uploadHeader(cropImagePath);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
