package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
        query = "SELECT p FROM PruebaClaveAreaPreguntaDistractor p WHERE p.idPruebaClave.idPruebaClave = :idPruebaClave AND p.idArea.idArea = :idArea AND p.idPregunta.idPregunta = :idPregunta ORDER BY p.idDistractor.idDistractor"
    ),
    @NamedQuery(
        name = "PruebaClaveAreaPreguntaDistractor.contarPorPadre",
        query = "SELECT COUNT(p) FROM PruebaClaveAreaPreguntaDistractor p WHERE p.idPruebaClave.idPruebaClave = :idPruebaClave AND p.idArea.idArea = :idArea AND p.idPregunta.idPregunta = :idPregunta"
    )
})
@Entity
@IdClass(PruebaClaveAreaPreguntaDistractorPK.class)
@Table(name = "prueba_clave_area_pregunta_distractor")
public class PruebaClaveAreaPreguntaDistractor implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave", nullable = false)
    private PruebaClave idPruebaClave;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta idPregunta;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor", nullable = false)
    private Distractor idDistractor;

    public PruebaClaveAreaPreguntaDistractor() {}

    public PruebaClaveAreaPreguntaDistractor(PruebaClave idPruebaClave, Area idArea, Pregunta idPregunta, Distractor idDistractor) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
        this.idPregunta = idPregunta;
        this.idDistractor = idDistractor;
    }

    public PruebaClave getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(PruebaClave idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Distractor getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(Distractor idDistractor) {
        this.idDistractor = idDistractor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null,
                idArea != null ? idArea.getIdArea() : null,
                idPregunta != null ? idPregunta.getIdPregunta() : null,
                idDistractor != null ? idDistractor.getIdDistractor() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PruebaClaveAreaPreguntaDistractor)) return false;
        PruebaClaveAreaPreguntaDistractor other = (PruebaClaveAreaPreguntaDistractor) obj;
        return Objects.equals(idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null,
            other.idPruebaClave != null ? other.idPruebaClave.getIdPruebaClave() : null)
            && Objects.equals(idArea != null ? idArea.getIdArea() : null,
                other.idArea != null ? other.idArea.getIdArea() : null)
            && Objects.equals(idPregunta != null ? idPregunta.getIdPregunta() : null,
                other.idPregunta != null ? other.idPregunta.getIdPregunta() : null)
            && Objects.equals(idDistractor != null ? idDistractor.getIdDistractor() : null,
                other.idDistractor != null ? other.idDistractor.getIdDistractor() : null);
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPreguntaDistractor [idPruebaClave="
            + (idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null)
            + ", idArea=" + (idArea != null ? idArea.getIdArea() : null)
            + ", idPregunta=" + (idPregunta != null ? idPregunta.getIdPregunta() : null)
            + ", idDistractor=" + (idDistractor != null ? idDistractor.getIdDistractor() : null) + "]";
    }
}