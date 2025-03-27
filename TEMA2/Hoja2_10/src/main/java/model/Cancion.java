package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.sql.Time;


/**
 * The persistent class for the canciones database table.
 * 
 */
@Entity
@Table(name="canciones")

@NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")


public class Cancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "numCancion", nullable = false)
	private int numCancion;
	@Column(name = "duracion", nullable = false)
	private Time duracion;

	private String titulo;

	@ColumnDefault("0")
	@Column(name="total_votos")
	private int totalVotos;

	//uni-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="grupo")
	private Grupo grupo;


	public Cancion() {
	}

	public int getNumCancion() {
		return this.numCancion;
	}

	public void setNumCancion(int numCancion) {
		this.numCancion = numCancion;
	}

	public Time getDuracion() {
		return this.duracion;
	}

	public void setDuracion(Time duracion) {
		this.duracion = duracion;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getTotalVotos() {
		return this.totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}