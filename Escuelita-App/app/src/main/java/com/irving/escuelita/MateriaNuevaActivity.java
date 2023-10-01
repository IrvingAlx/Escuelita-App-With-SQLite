package com.irving.escuelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MateriaNuevaActivity extends AppCompatActivity {

    private DataManager dataManager;
    private ListView listaMaterias;
    private ArrayList<Materia> misMaterias;
    private Materia miMateria;
    private EditText editClave;
    private EditText editNombreMateria;
    private EditText editCreditos;
    private Button botonGuardarMateria;
    private Button botonLimpiar;
    private Button botonEliminar;
    private Button botonActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_nueva);

        dataManager = new DataManager(getApplicationContext());
        listaMaterias = (ListView) findViewById(R.id.listaMaterias);
        editClave = (EditText) findViewById(R.id.editClave);
        editNombreMateria = (EditText) findViewById(R.id.editNombreMateria);
        editCreditos = (EditText) findViewById(R.id.editCreditos);
        botonGuardarMateria = (Button) findViewById(R.id.botonGuardarMateria);
        botonLimpiar = (Button) findViewById(R.id.botonLimpiarMateria);
        botonActualizar = (Button) findViewById(R.id.botonActualizarMateria);
        botonEliminar = (Button) findViewById(R.id.botonEliminarMateria);

        botonGuardarMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()){
                    try {
                        miMateria = new Materia();
                        String creditosText = editCreditos.getText().toString();

                        miMateria.setClave(editClave.getText().toString());
                        miMateria.setNombre(editNombreMateria.getText().toString());

                        if (!creditosText.equals("")){
                            miMateria.setCreditos(Integer.parseInt(creditosText));
                        }
                        if (dataManager.guardarMateria(miMateria) != -1){
                            Toast.makeText(getApplicationContext(), "Materia Guardada", Toast.LENGTH_SHORT).show();
                            mostrarMaterias();
                            limpiarDatos();
                        }else {
                            Toast.makeText(getApplicationContext(), "Error al guardar \n Posiblemente ya existe", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miMateria != null){
                    if (miMateria.getClave().equals(editClave.getText().toString())){

                        miMateria.setNombre(editNombreMateria.getText().toString());
                        String creditosText = editCreditos.getText().toString();

                        if (!creditosText.equals("")){
                            miMateria.setCreditos(Integer.parseInt(creditosText));
                        }
                        if (dataManager.actualizarMateria(miMateria)>0){
                            Toast.makeText(getApplicationContext(), "Regristro Actualizado", Toast.LENGTH_SHORT).show();
                            mostrarMaterias();
                        }else {
                            Toast.makeText(getApplicationContext(), "Error al Actualizar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "La clave no se puede cambiar", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Debe elegir una materia existente de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarDatos();
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clave = editClave.getText().toString();
                if (clave.length() == 5){
                    if (dataManager.eliminarMateria(clave)>0){
                        Toast.makeText(getApplicationContext(), "Materia borrada exitosamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error al borrar la materia", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Debe elegir una materia existente de la lisata o ingresar la clave", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listaMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                miMateria = (Materia) listaMaterias.getItemAtPosition(position);

                editClave.setText(miMateria.getClave());
                editNombreMateria.setText(miMateria.getNombre());
                editCreditos.setText(String.valueOf(miMateria.getCreditos()));
            }
        });
        mostrarMaterias();
        limpiarDatos();
    }

    private boolean validarDatos(){
        if (editClave.getText().toString().length()!=5){
            Toast.makeText(getApplicationContext(), "Necesitas poner la clave de 5 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editNombreMateria.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Nombre de la materia indispensable ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limpiarDatos(){
        editClave.setText("");
        editNombreMateria.setText("");
        editCreditos.setText("");
        miMateria=null;
    }

    private void mostrarMaterias(){
        try {
            misMaterias = dataManager.leerMaterias();

            ArrayAdapter<Materia> adaptador = new ArrayAdapter<Materia>(getApplicationContext()
                                                                    , android.R.layout.simple_list_item_1
                                                                    , misMaterias);
            listaMaterias.setAdapter(adaptador);
            listaMaterias.setVerticalScrollBarEnabled(true);
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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