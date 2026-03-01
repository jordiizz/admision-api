package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "distractor")
public class Distractor implements Serializable{

    @Id
    @Column(name = "id_distractor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDistractor;

    @Column(name = "valor")
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "idDistractor")
    private List<PruebaAreaPreguntaDistractor> listPruebaAreaPreguntaDistractor;

    @OneToMany(mappedBy = "idDistractor")
    private List<PreguntaDistractor> listPreguntaDistractor;

    @OneToMany(mappedBy = "idDistractor")
    private List<DistractorArea> listDistractorAreas;

    public Distractor(Long idDistractor) {
        this.idDistractor = idDistractor;
    }

    public Distractor(){}

    public Long getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(Long idDistractor) {
        this.idDistractor = idDistractor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @JsonbTransient
    public List<PruebaAreaPreguntaDistractor> getListPruebaAreaPreguntaDistractor() {
        return listPruebaAreaPreguntaDistractor;
    }

    public void setListPruebaAreaPreguntaDistractor(List<PruebaAreaPreguntaDistractor> listPruebaAreaPreguntaDistractor) {
        this.listPruebaAreaPreguntaDistractor = listPruebaAreaPreguntaDistractor;
    }

    @JsonbTransient
    public List<PreguntaDistractor> getListPreguntaDistractor() {
        return listPreguntaDistractor;
    }

    public void setListPreguntaDistractor(List<PreguntaDistractor> listPreguntaDistractor) {
        this.listPreguntaDistractor = listPreguntaDistractor;
    }

    @JsonbTransient
    public List<DistractorArea> getListDistractorAreas() {
        return listDistractorAreas;
    }

    public void setListDistractorAreas(List<DistractorArea> listDistractorAreas) {
        this.listDistractorAreas = listDistractorAreas;
    }

    




}
