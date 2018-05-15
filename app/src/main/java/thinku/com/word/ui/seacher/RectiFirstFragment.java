package thinku.com.word.ui.seacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import thinku.com.word.R;
import thinku.com.word.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RectiFirstFragment extends BaseFragment {

    private WordListBean recitWordBeen ;
    public static RectiFirstFragment newInstance(WordListBean recitWordBeen){
        RectiFirstFragment rectiFirstFragment = new RectiFirstFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data" ,recitWordBeen);
        rectiFirstFragment.setArguments(bundle);
        return rectiFirstFragment ;
    }


    @BindView(R.id.click)
    ImageView click;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;
    Unbinder unbinder;

    public RectiFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recitWordBeen = getArguments().getParcelable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recti_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_click)
    public void click(){
        start(WordTwoFragment.newInstance(recitWordBeen));
    }
}
