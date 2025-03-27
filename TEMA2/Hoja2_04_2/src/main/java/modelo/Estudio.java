package modelo;


import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

   @OneToMany(mappedBy = "estudio", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<EmpleadoEstudio> empleados = new ArrayList<>();

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

    public List<EmpleadoEstudio> getEmpleados() {

        return empleados;
    }

    public void setEmpleados(List<EmpleadoEstudio> empleados) {

        this.empleados = empleados;
    }


}
