package common.activity.ride.offerRide;



import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.data.RideMeData;
import app.data.RideMeSession;
import common.activity.ride.common.GlobalNavigation;
import common.activity.ride.common.R;
import common.activity.ride.common.SearchOnMapActivity;
import controller.RequestDispatcher;
import fragment.DatePickerFragment;
import fragment.TimePickerFragment;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceResponse;
import services.data.OfferRide;

public class OfferRideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerFragment.OnSetDate, TimePickerFragment.OnSetTime,ServiceListener {
    private GlobalNavigation globalNavigation;
    private EditText etStart;
    private EditText etEnd;
    private EditText etFair;
    private Button dateBtn;
    private Button timeBtn;
    private ArrayAdapter<String> startPointAdapter;
    private ArrayAdapter<String> endPointAdapter;
    private ListView startingPointListView;
    private ListView endPointListView;
    private List<Address> addressList;
    private Button submitBtn;
    private boolean isMapActivityOpen =false;
    private RequestDispatcher dispatcher;
    private int LOCATION_REQUEST;
    private int START_POINT=2;
    private int END_POINT=3;

 private String errorMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);
        initialiseUI();
        enableUI();
    }

    public void initialiseUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        globalNavigation = new GlobalNavigation(this, drawer, navigationView);
        etStart=(EditText)findViewById(R.id.startingPoint);
        etEnd=(EditText)findViewById(R.id.endPoint);

        dateBtn=(Button)findViewById(R.id.date);
        dateBtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Express.ttf"));
        timeBtn=(Button)findViewById(R.id.time);
        timeBtn.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Express.ttf"));
        startingPointListView=(ListView)findViewById(R.id.listViewStartPoint);
        endPointListView=(ListView)findViewById(R.id.listViewEndPoint);
        submitBtn=(Button)findViewById(R.id.submitOfferRide);
        submitBtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Express.ttf"));
        etFair=(EditText)findViewById(R.id.fair);
    }

    public void enableUI(){
        //date button's actions
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragment=getFragmentManager();
                DatePickerFragment dateDialog= DatePickerFragment.newInstance(new Date());
                dateDialog.show(fragment,"Date");

            }
        });
        //time button actions
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragment=getFragmentManager();
                TimePickerFragment timeDialog=new TimePickerFragment();
                timeDialog.show(fragment,"Time");
            }
        });
        //starting and end point actions
        final Geocoder geocoder=new Geocoder(getBaseContext());

        etStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    isMapActivityOpen =false;
                if( !isMapActivityOpen && s.length()>0) {
                    isMapActivityOpen =true;
                    Intent intent=new Intent(OfferRideActivity.this, SearchOnMapActivity.class);
                    LOCATION_REQUEST=START_POINT;
                    startActivityForResult(intent, LOCATION_REQUEST);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    isMapActivityOpen =false;
                if( !isMapActivityOpen && s.length()>0) {
                    isMapActivityOpen =true;
                    Intent intent=new Intent(OfferRideActivity.this, SearchOnMapActivity.class);
                    LOCATION_REQUEST=END_POINT;
                    startActivityForResult(intent, LOCATION_REQUEST);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startingPointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etStart.setText(OfferRideActivity.this.startPointAdapter.getItem(0));
                startingPointListView.setVisibility(View.GONE);
            }
        });

        endPointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etEnd.setText(OfferRideActivity.this.endPointAdapter.getItem(0));
                endPointListView.setVisibility(View.GONE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAll()){
                    Log.i("INFO:OfferRideActivity","In if block of submit btn onClickListener");
                    dispatcher=RequestDispatcher.getInstance();
                    Map map=new HashMap();
                    map.put("startingPoint",etStart.getText());
                    map.put("endPoint",etStart.getText());
                    map.put("date",dateBtn.getText());
                    map.put("time",timeBtn.getText());
                    map.put("fair",etFair.getText());
                    map.put("isUserLogged","");
                    dispatcher.sendDataToWebService(RequestDispatcher.OFFER_RIDE,OfferRideActivity.this,map);
                }else{
                    Log.i("INFO:OfferRideActivity","In else block of submit btn onClickListener");
                    buildValidationAlertMsg(errorMsg);
                }

            }
        });
    }
    public boolean validateAll(){
        boolean valid=false;
            if(validateStartingPoint()){
                if(validateEndPoint()){
                    if(validateDate()){
                        if(validateTime()){
                            if(validateFair()){
                                valid=true;
                            }
                            else{
                               errorMsg=getString(R.string.editTextValidation) +" "+getString(R.string.fair);
                                etFair.requestFocus();
                            }
                        }else{
                            errorMsg=getString(R.string.selectionValidation)+" "+getString(R.string.time);
                        }
                    }else{
                        errorMsg=getString(R.string.selectionValidation)+" "+getString(R.string.date);
                    }
                }else{
                    errorMsg=getString(R.string.editTextValidation)+" "+getString(R.string.endPoint);
                    etEnd.requestFocus();
                }

            }else{
                errorMsg=getString(R.string.editTextValidation)+" "+getString(R.string.startingPoint);
                etStart.requestFocus();
            }
        return valid;
    }

    public boolean validateStartingPoint(){
        return etStart.getText().length()>0?true:false;
    }

    public boolean validateEndPoint(){
        return etEnd.getText().length()>0?true:false;
    }

    public boolean validateDate(){
        return (!dateBtn.getText().equals(getString(R.string.date)));
    }

    public boolean validateTime(){
        return (!timeBtn.getText().equals(getString(R.string.time)));
    }

    public boolean validateFair(){
        return etFair.getText().length()>0?true:false;
    }

    private void buildValidationAlertMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return globalNavigation.onNavigationClick(item.getItemId());
    }

    @Override
    public void onDateSet(Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        dateBtn.setText(sdf.format(date));
    }

    @Override
    public void onTimeSet(Date date) {
    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        timeBtn.setText(sdf.format(date));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(startingPointListView.getVisibility()==View.VISIBLE){
            startingPointListView.setVisibility(View.INVISIBLE);
        }
        if(endPointListView.getVisibility()==View.VISIBLE){
            endPointListView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== START_POINT){
            if(data.hasExtra(RideMeData.LOCATION_PARAM)){
                etStart.setText(data.getStringExtra(RideMeData.LOCATION_PARAM));
            }
        }else{
            if(requestCode==END_POINT){
                if(data.hasExtra(RideMeData.LOCATION_PARAM)){
                    etEnd.setText(data.getStringExtra(RideMeData.LOCATION_PARAM));
                }
            }
        }
        isMapActivityOpen=false;
    }

    @Override
    public void onServiceCompletion(ServiceResponse response) {
        OfferRide data=(OfferRide) response.getResponseData();
        if(data.getIsUserLogged().equals("Y")){

        }else{
            RideMeSession rideSession =RideMeSession.getInstance();

        }
    }

    @Override
    public void onServiceError(String errorCode, ServiceResponse response) {

    }
}
