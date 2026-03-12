package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "aspirante_opcion")
public class AspiranteOpcion implements Serializable {

    @Id
    @Column(name = "id_aspirante_opcion", nullable = false)
    private UUID idAspiranteOpcion;

    @Column(name = "id_opcion", nullable = false)
    private String idOpcion;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Column(name = "fecha_creacion", nullable = false)
    private OffsetDateTime fechaCreacion;

    @OneToMany(mappedBy = "idAspiranteOpcion")
    private List<PruebaJornadaAulaAspiranteOpcion> listPruebaJornadaAulaAspiranteOpcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante", nullable = false)
    private Aspirante idAspirante;

    public AspiranteOpcion() {}

    public AspiranteOpcion(UUID idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public UUID getIdAspiranteOpcion() {
        return idAspiranteOpcion;
    }

    public void setIdAspiranteOpcion(UUID idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
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

    
    public Aspirante getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(Aspirante idAspirante) {
        this.idAspirante = idAspirante;
    }

    @JsonbTransient
    public List<PruebaJornadaAulaAspiranteOpcion> getListPruebaJornadaAulaAspiranteOpcion() {
        return listPruebaJornadaAulaAspiranteOpcion;
    }

    public void setListPruebaJornadaAulaAspiranteOpcion(List<PruebaJornadaAulaAspiranteOpcion> listPruebaJornadaAulaAspiranteOpcion) {
        this.listPruebaJornadaAulaAspiranteOpcion = listPruebaJornadaAulaAspiranteOpcion;
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
        return "AspiranteOpcion [idAspiranteOpcion=" + idAspiranteOpcion + ", idOpcion=" + idOpcion + ", prioridad="
                + prioridad + ", fechaCreacion=" + fechaCreacion + "]";
    }
}