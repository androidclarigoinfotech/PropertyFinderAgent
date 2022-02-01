package com.clarigo.propertyfinderagent.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class Request_Retrive_Service extends Service {
    public static final String ACTION_LOCATION_BROADCAST = Request_Retrive_Service.class.getName() + "LocationBroadcast";
    public static final String SYNCREQ = "Request";

    public static Request_Retrive_Service request_retrive_service;
    APIInterface apiInterface;
    private Handler mHandler;
    public static double user_lat = 0.0, user_lng = 0.0;
    SessionManager sessionManager;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 10000;
    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            try {
                syncData();
                // Repeat this runnable code block again every ... min
                mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceOreoCondition();
        request_retrive_service = this;
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
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public synchronized void syncData() {
        // call your rest service here
        if (Utility.isConnectingToInternet(this)) {
            if (SessionManager.IS_REQUEST) {
                this.stopSelf();
                Request_Retrive_Service.request_retrive_service.stopSelf();
                //      Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
            } else {

//                ((HomeFragment) homeFragment).get_Request_API();

                Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
                intent.putExtra(SYNCREQ, "sync");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                //      Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(this, "service start agent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
        }
    }
}