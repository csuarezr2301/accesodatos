package org.example.modelo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name="Profesor.findAll", query="SELECT p FROM Profesor p")
public class Profesor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;

    private String nombre;


    @OneToOne(mappedBy="profesor")
    private Curso cursos;

    public Profesor() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Curso getCursos() {
        return cursos;
    }

    public void setCursos(Curso cursos) {
        this.cursos = cursos;
    }
}