package com.clarigo.propertyfinderagent.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.clarigo.propertyfinderagent.Fragment.HomeFragment;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.user_location_dto.USER_LOCATION_DTO;
import com.clarigo.propertyfinderagent.dtos.user_location_dto.User_Location_Data_Dto;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class GET_User_Location_service extends Service {

    public static final String ACTION_LOCATION_BROADCAST = GET_User_Location_service.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE_USER = "extra_latitude_user";
    public static final String EXTRA_LONGITUDE_USER = "extra_longitude_user";

    public static final String EXTRA_LATITUDE_PROPERTY = "extra_latitude_property";
    public static final String EXTRA_LONGITUDE_PROPERTY = "extra_longitude_property";
    public static final String EXTRA_PROPERTY_ID = "extra_property_id";
    public static final String EXTRA_MODE_TYPE = "extra_mode_type";

    public static GET_User_Location_service get_user_location_service;
    APIInterface apiInterface;
    private Handler mHandler;
    public static double user_lat = 0.0, user_lng = 0.0;
    SessionManager sessionManager;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 10000;
    public String vProperty_id = "", vUser_id = "", mode_type = "";
    private double vUser_lat, vUser_lng, vProperty_lat, vProperty_lng;
    private int vMode_type;
    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {

            try {
                if (Utility.isConnectingToInternet(GET_User_Location_service.this)) {
                    syncData();
                } else {
                    Toast.makeText(GET_User_Location_service.this, getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                }
                // Repeat this runnable code block again every ... min
                mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceOreoCondition();
        get_user_location_service = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(this);
    }

    private void startServiceOreoCondition() {
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_service";
            String CHANNEL_NAME = "My Background Service";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setSmallIcon(R.drawable.ic_launcher_background).setPriority(PRIORITY_MIN).build();

            startForeground(101, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public synchronized void syncData() {
        // call your rest service here
        try {
            if (HomeFragment.is_Request) {
                final Call<USER_LOCATION_DTO> user_location_api = apiInterface.get_user_location_api(sessionManager.get_User_id());
                user_location_api.enqueue(new Callback<USER_LOCATION_DTO>() {
                    @Override
                    public void onResponse(@NotNull Call<USER_LOCATION_DTO> call, @NotNull Response<USER_LOCATION_DTO> response) {
                        USER_LOCATION_DTO user_location_dto = response.body();
                        if (user_location_dto == null) {
                            return;
                        }
                        ArrayList<User_Location_Data_Dto> user_location_data_dtos = new ArrayList<>();

                        if (user_location_dto.getResponse().equals(true)) {
                            user_location_data_dtos.add(user_location_dto.getData());
                            for (int i = 0; i < user_location_data_dtos.size(); i++) {
                                user_lat = Double.parseDouble(user_location_data_dtos.get(i).getLat());
                                user_lng = Double.parseDouble(user_location_data_dtos.get(i).getLng());
                                vProperty_id = sessionManager.get_user_app_details().get(SessionManager.PROPERTY_ID);
                                vProperty_lat = Double.parseDouble(sessionManager.get_user_app_details().get(SessionManager.PROPERTY_LAT));
                                vProperty_lng = Double.parseDouble(sessionManager.get_user_app_details().get(SessionManager.PROPERTY_LNG));
                                vMode_type = Integer.parseInt(sessionManager.get_user_app_details().get(SessionManager.USER_MODE));

                                if (user_lat != 0.0 || user_lng != 0.0) {
                                    sendBrodcast(user_lat, user_lng, vProperty_id, vProperty_lat, vProperty_lng, vMode_type);
                                    /*try {
                                        if (Utility.isConnectingToInternet(getBaseContext())) {
//                                            ((HomeFragment) homeFragment).show_markers_locations(user_lat, user_lng, vProperty_id, vProperty_lat, vProperty_lng, vMode_type);
                                        } else {
                                            Toast.makeText(GET_User_Location_service.this, "Internet Connection Error !", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }*/
                                }

                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<USER_LOCATION_DTO> call, @NotNull Throwable t) {
                        Log.e("onFaliure..", "onFailure: ", t);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendBrodcast(double vUser_lat, double vUser_lng, String vProperty_id, double vProperty_lat, double vProperty_lng, int vMode_type) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE_USER, vUser_lat);
        intent.putExtra(EXTRA_LONGITUDE_USER, vUser_lng);
        intent.putExtra(EXTRA_LATITUDE_PROPERTY, vProperty_lat);
        intent.putExtra(EXTRA_LONGITUDE_PROPERTY, vProperty_lng);
        intent.putExtra(EXTRA_PROPERTY_ID, vProperty_id);
        intent.putExtra(EXTRA_MODE_TYPE, vMode_type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}