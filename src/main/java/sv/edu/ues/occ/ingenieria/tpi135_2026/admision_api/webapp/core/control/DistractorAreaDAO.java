package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;

@Stateless
@LocalBean
public class DistractorAreaDAO extends DefaultDAO<DistractorArea> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public DistractorAreaDAO() {
        super(DistractorArea.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca registros de DistractorArea por distractor en un rango paginado.
     * @param idDistractor identificador del distractor
     * @param first indice inicial del rango
     * @param max cantidad maxima de registros
     * @return lista de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public List<DistractorArea> buscarPorDistractorRango(UUID idDistractor, int first, int max) {
        if (idDistractor != null && first >= 0 && max > 0) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                        "DistractorArea.buscarPorDistractorRango",
                        DistractorArea.class)
                            .setParameter("idDistractor", idDistractor)
                            .setFirstResult(first)
                            .setMaxResults(max)
                            .getResultList();
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new IllegalArgumentException("Parámetros inválidos");
    }

    /**
     * Cuenta registros de DistractorArea por distractor.
     * @param idDistractor identificador del distractor
     * @return total de registros encontrados
     * @throws IllegalArgumentException si el parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public Long contarPorDistractor(UUID idDistractor) {
        if (idDistractor != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                        "DistractorArea.contarPorDistractor",
                        Long.class)
                            .setParameter("idDistractor", idDistractor)
                            .getSingleResult();
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new IllegalArgumentException("Parámetros inválidos");
    }
}