package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(PruebaClaveAreaPreguntaPK.class)
@Table(name = "prueba_clave_area_pregunta")
public class PruebaClaveAreaPregunta implements Serializable{

    @Id
    @Column(name = "id_prueba_clave", nullable = false)
    private UUID idPruebaClave;

    @Id
    @Column(name = "id_area", nullable = false)
    private UUID idArea;

    @Id
    @Column(name = "id_pregunta", nullable = false)
    private UUID idPregunta;

    @Column(name = "porcentaje", precision = 5, scale = 2, nullable = false)
    private BigDecimal porcentaje;

    public PruebaClaveAreaPregunta(UUID idPruebaClave, UUID idArea, UUID idPregunta) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
        this.idPregunta = idPregunta;
    }

    public PruebaClaveAreaPregunta(UUID legacyId) {
        this.idPregunta = legacyId;
    }

    public PruebaClaveAreaPregunta(){}

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

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
    @Override
    public int hashCode() {
        return Objects.hash(idPruebaClave, idArea, idPregunta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PruebaClaveAreaPregunta other = (PruebaClaveAreaPregunta) obj;
        return Objects.equals(idPruebaClave, other.idPruebaClave)
                && Objects.equals(idArea, other.idArea)
            && Objects.equals(idPregunta, other.idPregunta);
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPregunta [idPruebaClave=" + idPruebaClave + ", idArea=" + idArea
            + ", idPregunta=" + idPregunta
                + ", porcentaje=" + porcentaje + "]";
    }
}
