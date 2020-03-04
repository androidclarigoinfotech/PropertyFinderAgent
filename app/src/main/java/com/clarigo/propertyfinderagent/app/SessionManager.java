package com.clarigo.propertyfinderagent.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.clarigo.propertyfinderagent.Activity.SignInActivity;
import com.clarigo.propertyfinderagent.dtos.USER_DETAILS_DATA_DTO;
import com.google.gson.Gson;

import java.util.HashMap;

public class SessionManager {
    private static final String PREF_NAME = "PROPERTY_FINDER_AGENT";
    private static final String IS_LOGIN = "IsLogin";
    private static final String USER_DETAIL = "user_info";
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String AGENT_ID = "AGENT_ID";
    public static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    public static final String USER_ID = "user_id";
    public static final String PROPERTY_ID = "PROPERTY_ID";
    public static final String PROPERTY_LAT = "PROPERTY_LAT";
    public static final String PROPERTY_LNG = "PROPERTY_LNG";
    public static final String USER_MODE = "USER_MODE";
    public static boolean IS_REQUEST = false;
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;


    public SessionManager(Context context) {
        this._context = context;
        this.preferences = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.preferences.edit();
    }

    public void login() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    // Get Installed State...
    public boolean isLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void Logout() {
        //clear alluser data
        editor.clear();
        editor.commit();
// after logout user goes to login screen
        Intent intent = new Intent(_context, SignInActivity.class);
        intent.putExtra("come_from_one", "no");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);

    }

    public void setDeviceToken(String vDeviceToken) {
        this.editor.putString(DEVICE_TOKEN, vDeviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        String deviceToken = preferences.getString(DEVICE_TOKEN, "");
        return deviceToken;
    }

    public void set_Agnet_id(String vAgent_id) {
        this.editor.putString(AGENT_ID, vAgent_id);
        editor.commit();
    }

    public String get_Agent_id() {
        String vAgent_id = preferences.getString(AGENT_ID, "");
        return vAgent_id;
    }

    public void setUser_id(String vUser_id) {
        this.editor.putString(USER_ID, vUser_id);
        editor.commit();
    }

    public String get_User_id() {
        String vUser_Id = preferences.getString(USER_ID, "");
        return vUser_Id;
    }


    public void set_user_app_details(String vProperty_id, double vProperty_lat, double vProperty_lng, int vUser_mode) {

        this.editor.putString(PROPERTY_ID, vProperty_id);
        this.editor.putString(PROPERTY_LAT, String.valueOf(vProperty_lat));
        this.editor.putString(PROPERTY_LNG, String.valueOf(vProperty_lng));
        this.editor.putString(USER_MODE, String.valueOf(vUser_mode));
        this.editor.commit();
    }

    public HashMap<String, String> get_user_app_details() {
        HashMap<String, String> get_user_app_details = new HashMap<>();
        get_user_app_details.put(PROPERTY_ID, this.preferences.getString(PROPERTY_ID, ""));
        get_user_app_details.put(PROPERTY_LAT, this.preferences.getString(PROPERTY_LAT, ""));
        get_user_app_details.put(PROPERTY_LNG, this.preferences.getString(PROPERTY_LNG, ""));
        get_user_app_details.put(USER_MODE, this.preferences.getString(USER_MODE, ""));
        return get_user_app_details;
    }


    public USER_DETAILS_DATA_DTO getUser_details() {
        Gson gson = new Gson();
        String vJson = preferences.getString(USER_DETAIL, "");
        USER_DETAILS_DATA_DTO user_details_dto = gson.fromJson(vJson, USER_DETAILS_DATA_DTO.class);
        return user_details_dto;
    }

    public void setUserDetail(USER_DETAILS_DATA_DTO userDTO) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vUserDTO = gson.toJson(userDTO);
        editor.putString(USER_DETAIL, vUserDTO);
        // commit changes
        editor.commit();
    }

    public void setMobile(String vMobile) {
        this.editor.putString(MOBILE_NUMBER, vMobile);
        this.editor.commit();
    }

    public HashMap<String, String> getMobileFromSession() {
        HashMap<String, String> userid_mobile = new HashMap<>();
        userid_mobile.put(MOBILE_NUMBER, this.preferences.getString(MOBILE_NUMBER, ""));

        return userid_mobile;
    }
}
