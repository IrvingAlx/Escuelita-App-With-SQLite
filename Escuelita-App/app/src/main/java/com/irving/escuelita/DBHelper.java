package com.irving.escuelita;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "escuelita_ibero3.db";
    private static int DB_VERSION = 1;

    public DBHelper(@Nullable Context context){super(context, DB_NAME,null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL( "CREATE TABLE alumnos(" +
                    "matricula INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ",nombre VARCHAR(100) NOT NULL" +
                    ",apellido_paterno VARCHAR(100) NOT NULL" +
                    ",apellido_materno VARCHAR(100)" +
                    ",fecha_nacimiento DATE" +
                    ",sexo CHAR)");

        db.execSQL( "CREATE TABLE materias(" +
                    "clave CHAR(5) PRIMARY KEY" +
                    ",nombre VARCHAR(100)" +
                    ",creditos INT DEFAULT 10)");

        db.execSQL( "CREATE TABLE calificaciones(" +
                    "alumno INT" +
                    ",materia CHAR(5)" +
                    ",calificacion FLOAT" +
                    ",fecha DATE" +
                    ",PRIMARY KEY (alumno, materia)" +
                    ",FOREIGN KEY (alumno) REFERENCES alumnos(matricula)" +
                    ",FOREIGN KEY (materia) REFERENCES materias(clave))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE calificaciones");
        db.execSQL("DROP TABLE materia");
        db.execSQL("DROP TABLE alumnos");
        onCreate(db);
    }
}