package thinku.com.word.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import thinku.com.word.R;
import thinku.com.word.bean.PackageDetails;
import thinku.com.word.utils.AudioTools.IMAudioManager;
import thinku.com.word.utils.ColorUtils;
import thinku.com.word.utils.StringUtils;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WordAdapter extends RecyclerView.Adapter {
    final static String TAG = WordAdapter.class.getSimpleName();
    private Context context ;
    private List<PackageDetails.Word> wordList ;

    public WordAdapter(Context context ,List<PackageDetails.Word> wordList){
        this.context = context ;
        this.wordList = wordList ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WordHolder wordHolder = new WordHolder(LayoutInflater.from(context).inflate(R.layout.item_word ,parent ,false));
        return wordHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordHolder wordHolder = (WordHolder) holder;

        final  PackageDetails.Word word = wordList.get(position);
        wordHolder.word.setText(word.getWord());
        String translate = StringUtils.spilt(word.getTranslate());
        wordHolder.phonetic_uk.setText(word.getPhonetic_us());
        wordHolder.translate.setText(translate);
        wordHolder.rl.setBackgroundResource(ColorUtils.getPackDetailsColor(context ,word.getFirstStatus()));
        wordHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /* audioUrl音频网络路径 */
                IMAudioManager.instance().playSound(word.getUs_audio(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return null== wordList ? 0 : wordList.size();
    }

    class WordHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rl ;
        private ImageView music ;
        private TextView word ,phonetic_uk ,translate ;
        public WordHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            music = (ImageView) itemView.findViewById(R.id.music);
            word = (TextView) itemView.findViewById(R.id.word);
            phonetic_uk = (TextView) itemView.findViewById(R.id.phonetic_uk);
            translate = (TextView) itemView.findViewById(R.id.translate);
        }
    }

}
