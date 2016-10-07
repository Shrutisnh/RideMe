package services.RideServices;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

import container.common.Container;
import container.handlers.JSONServiceListener;
import container.loader.LoaderData;
import container.loader.WebServicesHandler;
import services.data.ServiceConstants;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class ServiceObject implements JSONServiceListener{
    protected JSONObject data;
    protected Vector params;

    public static String PREV_OPCODE ="PREV_OPCODE";
    public static String PREV_RES_TIME="PREV_TIME";
    public static String JSON_OP_MESSAGE 			= "op_msg";
    protected WebServicesHandler handler;
    private ServiceListener listener;
    protected ServiceObject(ServiceListener listener,String[] prevOpCodeData){
        params=new Vector();
        params.addElement(new LoaderData(PREV_OPCODE,prevOpCodeData[0]));
        params.addElement(new LoaderData(PREV_RES_TIME,prevOpCodeData[1]));
        handler = Container.getInstance().getFactory().getWebServicesHandler();
        setListener(listener);
    }


    public ServiceResponse parseHeader(String action,short statusFlag,JSONObject jsonObj){
        ServiceResponse response=new ServiceResponse(statusFlag,action);
       JSONObject successResponseModel= jsonObj.optJSONObject("successResponseModel");
       JSONObject failureResponseModel= jsonObj.optJSONObject("failureResponseModel");
        JSONObject responseModel=(successResponseModel==null)?((failureResponseModel==null)?jsonObj:failureResponseModel):successResponseModel;
        JSONObject header=responseModel.optJSONObject("hdr");
        if(header!=null) {
            response.setResponseId(header.optString("resId"));
            response.setResponseCode(header.optString("resCode"));
            response.setResponseMessage(header.optString("resMsg"));
        }
        setData(responseModel.optJSONObject("data"));
        return response;
    }
    @Override
    public void parseJSON(String action, JSONObject response) throws JSONException {

    }

    @Override
    public void parseError(String action, String errorCode, String errorMessage, JSONObject jsonObj) {
        ServiceListener serviceListener = getListener();
        ServiceResponse response=null;
        if(serviceListener!=null){
            String errMsg=null;
            ServiceResponse resp=null;

            if(jsonObj==null){
                Hashtable errTable=handler.getDefaultErrorObject();
                response=new ServiceResponse(ServiceResponse.ERROR_STATUS,action);
                errMsg=(errorCode!=null && errTable.containsKey(errorCode))?(String)errTable.get(errorCode): ServiceConstants.MSG_GENERAL_ERROR_TXT;
            }else{
                response=parseHeader(action,ServiceResponse.ERROR_STATUS,jsonObj);
                errMsg=jsonObj.optString(JSON_OP_MESSAGE);
                response.setResponseData(jsonObj);
            }
            response.setErrorMsg(errMsg);
            response.setErrorCode(errorCode);
            serviceListener.onServiceError(errorCode,response);
        }
    }

    protected void onServiceCompletion(ServiceResponse response){
        try{
            ServiceListener listener =getListener();
            if(listener!=null){
                listener.onServiceCompletion(response);
            }
        }catch (Exception e){
            Log.i("Client exception",e.toString());
        }
    }

    protected void onServiceError(String errorCode,ServiceResponse response){
        try{
            ServiceListener listener=getListener();
            if(listener!=null){
                listener.onServiceError(errorCode,response);
            }
        }catch(Exception e){
            Log.i("Client exception",e.toString());
        }
    }


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public ServiceListener getListener() {
        return listener;
    }

    public void setListener(ServiceListener listener) {
        this.listener = listener;
    }
}
