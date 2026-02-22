package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

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
@Table(name = "aspirante_jornada")
public class AspiranteJornada implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aspirante_jornada")
    private Long idAspiranteJornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirante")
    private Aspirante idAspirante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornada")
    private Jornada idJornada;

    @Column(name = "id_facultad")
    private String idFacultad;

    @Column(name = "id_aula")
    private String idAula;

    @OneToMany(mappedBy = "idAspiranteJornada")
    private List<PruebaAspiranteJornada> listPruebaAspiranteJornada;

    public AspiranteJornada(Long idAspiranteJornada) {
        this.idAspiranteJornada = idAspiranteJornada;
    }

    public AspiranteJornada(){}

    public Long getIdAspiranteJornada() {
        return idAspiranteJornada;
    }

    public void setIdAspiranteJornada(Long idAspiranteJornada) {
        this.idAspiranteJornada = idAspiranteJornada;
    }

    public Aspirante getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(Aspirante idAspirante) {
        this.idAspirante = idAspirante;
    }

    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
    }

    public String getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(String idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    @JsonbTransient
    public List<PruebaAspiranteJornada> getListPruebaAspiranteJornada() {
        return listPruebaAspiranteJornada;
    }

    public void setListPruebaAspiranteJornada(List<PruebaAspiranteJornada> listPruebaAspiranteJornada) {
        this.listPruebaAspiranteJornada = listPruebaAspiranteJornada;
    }    

    

}
