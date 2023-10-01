package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlumnoListaActivity extends AppCompatActivity {

    DataManager dataManager;
    ListView listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_lista);

        dataManager = new DataManager(getApplicationContext());
        listaAlumnos = (ListView) findViewById(R.id.listaAlumnos);

        try {
            ArrayList<Alumno> misAlumnos = dataManager.leerAlumnos();
            ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(getApplicationContext()
                                                                    ,android.R.layout.simple_list_item_1
                                                                    ,misAlumnos);
            listaAlumnos.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        listaAlumnos.setVerticalScrollBarEnabled(true);
    }
    @Override
    protected void onPause(){
        super.onPause();
        dataManager.cerrar();
    }

    @Override
    protected void onResume(){
        super.onResume();
        dataManager.abrir();
    }
}