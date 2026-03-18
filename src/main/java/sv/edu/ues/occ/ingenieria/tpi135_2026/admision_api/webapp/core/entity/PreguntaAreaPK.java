package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PreguntaAreaPK implements Serializable {

    private UUID idPregunta;
    private UUID idArea;

    public PreguntaAreaPK() {
    }

    public PreguntaAreaPK(UUID idPregunta, UUID idArea) {
        this.idPregunta = idPregunta;
        this.idArea = idArea;
    }

    public UUID getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(UUID idPregunta) {
        this.idPregunta = idPregunta;
    }

    public UUID getIdArea() {
        return idArea;
    }

    public void setIdArea(UUID idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPregunta, idArea);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreguntaAreaPK)) {
            return false;
        }
        PreguntaAreaPK other = (PreguntaAreaPK) obj;
        return Objects.equals(idPregunta, other.idPregunta)
                && Objects.equals(idArea, other.idArea);
    }
}
