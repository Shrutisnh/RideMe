package services.RideServices;


import java.util.ArrayList;

/**
 * Created by G09561636 on 8/4/2016.
 */
public class ServiceResponse {
    private short statusFlag;
    private String opCode;
    private String errorMsg;
    private String errorCode;
    private Object responseData;

    private String responseId;
    private String responseCode;
    private String serviceVersion;
    private String responseMessage;
    private String responseHeaderCode;
    private String responseHeaderMessage;

    private ArrayList responseMessageList;

    public static final short SUCCESS_STATUS    = 0;
    public static final short WARNING_STATUS    = 1;
    public static final short ERROR_STATUS      = 2;
    public static final short FATAL_STATUS      = 3;

    public ServiceResponse(short statusFlag){
        this.statusFlag=statusFlag;
    }

    public ServiceResponse(short statusFlag,String opCode){
        this.statusFlag=statusFlag;
        this.opCode=opCode;
    }

    public short getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(short statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseHeaderCode() {
        return responseHeaderCode;
    }

    public void setResponseHeaderCode(String responseHeaderCode) {
        this.responseHeaderCode = responseHeaderCode;
    }

    public String getResponseHeaderMessage() {
        return responseHeaderMessage;
    }

    public void setResponseHeaderMessage(String responseHeaderMessage) {
        this.responseHeaderMessage = responseHeaderMessage;
    }

    public ArrayList getResponseMessageList() {
        return responseMessageList;
    }

    public void setResponseMessageList(ArrayList responseMessageList) {
        this.responseMessageList = responseMessageList;
    }


}
