package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MenuAlumnosActivity extends AppCompatActivity {

    private DataManager dataManager;
    private Alumno miAlumno;
    private Button botonBuscar;
    private Button botonLimpiar;
    private EditText editMatricula;
    private TextView textNombreCompleto;
    private TextView textSexo;
    private TextView textFechaNacimiento;
    private TextView textPromedio;
    private ListView listaCalificacion;
    private ArrayAdapter<Calificacion> adatadorCalificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_alumnos);

        dataManager = new DataManager(getApplicationContext());
        miAlumno = null;
        botonBuscar = (Button) findViewById(R.id.botonBuscarMatricula);
        botonLimpiar = (Button) findViewById(R.id.botonLimpiarAlumno);
        editMatricula = (EditText) findViewById(R.id.editBuscarMatricula);
        textNombreCompleto = (TextView) findViewById(R.id.textNombreCompleto);
        textSexo = (TextView) findViewById(R.id.textSexoAlumno);
        textFechaNacimiento = (TextView) findViewById(R.id.textFechaNacimientoAlumno);
        textPromedio = (TextView) findViewById(R.id.textPromedio);
        listaCalificacion = (ListView) findViewById(R.id.listaCalificaciones);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int matricula = 0;
                try {
                    matricula = Integer.parseInt(editMatricula.getText().toString());
                    miAlumno = dataManager.leerAlumno(matricula);
                    if (miAlumno != null){
                        miAlumno.calificaciones = dataManager.leerCalificaciones(matricula);
                        adatadorCalificacion = new ArrayAdapter<Calificacion>(getApplicationContext()
                                                                                , android.R.layout.simple_list_item_1
                                                                                , miAlumno.calificaciones);
                        listaCalificacion.setAdapter(adatadorCalificacion);
                        listaCalificacion.setVerticalScrollBarEnabled(true);

                        textNombreCompleto.setText(miAlumno.getNombreCompleto());
                        textFechaNacimiento.setText(miAlumno.leerFechaFormato("dd/MM/yyyy"));
                        textSexo.setText(miAlumno.getSexo()=='M'?"Masculino":"Femenino");
                        textPromedio.setText("Promedio: " + String.format("%1$,.2f", miAlumno.calcularPromedio()));
                    }else {
                        Toast.makeText(getApplicationContext(), "Alumno no esta inscrito", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Debe introducir una matricula validad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarDatos();
            }
        });

    }

    private void limpiarDatos(){
        miAlumno = null;
        editMatricula.setText("");
        textFechaNacimiento.setText("");
        textNombreCompleto.setText("");
        textSexo.setText("");
        textPromedio.setText("Promedio: 0.0");
        if (adatadorCalificacion != null){
            adatadorCalificacion.clear();
            adatadorCalificacion.notifyDataSetChanged();
        }
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