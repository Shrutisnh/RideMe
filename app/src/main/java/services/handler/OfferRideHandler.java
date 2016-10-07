package services.handler;

import org.json.JSONObject;

import java.util.HashMap;

import container.loader.LoaderData;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceObject;
import services.RideServices.ServiceResponse;
import services.data.OfferRide;
import services.data.ServiceConstants;

/**
 * Created by G09561636 on 8/22/2016.
 */
public class OfferRideHandler extends ServiceObject {
    public static final String STARTING_POINT="startingPoint";
    public static final String END_POINT="endPoint";
    public static final String DATE="date";
    public static final String TIME="time";
    public static final String FAIR="fair";
    public static final String IS_USER_LOGEED="isUserLogged";

    public OfferRideHandler(ServiceListener listener,String[] prevOpCodes){
        super(listener,prevOpCodes);
    }

    public void offerRide(HashMap map){
        params.addElement(new LoaderData(STARTING_POINT,map.get("startingPoint").toString()));
        params.addElement(new LoaderData(END_POINT,map.get("endPoint").toString()));
        params.addElement(new LoaderData(DATE,map.get("date").toString()));
        params.addElement(new LoaderData(TIME,map.get("time").toString()));
        params.addElement(new LoaderData(FAIR,map.get("fair").toString()));
        params.addElement(new LoaderData(IS_USER_LOGEED,map.get("isUserLogged").toString()));
        handler.makePostRequest(ServiceConstants.OFFER_RIDE,params,this);
    }

    public void parseJSON(String action, JSONObject jsonObj){
        Object responseData=null;
        params.removeAllElements();
        ServiceResponse response=super.parseHeader(action,ServiceResponse.SUCCESS_STATUS,jsonObj);
        JSONObject data=getData();
        if(action.equals(ServiceConstants.OFFER_RIDE)){
            responseData=new OfferRide(data);
        }
        response.setResponseData(responseData);
        onServiceCompletion(response);

    }

}
