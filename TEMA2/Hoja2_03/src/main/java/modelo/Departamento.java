package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "departamento")

public class Departamento {
    @Id
    private Long id;
    @Column( length = 50, nullable = false )
    private String nombre;
    @Column ( length = 50 )
    private String localidad;

    // Relación bidireccional uno a muchos con Empleado
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empleado> empleados= new ArrayList<>();

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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;

    }
    public Empleado addEmpleado(Empleado empleado) {
            empleados.add(empleado);
            empleado.setDepartamento(this); // Establecer la relación inversa
            return empleado;
        }
    }
    /*
    public void addEmpleado(Empleado empleado) {
            empleados.add(empleado);
            empleado.setDepartamento(this); // Establecer la relación inversa

        }
     */
