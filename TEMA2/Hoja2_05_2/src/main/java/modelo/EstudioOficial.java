package modelo;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "estudio_id")
@Table(name = "estudios_oficiales")

public class EstudioOficial extends Estudio {
    private static final long serialVersionUID = 1L;

    @Column(length=50)
    private String centro;
    @Column(length=15)
    private String realDecreto;

    public EstudioOficial() {

        super();
    }

    public String getCentro() {

        return centro;
    }

    public void setCentro(String centro) {

        this.centro = centro;
    }

    public String getRealDecreto() {

        return realDecreto;
    }

    public void setRealDecreto(String realDecreto) {

        this.realDecreto = realDecreto;
    }
}
