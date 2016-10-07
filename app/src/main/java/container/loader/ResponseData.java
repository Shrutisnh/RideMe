package container.loader;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class ResponseData {

    private byte[] data;
    private String action, errorCode,resStat;

    public ResponseData(String action,byte[] data,String errorCode){
        setData(data);
        setAction(action);
        setErrorCode(errorCode);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResStat() {
        return resStat;
    }

    public void setResStat(String resStat) {
        this.resStat = resStat;
    }
}
