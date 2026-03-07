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
@Table(name = "prueba_jornada_aula_aspirante_opcion")
public class PruebaJornadaAulaAspiranteOpcion implements Serializable {

    @Id
    @Column(name = "id_prueba_jornada_aula_aspirante_opcion", nullable = false)
    private UUID idPruebaJornadaAulaAspiranteOpcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_jornada", nullable = false)
    private PruebaJornada idPruebaJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante_opcion", nullable = false)
    private AspiranteOpcion idAspiranteOpcion;

    @Column(name = "id_aula", nullable = false, length = 100)
    private String idAula;

    @Column(name = "fecha")
    private OffsetDateTime fecha;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "idPruebaJornadaAulaAspiranteOpcion")
    private List<PruebaJornadaAulaAspiranteOpcionExamen> listPruebaJornadaAulaAspiranteOpcionExamen;

    public PruebaJornadaAulaAspiranteOpcion() {}

    public PruebaJornadaAulaAspiranteOpcion(UUID idPruebaJornadaAulaAspiranteOpcion) {
        this.idPruebaJornadaAulaAspiranteOpcion = idPruebaJornadaAulaAspiranteOpcion;
    }

    public UUID getIdPruebaJornadaAulaAspiranteOpcion() {
        return idPruebaJornadaAulaAspiranteOpcion;
    }

    public void setIdPruebaJornadaAulaAspiranteOpcion(UUID idPruebaJornadaAulaAspiranteOpcion) {
        this.idPruebaJornadaAulaAspiranteOpcion = idPruebaJornadaAulaAspiranteOpcion;
    }

    @JsonbTransient
    public PruebaJornada getIdPruebaJornada() {
        return idPruebaJornada;
    }

    public void setIdPruebaJornada(PruebaJornada idPruebaJornada) {
        this.idPruebaJornada = idPruebaJornada;
    }

    @JsonbTransient
    public AspiranteOpcion getIdAspiranteOpcion() {
        return idAspiranteOpcion;
    }

    public void setIdAspiranteOpcion(AspiranteOpcion idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @JsonbTransient
    public List<PruebaJornadaAulaAspiranteOpcionExamen> getListPruebaJornadaAulaAspiranteOpcionExamen() {
        return listPruebaJornadaAulaAspiranteOpcionExamen;
    }

    public void setListPruebaJornadaAulaAspiranteOpcionExamen(
            List<PruebaJornadaAulaAspiranteOpcionExamen> listPruebaJornadaAulaAspiranteOpcionExamen) {
        this.listPruebaJornadaAulaAspiranteOpcionExamen = listPruebaJornadaAulaAspiranteOpcionExamen;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaJornadaAulaAspiranteOpcion == null) ? 0
                : idPruebaJornadaAulaAspiranteOpcion.hashCode());
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
        PruebaJornadaAulaAspiranteOpcion other = (PruebaJornadaAulaAspiranteOpcion) obj;
        if (idPruebaJornadaAulaAspiranteOpcion == null) {
            if (other.idPruebaJornadaAulaAspiranteOpcion != null)
                return false;
        } else if (!idPruebaJornadaAulaAspiranteOpcion.equals(other.idPruebaJornadaAulaAspiranteOpcion))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaJornadaAulaAspiranteOpcion [idPruebaJornadaAulaAspiranteOpcion="
                + idPruebaJornadaAulaAspiranteOpcion + ", idAula=" + idAula + ", fecha=" + fecha
                + ", activo=" + activo + "]";
    }
}
