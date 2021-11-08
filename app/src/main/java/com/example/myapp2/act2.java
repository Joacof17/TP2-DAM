package com.example.myapp2;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class act2 extends AppCompatActivity {

    public Button mButtonSeleccionarFechaBtn;
    public Button mButtonSeleccionarHoraBtn;
    public TextView mTextViewFechaSeleccionada;
    public TextView mTextViewHoraSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);

        mButtonSeleccionarFechaBtn = findViewById(R.id.SeleccionarFecha);
        mButtonSeleccionarHoraBtn = findViewById(R.id.SeleccionarHora);
        mTextViewFechaSeleccionada = findViewById(R.id.fechaSeleccionada);
        mTextViewHoraSeleccionada = findViewById(R.id.horaaSeleccionada);


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builder.build();

        mButtonSeleccionarFechaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.getTag());
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mTextViewFechaSeleccionada.setText("Se eligió " + materialDatePicker.getHeaderText());
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
        });

    }


}