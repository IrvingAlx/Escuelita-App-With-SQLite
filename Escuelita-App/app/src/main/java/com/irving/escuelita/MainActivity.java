package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonControlEscolar = (Button) findViewById(R.id.botonControl);
        Button botonMenuAlumno = (Button) findViewById(R.id.botonMenuAlumnos);
        Button botonMenuMaestro = (Button) findViewById(R.id.botonMenuMaestro);

        botonControlEscolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaControlEscolar = new Intent(getApplicationContext(), ControlEscolarActivity.class);
                startActivity(pantallaControlEscolar);
            }
        });

        botonMenuAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuAlumno = new Intent(getApplicationContext(),MenuAlumnosActivity.class);
                startActivity(menuAlumno);
            }
        });

        botonMenuMaestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuMaestro = new Intent(getApplicationContext(),MenuMaestrosActivity.class);
                startActivity(menuMaestro);
            }
        });
    }
}