package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PruebaClaveAreaPreguntaPK implements Serializable {

    private UUID idPruebaClave;
    private UUID idArea;
    private UUID idPregunta;

    public PruebaClaveAreaPreguntaPK() {
    }

    public PruebaClaveAreaPreguntaPK(UUID idPruebaClave, UUID idArea, UUID idPregunta) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
        this.idPregunta = idPregunta;
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

    public UUID getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(UUID idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPruebaClave, idArea, idPregunta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PruebaClaveAreaPreguntaPK)) {
            return false;
        }
        PruebaClaveAreaPreguntaPK other = (PruebaClaveAreaPreguntaPK) obj;
        return Objects.equals(idPruebaClave, other.idPruebaClave)
                && Objects.equals(idArea, other.idArea)
                && Objects.equals(idPregunta, other.idPregunta);
    }
}
