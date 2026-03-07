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
@Table(name = "pregunta_area")
public class PreguntaArea implements Serializable{

    @Id
    @Column(name = "id_pregunta_area")
    private UUID idPreguntaArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta")
    private Pregunta idPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    private Area idArea;

    public PreguntaArea(UUID idPreguntaArea) {
        this.idPreguntaArea = idPreguntaArea;
    }

    public PreguntaArea(){}

    public UUID getIdPreguntaArea() {
        return idPreguntaArea;
    }

    public void setIdPreguntaArea(UUID idPreguntaArea) {
        this.idPreguntaArea = idPreguntaArea;
    }

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPreguntaArea == null) ? 0 : idPreguntaArea.hashCode());
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
        PreguntaArea other = (PreguntaArea) obj;
        if (idPreguntaArea == null) {
            if (other.idPreguntaArea != null)
                return false;
        } else if (!idPreguntaArea.equals(other.idPreguntaArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PreguntaArea [idPreguntaArea=" + idPreguntaArea + "]";
    }
}
