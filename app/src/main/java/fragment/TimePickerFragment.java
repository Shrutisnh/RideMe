package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import common.activity.ride.common.R;

/**
 * Created by G09561636 on 7/18/2016.
 */

public class TimePickerFragment extends DialogFragment {
private TimePicker timePicker;
    public interface OnSetTime{
        void onTimeSet(Date date);
    }

    OnSetTime mCallback;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
        timePicker=(TimePicker)v.findViewById(R.id.time);
        return new AlertDialog.Builder(getActivity()).setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour,min;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                   hour=timePicker.getHour();
                   min=timePicker.getMinute();
                }else{
                  hour=timePicker.getCurrentHour();
                    min=timePicker.getCurrentMinute();
                }
                Date date=new GregorianCalendar(0,0,0,hour,min,0).getTime();
                sendResult(date);
            }
        }).create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback=(OnSetTime)activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+" must implement OnSetTime");
        }
        }


    private void sendResult(Date date){
        mCallback.onTimeSet(date);
    }
}
