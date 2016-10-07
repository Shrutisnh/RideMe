package app;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import lib.SimpleLocation;

/**
 * Created by G09561636 on 6/27/2016.
 */
public class RideMeSingleton {
    private static RideMeSingleton instance;
    private static Toolbar toolbar;
    private static NavigationView navView;
    private static DrawerLayout drawerLayout;
    private static String Cntxt;
    private static Context context;
    private SimpleLocation currLoc;
    private String[] prevOpCodes;

    public static RideMeSingleton getInstance(){
        if(instance==null){
            instance=new RideMeSingleton();
        }
        return instance;
    }

    public String[] getPrevOpCodes(){
        if (prevOpCodes == null)
            prevOpCodes = new String[2];

        if (prevOpCodes[0] == null)
            prevOpCodes[0] = "";

        if (prevOpCodes[1] == null)
            prevOpCodes[1] = "";

        return prevOpCodes;
    }

    public void setPrevOpCodeData(String opCode, String responseTime)
    {
        if(prevOpCodes==null)
            prevOpCodes=new String[2];

        prevOpCodes[0]=opCode;
        prevOpCodes[1]=responseTime;
    }

    public static Toolbar getToolbar() {
        return toolbar;
    }

    public static void setToolbar(Toolbar toolbar) {
        RideMeSingleton.toolbar = toolbar;
    }

    public static NavigationView getNavView() {
        return navView;
    }

    public static void setNavView(NavigationView navView) {
        RideMeSingleton.navView = navView;
    }

    public static DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public static void setDrawerLayout(DrawerLayout drawerLayout) {
        RideMeSingleton.drawerLayout = drawerLayout;
    }

    public static String getCntxt() {
        return Cntxt;
    }

    public static void setCntxt(String cntxt) {
        Cntxt = cntxt;
    }

    public static void setInstance(RideMeSingleton instance) {
        RideMeSingleton.instance = instance;
    }

    public SimpleLocation getCurrLoc() {
        return currLoc;
    }

    public  void setCurrLoc(SimpleLocation currLoc) {
        this.currLoc = currLoc;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RideMeSingleton.context = context;
    }
}
