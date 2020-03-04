package com.clarigo.propertyfinderagent.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ps-dev on 26/4/19.
 */

public class CheckConnectivity extends BroadcastReceiver {
    public static boolean isConnected;

    @Override
    public void onReceive(Context context, Intent arg1) {

      //  isConnected = arg1.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//        try {
//            if (isConnected) {
//                Intent goToNetworkLost = new Intent(context, Lost_Connection_Activity.class);
//                goToNetworkLost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(goToNetworkLost);
//            } else {
//                Lost_Connection_Activity.lost_connection_activity.finish();
//                //Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
