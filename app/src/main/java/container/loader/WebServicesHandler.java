package container.loader;

import android.util.Log;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

import container.common.Container;
import container.common.ContainerFactory;
import container.handlers.JSONServiceListener;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class WebServicesHandler implements RequestListener {

    private static Hashtable defErrorTable;
    private Container container;
    private ContainerFactory factory;
    private PostRequest postReq;

    public WebServicesHandler(){
        container=Container.getInstance();
        factory=container.getFactory();
        postReq=factory.getPostRequest();
    }
    public Hashtable getDefaultErrorObject(){
        return defErrorTable;
    }
    private void setDefaultErrorObject(){
        defErrorTable=new Hashtable();
        defErrorTable.put(RequestConstants.OP_MSG_FATAL,RequestConstants.MSG_OP_FATAL_TXT);
        defErrorTable.put(RequestConstants.PARSE_FAILURE,RequestConstants.MSG_PARSER_ERROR);
        defErrorTable.put(RequestConstants.AIRPLANE_MODE_ON,RequestConstants.MSG_AIRPLANE_MODE_ON_TXT);
    }

    protected void returnDummyData(String action,Object serviceListener){
        if(serviceListener==null){
            return;
        }
        try{
            JSONObject jsonObj = ResourceLoader.getDummyResponse(action);
            if(serviceListener instanceof JSONServiceListener){
                JSONServiceListener listener =(JSONServiceListener)serviceListener;
                listener.parseJSON(action,jsonObj);
            }
        }
        catch (Exception e){
            Log.i("Exception","In requestHandler"+e.toString());
        }
    }

    public Object makePostRequest(String action, Vector params, JSONServiceListener listener){
        return makePostRequest(container.getEnvironment().getUrl(),action,params,listener);
    }

    public Object makePostRequest(String url, String action, Vector params, Object serviceListener){
        if(app.Environment.IS_STUBBED_BUILD.equals("true")){
         returnDummyData(action,serviceListener);
            return null;
        }else{
            RequestData reqData=new RequestData(url,action,this,params);
            return postReq.load(reqData);
        }
    }
    @Override
    public void onResponseReceived(ResponseData respData) {
            byte[] data=respData.getData();
        String action=respData.getAction();
        String respCode=respData.getResStat();
        if((RequestConstants.OP_MSG_SUCCESS).equals(respCode)){

        }
        byte[] decBody=null;
        JSONObject resObj=null;
    }

    @Override
    public void onError(ResponseData respData) {

    }
}
