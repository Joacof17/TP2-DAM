package com.example.myapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class RecordatorioReceiver extends BroadcastReceiver {

    public static String RECORDATORIO = "com.example.tp3.RECORDATORIO";
    public static String TEXTO = "TEXTO";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // Solo nos interesa la accion que hemos definido nosotros
        if(intent.getAction().equals(RECORDATORIO)){
            // Toast de ejemplo, acá debería armarse la notificación
            Toast.makeText(context, "LLEGUE! " + intent.getStringExtra(TEXTO), Toast.LENGTH_LONG).show();
        }
    }
}