package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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
    @NamedQuery(name = "PruebaClaveAreaPregunta.findByClaveAndArea", 
                query = "SELECT pcap FROM PruebaClaveAreaPregunta pcap WHERE pcap.idPruebaClave.idPruebaClave = :idPruebaClave AND pcap.idArea.idArea = :idArea")
})
@Entity
@IdClass(PruebaClaveAreaPreguntaPK.class)
@Table(name = "prueba_clave_area_pregunta")
public class PruebaClaveAreaPregunta implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave", nullable = false)
    private PruebaClave idPruebaClave;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta idPregunta;

    @Column(name = "porcentaje", precision = 5, scale = 2, nullable = false)
    private BigDecimal porcentaje;

    public PruebaClaveAreaPregunta(PruebaClave idPruebaClave, Area idArea, Pregunta idPregunta) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
        this.idPregunta = idPregunta;
    }

    public PruebaClaveAreaPregunta(){}

    public PruebaClave getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(PruebaClave idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
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
        return Objects.hash(
                idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null,
                idArea != null ? idArea.getIdArea() : null,
                idPregunta != null ? idPregunta.getIdPregunta() : null);
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
        return Objects.equals(idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null,
                other.idPruebaClave != null ? other.idPruebaClave.getIdPruebaClave() : null)
                && Objects.equals(idArea != null ? idArea.getIdArea() : null,
                        other.idArea != null ? other.idArea.getIdArea() : null)
                && Objects.equals(idPregunta != null ? idPregunta.getIdPregunta() : null,
                        other.idPregunta != null ? other.idPregunta.getIdPregunta() : null);
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPregunta [idPruebaClave="
            + (idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null)
            + ", idArea=" + (idArea != null ? idArea.getIdArea() : null)
            + ", idPregunta=" + (idPregunta != null ? idPregunta.getIdPregunta() : null)
                + ", porcentaje=" + porcentaje + "]";
    }
}
