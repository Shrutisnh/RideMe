package services.data;

import org.json.JSONObject;

/**
 * Created by G09561636 on 8/19/2016.
 */
public class OfferedRideData {
    private String ownerName;
    private String carMake;
    private String carModel;
    private String date;
    private String time;
    private String message;
    private String ac;
    private String fair;
    private String contactNo;
    private String pickupLocation;
    private String dropLocation;
    public OfferedRideData(JSONObject jsonObj){
        if(jsonObj==null){
            return;
        }
        setOwnerName(jsonObj.optString("ownerName"));
        setCarMake(jsonObj.optString("carMake"));
        setCarModel(jsonObj.optString("carModel"));
        setDate(jsonObj.optString("date"));
        setTime(jsonObj.optString("time"));
        setMessage(jsonObj.optString("message"));
        setAc(jsonObj.optString("ac"));
        setFair(jsonObj.optString("fair"));
        setContactNo(jsonObj.optString("contactNo"));
        setPickupLocation(jsonObj.optString("pickupLocation"));
        setDropLocation(jsonObj.optString("dropLocation"));

    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getFair() {
        return fair;
    }

    public void setFair(String fair) {
        this.fair = fair;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }
}
