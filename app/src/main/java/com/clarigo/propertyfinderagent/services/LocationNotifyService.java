package com.clarigo.propertyfinderagent.services;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * This Service class is used to get Locations.
 *
 * @version 1.2
 * @since 21/11/17.
 */

public abstract class LocationNotifyService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Location Service";
    private long UPDATE_INTERVAL = 10 * 1000;  /* 60 secs */
    private long FASTEST_INTERVAL = 10 * 1000;
    //private SessionManager sessionManager;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Location currentLocation;
    // private SessionManager sessionManager;
    PendingIntent mGeofencePendingIntent;
    // private List<Geofence> mGeofenceList;
    public static LocationNotifyService locationNotifyService;
    private double vLatitude = 0.0, vLongitude = 0.0;

    //private ArrayList<String> stopsIds;

    @Override
    public void onCreate() {
        locationNotifyService = this;
        //   mGeofenceList = new ArrayList<Geofence>();
        // sessionManager = new SessionManager(this);
        //show error dialog if GoolglePlayServices not available
        if (isGooglePlayServicesAvailable()) {
            mLocationRequest = new LocationRequest();
//            if (sessionManager.isOnRide()) {
//                UPDATE_INTERVAL = 3 * 1000;
//                FASTEST_INTERVAL = 3 * 1000; // 4 seconds
//            } else {
//                UPDATE_INTERVAL = 3 * 1000;
//                FASTEST_INTERVAL = 3 * 1000;// 50 seconds
//            }
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //  mLocationRequest.setSmallestDisplacement(5.0f);  /* min dist for location change, here it is 10 meter */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Check Google play is available or not
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        return ConnectionResult.SUCCESS == status;
    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
//                    mGoogleApiClient, mLocationRequest, this);
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        } catch (IllegalStateException e) {

        }
    }

    public static Location getLocationFromService() {
        return currentLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {

            //  Signin_Activity.vLatitude = location.getLatitude();
            //  Signin_Activity.vLongitude = location.getLongitude();
            vLatitude = location.getLatitude();
            vLongitude = location.getLongitude();
            System.out.println("Lovation____" + vLongitude + " " + vLatitude);

//            if (!isAddress) {
//                isAddress = true;
//            getAddress();
            //  }


        } catch (Exception e) {

        }

        //location update start and stop
/*        if (sessionManager.isLogin()) {
//            if (!sessionManager.isDriveStratFromPickupToSource()) {
//                stopSelf();
//                return;
//            }
            try {
                this.currentLocation = location;
                //  HomeNewFragment.homeNewFragment.setCarMovment(location);
            } catch (Exception e) {

            }

            try {
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                   // Update_driver_location_API(location.getLatitude(), location.getLongitude());
                } else {
                   // Update_driver_location_API(location.getLatitude(), location.getLongitude());
                    // background
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            stopSelf();
        }*/
    }

    /**
     * This method is used to call webservice.
     */


}