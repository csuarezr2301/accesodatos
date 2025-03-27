package org.example.modelo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a"),
        @NamedQuery(name = "Alumno.findByCurso", query = "SELECT a FROM Alumno a WHERE a.curso = :id")
})
public class Alumno implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String nombre;

    @Column(name = "nota_media")
    private BigDecimal notaMedia;

    @ManyToOne
    private Curso curso;

    public Alumno() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getNotaMedia() {
        return this.notaMedia;
    }

    public void setNotaMedia(BigDecimal notaMedia) {
        this.notaMedia = notaMedia;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}

