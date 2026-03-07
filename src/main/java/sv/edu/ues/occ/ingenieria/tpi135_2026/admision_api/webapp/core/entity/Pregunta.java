package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable{

    @Id
    @Column(name = "id_pregunta")
    private UUID idPregunta;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @OneToMany(mappedBy = "idPregunta")
    private List<PruebaClaveAreaPregunta> listPruebaClaveAreaPregunta;

    @OneToMany(mappedBy = "idPregunta")
    private List<PreguntaDistractor> listPreguntaDistractor;

    @OneToMany(mappedBy = "idPregunta")
    private List<PreguntaArea> listPreguntaArea;

    public Pregunta(UUID idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Pregunta(){}

    public UUID getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(UUID idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @JsonbTransient
    public List<PruebaClaveAreaPregunta> getListPruebaClaveAreaPregunta() {
        return listPruebaClaveAreaPregunta;
    }

    public void setListPruebaClaveAreaPregunta(List<PruebaClaveAreaPregunta> listPruebaClaveAreaPregunta) {
        this.listPruebaClaveAreaPregunta = listPruebaClaveAreaPregunta;
    }

    @JsonbTransient
    public List<PreguntaDistractor> getListPreguntaDistractor() {
        return listPreguntaDistractor;
    }

    public void setListPreguntaDistractor(List<PreguntaDistractor> listPreguntaDistractor) {
        this.listPreguntaDistractor = listPreguntaDistractor;
    }

    @JsonbTransient
    public List<PreguntaArea> getListPreguntaArea() {
        return listPreguntaArea;
    }

    public void setListPreguntaArea(List<PreguntaArea> listPreguntaArea) {
        this.listPreguntaArea = listPreguntaArea;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPregunta == null) ? 0 : idPregunta.hashCode());
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
        Pregunta other = (Pregunta) obj;
        if (idPregunta == null) {
            if (other.idPregunta != null)
                return false;
        } else if (!idPregunta.equals(other.idPregunta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pregunta [idPregunta=" + idPregunta + ", valor=" + valor + ", activo=" + activo + "]";
    }
}
