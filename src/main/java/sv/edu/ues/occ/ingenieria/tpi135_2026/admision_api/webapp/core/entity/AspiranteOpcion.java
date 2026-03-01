package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aspirante_opcion")
public class AspiranteOpcion implements Serializable {

    @Id
    @Column(name = "id_aspirante_opcion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAspiranteOpcion;

    @Column(name = "id_carrera", nullable = false)
    private String idCarrera;

    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante", nullable = false)
    private Aspirante idAspirante;

    public AspiranteOpcion() {}

    public AspiranteOpcion(Long idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public Long getIdAspiranteOpcion() {
        return idAspiranteOpcion;
    }

    public void setIdAspiranteOpcion(Long idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonbTransient
    public Aspirante getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(Aspirante idAspirante) {
        this.idAspirante = idAspirante;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAspiranteOpcion == null) ? 0 : idAspiranteOpcion.hashCode());
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
        AspiranteOpcion other = (AspiranteOpcion) obj;
        if (idAspiranteOpcion == null) {
            if (other.idAspiranteOpcion != null)
                return false;
        } else if (!idAspiranteOpcion.equals(other.idAspiranteOpcion))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AspiranteOpcion [idAspiranteOpcion=" + idAspiranteOpcion + ", idCarrera=" + idCarrera + ", prioridad="
                + prioridad + ", fechaCreacion=" + fechaCreacion + "]";
    }
}