package com.example.myapp2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListadoRecordatorios extends AppCompatActivity {

    List<RecordatorioModel> lista;
    public CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_recordatorio);
        circularProgressIndicator = (CircularProgressIndicator) findViewById(R.id.circularndicatorCargar);
        circularProgressIndicator.setVisibility(View.VISIBLE);
        lista = new ArrayList<>();
        lista.add(new RecordatorioModel("Recordatorio 1 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 2 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 3 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 4 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 5 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 6 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 7 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 8 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 9 aaaaaaaaaaaaaaa", new Date()));
        lista.add(new RecordatorioModel("Recordatorio 10 aaaaaaaaaaaaaaa", new Date()));
        ListAdapter listAdapter = new ListAdapter(lista, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }
    private void cargarRecordatorios(){

    }
}
