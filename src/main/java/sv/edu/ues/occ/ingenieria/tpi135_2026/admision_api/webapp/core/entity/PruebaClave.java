package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "PruebaClave.findByIdPrueba", query = "SELECT pk FROM PruebaClave pk WHERE pk.idPrueba.idPrueba = :idPrueba")
})
@Entity
@Table(name = "prueba_clave")
public class PruebaClave implements Serializable {

    @Id
    @Column(name = "id_prueba_clave", nullable = false)
    private UUID idPruebaClave;

    @Column(name = "nombre_clave", nullable = false)
    private String nombreClave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba", nullable = false)
    private Prueba idPrueba;

    public PruebaClave() {}

    public PruebaClave(UUID idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public UUID getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(UUID idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public String getNombreClave() {
        return nombreClave;
    }

    public void setNombreClave(String nombreClave) {
        this.nombreClave = nombreClave;
    }

    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
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