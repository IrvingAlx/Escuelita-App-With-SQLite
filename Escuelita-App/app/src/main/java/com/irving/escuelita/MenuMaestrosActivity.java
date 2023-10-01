package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MenuMaestrosActivity extends AppCompatActivity {

    Spinner spinAlumno;
    Spinner spinMateria;
    EditText editCalificacion;
    Button botonCalificar;
    DataManager dataManager;
    ArrayList<Alumno> alumnos;
    ArrayList<Materia> materias;
    Alumno miAlumno;
    Materia miMateria;
    Calificacion miCalificacion;
    Double calificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_maestros);

        dataManager = new DataManager(getApplicationContext());

        spinAlumno = (Spinner) findViewById(R.id.spinnerAlumno);
        spinMateria = (Spinner) findViewById(R.id.spinnerMateria);
        editCalificacion = (EditText) findViewById(R.id.editCalificacion);
        botonCalificar = (Button) findViewById(R.id.botonCalificar);

        try {
            alumnos  = dataManager.leerAlumnos();
            ArrayAdapter<Alumno> adaptadorAlumno = new ArrayAdapter<Alumno>(getApplicationContext()
                                                                            ,android.R.layout.simple_list_item_1
                                                                            ,alumnos);
            spinAlumno.setAdapter(adaptadorAlumno);

            materias = dataManager.leerMaterias();
            ArrayAdapter<Materia> adaptadorMateria = new ArrayAdapter<Materia>(getApplicationContext()
                                                                            ,android.R.layout.simple_list_item_1
                                                                            ,materias);
            spinMateria.setAdapter(adaptadorMateria);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al cargar los datos", Toast.LENGTH_LONG).show();
        }

        botonCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    miMateria = (Materia) spinMateria.getSelectedItem();
                    miAlumno = (Alumno) spinAlumno.getSelectedItem();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al cargar los datos"+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (validaCalificacion()){
                    miCalificacion = dataManager.leerCalificacion(miAlumno.getMatricula(), miMateria.getClave());
                    if (miCalificacion == null){
                        miCalificacion = new Calificacion(miMateria,calificacion);
                        dataManager.guardarCalificacion(miAlumno.getMatricula(), miCalificacion);
                        Toast.makeText(getApplicationContext(), "Calificacion Guardada", Toast.LENGTH_SHORT).show();
                    }else {
                        miCalificacion.setCalificacion(calificacion);
                        miCalificacion.setFecha(new Date());
                        dataManager.actualizarCalificacion(miAlumno.getMatricula(), miCalificacion);
                        Toast.makeText(getApplicationContext(), "Calificacion Actualizada", Toast.LENGTH_SHORT).show();
                    }
                    calificacion=0.0;
                    editCalificacion.setText("");
                }
            }
        });

    }

    private boolean validaCalificacion(){
        if (miAlumno == null || miMateria == null){
            Toast.makeText(getApplicationContext(), "Debes selecionar el alumno y la materia", Toast.LENGTH_SHORT).show();
            return  false;
        }
        try {
            calificacion = Double.parseDouble(editCalificacion.getText().toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "La calificacion debe ser entre o y 10", Toast.LENGTH_SHORT).show();
            calificacion = 0.0;
            return false;
        }
        return true;
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