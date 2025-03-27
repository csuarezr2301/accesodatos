package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ESTOF")
//@Table (name = "estudioNoOficial")

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
