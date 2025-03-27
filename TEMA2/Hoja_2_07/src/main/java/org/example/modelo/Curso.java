package org.example.modelo;

import jakarta.persistence.*;
import jdk.jfr.Name;

import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
        @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id"),

})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name="nombre", nullable=false, length= 50)
    private String nombre;

    @OneToMany(mappedBy = "curso", orphanRemoval = true)
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