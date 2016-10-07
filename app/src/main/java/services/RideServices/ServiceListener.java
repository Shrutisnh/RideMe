package services.RideServices;

/**
 * Created by G09561636 on 8/4/2016.
 */
public interface ServiceListener  {
    public void onServiceCompletion(ServiceResponse response);
    public void onServiceError(String errorCode,ServiceResponse response);
}
