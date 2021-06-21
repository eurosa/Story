package com.ero.poro.story;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;



public class NetworkChangeReceiver extends BroadcastReceiver {
    public final Handler handler3;
    Main2Activity activity;


    public NetworkChangeReceiver(Main2Activity main2Activity, Handler handler) {
         activity=main2Activity;
         handler3=handler;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(checkInternet(context))
        {

                    activity.fetchJSON();

            Toast.makeText(context, "Network Connected", Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(context, "No Network Available", Toast.LENGTH_LONG).show();

        }

    }

    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}