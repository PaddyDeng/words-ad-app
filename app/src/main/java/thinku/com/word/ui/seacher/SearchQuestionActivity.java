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
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import thinku.com.word.R;
import thinku.com.word.base.BaseNoImmActivity;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.periphery.adapter.SearchQuestionAdapter;
import thinku.com.word.utils.Utils;

import static thinku.com.word.utils.LogUtils.log;

public class SearchQuestionActivity extends BaseNoImmActivity {

    //    @BindView(R.id.question_search_cancel_btn)
//    Button cancelBtn;
    @BindView(R.id.question_search_input)
    EditText inputEdt;
    @BindView(R.id.question_search_shadow)
    View shadow;
    @BindView(R.id.question_search_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_no_result_msg)
    LinearLayout noResult;

    private SearchQuestionAdapter mAdapter;
    private List<WordBean> words;
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

    public static void startAct(Context c, String content) {
        Intent intent = new Intent(c, SearchQuestionActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        c.startActivity(intent);
    }

    protected void getArgs() {
        Intent intent = getIntent();
        if (intent == null) return;
        content = intent.getStringExtra(Intent.EXTRA_TEXT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question);
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

    @Override
    public AnimType getAnimType() {
        return AnimType.ANIM_TYPE_UP_IN;
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
                WordEvaluateFragment.start(SearchQuestionActivity.this, wordBean.getId());
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

    private void searchQuestion(String s) {
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

        //  搜索单词
    }

    @OnClick({R.id.question_search_cancel_btn, R.id.question_search_shadow})
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击“取消”按钮，及半透明背景，均退出页面
            case R.id.question_search_cancel_btn:
            case R.id.question_search_shadow:
                finish();
                break;
        }
    }
}
