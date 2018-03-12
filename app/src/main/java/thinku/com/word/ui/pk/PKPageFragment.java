package thinku.com.word.ui.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;
import thinku.com.word.view.ProgressView;

/**
 * PK子页
 */

public class PKPageFragment extends BaseFragment{

    private TextView to_pk,name,win_num,lose_num,vocabulary;
    private ImageView portrait;
    private ProgressView winning_probability;
    private RecyclerView ranking_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pk_page,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        setClick();
    }

    private void setClick() {
        to_pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findView(View v) {
        to_pk = (TextView) v.findViewById(R.id.to_pk);
        portrait = (ImageView) v.findViewById(R.id.portrait);
        name = (TextView) v.findViewById(R.id.name);
        winning_probability = (ProgressView) v.findViewById(R.id.winning_probability);
        win_num = (TextView) v.findViewById(R.id.win_num);
        lose_num = (TextView) v.findViewById(R.id.lose_num);
        vocabulary = (TextView) v.findViewById(R.id.vocabulary);
        ranking_list = (RecyclerView) v.findViewById(R.id.ranking_list);
    }
}
