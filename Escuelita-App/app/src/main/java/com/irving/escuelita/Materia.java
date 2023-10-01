package com.irving.escuelita;

public class Materia {
    private String clave;
    private String nombre;
    private int creditos;

    Materia() {
        creditos = 10;
    }

    public Materia(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
        creditos = 10;
    }

    public Materia(String clave, String nombre, int creditos) {
        this.clave = clave;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString(){ return clave + " - " + nombre;}
}