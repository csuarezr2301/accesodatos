package modelos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "votos")
public class Voto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private VotoId id;

    @MapsId("usuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cancion", nullable = false)
    private Cancione cancion;

    public VotoId getId() {
        return id;
    }

    public void setId(VotoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cancione getCancion() {
        return cancion;
    }

    public void setCancion(Cancione cancion) {
        this.cancion = cancion;
    }

}