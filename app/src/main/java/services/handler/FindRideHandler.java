package services.handler;

import org.json.JSONObject;

import java.util.Hashtable;

import container.loader.LoaderData;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceObject;
import services.RideServices.ServiceResponse;
import services.data.FindRide;
import services.data.ServiceConstants;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class FindRideHandler  extends ServiceObject {

    private static final String IS_USER_LOGGED= "isUserLogged";
    private static final String CITY="city";
    private static final String PICKUPLOCATION="pickupLocation";
    private static final String DROPLOCATION="dropLocation";

    public FindRideHandler(ServiceListener listener, String[] prevOpCode){
        super(listener,prevOpCode);
    }
    public void findRide(Hashtable table){
        params.addElement(new LoaderData(IS_USER_LOGGED,table.get("isUserLogged").toString()));
        params.addElement(new LoaderData(CITY,table.get("city").toString()));
        params.addElement(new LoaderData(PICKUPLOCATION,table.get("pickupLocation").toString()));
        params.addElement(new LoaderData(DROPLOCATION,table.get("dropLocation").toString()));

        handler.makePostRequest(ServiceConstants.FIND_RIDE,params,this);
    }
    public void parseJSON(String action, JSONObject jsonObj){
        Object responseData=null;
        params.removeAllElements();
        ServiceResponse response=super.parseHeader(action,ServiceResponse.SUCCESS_STATUS,jsonObj);

        JSONObject data=getData();
        if(action.equals(ServiceConstants.FIND_RIDE)){
            responseData=new FindRide(data);
        }
        response.setResponseData(responseData);
        onServiceCompletion(response);

    }
}
