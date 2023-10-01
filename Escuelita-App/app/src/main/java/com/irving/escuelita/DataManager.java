package com.irving.escuelita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DataManager {

    private Context miContexto;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase miBase;

    DataManager(Context context){
        miContexto = context;
        dbHelper = new DBHelper(miContexto);
        miBase = dbHelper.getWritableDatabase();
    }
    void abrir () { miBase = dbHelper.getWritableDatabase(); }
    void cerrar () { miBase.close(); }
    void borrarBase() { dbHelper.onUpgrade(miBase,1,1); }

    void guardarAlumno(Alumno alumno){
        ContentValues valores = new ContentValues(5);
        valores.put("nombre",alumno.getNombre());
        valores.put("apellido_paterno",alumno.getApellidoPaterno());
        valores.put("apellido_materno",alumno.getApeliidoMaterno());
        valores.put("fecha_nacimiento",alumno.leerFechaFormato());
        valores.put("sexo",String.valueOf(alumno.getSexo()));

        miBase.insert("alumnos",null,valores);
    }

    ArrayList<Alumno> leerAlumnos(){
        ArrayList<Alumno> alumnos = new ArrayList<>();
        String[] columnas = {"matricula","nombre","apellido_paterno","apellido_materno","fecha_nacimiento","sexo"};

        Cursor miCursor = miBase.query("alumnos",columnas,null,null,null,null,"matricula");

        while (miCursor.moveToNext()){
            Alumno miAlumno = new Alumno();
            miAlumno.setMatricula(miCursor.getInt(0));
            miAlumno.setNombre(miCursor.getString(1));
            miAlumno.setApellidoPaterno(miCursor.getString(2));
            miAlumno.setApeliidoMaterno(miCursor.getString(3));
            miAlumno.setFechaNacimiento(miCursor.getString(4));
            miAlumno.setSexo(miCursor.getString(5).charAt(0));
            alumnos.add(miAlumno);
        }
        miCursor.close();
        return alumnos;
    }

    Alumno leerAlumno(int matricula){
        Alumno miAlumno = null;
        String[] columnas = {"matricula","nombre","apellido_paterno","apellido_materno","fecha_nacimiento","sexo"};
        String[] args = { String.valueOf(matricula) };

        Cursor miCursor = miBase.query("alumnos",columnas,"matricula=?",args,null,null,null);

        while (miCursor.moveToNext()){
            miAlumno = new Alumno();
            miAlumno.setMatricula(miCursor.getInt(0));
            miAlumno.setNombre(miCursor.getString(1));
            miAlumno.setApellidoPaterno(miCursor.getString(2));
            miAlumno.setApeliidoMaterno(miCursor.getString(3));
            miAlumno.setFechaNacimiento(miCursor.getString(4));
            miAlumno.setSexo(miCursor.getString(5).charAt(0));
        }
        miCursor.close();
        return miAlumno;
    }

    long guardarMateria(Materia materia){
        long id;
        ContentValues valores = new ContentValues(3);

        valores.put("clave",materia.getClave());
        valores.put("nombre",materia.getNombre());
        valores.put("creditos",materia.getCreditos());
        id = miBase.insert("materias",null, valores);
        return id;
    }
    int actualizarMateria(Materia materia){
        int num = 0;
        ContentValues valores = new ContentValues(2);
        String[] argumentos = { materia.getClave() };
        valores.put("nombre", materia.getNombre());
        valores.put("creditos", materia.getCreditos());
        num = miBase.update("materias", valores,"clave=?", argumentos);
        return num;
    }
    int eliminarMateria(Materia materia){
        int num = 0;
        String[] argumentos = { materia.getClave() };
        num = miBase.delete("materias","clave=?",argumentos);
        return num;
    }
    int eliminarMateria(String clave){
        int num = 0;
        String[] argumentos = { clave };
        num = miBase.delete("materias","clave=?",argumentos);
        return num;
    }
    ArrayList<Materia> leerMaterias(){
        ArrayList<Materia> materias = new ArrayList<>();
        String[] columnas = {"clave","nombre","creditos"};

        Cursor miCursor = miBase.query("materias",columnas,null,null,null,null,"clave");

        while (miCursor.moveToNext()){
            Materia materia = new Materia();
            materia.setClave(miCursor.getString(0));
            materia.setNombre(miCursor.getString(1));
            materia.setCreditos(miCursor.getInt(2));
            materias.add(materia);
        }
        miCursor.close();
        return materias;
    }
    
    long guardarCalificacion(int matricula, Calificacion calificacion){
        long id;
        ContentValues valores = new ContentValues(4);

        valores.put("alumno",matricula);
        valores.put("materia",calificacion.getMateria().getClave());
        valores.put("calificacion",calificacion.getCalificacion());
        valores.put("fecha",calificacion.leerFechaFormato());

        id = miBase.insert("calificaciones",null,valores);
        return id;
    }
    int actualizarCalificacion(int matricula, Calificacion calificacion){
        int num=0;
        ContentValues valores = new ContentValues(4);
        String[] argumentos = {String.valueOf(matricula), calificacion.getMateria().getClave()};

        valores.put("calificacion",calificacion.getCalificacion());
        valores.put("fecha",calificacion.leerFechaFormato());

        num = miBase.update("calificaciones",valores,"alumno=? AND materia=?",argumentos);
        return num;
    }
    ArrayList<Calificacion> leerCalificaciones(int matricula){
        ArrayList<Calificacion> calificaciones = new ArrayList<>();
        String[] argumentos = {String.valueOf(matricula)};
        String comando = "SELECT materia, calificacion, fecha, nombre, creditos " +
                        "FROM calificaciones JOIN materias " +
                        "ON materia=clave WHERE alumno=? ORDER BY materia";
    Cursor miCursor = miBase.rawQuery(comando,argumentos);
        while (miCursor.moveToNext()){
            Materia materia = new Materia();
            Calificacion calificacion = new Calificacion();
            calificacion.setMateria(materia);
            materia.setClave(miCursor.getString(0));
            calificacion.setCalificacion(miCursor.getDouble(1));
            calificacion.setFecha(miCursor.getString(2));
            materia.setNombre(miCursor.getString(3));
            materia.setCreditos(miCursor.getInt(4));
            calificaciones.add(calificacion);
        }
        miCursor.close();
        return calificaciones;
    }
    Calificacion leerCalificacion(int matricula, String claveMateria){
        Calificacion calificacion = null;

        String[] argumentos = { claveMateria, String.valueOf(matricula) };

        String comando = "SELECT materia, calificacion, fecha, nombre, creditos " +
                        "FROM calificaciones JOIN materias " +
                        "ON materia=? WHERE alumno=? ORDER BY materia ";

        Cursor miCursor = miBase.rawQuery(comando,argumentos);

        while (miCursor.moveToNext()){
            Materia materia = new Materia();
            calificacion = new Calificacion();
            calificacion.setMateria(materia);

            materia.setClave(miCursor.getString(0));
            calificacion.setCalificacion(miCursor.getDouble(1));
            calificacion.setFecha(miCursor.getString(2));
            materia.setNombre(miCursor.getString(3));
            materia.setCreditos(miCursor.getInt(4));
        }
        miCursor.close();
        return calificacion;
    }

}