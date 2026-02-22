package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_prueba")
public class TipoPrueba implements Serializable{

    @Id
    @Column(name = "id_tipo_prueba")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPrueba;

    @Column(name = "valor")
    private String valor;

    @OneToMany(mappedBy = "idTipoPrueba")
    private List<Prueba> listPrueba;

    public TipoPrueba(Integer idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    public TipoPrueba(){}

    public Integer getIdTipoPrueba() {
        return idTipoPrueba;
    }

    public void setIdTipoPrueba(Integer idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @JsonbTransient
    public List<Prueba> getListPrueba() {
        return listPrueba;
    }

    public void setListPrueba(List<Prueba> listPrueba) {
        this.listPrueba = listPrueba;
    }

    
}
