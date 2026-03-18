package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PruebaJornadaPK implements Serializable {

    private UUID idPrueba;
    private UUID idJornada;

    public PruebaJornadaPK() {
    }

    public PruebaJornadaPK(UUID idPrueba, UUID idJornada) {
        this.idPrueba = idPrueba;
        this.idJornada = idJornada;
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

    @Override
    public int hashCode() {
        return Objects.hash(idPrueba, idJornada);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PruebaJornadaPK)) {
            return false;
        }
        PruebaJornadaPK other = (PruebaJornadaPK) obj;
        return Objects.equals(idPrueba, other.idPrueba)
                && Objects.equals(idJornada, other.idJornada);
    }
}
