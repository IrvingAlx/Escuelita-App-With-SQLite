package com.irving.escuelita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlumnoNuevaActivity extends AppCompatActivity {

    private DataManager dataManager;
    private Date fechaNacimiento;
    private CalendarView calendario;
    private TextView textFecha;
    private Spinner spinSexo;
    private EditText editNombre;
    private EditText editPaterno;
    private EditText editMaterno;
    private Button botonGuardarAlumno;
    private Button botonListaAlumno;
    DateFormat formato;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_nueva);

        calendario = (CalendarView) findViewById(R.id.calendarNacimiento);
        textFecha = (TextView) findViewById(R.id.editFechaNacimiento);
        spinSexo = (Spinner) findViewById(R.id.spinnerSexo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editPaterno = (EditText) findViewById(R.id.editPaterno);
        editMaterno = (EditText) findViewById(R.id.editMaterno);
        botonGuardarAlumno = (Button) findViewById(R.id.botonGuardarAlumno);
        botonListaAlumno = (Button) findViewById(R.id.botonListaAlumno);
        dataManager = new DataManager(getApplicationContext());
        fechaNacimiento = new Date();
        formato = new SimpleDateFormat("dd/MM/yyyy");

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(getApplicationContext()
                                    ,R.array.sexoItems, android.R.layout.simple_spinner_dropdown_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinSexo.setAdapter(adaptador);

        limpiarDatos();

        botonGuardarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()){
                    Alumno nuevoAlumno = new Alumno();

                    nuevoAlumno.setNombre(editNombre.getText().toString());
                    nuevoAlumno.setApellidoPaterno(editPaterno.getText().toString());
                    nuevoAlumno.setApeliidoMaterno(editMaterno.getText().toString());
                    nuevoAlumno.setFechaNacimiento(fechaNacimiento);
                    nuevoAlumno.setSexo(spinSexo.getSelectedItem().toString().charAt(0));

                    try {
                        dataManager.guardarAlumno(nuevoAlumno);
                        Toast.makeText(getApplicationContext(), "Alumno Guardado", Toast.LENGTH_LONG).show();
                    } catch (Exception ex){
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    limpiarDatos();
                }else{
                    Toast.makeText(getApplicationContext(), "Falta llenar datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar gregoriano = new GregorianCalendar(year,month,dayOfMonth);
                fechaNacimiento.setTime(gregoriano.getTimeInMillis());
                textFecha.setText(formato.format(fechaNacimiento));
                calendario.setVisibility(View.GONE);
            }
        });

        textFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFecha.setText("Elije una fecha");
                calendario.setVisibility(View.VISIBLE);
            }
        });

        botonListaAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaListaAlumno = new Intent(getApplicationContext(), AlumnoListaActivity.class);
                startActivity(pantallaListaAlumno);
            }
        });
    }

    private void limpiarDatos(){
        editNombre.setText("");
        editPaterno.setText("");
        editMaterno.setText("");
        spinSexo.setSelection(0);

        try {
            calendario.setDate(formato.parse("01/01/2002").getTime());
            fechaNacimiento = new Date();
        }catch (Exception ex){ }

        textFecha.setText("Click aqui para selecionar fecha");
        calendario.setVisibility(View.GONE);
        editNombre.selectAll();
    }

    private boolean validarDatos(){
        if (editNombre.getText().toString().equals("")){
            return false;
        }
        if (editPaterno.getText().toString().equals("")){
            return false;
        }
        return true;
    }

}