package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(DistractorAreaPK.class)
@Table(name = "distractor_area")
public class DistractorArea implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor", nullable = false)
    private Distractor idDistractor;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    public DistractorArea(Distractor idDistractor, Area idArea) {
        this.idDistractor = idDistractor;
        this.idArea = idArea;
    }

    public DistractorArea(){}

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
        return Objects.hash(
                idDistractor != null ? idDistractor.getIdDistractor() : null,
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
        DistractorArea other = (DistractorArea) obj;
        return Objects.equals(idDistractor != null ? idDistractor.getIdDistractor() : null,
                other.idDistractor != null ? other.idDistractor.getIdDistractor() : null)
                && Objects.equals(idArea != null ? idArea.getIdArea() : null,
                        other.idArea != null ? other.idArea.getIdArea() : null);
    }

    @Override
    public String toString() {
        return "DistractorArea [idDistractor="
                + (idDistractor != null ? idDistractor.getIdDistractor() : null)
                + ", idArea=" + (idArea != null ? idArea.getIdArea() : null) + "]";
    }
}
