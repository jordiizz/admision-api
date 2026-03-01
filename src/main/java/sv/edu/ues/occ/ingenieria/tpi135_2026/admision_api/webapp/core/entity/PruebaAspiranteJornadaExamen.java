package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
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
@Table(name = "prueba_aspirante_jornada_examen")
public class PruebaAspiranteJornadaExamen implements Serializable{

    @Id
    @Column(name = "id_prueba_aspirante_jornada_examen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPruebaAspiranteJornadaExamen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_aspirante_jornada")
    private PruebaAspiranteJornada idPruebaAspiranteJornada;

    @Column(name = "se_presento")
    private Boolean sePresento;

    @Column(name = "ubicacion_prueba")
    private String ubicacionPrueba;

    @Column(name = "puntaje_total", precision = 5, scale = 2)
    private BigDecimal puntajeTotal;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

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

    @JsonbTransient
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

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaAspiranteJornadaExamen == null) ? 0 : idPruebaAspiranteJornadaExamen.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PruebaAspiranteJornadaExamen other = (PruebaAspiranteJornadaExamen) obj;
        if (idPruebaAspiranteJornadaExamen == null) {
            if (other.idPruebaAspiranteJornadaExamen != null)
                return false;
        } else if (!idPruebaAspiranteJornadaExamen.equals(other.idPruebaAspiranteJornadaExamen))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaAspiranteJornadaExamen [idPruebaAspiranteJornadaExamen=" + idPruebaAspiranteJornadaExamen + 
               ", sePresento=" + sePresento + ", ubicacionPrueba=" + ubicacionPrueba + 
               ", puntajeTotal=" + puntajeTotal + ", fechaCreacion=" + fechaCreacion + "]";
    }
}
