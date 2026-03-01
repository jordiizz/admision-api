package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
@Table(name = "prueba_aspirante_jornada")
public class PruebaAspiranteJornada implements Serializable{

    @Id
    @Column(name = "id_prueba_aspirante_jornada")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPruebaAspiranteJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante_jornada", nullable = false)
    private AspiranteJornada idAspiranteJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba_clave")
    private PruebaClave idPruebaClave;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    @OneToMany(mappedBy = "idPruebaAspiranteJornada")
    private List<PruebaAspiranteJornadaExamen> listPruebaAspiranteJornadaExamen;

    public PruebaAspiranteJornada(Long idPruebaAspiranteJornada) {
        this.idPruebaAspiranteJornada = idPruebaAspiranteJornada;
    }

    public PruebaAspiranteJornada(){}

    public Long getIdPruebaAspiranteJornada() {
        return idPruebaAspiranteJornada;
    }

    public void setIdPruebaAspiranteJornada(Long idPruebaAspiranteJornada) {
        this.idPruebaAspiranteJornada = idPruebaAspiranteJornada;
    }

    @JsonbTransient
    public AspiranteJornada getIdAspiranteJornada() {
        return idAspiranteJornada;
    }

    public void setIdAspiranteJornada(AspiranteJornada idAspiranteJornada) {
        this.idAspiranteJornada = idAspiranteJornada;
    }

    @JsonbTransient
    public PruebaClave getIdPruebaClave() {
        return idPruebaClave;
    }

    public void setIdPruebaClave(PruebaClave idPruebaClave) {
        this.idPruebaClave = idPruebaClave;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonbTransient
    public List<PruebaAspiranteJornadaExamen> getListPruebaAspiranteJornadaExamen() {
        return listPruebaAspiranteJornadaExamen;
    }

    public void setListPruebaAspiranteJornadaExamen(List<PruebaAspiranteJornadaExamen> listPruebaAspiranteJornadaExamen) {
        this.listPruebaAspiranteJornadaExamen = listPruebaAspiranteJornadaExamen;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPruebaAspiranteJornada == null) ? 0 : idPruebaAspiranteJornada.hashCode());
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
        PruebaAspiranteJornada other = (PruebaAspiranteJornada) obj;
        if (idPruebaAspiranteJornada == null) {
            if (other.idPruebaAspiranteJornada != null)
                return false;
        } else if (!idPruebaAspiranteJornada.equals(other.idPruebaAspiranteJornada))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PruebaAspiranteJornada [idPruebaAspiranteJornada=" + idPruebaAspiranteJornada + 
               ", fechaCreacion=" + fechaCreacion + "]";
    }
}
