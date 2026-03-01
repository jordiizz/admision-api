package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class Area implements Serializable{

    @Id
    @Column(name = "id_area", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArea;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area_padre")
    private Area idAreaPadre;

    @OneToMany(mappedBy = "idAreaPadre")
    private List<Area> listAreas;

    @OneToMany(mappedBy = "idArea")
    private List<PruebaClaveArea> listPruebaClaveArea;

    @OneToMany(mappedBy = "idArea")
    private List<PreguntaArea> listPreguntaArea;

    @OneToMany(mappedBy = "idArea")
    private List<DistractorArea> listDistractorArea;

    public Area(Integer idArea) {
        this.idArea = idArea;
    }

    public Area(){}

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Area getIdAreaPadre() {
        return idAreaPadre;
    }

    public void setIdAreaPadre(Area idAreaPadre) {
        this.idAreaPadre = idAreaPadre;
    }

    @JsonbTransient
    public List<Area> getListAreas() {
        return listAreas;
    }

    public void setListAreas(List<Area> listAreas) {
        this.listAreas = listAreas;
    }

    @JsonbTransient
    public List<PruebaClaveArea> getListPruebaClaveArea() {
        return listPruebaClaveArea;
    }

    public void setListPruebaClaveArea(List<PruebaClaveArea> listPruebaClaveArea) {
        this.listPruebaClaveArea = listPruebaClaveArea;
    }

    @JsonbTransient
    public List<PreguntaArea> getListPreguntaArea() {
        return listPreguntaArea;
    }

    public void setListPreguntaArea(List<PreguntaArea> listPreguntaArea) {
        this.listPreguntaArea = listPreguntaArea;
    }

    @JsonbTransient
    public List<DistractorArea> getListDistractorArea() {
        return listDistractorArea;
    }

    public void setListDistractorArea(List<DistractorArea> listDistractorArea) {
        this.listDistractorArea = listDistractorArea;
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
