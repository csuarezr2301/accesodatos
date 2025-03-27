package org.example;

import java.sql.Date;
import java.time.LocalDate;

public class Grupos {
    private int codgrupo;
    private String nombre;
    private String localidad;
    private String estilo;
    private boolean esgrupo;
    private int annoGrab;
    private Date fechaEstreno;
    private String compania;

    public Grupos(int codgrupo, String nombre, String localidad, String estilo, boolean esgrupo,int annoGrab, Date fechaEstreno, String compania){
        this.codgrupo = codgrupo;
        this.nombre = nombre;
        this.localidad = localidad;
        this.estilo= estilo;
        this.esgrupo = esgrupo;
        this.annoGrab = annoGrab;
        this.fechaEstreno = fechaEstreno;
        this.compania = compania;
    }



    public int getCodgrupo() {
        return codgrupo;
    }

    public void setCodgrupo(int codgrupo) {
        this.codgrupo = codgrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public boolean getEsgrupo() {
        return esgrupo;
    }

    public void setEsgrupo(boolean esgrupo) {
        this.esgrupo = esgrupo;
    }

    public int getAnnoGrab() {
        return annoGrab;
    }

    public void setAnnoGrab(int annoGrab) {
        this.annoGrab = annoGrab;
    }

    public Date getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    @Override
    public String toString() {
        return "Grupos{" +
                "codgrupo=" + codgrupo +
                ", nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                ", estilo='" + estilo + '\'' +
                ", esgrupo=" + esgrupo +
                ", annoGrab=" + annoGrab +
                ", fechaEstreno=" + fechaEstreno +
                ", compania='" + compania + '\'' +
                '}';
    }

    public String formatInfo() {
        return String.format("%-10d %-20s %-10s %-10s %-15s %-10d %-20s %-20s", codgrupo,  nombre,  localidad,  estilo,  esgrupo, annoGrab,  fechaEstreno,  compania);


    }
}
