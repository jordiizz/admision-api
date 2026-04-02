package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaArea;

@Stateless
@LocalBean
public class PreguntaAreaDAO extends DefaultDAO<PreguntaArea> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PreguntaAreaDAO() {
        super(PreguntaArea.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca registros de PreguntaArea por pregunta en un rango paginado.
     * @param idPregunta identificador de la pregunta
     * @param first indice inicial del rango
     * @param max cantidad maxima de registros
     * @return lista de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public List<PreguntaArea> buscarPorPreguntaRango(UUID idPregunta, int first, int max) {
        if (idPregunta != null && first >= 0 && max > 0) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                        "PreguntaArea.buscarPorPreguntaRango",
                        PreguntaArea.class)
                            .setParameter("idPregunta", idPregunta)
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
     * Cuenta registros de PreguntaArea por pregunta.
     * @param idPregunta identificador de la pregunta
     * @return total de registros encontrados
     * @throws IllegalArgumentException si el parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public Long contarPorPregunta(UUID idPregunta) {
        if (idPregunta != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                        "PreguntaArea.contarPorPregunta",
                        Long.class)
                            .setParameter("idPregunta", idPregunta)
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