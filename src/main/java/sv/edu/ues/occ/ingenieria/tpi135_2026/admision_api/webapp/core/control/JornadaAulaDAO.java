package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;

@Stateless
@LocalBean
public class JornadaAulaDAO extends DefaultDAO<JornadaAula> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public JornadaAulaDAO() {
        super(JornadaAula.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca una relacion de JornadaAula por jornada y aula.
     * @param idJornada identificador de jornada
     * @param idAula identificador de aula
     * @return registro encontrado o null si no hay resultados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public JornadaAula buscarPorJornadaYAula(UUID idJornada, String idAula) {
        if (idJornada != null && idAula != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    List<JornadaAula> resultados = entityManager
                            .createNamedQuery("JornadaAula.buscarPorJornadaYAula", JornadaAula.class)
                            .setParameter("idJornada", idJornada)
                            .setParameter("idAula", idAula)
                            .setMaxResults(1)
                            .getResultList();
                    return resultados.isEmpty() ? null : resultados.get(0);
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new IllegalArgumentException("Parámetros inválidos");
    }

    public List<JornadaAula> listarPorJornada(UUID idJornada) {
        if(idJornada != null){
            try {
            return em.createNamedQuery("JornadaAula.findByIdJornada", JornadaAula.class)
                .setParameter("idJornada", idJornada)
                .getResultList();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } 
        }
        throw new IllegalArgumentException("id de jornada no puede ser nulo");
    }
}
