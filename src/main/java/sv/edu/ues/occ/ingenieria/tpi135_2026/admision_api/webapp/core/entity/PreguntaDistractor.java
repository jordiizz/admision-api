package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pregunta_distractor")
public class PreguntaDistractor implements Serializable{

    @Id
    @Column(name = "id_pregunta_distractor")
    private UUID idPreguntaDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta")
    private Pregunta idPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor")
    private Distractor idDistractor;

    @Column(name = "correcto", nullable = false)
    private Boolean correcto;

    public PreguntaDistractor(UUID idPreguntaDistractor) {
        this.idPreguntaDistractor = idPreguntaDistractor;
    }

    public PreguntaDistractor(){}

    public UUID getIdPreguntaDistractor() {
        return idPreguntaDistractor;
    }

    public void setIdPreguntaDistractor(UUID idPreguntaDistractor) {
        this.idPreguntaDistractor = idPreguntaDistractor;
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

    public Boolean getCorrecto() {
        return correcto;
    }

    public void setCorrecto(Boolean correcto) {
        this.correcto = correcto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPreguntaDistractor == null) ? 0 : idPreguntaDistractor.hashCode());
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
        PreguntaDistractor other = (PreguntaDistractor) obj;
        if (idPreguntaDistractor == null) {
            if (other.idPreguntaDistractor != null)
                return false;
        } else if (!idPreguntaDistractor.equals(other.idPreguntaDistractor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PreguntaDistractor [idPreguntaDistractor=" + idPreguntaDistractor + ", correcto=" + correcto + "]";
    }
}
