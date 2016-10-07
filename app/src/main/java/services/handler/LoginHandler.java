package services.handler;

import org.json.JSONObject;

import container.loader.LoaderData;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceObject;
import services.RideServices.ServiceResponse;
import services.data.Login;
import services.data.ServiceConstants;

/**
 * Created by G09561636 on 8/22/2016.
 */
public class LoginHandler  extends ServiceObject{
    private String username="username";
    private String password="password";

    public LoginHandler(ServiceListener listener,String[] prevOpCodes){
        super(listener,prevOpCodes);
    }

    public void authenticate(String[] data){
        params.add(new LoaderData(username,data[0]));
        params.add(new LoaderData(password,data[1]));

        handler.makePostRequest(ServiceConstants.AUTHENTICATE,params,this);
    }

    public void signUp(String[] data){
        params.add(new LoaderData(username,data[0]));
        params.add(new LoaderData(password,data[1]));

        handler.makePostRequest(ServiceConstants.SIGN_UP,params,this);
    }

    public void parseJSON(String action,JSONObject jsonObj){
            Object responseData=null;
            params.removeAllElements();
        ServiceResponse response=super.parseHeader(action,ServiceResponse.SUCCESS_STATUS,jsonObj);

        JSONObject data=getData();
        if(action.equals(ServiceConstants.AUTHENTICATE)|| action.equals(ServiceConstants.SIGN_UP)){
            responseData=new Login(data);
        }
        response.setResponseData(responseData);
        onServiceCompletion(response);
    }
}
