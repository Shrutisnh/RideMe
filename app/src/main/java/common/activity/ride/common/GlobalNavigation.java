package common.activity.ride.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import app.data.RideMeSession;
import common.activity.ride.findRide.FindRideActivity;
import common.activity.ride.offerRide.OfferRideActivity;

/**
 * Created by Shruti Sinha on 7/11/2016.
 */
public class GlobalNavigation {
    private Context context;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    public GlobalNavigation(Context context, DrawerLayout mDrawerLayout, NavigationView mNavigationView){
        this.context=context;
        this.mDrawerLayout=mDrawerLayout;
        this.mNavigationView=mNavigationView;
    }

    public boolean onNavigationClick(int id){
        switch (id){
            case R.id.nav_findRide:
                if(!((Activity)context).getClass().getSimpleName().equals(FindRideActivity.class.getSimpleName())) {
                    Intent findRideIntent = new Intent((Activity) context, FindRideActivity.class);
                    ((Activity) context).startActivity(findRideIntent);
                    ((Activity)context).finish();

                }
                break;

            case R.id.nav_offerRide:
                if(!((Activity)context).getClass().getSimpleName().equals(OfferRideActivity.class.getSimpleName())){
                    Intent offerRideIntent =new Intent((Activity)context, OfferRideActivity.class);
                    ((Activity)context).startActivity(offerRideIntent);
                    ((Activity)context).finish();
                }
                break;


        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean createOptionsMenu(Menu menu){
        ((Activity)context).getMenuInflater().inflate(R.menu.home_page, menu);
        RideMeSession session= new RideMeSession(context.getApplicationContext());
        Log.i("INFO:",""+((Activity)context).getClass().getSimpleName()+" "+context.getApplicationContext());
        if(!session.isLoggedIn()) {
            MenuItem itemLogout = menu.findItem(R.id.logout);
            itemLogout.setVisible(false);
        }
        return true;
    }
    public boolean onOptionsItemSelected(int id){
        switch(id){
            case R.id.action_settings:

                break;
        }
        return true;
    }
}
