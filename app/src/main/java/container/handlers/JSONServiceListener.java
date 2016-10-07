package container.handlers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by G09561636 on 8/11/2016.
 */
public interface JSONServiceListener {
    public  void parseJSON(String action, JSONObject response) throws JSONException;
    public void parseError(String action, String errorCode, String errorMessage,JSONObject response);
}

