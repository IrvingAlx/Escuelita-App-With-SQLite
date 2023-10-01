package com.irving.escuelita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Alumno {

    private int matricula;
    private String nombre;
    private String apellidoPaterno;
    private String apeliidoMaterno;
    private Date fechaNacimiento;
    private char sexo;
    ArrayList<Calificacion> calificaciones;

    // Constructores
    public Alumno() { }

    public Alumno(String nombre, String apellidoPaterno, String apeliidoMaterno) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apeliidoMaterno = apeliidoMaterno;
    }

    public Alumno(String nombre, String apellidoPaterno, String apeliidoMaterno, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apeliidoMaterno = apeliidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Alumno(String nombre, String apellidoPaterno, String apeliidoMaterno, Date fechaNacimiento, char sexo) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apeliidoMaterno = apeliidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }

    public Alumno(int matricula, String nombre, String apellidoPaterno, String apeliidoMaterno, Date fechaNacimiento, char sexo) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apeliidoMaterno = apeliidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }



    // Setters y Getters

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApeliidoMaterno() {
        return apeliidoMaterno;
    }
    public void setApeliidoMaterno(String apeliidoMaterno) {
        this.apeliidoMaterno = apeliidoMaterno;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Metodos para el manejo de fechas

    public void setFechaNacimiento(String fecha) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.fechaNacimiento = formato.parse(fecha);
        }catch (Exception ex){
            fechaNacimiento = new Date();
        }
    }

    public void setFechaNacimiento(String fecha, String formatoFecha) {
        DateFormat formato = new SimpleDateFormat(formatoFecha);
        try {
            this.fechaNacimiento = formato.parse(fecha);
        }catch (Exception ex){
            fechaNacimiento = new Date();
        }
    }

    String leerFechaFormato(){
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fechaNacimiento);
    }

    String leerFechaFormato(String formatoFecha){
        DateFormat formato = new SimpleDateFormat(formatoFecha);
        return formato.format(fechaNacimiento);
    }

    //----------------------------------------------------------------------------------------//

    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public double calcularPromedio(){
        double promedio = 0.0;
        double suma = 0.0;
        int n = calificaciones.size();

        if (n>0){
            for (Calificacion calificacion:calificaciones){
                suma += calificacion.getCalificacion();
            }
            promedio = suma / n;
        }
        return promedio;
    }

    public String getNombreCompleto(){
        return nombre + " " + apellidoPaterno + " " + apeliidoMaterno;
    }

    @Override
    public String toString(){ return matricula + " - " + getNombreCompleto(); }
}
