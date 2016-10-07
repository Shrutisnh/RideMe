package services.data;

import org.json.JSONObject;

/**
 * Created by G09561636 on 8/22/2016.
 */
public class OfferRide {
    private String isUserLogged;

    public OfferRide(JSONObject jsonObj){
        if(jsonObj==null)
            return;
        setIsUserLogged(jsonObj.optString("isUserLogged"));
    }

    public String getIsUserLogged() {
        return isUserLogged;
    }

    public void setIsUserLogged(String isUserLogged) {
        this.isUserLogged = isUserLogged;
    }
}
