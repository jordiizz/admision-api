package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prueba_clave_area")
public class PruebaClaveArea implements Serializable{

    @Id
    @Column(name = "id_prueba_clave_area")
    private UUID idPruebaClaveArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave", nullable = false)
    private PruebaClave idPruebaClave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area idArea;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @OneToMany(mappedBy = "idPruebaClaveArea")
    private List<PruebaClaveAreaPregunta> listPruebaClaveAreaPregunta;

    public PruebaClaveArea(UUID idPruebaClaveArea) {
        this.idPruebaClaveArea = idPruebaClaveArea;
    }

    public PruebaClaveArea(){}

    public UUID getIdPruebaClaveArea() {
        return idPruebaClaveArea;
    }

    public void setIdPruebaClaveArea(UUID idPruebaClaveArea) {
        this.idPruebaClaveArea = idPruebaClaveArea;
    }

    
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

    @JsonbTransient
    public List<PruebaClaveAreaPregunta> getListPruebaClaveAreaPregunta() {
        return listPruebaClaveAreaPregunta;
    }

    public void setListPruebaClaveAreaPregunta(List<PruebaClaveAreaPregunta> listPruebaClaveAreaPregunta) {
        this.listPruebaClaveAreaPregunta = listPruebaClaveAreaPregunta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaClaveArea == null) ? 0 : idPruebaClaveArea.hashCode());
        return result;
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
        if (idPruebaClaveArea == null) {
            if (other.idPruebaClaveArea != null)
                return false;
        } else if (!idPruebaClaveArea.equals(other.idPruebaClaveArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaClaveArea [idPruebaClaveArea=" + idPruebaClaveArea + ", cantidad=" + cantidad + 
               ", porcentaje=" + porcentaje + "]";
    }
}