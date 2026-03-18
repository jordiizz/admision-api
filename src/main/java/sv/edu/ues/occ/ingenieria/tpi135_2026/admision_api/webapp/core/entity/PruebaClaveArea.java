package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PruebaClaveArea.buscarPorPruebaClave",
        query = "SELECT p FROM PruebaClaveArea p WHERE p.idPruebaClave.idPruebaClave = :idPruebaClave ORDER BY p.idArea.idArea"
    ),
    @NamedQuery(
        name = "PruebaClaveArea.contarPorPruebaClave",
        query = "SELECT COUNT(p) FROM PruebaClaveArea p WHERE p.idPruebaClave.idPruebaClave = :idPruebaClave"
    )
})
@Entity
@IdClass(PruebaClaveAreaPK.class)
@Table(name = "prueba_clave_area")
public class PruebaClaveArea implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave", nullable = false)
    private PruebaClave idPruebaClave;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    public PruebaClaveArea(PruebaClave idPruebaClave, Area idArea) {
        this.idPruebaClave = idPruebaClave;
        this.idArea = idArea;
    }

    public PruebaClaveArea(UUID legacyId) {
        Area area = new Area();
        area.setIdArea(legacyId);
        this.idArea = area;
    }

    public PruebaClaveArea(){}

    
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

    @Transient
    public UUID getIdPruebaClaveArea() {
        return idArea != null ? idArea.getIdArea() : null;
    }

    public void setIdPruebaClaveArea(UUID legacyId) {
        if (legacyId == null) {
            this.idArea = null;
            return;
        }
        if (this.idArea == null) {
            this.idArea = new Area();
        }
        this.idArea.setIdArea(legacyId);
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
                idArea != null ? idArea.getIdArea() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PruebaClaveArea other = (PruebaClaveArea) obj;
        return Objects.equals(idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null,
                other.idPruebaClave != null ? other.idPruebaClave.getIdPruebaClave() : null)
                && Objects.equals(idArea != null ? idArea.getIdArea() : null,
                        other.idArea != null ? other.idArea.getIdArea() : null);
    }

    @Override
    public String toString() {
        return "PruebaClaveArea [idPruebaClave="
                + (idPruebaClave != null ? idPruebaClave.getIdPruebaClave() : null)
                + ", idArea=" + (idArea != null ? idArea.getIdArea() : null)
                + ", cantidad=" + cantidad + ", porcentaje=" + porcentaje + "]";
    }
}
