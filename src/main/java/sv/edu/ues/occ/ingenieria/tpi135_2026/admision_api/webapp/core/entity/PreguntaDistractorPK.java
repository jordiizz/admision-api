package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PreguntaDistractorPK implements Serializable {

    private UUID idPregunta;
    private UUID idDistractor;

    public PreguntaDistractorPK() {
    }

    public PreguntaDistractorPK(UUID idPregunta, UUID idDistractor) {
        this.idPregunta = idPregunta;
        this.idDistractor = idDistractor;
    }

    public UUID getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(UUID idPregunta) {
        this.idPregunta = idPregunta;
    }

    public UUID getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(UUID idDistractor) {
        this.idDistractor = idDistractor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPregunta, idDistractor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreguntaDistractorPK)) {
            return false;
        }
        PreguntaDistractorPK other = (PreguntaDistractorPK) obj;
        return Objects.equals(idPregunta, other.idPregunta)
                && Objects.equals(idDistractor, other.idDistractor);
    }
}
