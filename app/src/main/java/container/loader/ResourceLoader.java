package container.loader;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;

import app.RideMeSingleton;

/**
 * Created by G09561636 on 8/8/2016.
 */
public class ResourceLoader {

    private static Hashtable responses= null;
    public static JSONObject getDummyResponse(String key) throws JSONException{
        if(responses==null){
            responses=loadFile();
        }
        Object obj=responses.get(key);
        String val = (obj!=null && obj instanceof String)?(String)obj : null;
        if(val==null){
            Log.i("INFO:","No response available for "+key);
        }
        return (val==null ?null : new JSONObject(val));
    }

    public static Hashtable loadFile() {

        try {
            String fileName = "stubbed/Response.properties";

            Hashtable table 		= new Hashtable();
            StringBuffer buf 		= new StringBuffer();
            AssetManager assetManager= RideMeSingleton.getContext().getAssets();
            InputStream in=assetManager.open(fileName);
            InputStreamReader reader = new InputStreamReader(in);

            char[] chars = new char[2048];
            for (int i; (i = reader.read(chars)) != -1; i++) {
                buf.append(chars, 0, i);
            }
            try { in.close(); } catch (Exception e) {}
            in = null;
            try { reader.close(); } catch (Exception e) {}
            reader = null;

            String[] lines = buf.toString().split("\n");
            for (int i = 0; i < lines.length; i++) {

                String line = lines[i].trim();
                if (line.equals("") || line.startsWith("/") || line.startsWith("#")) { continue; }			// Exclude comments
                int index = line.indexOf('=');
                if (index > 0) {
                    String name = line.substring(0, index);
                    String val 	= line.substring(index + 1).trim();
                    table.put(name, val);
                }
            }
            return table;
        } catch (Exception e) {
            Log.i("Exception", e.toString());
        }
        return null;
    }

}
