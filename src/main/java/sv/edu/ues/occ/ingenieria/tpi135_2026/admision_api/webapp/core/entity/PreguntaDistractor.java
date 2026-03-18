package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(PreguntaDistractorPK.class)
@Table(name = "pregunta_distractor")
public class PreguntaDistractor implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta idPregunta;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor", nullable = false)
    private Distractor idDistractor;

    @Column(name = "correcto", nullable = false)
    private Boolean correcto;

    public PreguntaDistractor(Pregunta idPregunta, Distractor idDistractor) {
        this.idPregunta = idPregunta;
        this.idDistractor = idDistractor;
    }

    public PreguntaDistractor(){}

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

    public Boolean getCorrecto() {
        return correcto;
    }

    public void setCorrecto(Boolean correcto) {
        this.correcto = correcto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                idPregunta != null ? idPregunta.getIdPregunta() : null,
                idDistractor != null ? idDistractor.getIdDistractor() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PreguntaDistractor other = (PreguntaDistractor) obj;
        return Objects.equals(idPregunta != null ? idPregunta.getIdPregunta() : null,
                other.idPregunta != null ? other.idPregunta.getIdPregunta() : null)
                && Objects.equals(idDistractor != null ? idDistractor.getIdDistractor() : null,
                        other.idDistractor != null ? other.idDistractor.getIdDistractor() : null);
    }

    @Override
    public String toString() {
        return "PreguntaDistractor [idPregunta="
                + (idPregunta != null ? idPregunta.getIdPregunta() : null)
                + ", idDistractor=" + (idDistractor != null ? idDistractor.getIdDistractor() : null)
                + ", correcto=" + correcto + "]";
    }
}
