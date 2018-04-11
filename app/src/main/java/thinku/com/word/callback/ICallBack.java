package thinku.com.word.callback;

public interface ICallBack<T> {
    void onSuccess(T t);

    void onFail();
}
