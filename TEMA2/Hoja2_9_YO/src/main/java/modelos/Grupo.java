package modelos;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codgrupo", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "localidad", nullable = false, length = 20)
    private String localidad;

    @Column(name = "estilo", nullable = false, length = 20)
    private String estilo;

    @ColumnDefault("1")
    @Column(name = "esgrupo", nullable = false)
    private Boolean esgrupo = false;

    @Column(name = "annoGrab")
    private Integer annoGrab;

    @Column(name = "fechaEstreno")
    private LocalDate fechaEstreno;

    @Column(name = "compania", length = 35)
    private String compania;

    @OneToMany(mappedBy = "grupo")
    private List<Componente> componentes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Boolean getEsgrupo() {
        return esgrupo;
    }

    public void setEsgrupo(Boolean esgrupo) {
        this.esgrupo = esgrupo;
    }

    public Integer getAnnoGrab() {
        return annoGrab;
    }

    public void setAnnoGrab(Integer annoGrab) {
        this.annoGrab = annoGrab;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

}