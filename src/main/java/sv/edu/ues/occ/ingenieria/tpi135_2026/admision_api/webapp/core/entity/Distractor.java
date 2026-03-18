package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "distractor")
public class Distractor implements Serializable{

    @Id
    @Column(name = "id_distractor")
    private UUID idDistractor;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "imagen_url")
    private String imagenUrl;



    public Distractor(UUID idDistractor) {
        this.idDistractor = idDistractor;
    }

    public Distractor(){}

    public UUID getIdDistractor() {
        return idDistractor;
    }

    public void setIdDistractor(UUID idDistractor) {
        this.idDistractor = idDistractor;
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
        result = prime * result + ((idDistractor == null) ? 0 : idDistractor.hashCode());
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
        Distractor other = (Distractor) obj;
        if (idDistractor == null) {
            if (other.idDistractor != null)
                return false;
        } else if (!idDistractor.equals(other.idDistractor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Distractor [idDistractor=" + idDistractor + ", valor=" + valor + ", activo=" + activo + "]";
    }
}
