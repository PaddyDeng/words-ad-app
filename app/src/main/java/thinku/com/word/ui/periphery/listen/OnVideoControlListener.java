package thinku.com.word.ui.periphery.listen;

public interface OnVideoControlListener {

    /**
     * 返回
     */
    void onBack();

    /**
     * 直接关闭
     */
//    void onClose();

    /**
     * 隐藏
     */
    void showAndHide();
    /**
     * 全屏
     */
    void onFullScreen();

    /**
     * 错误后的重试
     *
     * @param errorStatus 当前错误状态
     *                    <ul>
     *                    <li>{@link com.boredream.bdvideoplayer.view.VideoErrorView#STATUS_NORMAL}
     *                    <li>{@link com.boredream.bdvideoplayer.view.VideoErrorView#STATUS_VIDEO_DETAIL_ERROR}
     *                    <li>{@link com.boredream.bdvideoplayer.view.VideoErrorView#STATUS_VIDEO_SRC_ERROR}
     *                    <li>{@link com.boredream.bdvideoplayer.view.VideoErrorView#STATUS_UN_WIFI_ERROR}
     *                    <li>{@link com.boredream.bdvideoplayer.view.VideoErrorView#STATUS_NO_NETWORK_ERROR}
     *                    </ul>
     */
    void onRetry(int errorStatus);

}
