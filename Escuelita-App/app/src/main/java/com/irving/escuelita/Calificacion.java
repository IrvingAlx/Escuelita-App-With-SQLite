package com.irving.escuelita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calificacion {
    private Materia materia;
    private Double calificacion;
    private Date fecha;

    Calificacion(){ fecha = new Date(); }

    public Calificacion(Materia materia, double calificacion) {
        this.materia = materia;
        this.calificacion = calificacion;
        fecha = new Date();
    }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }
    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public void setFecha(String fecha) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.fecha = formato.parse(fecha);
        }catch (Exception ex){
            this.fecha = new Date();
        }
    }
    public void setFecha(String fecha, String formatoFecha){
        DateFormat formato = new SimpleDateFormat(formatoFecha);
        try {
            this.fecha = formato.parse(fecha);
        }catch (Exception ex){
            this.fecha = new Date();
        }
    }

    String leerFechaFormato(){
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }

    @Override
    public String toString(){ return materia + ": " + String.format("%1$,.2f",calificacion);}
}