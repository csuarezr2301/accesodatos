package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String user;
	private String apellidos;

	@Column(name="contraseña")
	private String password;

	
	private LocalDate fechanac;

	private String nombre;

	private int numVotos;

	//bi-directional many-to-one association to Voto
	@OneToMany(mappedBy="usuarioBean")
	private List<Voto> votos=new ArrayList<>();

	public Usuario() {
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechanac() {
		return this.fechanac;
	}

	public void setFechanac(LocalDate fechanac) {
		this.fechanac = fechanac;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumVotos() {
		return this.numVotos;
	}

	public void setNumVotos(int numVotos) {
		this.numVotos = numVotos;
	}

	public List<Voto> getVotos() {
		return this.votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	public Voto addVoto(Voto voto) {
		getVotos().add(voto);
		voto.setUsuarioBean(this);

		return voto;
	}

	public Voto removeVoto(Voto voto) {
		getVotos().remove(voto);
		voto.setUsuarioBean(null);

		return voto;
	}

}