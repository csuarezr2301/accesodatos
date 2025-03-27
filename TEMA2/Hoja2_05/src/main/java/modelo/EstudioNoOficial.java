package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ESTNOOF")
//@Table (name = "estudioNoOficial")

public class EstudioNoOficial extends Estudio {
    private static final long serialVersionUID = 1L;

    @Column(length = 30)
    private String academia;
    @Column
    private int numeroHoras;

    public EstudioNoOficial() {
        super(); //invocar el constructor de la clase base o superclase de la clase actual.
    }

    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }

    public int getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(int numeroHoras) {
        this.numeroHoras = numeroHoras;
    }
}
