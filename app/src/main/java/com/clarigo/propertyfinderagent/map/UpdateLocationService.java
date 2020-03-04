package com.clarigo.propertyfinderagent.map;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.UpdateLocationDTO;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateLocationService extends Service {

    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    private SessionManager sessionManager;
    LatLng latLng1;
    public static UpdateLocationService updateLocationService;
    private APIInterface apiInterface;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        updateLocationService = this;
        sessionManager = new SessionManager(this);

        locationUpdate();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
            restartServiceIntent.setPackage(getPackageName());

            PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmService.set(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 1000,
                    restartServicePendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void locationUpdate() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(8000); // two minute interval
        mLocationRequest.setFastestInterval(8000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                        Looper.myLooper());
            } else {
                UpdateLocationService.this.stopSelf();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
        }
    }


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            try {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    Location location = locationList.get(locationList.size() - 1);

                    if (sessionManager.isLogin() && !sessionManager.get_Agent_id().isEmpty()) {
                        //
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MyLocationBean myLocationBean = new MyLocationBean();
                        myLocationBean.setDate(String.valueOf(Calendar.getInstance().getTime()));
                        myLocationBean.setLatitude(location.getLatitude());
                        myLocationBean.setLongitude(location.getLongitude());

                        //System.out.println("location..........." + location.getLatitude() + " " + location.getLongitude());

                        if (Utility.isConnectingToInternet(UpdateLocationService.this)) {
                            Update_driver_location_API(latLng.latitude, latLng.longitude);
                        } else {
                            Toast.makeText(UpdateLocationService.this, getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final int SPLASH_TIME = 300;

    private void startSplashTimer() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                        Toast.makeText(UpdateLocationService.this, "location stop", Toast.LENGTH_SHORT).show();
                    }
                }, SPLASH_TIME);
    }


    public void Update_driver_location_API(final double vLa, final double vLo) {
        try {
            final Call<UpdateLocationDTO> updateLocationDTOCall = apiInterface.update_lat_lng_server(sessionManager.get_Agent_id(), vLa, vLo);
            updateLocationDTOCall.enqueue(new Callback<UpdateLocationDTO>() {
                @Override
                public void onResponse(Call<UpdateLocationDTO> call, retrofit2.Response<UpdateLocationDTO> response) {
                    try {
                        UpdateLocationDTO updateLocation_dto = response.body();
                        if (updateLocation_dto != null) {
                            if (updateLocation_dto.getStatus() == 1) {
                                System.out.println("mylocation..." + " lat..." + vLa + " lan..." + vLo);

                            } else {
                                Toast.makeText(UpdateLocationService.this, updateLocation_dto.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<UpdateLocationDTO> call, Throwable t) {
                    Log.e("onFaliure..", "onFailure: ", t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void Update_driver_location_API(final double vLa, final double vLo) {
//        StringRequest req = new StringRequest(Request.Method.POST, Constant.getMethodUrl(Constant.UPDATELOCATION),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String vMessage = "";
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            try {
//                                System.out.println("mylocation..." + jsonObject.toString() + " lat..." + vLa + " lan..." + vLo);
//                            } catch (Exception e) {
//
//                            }
//
//                        } catch (Exception e) {
//                            // TODO: handle exception
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        try {
//                            if (error == null || error.networkResponse == null) {
//                                return;
//                            }
//                            String body;
//                            try {
//                                body = new String(error.networkResponse.data, "UTF-8");
//                                JSONObject jsonObject = new JSONObject(body.toString());
//                                if (jsonObject.has("message")) {
//                                    //Toast.makeText(getApplicationContext(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                                } else if (jsonObject.has("error")) {
//                                    //    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
//                                }
//                                System.out.println("body..." + body.toString());
//                            } catch (UnsupportedEncodingException e) {
//
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("agent_id", sessionManager.get_Agent_id());
//                //  params.put("timezone", TimeZone.getDefault().getID());
//                params.put("lat", String.valueOf(vLa));
//                params.put("lng", String.valueOf(vLo));
//                return params;
//            }
//        };
//        // Adding request to request queue
//        try {
//            MyApplication.getInstance().addToRequestQueue(req);
//        } catch (Exception e) {
//
//        }
//    }


}