package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prueba_aspirante_jornada_examen")
public class PruebaAspiranteJornadaExamen implements Serializable{

    @Id
    @Column(name = "id_prueba_aspirante_jornada_examen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPruebaAspiranteJornadaExamen;

    @ManyToOne()
    @JoinColumn(name = "id_prueba_aspirante_jornada")    
    private PruebaAspiranteJornada idPruebaAspiranteJornada;

    @Column(name = "se_presento")
    private Boolean sePresento;

    @Column(name = "ubicacion_prueba")
    private String ubicacionPrueba;

    @Column(name = "puntaje_total", precision = 5, scale = 2)
    private BigDecimal puntajeTotal;

    public PruebaAspiranteJornadaExamen(Integer idPruebaAspiranteJornadaExamen) {
        this.idPruebaAspiranteJornadaExamen = idPruebaAspiranteJornadaExamen;
    }

    public PruebaAspiranteJornadaExamen(){}

    public Integer getIdPruebaAspiranteJornadaExamen() {
        return idPruebaAspiranteJornadaExamen;
    }

    public void setIdPruebaAspiranteJornadaExamen(Integer idPruebaAspiranteJornadaExamen) {
        this.idPruebaAspiranteJornadaExamen = idPruebaAspiranteJornadaExamen;
    }

    public PruebaAspiranteJornada getIdPruebaAspiranteJornada() {
        return idPruebaAspiranteJornada;
    }

    public void setIdPruebaAspiranteJornada(PruebaAspiranteJornada idPruebaAspiranteJornada) {
        this.idPruebaAspiranteJornada = idPruebaAspiranteJornada;
    }

    public Boolean getSePresento() {
        return sePresento;
    }

    public void setSePresento(Boolean sePresento) {
        this.sePresento = sePresento;
    }

    public String getUbicacionPrueba() {
        return ubicacionPrueba;
    }

    public void setUbicacionPrueba(String ubicacionPrueba) {
        this.ubicacionPrueba = ubicacionPrueba;
    }

    public BigDecimal getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(BigDecimal puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    
}
