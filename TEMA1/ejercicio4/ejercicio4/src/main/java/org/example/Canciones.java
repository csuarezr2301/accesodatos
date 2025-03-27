package org.example;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

public class Canciones {
    private int numCancion;
    private int grupo;
    private Time duracion;
    private String titulo;
    private int total_votos;

    public Canciones (int numCancion, int grupo, Time duracion, String titulo, int total_votos) {
        this.numCancion = numCancion;
        this.grupo = grupo;
        this.duracion = duracion;
        this.titulo = titulo;
        this.total_votos = total_votos;
    }

    public int getNumCancion() {
        return numCancion;
    }

    public void setNumCancion(int numCancion) {
        this.numCancion = numCancion;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotal_votos() {
        return total_votos;
    }

    public void setTotal_votos(int total_votos) {
        this.total_votos = total_votos;
    }

    @Override
    public String toString() {
        return "Canciones{" +
                "numCancion=" + numCancion +
                ", grupo=" + grupo +
                ", duracion=" + duracion +
                ", titulo='" + titulo + '\'' +
                ", total_votos='" + total_votos + '\'' +
                '}';
    }

    public String formatInfo() {
        return String.format("%-10d %-20s %-10s %-10s %-15s",  numCancion,  grupo,  duracion,  titulo.length() > 20 ? titulo.substring(0, 17) + "..." : titulo ,  total_votos);

    }
}
