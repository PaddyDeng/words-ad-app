package thinku.com.word.ui.seacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import thinku.com.word.R;
import thinku.com.word.base.BaseNoImmActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.SearchQuestionAdapter;
import thinku.com.word.ui.report.WordEvaluateFragment;
import thinku.com.word.utils.Utils;

import static thinku.com.word.utils.LogUtils.log;

public class PicSearchActivity extends BaseNoImmActivity {


    @BindView(R.id.question_search_cancel_btn)
    Button questionSearchCancelBtn;

    public static void startAct(Context c, String content) {
        Intent intent = new Intent(c, PicSearchActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        c.startActivity(intent);
    }

    @BindView(R.id.question_search_input)
    EditText inputEdt;
    @BindView(R.id.question_search_shadow)
    RelativeLayout shadow;
    @BindView(R.id.question_search_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_no_result_msg)
    LinearLayout noResult;

    private SearchQuestionAdapter mAdapter;
    private List<WordBean> words;
    private List<String> recordQuestionIds;
    private String content;
    private static final int SEARCH_WHAT = 99;//搜索题目
    private static final int SEARCH_WAIT = SEARCH_WHAT + 1;//等待搜索
    private static final int WAIT_SEARCH_TIME = 400;//等待搜索时间
    private String needSearchStr;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            if (what == SEARCH_WHAT) {
                String str = (String) msg.obj;
                if (!TextUtils.equals(needSearchStr, str)) {
                    needSearchStr = str;
                }
            }
            switch (what) {
                case SEARCH_WHAT:
                    if (mHandler.hasMessages(SEARCH_WAIT)) {
                        mHandler.removeMessages(SEARCH_WAIT);
                    }
                    mHandler.sendEmptyMessageDelayed(SEARCH_WAIT, WAIT_SEARCH_TIME);
                    break;
                case SEARCH_WAIT:
                    searchQuestion(needSearchStr);
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    protected void getArgs() {
        Intent intent = getIntent();
        if (intent == null) return;
        content = intent.getStringExtra(Intent.EXTRA_TEXT);
        log(content);
        inputEdt.setText(content);
        inputEdt.setSelection(content.length());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_search);
        ButterKnife.bind(this);

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        getArgs();
        initView();
    }

    protected void initView() {
        inputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                log(s.toString());
                //内容改变
                Message message = mHandler.obtainMessage();
                message.what = SEARCH_WHAT;
                message.obj = s.toString();
                mHandler.sendMessage(message);
//                searchQuestion(s.toString());
            }
        });
        words = new ArrayList<>();
        mAdapter = new SearchQuestionAdapter(this, words);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.SelectListener(new SelectListener() {
            @Override
            public void setListener(int position) {
                WordBean wordBean = words.get(position);
                WordEvaluateFragment.start(PicSearchActivity.this, wordBean.getId());
            }
        });
        mRecyclerView.addItemDecoration(new RecycleViewLinearDivider(mContext, LinearLayoutManager.VERTICAL, R.drawable.gray_one_height_divider));
        mRecyclerView.setAdapter(mAdapter);

        if (!TextUtils.isEmpty(content)) {
            inputEdt.setText(content);
            inputEdt.setSelection(content.length());
        }
        shadow.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.keyBordShowFromWindow(mContext, shadow);
            }
        }, 200);
    }

    private void searchQuestion(final String s) {
        if (TextUtils.isEmpty(s)) {
            Utils.setVisible(noResult);
            Utils.setGone(shadow, mRecyclerView);
            return;
        }

        addToCompositeDis(HttpUtil.seacherWordObservable(s)
                .subscribe(new Consumer<List<WordBean>>() {
                    @Override
                    public void accept(@NonNull List<WordBean> wordBeen) throws Exception {
                        if (wordBeen != null && wordBeen.size() > 0) {
                            words.clear();
                            words.addAll(wordBeen);
                            mAdapter.notifyDataSetChanged();
                            Utils.setVisible(mRecyclerView);
                            Utils.setGone(shadow, noResult);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Utils.setVisible(noResult);
                        Utils.setGone(shadow, mRecyclerView);
                    }
                }));

    }

    @OnClick({R.id.question_search_cancel_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击“取消”按钮，及半透明背景，均退出页面
            case R.id.question_search_cancel_btn:
                finish();
                break;
        }
    }
}
