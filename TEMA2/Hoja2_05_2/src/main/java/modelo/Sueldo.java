package modelo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Sueldo {
    //@Column(nullable = false)
    private double salario;
    private double comision;

    public Sueldo(double salario) {
        super();
        this.salario = salario;

    }
    public Sueldo(){

    }

    public Sueldo(double salario, double comision) {
        this.salario = salario;
        this.comision = comision;
    }


    public double getSalario() {

        return salario;
    }

    public void setSalario(double salario) {

        this.salario = salario;
    }

    public double getComision() {

        return comision;
    }

    public void setComision(double comision) {

        this.comision = comision;
    }
}
