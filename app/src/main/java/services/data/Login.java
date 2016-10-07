package services.data;

import org.json.JSONObject;

/**
 * Created by G09561636 on 8/22/2016.
 */
public class Login {
    private String statusFlag;
    private String sessionId;
    private String statusMsg;

    public Login(JSONObject jsonObj){
        if(jsonObj==null){
            return;
        }
        setStatusFlag(jsonObj.optString("statusFlag"));
        setSessionId(jsonObj.optString("sessionId"));
        setStatusMsg(jsonObj.optString("statusMsg"));

    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
