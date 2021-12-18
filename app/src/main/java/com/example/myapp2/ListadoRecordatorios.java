package com.example.myapp2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class ListadoRecordatorios extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String NOTIFICATION_ID_STRING = "0";
    private RecordatorioRepository repositorioRec;
    private RecordatorioPreferencesDataSource dataSourceRec;
    private ListAdapter listAdapter;
    public List<RecordatorioModel> lista;
    public CircularProgressIndicator circularProgressIndicator;
    public FloatingActionButton buttonAgregarRecordatorio;
    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        createNotificationChannel();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setContentView(R.layout.listado_recordatorio);
        buttonAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.botonAgregarRecordatorio);
        buttonAgregarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir = new Intent(v.getContext(), act2.class);
                startActivity(ir);
            }
        });

        setNavigationViewListener();
        circularProgressIndicator = (CircularProgressIndicator) findViewById(R.id.circularndicatorCargar);
        circularProgressIndicator.setVisibility(View.GONE);


        listAdapter = new ListAdapter(new ArrayList<>(), this);


        dataSourceRec = new RecordatorioPreferencesDataSource();
        dataSourceRec.RecordatorioDataSource(this.getApplicationContext());
        repositorioRec = new RecordatorioRepository(dataSourceRec);
        cargarRecordatorios();
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listAdapter);

    }
    private void cargarRecordatorios(){
        lista = new ArrayList<>();
        repositorioRec.recuperarRecordatorios(new RecordatorioDataSource.RecuperarRecordatorioCallback() {
            @Override
            public void resultado(boolean exito, List<RecordatorioModel> recordatorios) {
                if(exito){
                    lista = recordatorios;
                }
            }
        });
        listAdapter.setItems(lista);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cargarRecordatorios();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem itemMenu){
        switch (itemMenu.getItemId()){
            case R.id.botonDrawerRecordatorios:{
                System.out.println("Se clickeo Recordatorios");
                Toast msj = Toast.makeText(getApplicationContext(),"Se clickeo Recordatorios", Toast.LENGTH_SHORT);
                msj.show();

                break;
            }
            case R.id.botonDrawerPreferencias:{
                Toast msj = Toast.makeText(getApplicationContext(),"Se clickeo preferencias", Toast.LENGTH_SHORT);
                msj.show();
                System.out.println("Se clickeo preferencias");
                Intent intentPreferencias = new Intent(getApplicationContext(), preferencias.class);
                startActivity(intentPreferencias);
                break;
            }
        }

        return true;
    }
    private void setNavigationViewListener(){
        NavigationView navigationView =  findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "Channel to display alarms";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID_STRING, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
