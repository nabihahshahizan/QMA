package com.example.qma;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import login.LoginData;

public class SessionManager {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static  final String IS_LOGGED_IN = "isLoggedIn";
    public static final String NRIC = "nric";
    public static final String FULLNAME = "fullname";
    public static final String NO_PHONE = "no_phone";
    public static final String QUARANTINE_LOCATION = "quarantine_location";
    public static final String COVID_STATUS = "covid_status";
    public static final String TRACK_FROM = "track_from";
    public static final String TRACK_ARRIVAL = "track_arrival";

    public  SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(NRIC, user.getNric());
        editor.putString(FULLNAME, user.getFullname());
        editor.putString(NO_PHONE, user.getNoPhone());
        editor.putString(QUARANTINE_LOCATION, user.getQuarantineLocation());
        editor.putString(COVID_STATUS, user.getCovidStatus());
        editor.putString(TRACK_FROM, user.getTrackFrom());
        editor.putString(TRACK_ARRIVAL, user.getTrackArrival());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NRIC, sharedPreferences.getString(NRIC,null));
        user.put(FULLNAME, sharedPreferences.getString(FULLNAME,null));
        user.put(NO_PHONE, sharedPreferences.getString(NO_PHONE,null));
        user.put(QUARANTINE_LOCATION, sharedPreferences.getString(QUARANTINE_LOCATION,null));
        user.put(COVID_STATUS, sharedPreferences.getString(COVID_STATUS,null));
        user.put(TRACK_FROM, sharedPreferences.getString(TRACK_FROM,null));
        user.put(TRACK_ARRIVAL, sharedPreferences.getString(TRACK_ARRIVAL,null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }
}
