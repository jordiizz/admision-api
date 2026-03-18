package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    }@Override
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
