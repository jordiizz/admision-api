package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

import java.io.Serializable;

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
@Table(name = "pregunta_distractor")
public class PreguntaDistractor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta_distractor")
    private Long idPreguntaDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta")
    private Pregunta idPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distractor")
    private Distractor idDistractor;

    @Column(name = "correcto")
    private Boolean idCorrecto;

    public PreguntaDistractor(Long idPreguntaDistractor) {
        this.idPreguntaDistractor = idPreguntaDistractor;
    }

    public PreguntaDistractor(){}

    public Long getIdPreguntaDistractor() {
        return idPreguntaDistractor;
    }

    public void setIdPreguntaDistractor(Long idPreguntaDistractor) {
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

    public Boolean getIdCorrecto() {
        return idCorrecto;
    }

    public void setIdCorrecto(Boolean idCorrecto) {
        this.idCorrecto = idCorrecto;
    }

    

}
