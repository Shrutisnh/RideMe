package common.activity.ride.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import app.RideMeSingleton;
import lib.SimpleLocation;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private SimpleLocation currLoc;

    @Nullable
    @Override
    protected void onResume() {
        super.onResume();

        LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if(getCallingActivity()!=null) {
            Log.i("INFO:Calling activity", getCallingActivity().getClassName());
        }

        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("INFO:SplashActivity", "In on Resume if block");
                    if(RideMeSingleton.getCntxt().equals("Return")) {
                        {
                            Intent i = new Intent(SplashActivity.this, HomePageActivity.class);
                            startActivity(i);
                        }

                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tvTitle=(TextView)findViewById(R.id.textView);
        Typeface myCustomFont= Typeface.createFromAsset(getAssets(),"fonts/Express.ttf");
        tvTitle.setTypeface(myCustomFont);

        RideMeSingleton.setCntxt("SplashActivity");
        RideMeSingleton.setContext(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    Log.i("INFO:SplashActivity","In onCreate if block");
                    buildAlertMessageNoGps();
                }else{
                    Log.i("INFO:SplashActivity","In onCreate else block");
                    Intent i=new Intent(SplashActivity.this,HomePageActivity.class);
                    startActivity(i);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        finish();
                    }
                });
        RideMeSingleton.setCntxt("Return");
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
