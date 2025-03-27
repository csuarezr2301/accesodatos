package modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class VotoId implements Serializable {
    private static final long serialVersionUID = 1031873095178736389L;
    @Column(name = "usuario", nullable = false, length = 15)
    private String usuario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VotoId entity = (VotoId) o;
        return Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.usuario, entity.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, usuario);
    }

}