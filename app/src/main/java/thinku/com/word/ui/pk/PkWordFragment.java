package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.http.HttpUtil;
import thinku.com.word.ui.pk.adapter.PkWordAdapter;
import thinku.com.word.ui.pk.been.PkWordData;

/**
 * PK单词团
 */

public class PkWordFragment extends BaseFragment {
    private static final String TAG = PkWordFragment.class.getSimpleName();
    @BindView(R.id.pk_word_rl)
    RecyclerView pkWordRl;
    Unbinder unbinder;
    @BindView(R.id.refer)
    SwipeRefreshLayout refer;

    private PkWordAdapter pkWordAdapter ;
    private List<PkWordData.DataBean>  dataBeanList ;
    private int page = 1 ;
    public static PkWordFragment newInstance() {
        PkWordFragment pkPageFragment = new PkWordFragment();
        return pkPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk_word, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecy();
        addNet();
        return view;
    }

    public void initRecy(){
        dataBeanList = new ArrayList<>();
        pkWordAdapter = new PkWordAdapter(_mActivity , dataBeanList);
        pkWordRl.setLayoutManager(new LinearLayoutManager(_mActivity));
        pkWordRl.setAdapter(pkWordAdapter);

        refer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refer.setRefreshing(false);
                addNet();
            }
        });
    }

    /**
     *  获取数据
     */
    public void addNet(){
        addToCompositeDis(HttpUtil.pkDiscoverObservable(page +"" ,"20")
        .subscribe(new Consumer<PkWordData>() {
            @Override
            public void accept(@NonNull PkWordData pkWordData) throws Exception {
                if (getHttpResSuc(pkWordData.getCode())){
                    if (pkWordData.getData()!= null && pkWordData.getData().size() >0){
                        referUi(pkWordData.getData());
                    }
                }
            }
        }));
    }


    public void referUi(List<PkWordData.DataBean> dataBeans){
        dataBeanList.addAll(dataBeans);
        pkWordAdapter.notifyDataSetChanged();
        page++;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}