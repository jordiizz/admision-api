package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "JornadaAula.buscarPorJornadaYAula",
        query = "SELECT j FROM JornadaAula j WHERE j.idJornada.idJornada = :idJornada AND j.idAula = :idAula"
    ),
    @NamedQuery(name = "JornadaAula.findByIdJornada", 
    query = "SELECT ja FROM JornadaAula ja WHERE  ja.idJornada.idJornada = :idJornada"
)
})
@Entity
@Table(name = "jornada_aula")
public class JornadaAula implements Serializable {

    @Id
    @Column(name = "id_jornada_aula", nullable = false)
    private UUID idJornadaAula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornada", nullable = false)
    private Jornada idJornada;

    @Column(name = "id_aula", nullable = false, length = 100)
    private String idAula;

    public JornadaAula() {}

    public JornadaAula(UUID idJornadaAula) {
        this.idJornadaAula = idJornadaAula;
    }

    public UUID getIdJornadaAula() {
        return idJornadaAula;
    }

    public void setIdJornadaAula(UUID idJornadaAula) {
        this.idJornadaAula = idJornadaAula;
    }

    
    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idJornadaAula == null) ? 0 : idJornadaAula.hashCode());
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
        JornadaAula other = (JornadaAula) obj;
        if (idJornadaAula == null) {
            if (other.idJornadaAula != null)
                return false;
        } else if (!idJornadaAula.equals(other.idJornadaAula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JornadaAula [idJornadaAula=" + idJornadaAula + ", idAula=" + idAula + "]";
    }
    
}
