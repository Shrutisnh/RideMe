package container.loader;

/**
 * Created by G09561636 on 8/11/2016.
 */
public interface RequestListener {
    public void onResponseReceived(ResponseData respData);
    public void onError(ResponseData respData);
}
