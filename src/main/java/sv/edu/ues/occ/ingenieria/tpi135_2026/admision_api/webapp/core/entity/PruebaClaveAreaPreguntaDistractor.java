package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;

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
@Table(name = "prueba_clave_area_pregunta_distractor")
public class PruebaClaveAreaPreguntaDistractor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prueba_clave_area_pregunta_distractor")
    private Long idPruebaClaveAreaPreguntaDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave_area_pregunta", nullable = false)
    private PruebaClaveAreaPregunta idPruebaClaveAreaPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor", nullable = false)
    private Distractor idDistractor;

    public PruebaClaveAreaPreguntaDistractor(Long idPruebaClaveAreaPreguntaDistractor) {
        this.idPruebaClaveAreaPreguntaDistractor = idPruebaClaveAreaPreguntaDistractor;
    }

    public PruebaClaveAreaPreguntaDistractor(){}

    public Long getIdPruebaClaveAreaPreguntaDistractor() {
        return idPruebaClaveAreaPreguntaDistractor;
    }

    public void setIdPruebaClaveAreaPreguntaDistractor(Long idPruebaClaveAreaPreguntaDistractor) {
        this.idPruebaClaveAreaPreguntaDistractor = idPruebaClaveAreaPreguntaDistractor;
    }

    @JsonbTransient
    public PruebaClaveAreaPregunta getIdPruebaClaveAreaPregunta() {
        return idPruebaClaveAreaPregunta;
    }

    public void setIdPruebaClaveAreaPregunta(PruebaClaveAreaPregunta idPruebaClaveAreaPregunta) {
        this.idPruebaClaveAreaPregunta = idPruebaClaveAreaPregunta;
    }

    @JsonbTransient
    public Distractor getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(Distractor idDistractor) {
        this.idDistractor = idDistractor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaClaveAreaPreguntaDistractor == null) ? 0 : idPruebaClaveAreaPreguntaDistractor.hashCode());
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
        PruebaClaveAreaPreguntaDistractor other = (PruebaClaveAreaPreguntaDistractor) obj;
        if (idPruebaClaveAreaPreguntaDistractor == null) {
            if (other.idPruebaClaveAreaPreguntaDistractor != null)
                return false;
        } else if (!idPruebaClaveAreaPreguntaDistractor.equals(other.idPruebaClaveAreaPreguntaDistractor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPreguntaDistractor [idPruebaClaveAreaPreguntaDistractor=" + idPruebaClaveAreaPreguntaDistractor + "]";
    }
}