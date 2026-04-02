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
        name = "PreguntaArea.buscarPorPreguntaRango",
        query = "SELECT p FROM PreguntaArea p WHERE p.idPregunta.idPregunta = :idPregunta ORDER BY p.idArea.idArea"
    ),
    @NamedQuery(
        name = "PreguntaArea.contarPorPregunta",
        query = "SELECT COUNT(p) FROM PreguntaArea p WHERE p.idPregunta.idPregunta = :idPregunta"
    )
})
@Entity
@IdClass(PreguntaAreaPK.class)
@Table(name = "pregunta_area")
public class PreguntaArea implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta idPregunta;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    public PreguntaArea(Pregunta idPregunta, Area idArea) {
        this.idPregunta = idPregunta;
        this.idArea = idArea;
    }

    public PreguntaArea(){}

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                idPregunta != null ? idPregunta.getIdPregunta() : null,
                idArea != null ? idArea.getIdArea() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PreguntaArea other = (PreguntaArea) obj;
        return Objects.equals(idPregunta != null ? idPregunta.getIdPregunta() : null,
                other.idPregunta != null ? other.idPregunta.getIdPregunta() : null)
                && Objects.equals(idArea != null ? idArea.getIdArea() : null,
                        other.idArea != null ? other.idArea.getIdArea() : null);
    }

    @Override
    public String toString() {
        return "PreguntaArea [idPregunta="
                + (idPregunta != null ? idPregunta.getIdPregunta() : null)
                + ", idArea=" + (idArea != null ? idArea.getIdArea() : null) + "]";
    }
}
