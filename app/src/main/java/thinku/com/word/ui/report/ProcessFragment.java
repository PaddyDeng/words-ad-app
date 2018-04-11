package thinku.com.word.ui.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;
import thinku.com.word.R;
import thinku.com.word.adapter.GMATBagAdapter;
import thinku.com.word.adapter.WordRankAdapter;

/**
 * 背单词轨迹
 */

public class ProcessFragment extends SupportFragment {

    private TextView total_day, total_num, know_num, unknow_num, name, total_word_num, ranking;
    private RecyclerView bag_list, ranking_list;
    private CircleImageView portrait;

    private GMATBagAdapter gmatBagAdapter;
    private WordRankAdapter wordRankAdapter ;
    public static ProcessFragment newInstance() {
        ProcessFragment processFragment = new ProcessFragment();
        return processFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_process, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        initAdapter();
    }

    private void findView(View v) {
        total_day = (TextView) v.findViewById(R.id.total_day);
        total_num = (TextView) v.findViewById(R.id.total_num);
        know_num = (TextView) v.findViewById(R.id.know_num);
        unknow_num = (TextView) v.findViewById(R.id.unknow_num);
        bag_list = (RecyclerView) v.findViewById(R.id.bag_list);
        portrait = (CircleImageView) v.findViewById(R.id.portrait);
        name = (TextView) v.findViewById(R.id.name);
        total_word_num = (TextView) v.findViewById(R.id.total_word_num);
        ranking = (TextView) v.findViewById(R.id.ranking);
        ranking_list = (RecyclerView) v.findViewById(R.id.ranking_list);
    }

    /**
     *  初始化adapter
     */
    public void initAdapter() {
        gmatBagAdapter = new GMATBagAdapter(getActivity());
        bag_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        bag_list.setAdapter(gmatBagAdapter);
        wordRankAdapter = new WordRankAdapter(_mActivity);
        ranking_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        ranking_list.setAdapter(wordRankAdapter);

    }

}
