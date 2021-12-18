package com.example.myapp2;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class act2 extends AppCompatActivity {

    public Button mButtonSeleccionarFechaBtn;
    public Button mButtonSeleccionarHoraBtn;
    public Button mGuardarDatos;
    public TextView mTextViewFechaSeleccionada;
    public TextView mTextViewHoraSeleccionada;
    public TextView mTextViewNombreRecordatorio;
    private int hora;
    private int min;
    private Date fecha;
    private String stringRec;
    private RecordatorioModel modeloRec;
    private RecordatorioRepository repositorioRec;
    private RecordatorioPreferencesDataSource dataSourceRec;
    private SharedPreferences sharedPreferences;
    private Boolean boolNotificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);
        boolNotificaciones = new Boolean(false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolNotificaciones = sharedPreferences.getBoolean("Notificaciones",false);
        mButtonSeleccionarFechaBtn = findViewById(R.id.SeleccionarFecha);
        mButtonSeleccionarHoraBtn = findViewById(R.id.SeleccionarHora);
        mTextViewFechaSeleccionada = findViewById(R.id.fechaSeleccionada);
        mTextViewHoraSeleccionada = findViewById(R.id.horaaSeleccionada);
        mTextViewNombreRecordatorio = findViewById(R.id.textView3);
        mGuardarDatos = findViewById(R.id.buttonGuardar);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Selecciona una fecha");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        dataSourceRec = new RecordatorioPreferencesDataSource();
        dataSourceRec.RecordatorioDataSource(this.getApplicationContext());
        repositorioRec = new RecordatorioRepository(dataSourceRec);

        mButtonSeleccionarFechaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.getTag());
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                TimeZone zona = TimeZone.getDefault();
                int difHoraria = zona.getOffset(new Date().getTime()) * -3;
                SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(selection + difHoraria);
                fecha = date;
                mTextViewFechaSeleccionada.setText("Se eligió " + sf.format(date));

            }
        });

        MaterialTimePicker materialTimePicker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Seleccionar hora del recordatorio")
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setInputMode(INPUT_MODE_KEYBOARD)
                        .build()

                ;
        mButtonSeleccionarHoraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTimePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
            mTextViewHoraSeleccionada.setText("Se eligió "+materialTimePicker.getHour() + ":" + materialTimePicker.getMinute());
            hora =materialTimePicker.getHour();
            min =materialTimePicker.getMinute();




        });

        mGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringRec = mTextViewNombreRecordatorio.getText().toString();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                calendar.set(Calendar.HOUR_OF_DAY,hora);
                calendar.set(Calendar.MINUTE,min);
                calendar.set(Calendar.SECOND,0);
                // Recuperando el alarm manager
                boolNotificaciones = sharedPreferences.getBoolean("Notificaciones",false);
                if(boolNotificaciones) {
                    final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    // Seteo de la alarma

                    Long tiempoEnMillis = calendar.getTimeInMillis();

                    Notification notificacionRec = createNotification(stringRec);

                    scheduleNotification(notificacionRec, tiempoEnMillis);
                }

                Date fechaFinal = new Date(calendar.getTimeInMillis());
                //Creo Recordatorio

                modeloRec = new RecordatorioModel(stringRec,fechaFinal);
                dataSourceRec.guardarRecordatorio(modeloRec, new RecordatorioDataSource.GuardarRecordatorioCallback() {
                    @Override
                    public void resultado(boolean exito) {
                        System.out.println(exito);
                        if(exito){
                            Toast msj = Toast.makeText(getApplicationContext(), stringRec +" creado con éxito!", Toast.LENGTH_SHORT);
                            msj.show();
                        }
                        else{
                            Toast msj = Toast.makeText(getApplicationContext(),"Error al crear!", Toast.LENGTH_SHORT);
                            msj.show();
                        }
                    }
                });

                Intent intentFinal = new Intent(getApplicationContext(), ListadoRecordatorios.class);
                startActivity(intentFinal);
                finish();

            }

        });

    }

    private void scheduleNotification (Notification notification , long tiempoEnMillis) {
        final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent( this, RecordatorioReceiver.class ) ;
        notificationIntent.putExtra("notification-id" , 1 ) ;
        notificationIntent.putExtra("notification" , notification) ;
        PendingIntent pendingIntentConActionParaMiBroadcastReceiver = PendingIntent.getBroadcast(act2.this, 1, notificationIntent, 0);
        alarm.set(AlarmManager.RTC_WAKEUP, tiempoEnMillis, pendingIntentConActionParaMiBroadcastReceiver);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP , tiempoEnMillis , pendingIntentConActionParaMiBroadcastReceiver) ;
    }
    private Notification createNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, "0" ) ;
        builder.setContentTitle( "Recordatorio!" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( "0" ) ;
        return builder.build() ;
    }


}