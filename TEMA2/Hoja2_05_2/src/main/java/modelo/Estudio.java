package modelo;


import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "estudios")

public class Estudio implements Serializable {
    private static final long serialVersionUID = 1L; //es un identificador único utilizado para la serialización de objetos en Java / para evitar excepciones de deserialización
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia autonumérica
    private Long id;
    private String nombre;
    @NaturalId
    @Column(length = 10, unique = true)
    private String codEstudio;

   @OneToMany(mappedBy = "estudio", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<EmpleadoEstudio> empleados = new ArrayList<>();


    public Estudio() {

    }

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
