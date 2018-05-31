package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.adapter.PackageTwoAdapter;
import thinku.com.word.adapter.WordPackageAdapter;
import thinku.com.word.base.BaseActivity;
import thinku.com.word.bean.WordPackageBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.other.MainActivity;
import thinku.com.word.utils.LoginHelper;

public class WordPackageActivity extends BaseActivity {
    private static final String TAG = WordPackageActivity.class.getSimpleName() ;
    private static final int REQUEST_CODE = 101 ;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_t)
    TextView titleT;
    @BindView(R.id.title_right_t)
    TextView titleRightT;
    @BindView(R.id.word_package_title)
    RecyclerView wordPackageTitle;
    @BindView(R.id.word_package)
    RecyclerView wordPackage;
    private WordPackageAdapter wordPackageAdapter  ;
    private PackageTwoAdapter packageTwoAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        ButterKnife.bind(this);
        titleT.setText("雷哥词包");
        initRecy();
        initData();
    }

    public void initRecy(){
        wordPackageTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void start(Context context){
        Intent intent = new Intent(context ,WordPackageActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    public void initData(){
        addToCompositeDis(HttpUtil.wordPackList()
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                showLoadDialog();
            }
        }).subscribe(new Consumer <WordPackageBeen>() {
                    @Override
                    public void accept( final @NonNull WordPackageBeen wordpackage) throws Exception {
                        dismissLoadDialog();
                        if (wordpackage.getCode() == 99) {
                           LoginHelper.needLogin(WordPackageActivity.this  ,getResources().getString(R.string.str_need_login) ,REQUEST_CODE);
                        } else {
                            wordPackageAdapter = new WordPackageAdapter(WordPackageActivity.this, wordpackage.getPackages(), new SelectListener() {
                                @Override
                                public void setListener(int position) {
                                    final WordPackageBeen.WordPackage wordPackageBeen = wordpackage.getPackages().get(position);
                                    packageTwoAdapter = new PackageTwoAdapter(WordPackageActivity.this, new SelectListener() {
                                        @Override
                                        public void setListener(int position) {
                                            WordbagDetailActivity.start(WordPackageActivity.this, wordPackageBeen.getChild().get(position).getId()
                                                    , wordPackageBeen.getChild().get(position).getTotal(), wordPackageBeen.getChild().get(position).getUserWords()
                                                    , wordPackageBeen.getChild().get(position).getName() , wordPackageBeen.getChild().get(position).getIs());
                                        }
                                    }, wordPackageBeen.getChild()
                                    );
                                    wordPackage.setLayoutManager(new LinearLayoutManager(WordPackageActivity.this));
                                    wordPackage.setAdapter(packageTwoAdapter);
                                }
                            });
                            wordPackageTitle.setAdapter(wordPackageAdapter);
                            wordPackageAdapter.setmPosition(0);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            this.finishWithAnim();
            MainActivity.toMain(this);
        }
    }
}
