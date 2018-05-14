package thinku.com.word.ui.recite;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import thinku.com.word.R;
import thinku.com.word.adapter.ReciteWordParentAdapter;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.bean.RecitWordBeen;
import thinku.com.word.bean.ReciteWordParent;
import thinku.com.word.bean.WordEvaluateEvent;
import thinku.com.word.ui.report.bean.WordBean;
import thinku.com.word.utils.C;

public class WordEvaluateFragment extends BaseFragment {
    private static final String TAG = WordEvaluateFragment.class.getSimpleName();
    @BindView(R.id.mnemonic)
    TextView mnemonic;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.data_list)
    RecyclerView dataList;
    @BindView(R.id.know)
    Button know;
    @BindView(R.id.unknow)
    Button unknow;
    @BindView(R.id.blurry)
    Button blurry;
    @BindView(R.id.bottom_click)
    LinearLayout bottomClick;
    Unbinder unbinder;
    private RecitWordBeen recitWordBeen;
    private WordBean wordBean ;
    private List<ReciteWordParent> reciteWordParents;
    private ReciteWordParentAdapter reciteWordParentAdapter;

    public WordEvaluateFragment() {
        // Required empty public constructor
    }


    public static WordEvaluateFragment newInstance(RecitWordBeen recitWordBeen) {
        WordEvaluateFragment fragment = new WordEvaluateFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", recitWordBeen);
        fragment.setArguments(args);
        return fragment;
    }

    public static WordEvaluateFragment newInstance(WordBean recitWordBeen) {
        WordEvaluateFragment fragment = new WordEvaluateFragment();
        Bundle args = new Bundle();
        args.putParcelable("wordBean", recitWordBeen);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recitWordBeen = getArguments().getParcelable("data");
            wordBean = getArguments().getParcelable("wordBean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_evalaute, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (C.NORMAL.equals(recitWordBeen.getTag())){
            blurry.setText("模糊");
        }else if(C.TAGS.equals(recitWordBeen.getTag())){
            blurry.setText("忘记");
        }
        mnemonic.setText(recitWordBeen.getWords().getTranslate());
        initRecyclerView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mnemonic.setText(recitWordBeen.getWords().getMnemonic());
            initRecyclerView();
        }
    }

    public void initRecyclerView() {
        List<ReciteWordParent> reciteWordParents = reciteWordParents();
        dataList.setLayoutManager(new LinearLayoutManager(_mActivity));
        reciteWordParentAdapter = new ReciteWordParentAdapter(_mActivity, reciteWordParents);
        dataList.setAdapter(reciteWordParentAdapter);
    }

    /**
     * 得到数据
     *
     * @return
     */
    public List<ReciteWordParent> reciteWordParents() {
        reciteWordParents = new ArrayList<>();
        if (!TextUtils.isEmpty(recitWordBeen.getWords().getMnemonic()) & !"".equals(recitWordBeen.getWords().getMnemonic())) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("助句");
            RecitWordBeen.LowSentenceBean sentent = new RecitWordBeen.LowSentenceBean();
            sentent.setChinese(recitWordBeen.getWords().getMnemonic());
            sentent.setId("助句");
            List<RecitWordBeen.LowSentenceBean> arrs = new ArrayList<>();
            reciteWordParent.setSentenceList(arrs);
        }
        if (recitWordBeen.getLowSentence() != null & recitWordBeen.getLowSentence().size() > 0) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("短句");
            reciteWordParent.setSentenceList(recitWordBeen.getLowSentence());
            reciteWordParents.add(reciteWordParent);
        }

        if (recitWordBeen.getSentence() != null & recitWordBeen.getSentence().size() > 0) {
            ReciteWordParent reciteWordParent = new ReciteWordParent();
            reciteWordParent.setName("例句");
            reciteWordParent.setSentenceList(recitWordBeen.getSentence());
            reciteWordParents.add(reciteWordParent);
        }

        if (recitWordBeen.getQuestion() != null ){

        }

        return reciteWordParents;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.unknow ,R.id.know , R.id.blurry})
    public void updateStatus(View view){
        switch (view.getId()){
            case R.id.unknow:
                EventBusActivityScope.getDefault(_mActivity).post(new WordEvaluateEvent(recitWordBeen.getWords().getId() , C.LGWordStatusIncoginzance+"",recitWordBeen.getTag()));
                break;
            case R.id.know:
                EventBusActivityScope.getDefault(_mActivity).post(new WordEvaluateEvent(recitWordBeen.getWords().getId() , C.LGWordStatusKnow+"" ,recitWordBeen.getTag()));
                break;
            case R.id.blurry:
                EventBusActivityScope.getDefault(_mActivity).post(new WordEvaluateEvent(recitWordBeen.getWords().getId() , C.LGWordStatusVague+"" ,recitWordBeen.getTag()));
                break;
        }
    }

}
