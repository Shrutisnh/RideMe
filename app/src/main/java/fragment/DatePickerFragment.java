package fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import common.activity.ride.common.R;

/**
 * Created by G09561636 on 7/18/2016.
 */
public class DatePickerFragment extends DialogFragment{
    private static final String ARG_DATE        ="date";

    private DatePicker mDatePicker;
    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";


    public interface OnSetDate{
        void onDateSet(Date date);
    }

    OnSetDate mCallback;
    public static DatePickerFragment newInstance(Date date){
        Log.i("INFO:DatePickerFragment","In newInstance");
        Bundle args=new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("INFO:DatePickerFragment","In onCreateDialog");
        Date date=(Date)getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        mDatePicker=(DatePicker)v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getActivity()).setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year=mDatePicker.getYear();
                int month=mDatePicker.getMonth();
                int day=mDatePicker.getDayOfMonth();

                Date date=new GregorianCalendar(year,month,day).getTime();
                sendResult(Activity.RESULT_OK,date);
            }
        }).create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnSetDate) activity;
        }catch(ClassCastException e){
            throw  new ClassCastException(activity.toString()+" must implement OnSetDate");
        }
    }

    private void sendResult(int resultCode, Date date){
        mCallback.onDateSet(date);
    }
}
