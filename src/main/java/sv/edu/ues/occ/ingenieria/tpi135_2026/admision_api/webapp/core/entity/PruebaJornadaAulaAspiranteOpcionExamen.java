package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorPadre",
        query = "SELECT p FROM PruebaJornadaAulaAspiranteOpcionExamen p WHERE p.idPrueba = :idPrueba AND p.idJornada = :idJornada AND p.idAula = :idAula AND p.idAspiranteOpcion = :idAspiranteOpcion ORDER BY p.fechaResultado"
    ),
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcionExamen.contarPorPadre",
        query = "SELECT COUNT(p) FROM PruebaJornadaAulaAspiranteOpcionExamen p WHERE p.idPrueba = :idPrueba AND p.idJornada = :idJornada AND p.idAula = :idAula AND p.idAspiranteOpcion = :idAspiranteOpcion"
    )
})
@Entity
@IdClass(PruebaJornadaAulaAspiranteOpcionExamenPK.class)
@Table(name = "prueba_jornada_aula_aspirante_opcion_examen")
public class PruebaJornadaAulaAspiranteOpcionExamen implements Serializable {

    @Id
    @Column(name = "id_prueba", nullable = false)
    private UUID idPrueba;

    @Id
    @Column(name = "id_jornada", nullable = false)
    private UUID idJornada;

    @Id
    @Column(name = "id_aula", nullable = false, length = 100)
    private String idAula;

    @Id
    @Column(name = "id_aspirante_opcion", nullable = false)
    private UUID idAspiranteOpcion;

    @Column(name = "id_prueba_clave", nullable = false)
    private UUID idPruebaClave;

    @Column(name = "resultado", precision = 5, scale = 2, nullable = false)
    private BigDecimal resultado;

    @Column(name = "fecha_resultado")
    private OffsetDateTime fechaResultado;

    public PruebaJornadaAulaAspiranteOpcionExamen() {}

    public PruebaJornadaAulaAspiranteOpcionExamen(UUID idPrueba, UUID idJornada, String idAula, UUID idAspiranteOpcion) {
        this.idPrueba = idPrueba;
        this.idJornada = idJornada;
        this.idAula = idAula;
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public PruebaJornadaAulaAspiranteOpcionExamen(UUID legacyId) {
        this.idAspiranteOpcion = legacyId;
    }

    public UUID getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(UUID idPrueba) {
        this.idPrueba = idPrueba;
    }

    public UUID getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(UUID idJornada) {
        this.idJornada = idJornada;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public UUID getIdAspiranteOpcion() {
        return idAspiranteOpcion;
    }

    public void setIdAspiranteOpcion(UUID idAspiranteOpcion) {
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public UUID getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(UUID idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
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
        return Objects.hash(idPrueba, idJornada, idAula, idAspiranteOpcion);
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
        return Objects.equals(idPrueba, other.idPrueba)
                && Objects.equals(idJornada, other.idJornada)
                && Objects.equals(idAula, other.idAula)
                && Objects.equals(idAspiranteOpcion, other.idAspiranteOpcion);
    }

    @Override
    public String toString() {
        return "PruebaJornadaAulaAspiranteOpcionExamen [idPrueba=" + idPrueba + ", idJornada=" + idJornada
            + ", idAula=" + idAula + ", idAspiranteOpcion=" + idAspiranteOpcion + ", resultado=" + resultado
                + ", fechaResultado=" + fechaResultado + "]";
    }
}
