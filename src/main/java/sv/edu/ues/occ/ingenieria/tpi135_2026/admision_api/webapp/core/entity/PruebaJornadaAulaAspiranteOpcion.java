package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.buscarPorPruebaJornadaYJornadaAula",
        query = "SELECT p FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idPrueba = :idPrueba AND p.idJornada = :idJornada AND p.idAula = :idAula ORDER BY p.idAspiranteOpcion"
    ),
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.contarPorPruebaJornadaYJornadaAula",
        query = "SELECT COUNT(p) FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idPrueba = :idPrueba AND p.idJornada = :idJornada AND p.idAula = :idAula"
    ),
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.buscarPorIdYPruebaJornadaYJornadaAula",
        query = "SELECT p FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idAspiranteOpcion = :idAspiranteOpcion AND p.idPrueba = :idPrueba AND p.idJornada = :idJornada AND p.idAula = :idAula"
    )
})
@Entity
@IdClass(PruebaJornadaAulaAspiranteOpcionPK.class)
@Table(name = "prueba_jornada_aula_aspirante_opcion")
public class PruebaJornadaAulaAspiranteOpcion implements Serializable {

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

    @Column(name = "fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime fecha;

    @Column(name = "activo")
    private Boolean activo;

    public PruebaJornadaAulaAspiranteOpcion() {}

    public PruebaJornadaAulaAspiranteOpcion(UUID idPrueba, UUID idJornada, String idAula, UUID idAspiranteOpcion) {
        this.idPrueba = idPrueba;
        this.idJornada = idJornada;
        this.idAula = idAula;
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public PruebaJornadaAulaAspiranteOpcion(UUID legacyId) {
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

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
        PruebaJornadaAulaAspiranteOpcion other = (PruebaJornadaAulaAspiranteOpcion) obj;
        return Objects.equals(idPrueba, other.idPrueba)
                && Objects.equals(idJornada, other.idJornada)
                && Objects.equals(idAula, other.idAula)
            && Objects.equals(idAspiranteOpcion, other.idAspiranteOpcion);
    }

    @Override
    public String toString() {
        return "PruebaJornadaAulaAspiranteOpcion [idPrueba=" + idPrueba + ", idJornada=" + idJornada
            + ", idAula=" + idAula + ", idAspiranteOpcion=" + idAspiranteOpcion
            + ", fecha=" + fecha + ", activo=" + activo + "]";
    }
}
