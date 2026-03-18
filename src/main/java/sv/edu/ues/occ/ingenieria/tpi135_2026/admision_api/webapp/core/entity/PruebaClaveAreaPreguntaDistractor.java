package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
        query = "SELECT p FROM PruebaClaveAreaPreguntaDistractor p WHERE p.idPruebaClave = :idPruebaClave AND p.idArea = :idArea AND p.idPregunta = :idPregunta ORDER BY p.idDistractor"
    ),
    @NamedQuery(
        name = "PruebaClaveAreaPreguntaDistractor.contarPorPadre",
        query = "SELECT COUNT(p) FROM PruebaClaveAreaPreguntaDistractor p WHERE p.idPruebaClave = :idPruebaClave AND p.idArea = :idArea AND p.idPregunta = :idPregunta"
    ),
    @NamedQuery(
        name = "PruebaClaveAreaPreguntaDistractor.buscarPorIdYPadre",
        query = "SELECT p FROM PruebaClaveAreaPreguntaDistractor p WHERE p.idDistractor = :idDistractor AND p.idPruebaClave = :idPruebaClave AND p.idArea = :idArea AND p.idPregunta = :idPregunta"
    )
})
@Entity
@IdClass(PruebaClaveAreaPreguntaDistractorPK.class)
@Table(name = "prueba_clave_area_pregunta_distractor")
public class PruebaClaveAreaPreguntaDistractor implements Serializable {

    @Id
    @Column(name = "id_prueba_clave", nullable = false)
    private UUID idPruebaClave;

    @Id
    @Column(name = "id_area", nullable = false)
    private UUID idArea;

    @Id
    @Column(name = "id_pregunta", nullable = false)
    private UUID idPregunta;

    @Id
    @Column(name = "id_distractor", nullable = false)
    private UUID idDistractor;

    public PruebaClaveAreaPreguntaDistractor() {}

    public PruebaClaveAreaPreguntaDistractor(UUID idPruebaClave, UUID idArea, UUID idPregunta, UUID idDistractor) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
        this.idPregunta = idPregunta;
        this.idDistractor = idDistractor;
    }

    public UUID getIdPruebaClave() { 
        return idPruebaClave; 
    }

    public void setIdPruebaClave(UUID idPruebaClave) { 
        this.idPruebaClave = idPruebaClave; 
    }

    public UUID getIdArea() { 
        return idArea; 
    }
    public void setIdArea(UUID idArea) { 
        this.idArea = idArea; 
    }

    public UUID getIdPregunta() { 
        return idPregunta; 
    }

    public void setIdPregunta(UUID idPregunta) { 
        this.idPregunta = idPregunta; 
    }

    public UUID getIdDistractor() { return idDistractor; }
    public void setIdDistractor(UUID idDistractor) { this.idDistractor = idDistractor; }

    @Override
    public int hashCode() {
        return Objects.hash(idPruebaClave, idArea, idPregunta, idDistractor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PruebaClaveAreaPreguntaDistractor)) return false;
        PruebaClaveAreaPreguntaDistractor other = (PruebaClaveAreaPreguntaDistractor) obj;
        return Objects.equals(idPruebaClave, other.idPruebaClave)
            && Objects.equals(idArea, other.idArea)
            && Objects.equals(idPregunta, other.idPregunta)
            && Objects.equals(idDistractor, other.idDistractor);
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPreguntaDistractor [idPruebaClave=" + idPruebaClave
                + ", idArea=" + idArea
                + ", idPregunta=" + idPregunta
                + ", idDistractor=" + idDistractor + "]";
    }
}