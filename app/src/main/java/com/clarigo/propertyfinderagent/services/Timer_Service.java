package com.clarigo.propertyfinderagent.services;


import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class Timer_Service extends Service {

    public static String str_receiver = "com.waterTankDriver.receiver";

    //  RequestDetails requestDetails = new RequestDetails();
    //   private SessionManager sessionManager;
    Date servsreDate;
    Date requestDate;
    CountDownTimer countDownTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       /* sessionManager = new SessionManager(this);
       // if (sessionManager.onRequest()){
            requestDetails  = sessionManager.getRequestDetails();
            servsreDate = Constant.converStringToDate(requestDetails.getServer_time(),
                    Constant.LOCAL_DATE_FORMATE);
            requestDate = Constant.converStringToDate(requestDetails.getLocal_time(),
                    Constant.LOCAL_DATE_FORMATE);

          //  long time_diffInMs = requestDate.getTime()- requestDate.getTime();
          // long time_diffInMs = 400000;
            long time_diffInMs = sessionManager.getRequestDetails().getDriver_waiting_time()*1000;
            countDownTimer =   new CountDownTimer(time_diffInMs, 1000) {
                public void onTick(long millisUntilFinished) {
                    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                  //  Time_Left_TV.setText("Auction will be start in : "+hms);
                      System.out.print("timer:"+hms);
                    Toast.makeText(Timer_Service.this,hms, Toast.LENGTH_SHORT).show();
                }
                public void onFinish() {
                    Toast.makeText(Timer_Service.this,"finish", Toast.LENGTH_SHORT).show();
                    cancel();
               //     sessionManager.isOnRequest(false);
                    stopSelf();
                    if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                            NotificationUtils.startActivityFromBackground(getApplicationContext(), "1","","");

                    }
                   // fistTimeListener();
                }
            }.start();
    //    }
      //  sendBroadcast(intent);
     //   intent = new Intent(str_receiver);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        Log.e("Service finish", "Finish");
    }
}
