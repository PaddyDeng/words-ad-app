package thinku.com.word.ui.recite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

/**
 * 词库
 */

public class ThesaurusActivity extends BaseActivity {

    private ImageView back;
    private TextView title_t;
    private RecyclerView title_list,content_list;

    public static void start(Context context){
        Intent intent =new Intent(context,ThesaurusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thesaurus);
        findView();
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        title_t = (TextView) findViewById(R.id.title_t);
        title_t.setText("雷哥词包");
        title_list = (RecyclerView) findViewById(R.id.title_list);
        content_list = (RecyclerView) findViewById(R.id.content_list);
        LinearLayoutManager titleManager =new LinearLayoutManager(ThesaurusActivity.this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager contentManager =new LinearLayoutManager(ThesaurusActivity.this,LinearLayoutManager.VERTICAL,false);
        title_list.setLayoutManager(titleManager);
        content_list.setLayoutManager(contentManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
