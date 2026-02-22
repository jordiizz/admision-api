package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

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
@Table(name = "pregunta")
public class Pregunta implements Serializable{

    @Id
    @Column(name = "id_pregunta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;

    @Column(name = "valor")
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @OneToMany(mappedBy = "idPregunta")
    private List<PruebaAreaPregunta> listPruebaAreaPregunta;

    @OneToMany(mappedBy = "idPregunta")
    private List<PreguntaDistractor> listPreguntaDistractor;

    @OneToMany(mappedBy = "idPregunta")
    private List<PreguntaArea> listPreguntaArea;

    public Pregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Pregunta(){}

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @JsonbTransient
    public List<PruebaAreaPregunta> getListPruebaAreaPregunta() {
        return listPruebaAreaPregunta;
    }

    public void setListPruebaAreaPregunta(List<PruebaAreaPregunta> listPruebaAreaPregunta) {
        this.listPruebaAreaPregunta = listPruebaAreaPregunta;
    }

    @JsonbTransient
    public List<PreguntaDistractor> getListPreguntaDistractor() {
        return listPreguntaDistractor;
    }

    public void setListPreguntaDistractor(List<PreguntaDistractor> listPreguntaDistractor) {
        this.listPreguntaDistractor = listPreguntaDistractor;
    }

    @JsonbTransient
    public List<PreguntaArea> getListPreguntaArea() {
        return listPreguntaArea;
    }

    public void setListPreguntaArea(List<PreguntaArea> listPreguntaArea) {
        this.listPreguntaArea = listPreguntaArea;
    }

    
}
