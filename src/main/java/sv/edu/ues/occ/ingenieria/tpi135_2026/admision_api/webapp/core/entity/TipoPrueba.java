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
@Table(name = "tipo_prueba")
public class TipoPrueba implements Serializable{

    @Id
    @Column(name = "id_tipo_prueba")
    private UUID idTipoPrueba;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "idTipoPrueba")
    private List<Prueba> listPrueba;

    public TipoPrueba(UUID idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    public TipoPrueba(){}

    public UUID getIdTipoPrueba() {
        return idTipoPrueba;
    }

    public void setIdTipoPrueba(UUID idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
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

    @JsonbTransient
    public List<Prueba> getListPrueba() {
        return listPrueba;
    }

    public void setListPrueba(List<Prueba> listPrueba) {
        this.listPrueba = listPrueba;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTipoPrueba == null) ? 0 : idTipoPrueba.hashCode());
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
        TipoPrueba other = (TipoPrueba) obj;
        if (idTipoPrueba == null) {
            if (other.idTipoPrueba != null)
                return false;
        } else if (!idTipoPrueba.equals(other.idTipoPrueba))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TipoPrueba [idTipoPrueba=" + idTipoPrueba + ", valor=" + valor + ", activo=" + activo + "]";
    }
}
