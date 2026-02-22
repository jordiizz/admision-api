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
@Table(name = "prueba_area_pregunta_distractor")
public class PruebaAreaPreguntaDistractor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prueba_area_pregunta_distractor")
    private Long idPruebaAreaPreguntaDistractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_area_pregunta")
    private PruebaAreaPregunta idPruebaAreaPregunta;

    @ManyToOne()
    @JoinColumn(name = "id_distractor")
    private Distractor idDistractor;

    public PruebaAreaPreguntaDistractor(Long idPruebaAreaPreguntaDistractor) {
        this.idPruebaAreaPreguntaDistractor = idPruebaAreaPreguntaDistractor;
    }

    public PruebaAreaPreguntaDistractor(){}

    public Long getIdPruebaAreaPreguntaDistractor() {
        return idPruebaAreaPreguntaDistractor;
    }

    public void setIdPruebaAreaPreguntaDistractor(Long idPruebaAreaPreguntaDistractor) {
        this.idPruebaAreaPreguntaDistractor = idPruebaAreaPreguntaDistractor;
    }

    public PruebaAreaPregunta getIdPruebaAreaPregunta() {
        return idPruebaAreaPregunta;
    }

    public void setIdPruebaAreaPregunta(PruebaAreaPregunta idPruebaAreaPregunta) {
        this.idPruebaAreaPregunta = idPruebaAreaPregunta;
    }

    public Distractor getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(Distractor idDistractor) {
        this.idDistractor = idDistractor;
    }

    
    
}
