package controller;

import java.util.HashMap;
import java.util.Hashtable;

import app.RideMeSingleton;
import container.common.Container;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceResponse;
import services.data.ServiceConstants;
import services.handler.FindRideHandler;
import services.handler.LoginHandler;
import services.handler.OfferRideHandler;

/**
 * Created by G09561636 on 8/4/2016.
 */
public class RequestDispatcher implements ServiceListener{

    public static final int FIND_RIDE=0;
    public static final int OFFER_RIDE=1;
    public static final int LOGIN_AUTHENTICATION=2;
    public static final int SIGNUP=3;
    public String[] opCodes={"OP0100","OP0101"};
    private String[] requestType={"Find ride","Offer ride"};
    private String[] prevOpCodes;
    private ServiceListener appListener;
    private static RequestDispatcher instance;
//    private Container container;
    private final String URL= "";
    private RequestDispatcher(){
        container.android.Environment env=new container.android.Environment(URL);
        if(app.Environment.ENV_TYPE.equals("SIT")){
            env.setUrl("");//set the correct url for server
        }

        try{
            Container   container =Container.initialize(env);
        }catch (Exception e){
                e.printStackTrace();
        }
    }
    public static RequestDispatcher getInstance()
    {
        if(instance==null)
        {
            instance=new RequestDispatcher();

        }
        return (RequestDispatcher)instance;
    }
    public synchronized void sendDataToWebService(int opCode, ServiceListener listener,Object inputData)
    {
        this.appListener=listener;
        switch (opCode)
        {
            case FIND_RIDE:{
                RideMeSingleton.getInstance().setPrevOpCodeData(ServiceConstants.FIND_RIDE,"1");
                prevOpCodes= RideMeSingleton.getInstance().getPrevOpCodes();
                Hashtable findRideDetails=(Hashtable)inputData;
                new FindRideHandler(this,prevOpCodes).findRide(findRideDetails);
                break;
            }
            case OFFER_RIDE:{
                RideMeSingleton.getInstance().setPrevOpCodeData(ServiceConstants.OFFER_RIDE,"1");
                prevOpCodes= RideMeSingleton.getInstance().getPrevOpCodes();
                HashMap offerRideDetails=(HashMap)inputData;
                new OfferRideHandler(this,prevOpCodes).offerRide(offerRideDetails);
                break;
            }
            case LOGIN_AUTHENTICATION:{
                String[] data=(String[])inputData;
                new LoginHandler(this,prevOpCodes).authenticate(data);
                break;
            }
            case SIGNUP:{
                String[] data=(String[])inputData;
                new LoginHandler(this,prevOpCodes).signUp(data);
                break;
            }

        }
    }

    @Override
    public void onServiceError(String errorCode, ServiceResponse response) {

    }

    @Override
    public void onServiceCompletion(ServiceResponse response) {
        String opCode="";
        String responseTime="";
        if(response!=null){
            opCode=response.getOpCode();
            appListener.onServiceCompletion(response);
        }

    }
}
