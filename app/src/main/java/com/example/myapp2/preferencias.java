package com.example.myapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class preferencias extends AppCompatActivity {
    public Switch notificaciones;
    private SharedPreferences sharedPreferences;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.preferencias);
        notificaciones = (Switch) findViewById(R.id.notificaciones);
        notificaciones.setVisibility(View.VISIBLE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean estadoActual = sharedPreferences.getBoolean("Notificaciones",false);
        if(estadoActual){
            notificaciones.setChecked(true);
        }
        notificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editor.putBoolean("Notificaciones", true);
                editor.apply();
                finish();
            } else {
                editor.putBoolean("Notificaciones", false);
                editor.apply();
                finish();
            }
        });
    }


}




