package logica.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Registros_Archivados")
public class RegistroArchivado {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    private float costo;

    @Column(nullable = false)
    private String nombreTipoRegistro;

    @ManyToOne
    @JoinColumn(name = "asistente_user_id")
    private Asistente asistente;

    // relación con EdicionArchivada
    @ManyToOne
    @JoinColumn(name = "edicion_archivada_id")
    private EdicionArchivada edicionArchivada;

    public RegistroArchivado() {}

    public RegistroArchivado(LocalDate fechaRegistro, float costo, String nombreTipoRegistro, Asistente asistente, EdicionArchivada edArch) {
        this.fechaRegistro = fechaRegistro;
        this.costo = costo;
        this.nombreTipoRegistro = nombreTipoRegistro;
        this.asistente = asistente;
        this.edicionArchivada = edArch;
    }

    // Getters
    public int getId() { return id; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public float getCosto() { return costo; }
    public String getNombreTipoRegistro() { return nombreTipoRegistro; }
    public Asistente getAsistente() { return asistente; }
    public EdicionArchivada getEdicionArchivada() { return edicionArchivada; }
}
