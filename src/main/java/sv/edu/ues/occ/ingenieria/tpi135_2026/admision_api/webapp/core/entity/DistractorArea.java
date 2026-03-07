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
@Table(name = "distractor_area")
public class DistractorArea implements Serializable{

    @Id
    @Column(name = "id_distractor_area")
    private UUID idDistractorArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor")
    private Distractor idDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    private Area idArea;

    public DistractorArea(UUID idDistractorArea) {
        this.idDistractorArea = idDistractorArea;
    }

    public DistractorArea(){}

    public UUID getIdDistractorArea() {
        return idDistractorArea;
    }

    public void setIdDistractorArea(UUID idDistractorArea) {
        this.idDistractorArea = idDistractorArea;
    }

    public Distractor getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(Distractor idDistractor) {
        this.idDistractor = idDistractor;
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
        result = prime * result + ((idDistractorArea == null) ? 0 : idDistractorArea.hashCode());
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
        DistractorArea other = (DistractorArea) obj;
        if (idDistractorArea == null) {
            if (other.idDistractorArea != null)
                return false;
        } else if (!idDistractorArea.equals(other.idDistractorArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DistractorArea [idDistractorArea=" + idDistractorArea + "]";
    }
}
