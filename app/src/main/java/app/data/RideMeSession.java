package app.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by G09561636 on 8/4/2016.
 */
public  class RideMeSession {

    SharedPreferences pref;
    Editor editor;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="pref";
    Context _context;
    private static String IS_LOGIN="IsLoggedIn";
    public static String USERNAME="username";
    public static String PASSWORD="password";

    public static RideMeSession instance;
    public static RideMeSession getInstance(){
        if(instance==null){
            instance=new RideMeSession();
        }
            return instance;
    }
    public RideMeSession(){

    }
    public RideMeSession(Context context){
        this._context=context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void createLoginSession(String username,String password){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(USERNAME,username);
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){

        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN,false);

    }
}
