package model;

import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the componentes database table.
 * 
 */
@Entity
@Table(name="componentes")
@NamedQuery(name="Componente.findAll", query="SELECT c FROM Componente c")
public class Componente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idComp", nullable = false)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 20)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 40)
	private String apellido;

	@Column(name = "alias", length = 20)
	private String alias;

	@Column(name = "funcion", length = 30)
	private String funcion;


//uni-directional many-to-one association to Grupo
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="grupo")
	private Grupo grupo;

	public Componente() {
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFuncion() {
		return this.funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public int getIdComp() {
		return this.id;
	}

	public void setIdComp(int idComp) {
		this.id = idComp;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}