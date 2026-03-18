package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PruebaClaveAreaPK implements Serializable {

    private UUID idPruebaClave;
    private UUID idArea;

    public PruebaClaveAreaPK() {
    }

    public PruebaClaveAreaPK(UUID idPruebaClave, UUID idArea) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
    }

    public UUID getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(UUID idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public UUID getIdArea() {
        return idArea;
    }

    public void setIdArea(UUID idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPruebaClave, idArea);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PruebaClaveAreaPK)) {
            return false;
        }
        PruebaClaveAreaPK other = (PruebaClaveAreaPK) obj;
        return Objects.equals(idPruebaClave, other.idPruebaClave)
                && Objects.equals(idArea, other.idArea);
    }
}
