package com.example.myapp2;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private int seg;
    private Date fecha;
    private RecordatorioModel modeloRec;
    private RecordatorioRepository repositorioRec;
    private RecordatorioPreferencesDataSource dataSourceRec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);

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
            hora = materialTimePicker.getHour()*60*60*1000;
            min = materialTimePicker.getMinute()*60*1000;


        });

        mGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);

                // Recuperando el alarm manager
                final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Seteo de la alarma
                Long tiempoEnMillis = calendar.getTimeInMillis() + hora + min;
                Intent intent = new Intent(act2.this, RecordatorioReceiver.class);
                PendingIntent pendingIntentConActionParaMiBroadcastReceiver = PendingIntent.getBroadcast(act2.this, 1, intent, 0);
                alarm.set(AlarmManager.RTC_WAKEUP, tiempoEnMillis, pendingIntentConActionParaMiBroadcastReceiver);

                //Creo Recordatorio

                modeloRec = new RecordatorioModel(mTextViewNombreRecordatorio.getText().toString(),fecha);
                dataSourceRec.guardarRecordatorio(modeloRec, new RecordatorioDataSource.GuardarRecordatorioCallback() {
                    @Override
                    public void resultado(boolean exito) {
                        System.out.println(exito);
                        if(exito){
                            Toast msj = Toast.makeText(getApplicationContext(), mTextViewNombreRecordatorio.getText().toString()+" creado con éxito!", Toast.LENGTH_SHORT);
                            msj.show();
                        }
                        else{
                            Toast msj = Toast.makeText(getApplicationContext(),"Error al crear!", Toast.LENGTH_SHORT);
                            msj.show();
                        }
                    }
                });

            }
        });

    }




}