package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prueba_jornada_aula_aspirante_opcion_examen")
public class PruebaJornadaAulaAspiranteOpcionExamen implements Serializable {

    @Id
    @Column(name = "id_prueba_jornada_aula_aspirante_opcion_examen", nullable = false)
    private UUID idPruebaJornadaAulaAspiranteOpcionExamen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_jornada_aula_aspirante_opcion", nullable = false)
    private PruebaJornadaAulaAspiranteOpcion idPruebaJornadaAulaAspiranteOpcion;

    @Column(name = "resultado", precision = 5, scale = 2, nullable = false)
    private BigDecimal resultado;

    @Column(name = "fecha_resultado")
    private OffsetDateTime fechaResultado;

    public PruebaJornadaAulaAspiranteOpcionExamen() {}

    public PruebaJornadaAulaAspiranteOpcionExamen(UUID idPruebaJornadaAulaAspiranteOpcionExamen) {
        this.idPruebaJornadaAulaAspiranteOpcionExamen = idPruebaJornadaAulaAspiranteOpcionExamen;
    }

    public UUID getIdPruebaJornadaAulaAspiranteOpcionExamen() {
        return idPruebaJornadaAulaAspiranteOpcionExamen;
    }

    public void setIdPruebaJornadaAulaAspiranteOpcionExamen(UUID idPruebaJornadaAulaAspiranteOpcionExamen) {
        this.idPruebaJornadaAulaAspiranteOpcionExamen = idPruebaJornadaAulaAspiranteOpcionExamen;
    }

    
    public PruebaJornadaAulaAspiranteOpcion getIdPruebaJornadaAulaAspiranteOpcion() {
        return idPruebaJornadaAulaAspiranteOpcion;
    }

    public void setIdPruebaJornadaAulaAspiranteOpcion(
            PruebaJornadaAulaAspiranteOpcion idPruebaJornadaAulaAspiranteOpcion) {
        this.idPruebaJornadaAulaAspiranteOpcion = idPruebaJornadaAulaAspiranteOpcion;
    }

    public BigDecimal getResultado() {
        return resultado;
    }

    public void setResultado(BigDecimal resultado) {
        this.resultado = resultado;
    }

    public OffsetDateTime getFechaResultado() {
        return fechaResultado;
    }

    public void setFechaResultado(OffsetDateTime fechaResultado) {
        this.fechaResultado = fechaResultado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaJornadaAulaAspiranteOpcionExamen == null) ? 0
                : idPruebaJornadaAulaAspiranteOpcionExamen.hashCode());
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
        PruebaJornadaAulaAspiranteOpcionExamen other = (PruebaJornadaAulaAspiranteOpcionExamen) obj;
        if (idPruebaJornadaAulaAspiranteOpcionExamen == null) {
            if (other.idPruebaJornadaAulaAspiranteOpcionExamen != null)
                return false;
        } else if (!idPruebaJornadaAulaAspiranteOpcionExamen
                .equals(other.idPruebaJornadaAulaAspiranteOpcionExamen))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaJornadaAulaAspiranteOpcionExamen [idPruebaJornadaAulaAspiranteOpcionExamen="
                + idPruebaJornadaAulaAspiranteOpcionExamen + ", resultado=" + resultado
                + ", fechaResultado=" + fechaResultado + "]";
    }
}
