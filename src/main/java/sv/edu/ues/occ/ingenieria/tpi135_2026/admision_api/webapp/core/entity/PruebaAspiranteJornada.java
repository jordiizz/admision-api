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
@Table(name = "prueba_aspirante_jornada")
public class PruebaAspiranteJornada implements Serializable{

    @Id
    @Column(name = "id_prueba_aspirante_jornada")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPruebaAspiranteJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante_jornada")
    private AspiranteJornada idAspiranteJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prueba")
    private Prueba idPrueba;

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

    public AspiranteJornada getIdAspiranteJornada() {
        return idAspiranteJornada;
    }

    public void setIdAspiranteJornada(AspiranteJornada idAspiranteJornada) {
        this.idAspiranteJornada = idAspiranteJornada;
    }

    public Prueba getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Prueba idPrueba) {
        this.idPrueba = idPrueba;
    }

    @JsonbTransient
    public List<PruebaAspiranteJornadaExamen> getListPruebaAspiranteJornadaExamen() {
        return listPruebaAspiranteJornadaExamen;
    }

    public void setListPruebaAspiranteJornadaExamen(List<PruebaAspiranteJornadaExamen> listPruebaAspiranteJornadaExamen) {
        this.listPruebaAspiranteJornadaExamen = listPruebaAspiranteJornadaExamen;
    }

    
    
}
