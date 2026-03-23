package logica.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ediciones_Archivadas")
public class EdicionArchivada {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false , unique = true)
    private String nombre;
    @Column(nullable = false)
    private String sigla;
    @Column(nullable = false)
    private LocalDate fechaInicio;
    @Column(nullable = false)
    private LocalDate fechaFin;
    @Column(nullable = false)
    private LocalDate fechaAlta;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String nombreEvento;

    @Column(nullable = true)
    private String organizadorNick;

    @OneToMany(mappedBy = "edicionArchivada", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private java.util.Set<RegistroArchivado> registros = new java.util.LinkedHashSet<>();

    public EdicionArchivada() {}

    public EdicionArchivada(Edicion e) {
        this.nombre = e.getNombre();
        this.sigla = e.getSigla();
        this.fechaInicio = e.getFechaInicio();
        this.fechaFin = e.getFechaFin();
        this.fechaAlta = e.getFechaAlta();
        this.ciudad = e.getCiudad();
        this.pais = e.getPais();
        this.nombreEvento = e.getEvento() != null ? e.getEvento().getNombre() : null;
        this.organizadorNick = (e.getOrganizador() != null) ? e.getOrganizador().getNickname() : null;
    }

    public java.util.Set<RegistroArchivado> getRegistros() { return registros; }
    public void addRegistro(RegistroArchivado r) { this.registros.add(r); }

    // Getters necesarios
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getSigla() { return sigla; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public String getCiudad() { return ciudad; }
    public String getPais() { return pais; }
    public String getNombreEvento() { return nombreEvento; }
    public String getOrganizadorNick() { return organizadorNick; }
}
