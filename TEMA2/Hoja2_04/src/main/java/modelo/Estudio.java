package modelo;


import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.*;

@Entity
@Table(name = "estudios")

public class Estudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia autonumérica
    private Long id;
    private String nombre;
    @NaturalId
    @Column(length = 5, unique = true)
    private String codEstudio;

    @ManyToMany
    @JoinTable(name="estudios_empleados",
            joinColumns = 	{@JoinColumn(name="idEstudio")},
            inverseJoinColumns = {@JoinColumn(name="idEmpleado")})
    private Set<Empleado> empleados=new HashSet<>();

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getCodEstudio() {

        return codEstudio;
    }

    public void setCodEstudio(String codEstudio) {

        this.codEstudio = codEstudio;
    }

    public Set<Empleado> getEmpleados() {

        return empleados;
    }
    // Métodos para agregar empleados a este estudio
    public Empleado addEmpleado(Empleado empleado) {
        getEmpleados().add(empleado);
        empleado.getEstudios().add(this);
        return empleado;
    }
}
