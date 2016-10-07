package common.activity.ride.findRide;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import adapter.CardAdapter;
import app.data.RideMeAppData;
import app.data.RideMeData;
import app.data.RideMeSession;
import common.activity.ride.common.DemoActivity;
import common.activity.ride.common.GlobalNavigation;
import common.activity.ride.common.R;
import common.activity.ride.common.SearchOnMapActivity;
import controller.RequestDispatcher;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceResponse;
import services.data.FindRide;
import services.data.OfferedRideData;
import services.data.ServiceConstants;

public class FindRideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ServiceListener {
    private GlobalNavigation globalNavigation;
    private EditText etCity;
    private EditText etPickup;
    private EditText etDrop;
    private ListView cityListView;
    private ListView pickupListView;
    private ListView dropListView;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> pickupAdapter;
    private ArrayAdapter<String> dropAdapter;
    private List<Address> addressList;
    private Button submitBtn;
    private String errorMsg;
    private RequestDispatcher dispatcher;
    private ArrayList<OfferedRideData> list;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private int LOCATION_REQUEST;
    private int PICKUP_LOCATION_REQUEST=2;
    private int DROP_LOCATION_REQUEST=3;
    private boolean isMapActivityOpen =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        initialiseUI();
        enableUI();
    }
    public void initialiseUI(){
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
        globalNavigation=new GlobalNavigation(this,drawer,navigationView);
        etCity=(EditText)findViewById(R.id.city);
        etPickup=(EditText)findViewById(R.id.pickup);
        etDrop=(EditText)findViewById(R.id.drop);
        cityListView=(ListView)findViewById(R.id.cityListView);
        pickupListView=(ListView)findViewById(R.id.pickupListView);
        dropListView=(ListView)findViewById(R.id.dropListView);
        submitBtn=(Button)findViewById(R.id.submitFindRide);
        submitBtn.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Express.ttf"));
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }
    public void enableUI(){
        final Geocoder geocoder=new Geocoder(getBaseContext());
        cityAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, RideMeAppData.cities);
        cityListView.setAdapter(cityAdapter);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityListView.setVisibility(View.VISIBLE);
                FindRideActivity.this.cityAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etCity.setText(FindRideActivity.this.cityAdapter.getItem(0));
                cityListView.setVisibility(View.GONE);
            }
        });

        etPickup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    isMapActivityOpen =false;
                if( !isMapActivityOpen && s.length()>0) {
                    isMapActivityOpen =true;
                    Intent intent=new Intent(FindRideActivity.this, SearchOnMapActivity.class);
                    LOCATION_REQUEST=PICKUP_LOCATION_REQUEST;
                    startActivityForResult(intent, LOCATION_REQUEST);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        pickupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etPickup.setText(FindRideActivity.this.pickupAdapter.getItem(0));
                pickupListView.setVisibility(View.GONE);
            }
        });
        etDrop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    isMapActivityOpen=false;
                else{
                    if(!isMapActivityOpen){
                        isMapActivityOpen=true;
                        Intent intent=new Intent(FindRideActivity.this, SearchOnMapActivity.class);
                        LOCATION_REQUEST=DROP_LOCATION_REQUEST;
                        startActivityForResult(intent, LOCATION_REQUEST);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etDrop.setText(FindRideActivity.this.dropAdapter.getItem(0));
                dropListView.setVisibility(View.GONE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAll()){
                    Hashtable table=new Hashtable<>();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", 0);
                    SharedPreferences.Editor editor=pref.edit();
                    if(pref.getString(RideMeSession.USERNAME,null)!=null){
                        table.put("isUserLogged","Y");
                    }else{
                        table.put("isUserLogged","N");
                    }

                    table.put("city",etCity.getText());
                    table.put("pickupLocation",etPickup.getText());
                    table.put("dropLocation",etDrop.getText());
                    dispatcher=RequestDispatcher.getInstance();
                    dispatcher.sendDataToWebService(RequestDispatcher.FIND_RIDE,FindRideActivity.this,table);

                }else{
                    buildValidationAlertMsg(errorMsg);
                }
            }
        });



    }
    private boolean validateCity(){
        return etCity.getText().length()>0?true:false;
    }

    private boolean validatePickupLocation(){
        return etPickup.getText().length()>0?true:false;
    }

    private boolean validateDropLocation(){
        return etDrop.getText().length()>0?true:false;
    }

    private boolean validateAll(){
        boolean valid=false;
        if(validateCity()){
            if(validatePickupLocation()){
                if(validateDropLocation()){
                    valid=true;
                }else{
                    errorMsg=getString(R.string.editTextValidation)+" "+getString(R.string.drop);
                    etDrop.requestFocus();
                }
            }else{
                errorMsg=getString(R.string.editTextValidation)+" "+getString(R.string.pickup);
                etPickup.requestFocus();
            }
        }else {
            errorMsg=getString(R.string.editTextValidation)+" "+getString(R.string.city);
            etCity.requestFocus();
        }
        return valid;
    }

    private void buildValidationAlertMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== PICKUP_LOCATION_REQUEST){
            if(data.hasExtra(RideMeData.LOCATION_PARAM)){
                etPickup.setText(data.getStringExtra(RideMeData.LOCATION_PARAM));
            }
        }else{
            if(requestCode==DROP_LOCATION_REQUEST){
                if(data.hasExtra(RideMeData.LOCATION_PARAM)){
                    etDrop.setText(data.getStringExtra(RideMeData.LOCATION_PARAM));
                }
            }
        }
        isMapActivityOpen=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      return globalNavigation.createOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return globalNavigation.onOptionsItemSelected(id);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
            return globalNavigation.onNavigationClick(item.getItemId());
    }




    @Override
    public void onServiceCompletion(ServiceResponse response) {
    Log.i("INFO","In onServiceCompletion");
        if(ServiceConstants.FIND_RIDE.equals(response.getOpCode())){
            FindRide rideData=(FindRide) response.getResponseData();
            list=rideData.getOfferedRideList();
            cardAdapter=new CardAdapter(this,list);
            recyclerView.setAdapter(cardAdapter);
            cardAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onServiceError(String errorCode, ServiceResponse response) {
    Log.i("INFO","In onServiceError");
    }


}
