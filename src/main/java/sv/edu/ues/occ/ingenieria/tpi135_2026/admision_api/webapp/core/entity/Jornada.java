package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQuery(name = "Jornada.findByIdPrueba", 
    query = "SELECT pj.idJornada FROM PruebaJornada pj WHERE pj.idPrueba.idPrueba = :idPrueba"
)
@Entity
@Table(name = "jornada")
public class Jornada implements Serializable{

    @Id
    @Column(name = "id_jornada")
    private UUID idJornada;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime fechaFin;


    public Jornada(UUID idJornada) {
        this.idJornada = idJornada;
    }
    
    public Jornada(){}

    public UUID getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(UUID idJornada) {
        this.idJornada = idJornada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idJornada == null) ? 0 : idJornada.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jornada other = (Jornada) obj;
        if (idJornada == null) {
            if (other.idJornada != null)
                return false;
        } else if (!idJornada.equals(other.idJornada))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Jornada [idJornada=" + idJornada + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
    }
}
