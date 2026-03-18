package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PruebaJornadaAulaAspiranteOpcionExamenPK implements Serializable {

    private UUID idPrueba;
    private UUID idJornada;
    private String idAula;
    private UUID idAspiranteOpcion;

    public PruebaJornadaAulaAspiranteOpcionExamenPK() {
    }

    public PruebaJornadaAulaAspiranteOpcionExamenPK(UUID idPrueba, UUID idJornada, String idAula, UUID idAspiranteOpcion) {
        this.idPrueba = idPrueba;
        this.idJornada = idJornada;
        this.idAula = idAula;
        this.idAspiranteOpcion = idAspiranteOpcion;
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

    @Override
    public int hashCode() {
        return Objects.hash(idPrueba, idJornada, idAula, idAspiranteOpcion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PruebaJornadaAulaAspiranteOpcionExamenPK)) {
            return false;
        }
        PruebaJornadaAulaAspiranteOpcionExamenPK other = (PruebaJornadaAulaAspiranteOpcionExamenPK) obj;
        return Objects.equals(idPrueba, other.idPrueba)
                && Objects.equals(idJornada, other.idJornada)
                && Objects.equals(idAula, other.idAula)
                && Objects.equals(idAspiranteOpcion, other.idAspiranteOpcion);
    }
}
