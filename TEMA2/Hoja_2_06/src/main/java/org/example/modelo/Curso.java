package org.example.modelo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NamedQuery(name="Curso.findAll", query="SELECT c FROM Curso c")
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String nombre;

    @OneToMany(mappedBy = "curso")
    private List<Alumno> alumnos;


    @OneToOne
    @JoinColumn(name = "tutor_id")
    private Profesor profesor;

    public Curso() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return this.alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Alumno addAlumno(Alumno alumno) {
        getAlumnos().add(alumno);
        alumno.setCurso(this);

        return alumno;
    }

    public Alumno removeAlumno(Alumno alumno) {
        getAlumnos().remove(alumno);
        alumno.setCurso(null);

        return alumno;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}