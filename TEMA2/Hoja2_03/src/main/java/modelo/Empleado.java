
package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia autonumérica
    private Long id;

    @Column(length = 45, nullable = false)
    private String nombre;

    @Column(length = 30)
    private String oficio;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    // Relación muchos a uno con Departamento
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @Embedded  // Usamos @Embedded para mapear el objeto Sueldo en la tabla de empleados
    private Sueldo sueldo;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    // Eliminamos el método get/set para 'salario' ya que ahora viene de Sueldo
    public Sueldo getSueldo() {
        return sueldo;
    }

    public void setSueldo(Sueldo sueldo) {
        this.sueldo = sueldo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
