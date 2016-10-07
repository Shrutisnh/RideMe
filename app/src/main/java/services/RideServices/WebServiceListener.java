package services.RideServices;

import services.RideServices.ServiceResponse;

/**
 * Created by G09561636 on 8/10/2016.
 */
public interface WebServiceListener {

    public void onResponseReceived(String opCode,ServiceResponse response);
    public void onError(String opCode,ServiceResponse response);
}
