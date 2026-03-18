package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DistractorAreaPK implements Serializable {

    private UUID idDistractor;
    private UUID idArea;

    public DistractorAreaPK() {
    }

    public DistractorAreaPK(UUID idDistractor, UUID idArea) {
        this.idDistractor = idDistractor;
        this.idArea = idArea;
    }

    public UUID getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(UUID idDistractor) {
        this.idDistractor = idDistractor;
    }

    public UUID getIdArea() {
        return idArea;
    }

    public void setIdArea(UUID idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDistractor, idArea);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DistractorAreaPK)) {
            return false;
        }
        DistractorAreaPK other = (DistractorAreaPK) obj;
        return Objects.equals(idDistractor, other.idDistractor)
                && Objects.equals(idArea, other.idArea);
    }
}
