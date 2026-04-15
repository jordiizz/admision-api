package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.buscarPorPruebaJornadaYJornadaAula",
        query = "SELECT p FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idPrueba.idPrueba = :idPrueba AND p.idJornada.idJornada = :idJornada AND p.idAula = :idAula ORDER BY p.idAspiranteOpcion.idAspiranteOpcion"
    ),
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.contarPorPruebaJornadaYJornadaAula",
        query = "SELECT COUNT(p) FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idPrueba.idPrueba = :idPrueba AND p.idJornada.idJornada = :idJornada AND p.idAula = :idAula"
    ),
    @NamedQuery(
        name = "PruebaJornadaAulaAspiranteOpcion.buscarPorIdYPruebaJornadaYJornadaAula",
        query = "SELECT p FROM PruebaJornadaAulaAspiranteOpcion p WHERE p.idAspiranteOpcion.idAspiranteOpcion = :idAspiranteOpcion AND p.idPrueba.idPrueba = :idPrueba AND p.idJornada.idJornada = :idJornada AND p.idAula = :idAula"
    )
})
@Entity
@IdClass(PruebaJornadaAulaAspiranteOpcionPK.class)
@Table(name = "prueba_jornada_aula_aspirante_opcion")
public class PruebaJornadaAulaAspiranteOpcion implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba", nullable = false)
    private Prueba idPrueba;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornada", nullable = false)
    private Jornada idJornada;

    @Id
    @Column(name = "id_aula", nullable = false, length = 100)
    private String idAula;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante_opcion", nullable = false)
    private AspiranteOpcion idAspiranteOpcion;

    @Column(name = "fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime fecha;

    @Column(name = "activo")
    private Boolean activo;

    public PruebaJornadaAulaAspiranteOpcion() {}

    public PruebaJornadaAulaAspiranteOpcion(Prueba idPrueba, Jornada idJornada, String idAula, AspiranteOpcion idAspiranteOpcion) {
        this.idPrueba = idPrueba;
        this.idJornada = idJornada;
        this.idAula = idAula;
        this.idAspiranteOpcion = idAspiranteOpcion;
    }

    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
    }

    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public AspiranteOpcion getIdAspiranteOpcion() {
        return idAspiranteOpcion;
    }

    public void setIdAspiranteOpcion(AspiranteOpcion idAspiranteOpcion) {
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
        return Objects.hash(
                idPrueba != null ? idPrueba.getIdPrueba() : null,
                idJornada != null ? idJornada.getIdJornada() : null,
                idAula,
                idAspiranteOpcion != null ? idAspiranteOpcion.getIdAspiranteOpcion() : null);
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
        return Objects.equals(idPrueba != null ? idPrueba.getIdPrueba() : null,
                other.idPrueba != null ? other.idPrueba.getIdPrueba() : null)
                && Objects.equals(idJornada != null ? idJornada.getIdJornada() : null,
                        other.idJornada != null ? other.idJornada.getIdJornada() : null)
                && Objects.equals(idAula, other.idAula)
            && Objects.equals(idAspiranteOpcion != null ? idAspiranteOpcion.getIdAspiranteOpcion() : null,
                    other.idAspiranteOpcion != null ? other.idAspiranteOpcion.getIdAspiranteOpcion() : null);
    }

    @Override
    public String toString() {
        return "PruebaJornadaAulaAspiranteOpcion [idPrueba="
            + (idPrueba != null ? idPrueba.getIdPrueba() : null)
            + ", idJornada=" + (idJornada != null ? idJornada.getIdJornada() : null)
            + ", idAula=" + idAula + ", idAspiranteOpcion="
            + (idAspiranteOpcion != null ? idAspiranteOpcion.getIdAspiranteOpcion() : null)
            + ", fecha=" + fecha + ", activo=" + activo + "]";
    }
}
