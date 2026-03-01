package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prueba_area_pregunta")
public class PruebaAreaPregunta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prueba_area_pregunta")
    private Long idPruebaAreaPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_area")
    private PruebaArea idPruebaArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta")
    private Pregunta idPregunta;

    @OneToMany(mappedBy = "idPruebaAreaPregunta")
    private List<PruebaAreaPreguntaDistractor> listPruebaAreaPreguntaDistractor;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    public PruebaAreaPregunta(Long idPruebaAreaPregunta) {
        this.idPruebaAreaPregunta = idPruebaAreaPregunta;
    }

    public PruebaAreaPregunta(){}

    public Long getIdPruebaAreaPregunta() {
        return idPruebaAreaPregunta;
    }

    public void setIdPruebaAreaPregunta(Long idPruebaAreaPregunta) {
        this.idPruebaAreaPregunta = idPruebaAreaPregunta;
    }

    public PruebaArea getIdPruebaArea() {
        return idPruebaArea;
    }

    public void setIdPruebaArea(PruebaArea idPruebaArea) {
        this.idPruebaArea = idPruebaArea;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    @JsonbTransient
    public List<PruebaAreaPreguntaDistractor> getListPruebaAreaPreguntaDistractor() {
        return listPruebaAreaPreguntaDistractor;
    }

    public void setListPruebaAreaPreguntaDistractor(List<PruebaAreaPreguntaDistractor> listPruebaAreaPreguntaDistractor) {
        this.listPruebaAreaPreguntaDistractor = listPruebaAreaPreguntaDistractor;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    

}
