package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class Area implements Serializable{

    @Id
    @Column(name = "id_area", nullable = false)
    private UUID idArea;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_area_padre")
    private Area idAreaPadre;



    public Area(UUID idArea) {
        this.idArea = idArea;
    }

    public Area(){}

    public UUID getIdArea() {
        return idArea;
    }

    public void setIdArea(UUID idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Area getIdAreaPadre() {
        return idAreaPadre;
    }

    public void setIdAreaPadre(Area idAreaPadre) {
        this.idAreaPadre = idAreaPadre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idArea == null) ? 0 : idArea.hashCode());
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
        Area other = (Area) obj;
        if (idArea == null) {
            if (other.idArea != null)
                return false;
        } else if (!idArea.equals(other.idArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Area [idArea=" + idArea + ", nombre=" + nombre + ", descripcion=" + descripcion +
               ", activo=" + activo + "]";
    }
}
