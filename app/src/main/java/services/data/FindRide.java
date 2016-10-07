package services.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by G09561636 on 8/4/2016.
 */
public class FindRide {
    private ArrayList<OfferedRideData> offeredRideList;

    public FindRide(JSONObject jsonObj){
    if(jsonObj==null){
        return;
    }
        JSONArray jsonOfferedRideList=jsonObj.optJSONArray("offeredRidesList");
        if(jsonOfferedRideList!=null){
            int len=jsonOfferedRideList.length();
            ArrayList list=new ArrayList(len);
            for(int i=0;i<len;i++){
                OfferedRideData offeredRideData=new OfferedRideData(jsonOfferedRideList.optJSONObject(i));
                list.add(offeredRideData);

            }
            setOfferedRideList(list);
        }

    }

    public ArrayList<OfferedRideData> getOfferedRideList() {
        return offeredRideList;
    }

    public void setOfferedRideList(ArrayList<OfferedRideData> offeredRideList) {
        this.offeredRideList = offeredRideList;
    }
}
