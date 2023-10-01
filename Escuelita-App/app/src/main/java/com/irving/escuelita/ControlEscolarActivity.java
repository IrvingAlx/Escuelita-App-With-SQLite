package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ControlEscolarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_escolar);

        Button botonNuevoAlumno = (Button) findViewById(R.id.botonAlumnos);
        Button botonNuevaMateria = (Button) findViewById(R.id.botonMaterias);
        ImageView imagenAlumnos = (ImageView) findViewById(R.id.imagenAlumnos);
        ImageView imagenMateria = (ImageView) findViewById(R.id.imagenMaterias);

        botonNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaNuevoAlumno = new Intent(getApplicationContext(),AlumnoNuevaActivity.class);
                startActivity(pantallaNuevoAlumno);
            }
        });

        botonNuevaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaNuevaMateria = new Intent(getApplicationContext(),MateriaNuevaActivity.class);
                startActivity(pantallaNuevaMateria);
            }
        });

        imagenMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonNuevaMateria.callOnClick();
            }
        });

        imagenAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonNuevoAlumno.callOnClick();
            }
        });
    }
}