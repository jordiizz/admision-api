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
@Table(name = "prueba_clave_area_pregunta")
public class PruebaClaveAreaPregunta implements Serializable{

    @Id
    @Column(name = "id_prueba_clave_area_pregunta")
    private UUID idPruebaClaveAreaPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave_area", nullable = false)
    private PruebaClaveArea idPruebaClaveArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta idPregunta;

    @Column(name = "porcentaje", precision = 5, scale = 2, nullable = false)
    private BigDecimal porcentaje;

    @OneToMany(mappedBy = "idPruebaClaveAreaPregunta")
    private List<PruebaClaveAreaPreguntaDistractor> listPruebaClaveAreaPreguntaDistractor;

    public PruebaClaveAreaPregunta(UUID idPruebaClaveAreaPregunta) {
        this.idPruebaClaveAreaPregunta = idPruebaClaveAreaPregunta;
    }

    public PruebaClaveAreaPregunta(){}

    public UUID getIdPruebaClaveAreaPregunta() {
        return idPruebaClaveAreaPregunta;
    }

    public void setIdPruebaClaveAreaPregunta(UUID idPruebaClaveAreaPregunta) {
        this.idPruebaClaveAreaPregunta = idPruebaClaveAreaPregunta;
    }

    @JsonbTransient
    public PruebaClaveArea getIdPruebaClaveArea() {
        return idPruebaClaveArea;
    }

    public void setIdPruebaClaveArea(PruebaClaveArea idPruebaClaveArea) {
        this.idPruebaClaveArea = idPruebaClaveArea;
    }

    @JsonbTransient
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

    @JsonbTransient
    public List<PruebaClaveAreaPreguntaDistractor> getListPruebaClaveAreaPreguntaDistractor() {
        return listPruebaClaveAreaPreguntaDistractor;
    }

    public void setListPruebaClaveAreaPreguntaDistractor(List<PruebaClaveAreaPreguntaDistractor> listPruebaClaveAreaPreguntaDistractor) {
        this.listPruebaClaveAreaPreguntaDistractor = listPruebaClaveAreaPreguntaDistractor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaClaveAreaPregunta == null) ? 0 : idPruebaClaveAreaPregunta.hashCode());
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
        PruebaClaveAreaPregunta other = (PruebaClaveAreaPregunta) obj;
        if (idPruebaClaveAreaPregunta == null) {
            if (other.idPruebaClaveAreaPregunta != null)
                return false;
        } else if (!idPruebaClaveAreaPregunta.equals(other.idPruebaClaveAreaPregunta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaClaveAreaPregunta [idPruebaClaveAreaPregunta=" + idPruebaClaveAreaPregunta + 
               ", porcentaje=" + porcentaje + "]";
    }
}