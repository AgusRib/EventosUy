package logica.models;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import logica.data_types.DTAsistente;

@Entity
@Table(name="ASISTENTE")
public class Asistente extends Usuario {
	
	@Column(name="APELLIDO", nullable=false) private String apellido;
	@Column(name="FECHA_NAC", nullable=false) private LocalDate fechaNacimiento;
	
	@ManyToOne @JoinColumn(name = "id_institucion") private Institucion institucion = null;
	
	@OneToMany(mappedBy = "asistente") private Set<Registro> registros = new HashSet<Registro>();
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	

	public Institucion getInstitucion() {
		return institucion;
	}
	
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	

	public Set<Registro> getRegistros() {
		return registros;
	}
	
	public void addRegistro(Registro reg) {
		this.registros.add(reg);
	}
	
	public Registro getRegistro(Edicion edicion) {
		for (Registro reg : registros) {
			if (reg.getEdicion().equals(edicion)) {
				return reg;
			}
		}
		return null;
	}

	public Asistente(String nickname, String nombre, String email, String password, String apellido, LocalDate fechaNacimiento) {
		super(nickname, nombre, email, password);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Asistente( ) { super(); } //necesario para que funcione el jpa
	
	public DTAsistente infoAsist() {
		//para evitar los getters podriamos hacer que Usuario sea protected 
		DTAsistente dtasis = new DTAsistente(this.getNickname(), this.getNombre(), this.getEmail(), this.apellido, this.fechaNacimiento, (this.institucion != null) ? this.institucion.getNombre() : null);
		return dtasis;
	}
	
}
