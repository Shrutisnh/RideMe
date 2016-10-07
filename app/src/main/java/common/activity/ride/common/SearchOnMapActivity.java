package common.activity.ride.common;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.data.RideMeData;

public class SearchOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener,View.OnClickListener {
    private GoogleMap mMap;
    private SearchView svLocation;
    private SupportMapFragment mapFragment;
    private ListView listView;
    private List<Address> list;
    private ArrayAdapter<String> adapter;
    private Map<LatLng,String> map;
    private LatLng selectedLocation;
    private ImageButton back;
    private int LOCATION_REQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_on_map);
        clear();
        initializeUI();
        enableUI();
    }

    public void clear(){
        mMap=null;
    }

    private void initializeUI(){
        svLocation=(SearchView)findViewById(R.id.search) ;
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        svLocation=(SearchView)findViewById(R.id.search);
        listView=(ListView)findViewById(R.id.list);
        back=(ImageButton)findViewById(R.id.back);

    }
    private void enableUI(){

        mapFragment.getMapAsync(this);
        svLocation.setOnQueryTextListener(this);
        listView.setOnItemClickListener(this);
        back.setOnClickListener(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        final Geocoder geocoder=new Geocoder(getBaseContext());
        try{
        if(query.length()>0){
            list=geocoder.getFromLocationName(query.toString(), 5);
            int i=0;
            String[] addressArray=new String[list.size()];
             map=new HashMap<>();
            while(i<list.size()){
                LatLng location=new LatLng(list.get(i).getLatitude(),list.get(i).getLongitude());
                addressArray[i]=list.get(i).getFeatureName()+" "+list.get(i).getSubAdminArea()+" "+list.get(i).getAdminArea()+" "+list.get(i).getCountryName();
                map.put(location,addressArray[i]);
                i++;
            }
            adapter=new ArrayAdapter<String>(SearchOnMapActivity.this,android.R.layout.simple_list_item_1,addressArray);

            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }}
        catch(Exception e){

        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                svLocation.setQuery(adapter.getItem(0),false);
                for(Map.Entry<LatLng,String> entry:map.entrySet())
                    if (entry.getValue().trim().equals(svLocation.getQuery().toString().trim())) {

                        selectedLocation = (entry.getKey());
                        if (mMap != null) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(selectedLocation.latitude, selectedLocation.longitude)));
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selectedLocation.latitude, selectedLocation.longitude), 15));
                        }
                    }
                listView.setVisibility(View.GONE);

    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.putExtra(RideMeData.LOCATION_PARAM,svLocation.getQuery().toString());
        setResult(LOCATION_REQUEST,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra(RideMeData.LOCATION_PARAM,svLocation.getQuery().toString());
        setResult(LOCATION_REQUEST,intent);
        finish();
    }
}
