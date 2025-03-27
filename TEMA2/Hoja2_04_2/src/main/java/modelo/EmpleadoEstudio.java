package modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

import static java.lang.Object.*;

@Entity
@IdClass(EmpleadoEstudioId.class)
@Table(name = "empleados_estudios")
public class EmpleadoEstudio {
    @Id
    @ManyToOne(fetch = FetchType.LAZY) //LAZY cuando cargue los empleados, cargará los datos de los asociados a demanda / EAGER los cargaría todos
    @JoinColumn(name ="empleados_id", insertable = false, updatable = false) //No se puede hacer directamente una inserción o modificación sobre este atributo
    private Empleado empleado;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="estudios_id", insertable = false, updatable = false)
    private Estudio estudio;

    @Column(name="fecha_finalizacion")
    private LocalDate fechafin;

    public EmpleadoEstudio(Empleado empleadoSeleccionado, Estudio estudioSeleccionado, LocalDate now) {
    }

    public EmpleadoEstudio() {

    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    @Override
    public int hashCode() {

        return Objects.hash(empleado, estudio, fechafin);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmpleadoEstudio other = (EmpleadoEstudio) obj;
        return Objects.equals(empleado, other.empleado) && Objects.equals(estudio, other.estudio)
                && Objects.equals(fechafin, other.fechafin);
    }
}