package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "distractor_area")
public class DistractorArea implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distractor_area")
    private Long idDistractorArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor")
    private Distractor idDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    private Area idArea;

    public DistractorArea(Long idDistractorArea) {
        this.idDistractorArea = idDistractorArea;
    }

    public DistractorArea(){}

    public Long getIdDistractorArea() {
        return idDistractorArea;
    }

    public void setIdDistractorArea(Long idDistractorArea) {
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

    

}
