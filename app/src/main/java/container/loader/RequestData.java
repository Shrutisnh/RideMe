package container.loader;

import java.util.Vector;

/**
 * Created by G09561636 on 8/12/2016.
 */
public class RequestData {
    private Vector params;
    private String url,action,resStat,resMsg;
    private RequestListener requestListener;
    private Object ServiceListener;


    public RequestData(String url, String action, RequestListener listener, Vector params){
        setUrl(url);
        setAction(action);
        setParams(params);
        setRequestListener(listener);
    }

    public Vector getParams() {
        return params;
    }

    public void setParams(Vector params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResStat() {
        return resStat;
    }

    public void setResStat(String resStat) {
        this.resStat = resStat;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public Object getServiceListener() {
        return ServiceListener;
    }

    public void setServiceListener(Object serviceListener) {
        ServiceListener = serviceListener;
    }
}
