package com.example.myapp2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RecordatorioReceiver extends BroadcastReceiver {
    public static String RECORDATORIO = "com.example.tp3.RECORDATORIO";
    public static String TEXTO = "TEXTO";

    @Override
    public void onReceive(final Context context, final Intent intent) {
            /*Bundle bundleRecibido = intent.getBundleExtra("BundleConString");
            String recordatorio = bundleRecibido.getString("TEXTOREC");
            Toast.makeText(context, "LLEGUE! " + recordatorio, Toast.LENGTH_LONG).show();*/

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
            Notification notification = intent.getParcelableExtra( "notification" ) ;
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( "0" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                assert notificationManager != null;
                notificationManager.createNotificationChannel(notificationChannel) ;
            }
            int id = intent.getIntExtra( "notification-id" , 0 ) ;
            assert notificationManager != null;
            notificationManager.notify(id , notification) ;


    }
}