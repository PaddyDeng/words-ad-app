package thinku.com.word.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.WordPackageBeen;
import thinku.com.word.callback.SelectListener;
import thinku.com.word.utils.GlideUtils;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WordPackageAdapter extends RecyclerView.Adapter {
    private final static String TAG = WordPackageAdapter.class.getSimpleName();
    private Context context;
    private List<WordPackageBeen.WordPackage> packageList;
    private SelectListener selectListener;
    private int mPosition = 1 ;

    public WordPackageAdapter(Context context, List<WordPackageBeen.WordPackage> packageList, SelectListener selectListener) {
        this.context = context;
        this.packageList = packageList;
        this.selectListener = selectListener;
    }


    public void setmPosition(int mPosition){
        this.mPosition = mPosition ;
        selectListener.setListener(mPosition);
        notifyDataSetChanged();
    }
    public void setData(List<WordPackageBeen.WordPackage> packageList){
       this.packageList = packageList ;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PackageHolder holder = new PackageHolder(LayoutInflater.from(context).inflate(R.layout.item_word_package, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PackageHolder packageHolder = (PackageHolder) holder;
        WordPackageBeen.WordPackage data = packageList.get(position);
        if (mPosition == position) {
            packageHolder.rl.setBackgroundColor(context.getResources().getColor(R.color.gray));
        } else {
            packageHolder.rl.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        if (!TextUtils.isEmpty(data.getImage())) new GlideUtils().load(context, data.getImage(), packageHolder.packageImg);
        packageHolder.title.setText(data.getName());
        packageHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.setListener(position);
                setmPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == packageList ? 0 : packageList.size();
    }

    class PackageHolder extends RecyclerView.ViewHolder {
        private ImageView packageImg;
        private TextView title;
        private AutoRelativeLayout rl;


        public PackageHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            packageImg = (ImageView) itemView.findViewById(R.id.word_package_img);
            title = (TextView) itemView.findViewById(R.id.word_package_txt);
            rl = (AutoRelativeLayout) itemView.findViewById(R.id.rl);
        }
    }

}
