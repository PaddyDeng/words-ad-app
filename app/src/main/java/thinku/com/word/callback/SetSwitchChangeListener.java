package thinku.com.word.callback;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2018/5/4.
 */

public interface SetSwitchChangeListener {
    void toggleToOn(SwitchView view, int position);

    void toggleToOff(SwitchView view, int position);
}
