package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
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
@Table(name = "prueba_jornada")
public class PruebaJornada implements Serializable {

    @Id
    @Column(name = "id_prueba_jornada", nullable = false)
    private UUID idPruebaJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba", nullable = false)
    private Prueba idPrueba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornada", nullable = false)
    private Jornada idJornada;

    @OneToMany(mappedBy = "idPruebaJornada")
    private List<PruebaJornadaAulaAspiranteOpcion> listPruebaJornadaAulaAspiranteOpcion;

    public PruebaJornada() {}

    public PruebaJornada(UUID idPruebaJornada) {
        this.idPruebaJornada = idPruebaJornada;
    }

    public UUID getIdPruebaJornada() {
        return idPruebaJornada;
    }

    public void setIdPruebaJornada(UUID idPruebaJornada) {
        this.idPruebaJornada = idPruebaJornada;
    }

    
    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
    }

    
    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
    }

    @JsonbTransient
    public List<PruebaJornadaAulaAspiranteOpcion> getListPruebaJornadaAulaAspiranteOpcion() {
        return listPruebaJornadaAulaAspiranteOpcion;
    }

    public void setListPruebaJornadaAulaAspiranteOpcion(
            List<PruebaJornadaAulaAspiranteOpcion> listPruebaJornadaAulaAspiranteOpcion) {
        this.listPruebaJornadaAulaAspiranteOpcion = listPruebaJornadaAulaAspiranteOpcion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaJornada == null) ? 0 : idPruebaJornada.hashCode());
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
        PruebaJornada other = (PruebaJornada) obj;
        if (idPruebaJornada == null) {
            if (other.idPruebaJornada != null)
                return false;
        } else if (!idPruebaJornada.equals(other.idPruebaJornada))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaJornada [idPruebaJornada=" + idPruebaJornada + "]";
    }
}