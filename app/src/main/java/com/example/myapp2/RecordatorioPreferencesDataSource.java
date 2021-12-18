package com.example.myapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RecordatorioPreferencesDataSource implements RecordatorioDataSource{
    private SharedPreferences sharedPreferences;

    public void RecordatorioDataSource (final Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long fechaEnMillis = recordatorio.getFecha().getTime();
        editor.putLong(recordatorio.getTexto(),fechaEnMillis);

        editor.apply();
        callback.resultado(editor.commit());
    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {
        Map<String,?> mapaRecordatorios = sharedPreferences.getAll();
        List<RecordatorioModel> listaRec = new ArrayList<RecordatorioModel>();
        for(Map.Entry<String,?> entrada : mapaRecordatorios.entrySet()){
            if(!entrada.getKey().equals("Notificaciones")){
                Date fechaRec = new Date(Long.parseLong(entrada.getValue().toString()));
                RecordatorioModel recordatorio = new RecordatorioModel(entrada.getKey(), fechaRec);
                listaRec.add(recordatorio);
                System.out.println(recordatorio.getTexto());}
        }

        callback.resultado(true,listaRec);
    }

}
