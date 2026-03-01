package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prueba")
public class Prueba implements Serializable{

    @Id
    @Column(name = "id_prueba")
    private UUID idPrueba;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "clave")
    private char clave;

    @Column(name = "indicaciones")
    private String indicaciones;

    @Column(name = "puntaje_maximo", precision = 5, scale = 2)
    private BigDecimal puntajeMaximo;

    @Column(name = "nota_aprobacion", precision = 5, scale = 2)
    private BigDecimal notaAprobacion;

    @Column(name = "duracion")
    private Integer duracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_prueba")
    private TipoPrueba idTipoPrueba;

    @OneToMany(mappedBy = "idPrueba")
    private List<PruebaAspiranteJornada> listPruebaAspiranteJornada;

    @OneToMany(mappedBy = "idPrueba")
    private List<PruebaArea> listPruebaArea;

    public Prueba(UUID idPrueba) {
        this.idPrueba = idPrueba;
    }

    public Prueba(){}

    public UUID getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(UUID idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getClave() {
        return clave;
    }

    public void setClave(char clave) {
        this.clave = clave;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public BigDecimal getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(BigDecimal puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public BigDecimal getNotaAprobacion() {
        return notaAprobacion;
    }

    public void setNotaAprobacion(BigDecimal notaAprobacion) {
        this.notaAprobacion = notaAprobacion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public TipoPrueba getIdTipoPrueba() {
        return idTipoPrueba;
    }

    public void setIdTipoPrueba(TipoPrueba idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    @JsonbTransient
    public List<PruebaAspiranteJornada> getListPruebaAspiranteJornada() {
        return listPruebaAspiranteJornada;
    }

    public void setListPruebaAspiranteJornada(List<PruebaAspiranteJornada> listPruebaAspiranteJornada) {
        this.listPruebaAspiranteJornada = listPruebaAspiranteJornada;
    }

    @JsonbTransient
    public List<PruebaArea> getListPruebaArea() {
        return listPruebaArea;
    }

    public void setListPruebaArea(List<PruebaArea> listPruebaArea) {
        this.listPruebaArea = listPruebaArea;
    }


    

}
