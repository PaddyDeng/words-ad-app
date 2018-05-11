package thinku.com.word.ui.periphery.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fire on 2017/10/16  11:23.
 */

public class Conf implements Serializable{
    private String urlStart;
    private String hls;
    private List<String> pngs;
    private List<Integer> times;

    public String getUrlStart() {
        return urlStart;
    }

    public void setUrlStart(String urlStart) {
        this.urlStart = urlStart;
    }

    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public List<String> getPngs() {
        return pngs;
    }

    public void setPngs(List<String> pngs) {
        this.pngs = pngs;
    }

    public List<Integer> getTimes() {
        return times;
    }

    public void setTimes(List<Integer> times) {
        this.times = times;
    }
}
