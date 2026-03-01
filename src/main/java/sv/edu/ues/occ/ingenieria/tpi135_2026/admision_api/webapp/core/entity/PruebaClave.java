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
@Table(name = "prueba_clave")
public class PruebaClave implements Serializable {

    @Id
    @Column(name = "id_prueba_clave", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPruebaClave;

    @Column(name = "nombre_clave", nullable = false)
    private String nombreClave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba", nullable = false)
    private Prueba idPrueba;

    @OneToMany(mappedBy = "idPruebaClave")
    private List<PruebaClaveArea> listPruebaClaveArea;

    @OneToMany(mappedBy = "idPruebaClave")
    private List<PruebaAspiranteJornada> listPruebaAspiranteJornada;

    public PruebaClave() {}

    public PruebaClave(Long idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public Long getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(Long idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public String getNombreClave() {
        return nombreClave;
    }

    public void setNombreClave(String nombreClave) {
        this.nombreClave = nombreClave;
    }

    @JsonbTransient
    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
    }

    @JsonbTransient
    public List<PruebaClaveArea> getListPruebaClaveArea() {
        return listPruebaClaveArea;
    }

    public void setListPruebaClaveArea(List<PruebaClaveArea> listPruebaClaveArea) {
        this.listPruebaClaveArea = listPruebaClaveArea;
    }

    @JsonbTransient
    public List<PruebaAspiranteJornada> getListPruebaAspiranteJornada() {
        return listPruebaAspiranteJornada;
    }

    public void setListPruebaAspiranteJornada(List<PruebaAspiranteJornada> listPruebaAspiranteJornada) {
        this.listPruebaAspiranteJornada = listPruebaAspiranteJornada;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaClave == null) ? 0 : idPruebaClave.hashCode());
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
        PruebaClave other = (PruebaClave) obj;
        if (idPruebaClave == null) {
            if (other.idPruebaClave != null)
                return false;
        } else if (!idPruebaClave.equals(other.idPruebaClave))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaClave [idPruebaClave=" + idPruebaClave + ", nombreClave=" + nombreClave + "]";
    }
}