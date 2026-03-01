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
@Table(name = "prueba_area")
public class PruebaArea implements Serializable{

    @Id
    @Column(name = "id_prueba_area")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPruebaArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba")
    private Prueba idPrueba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    private Area idArea;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @OneToMany(mappedBy = "idPruebaArea")
    private List<PruebaAreaPregunta> listPruebaAreaPregunta;

    public PruebaArea(Long idPruebaArea) {
        this.idPruebaArea = idPruebaArea;
    }

    public PruebaArea(){}

    public Long getIdPruebaArea() {
        return idPruebaArea;
    }

    public void setIdPruebaArea(Long idPruebaArea) {
        this.idPruebaArea = idPruebaArea;
    }

    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    @JsonbTransient
    public List<PruebaAreaPregunta> getListPruebaAreaPregunta() {
        return listPruebaAreaPregunta;
    }

    public void setListPruebaAreaPregunta(List<PruebaAreaPregunta> listPruebaAreaPregunta) {
        this.listPruebaAreaPregunta = listPruebaAreaPregunta;
    }

    


}
