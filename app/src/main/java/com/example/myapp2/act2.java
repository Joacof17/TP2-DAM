package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class act2 extends AppCompatActivity {

    public Button mButtonSelecionarBtn;
    public TextView mTextViewFechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);

        mButtonSelecionarBtn = findViewById(R.id.Seleccionar);
        mTextViewFechaSeleccionada = findViewById(R.id.fechaSeleccionada);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builder.build();

        mButtonSelecionarBtn.setOnClickListener(new View.OnClickListener() {
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


    }


}