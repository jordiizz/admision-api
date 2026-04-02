package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaDistractor;

@Stateless
@LocalBean
public class PruebaClaveAreaPreguntaDistractorDAO extends DefaultDAO<PruebaClaveAreaPreguntaDistractor> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaClaveAreaPreguntaDistractorDAO() {
        super(PruebaClaveAreaPreguntaDistractor.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca registros por llaves padre en un rango paginado.
     * @param idPruebaClave identificador de prueba clave
     * @param idArea identificador de area
     * @param idPregunta identificador de pregunta
     * @param first indice inicial del rango
     * @param max cantidad maxima de registros
     * @return lista de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public List<PruebaClaveAreaPreguntaDistractor> buscarPorPadreRango(
            UUID idPruebaClave, UUID idArea, UUID idPregunta, int first, int max) {
        if (idPruebaClave != null && idArea != null && idPregunta != null && first >= 0 && max > 0) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                            "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
                            PruebaClaveAreaPreguntaDistractor.class)
                        .setParameter("idPruebaClave", idPruebaClave)
                        .setParameter("idArea", idArea)
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
     * Cuenta registros por llaves padre.
     * @param idPruebaClave identificador de prueba clave
     * @param idArea identificador de area
     * @param idPregunta identificador de pregunta
     * @return total de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public Long contarPorPadre(UUID idPruebaClave, UUID idArea, UUID idPregunta) {
        if (idPruebaClave != null && idArea != null && idPregunta != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                            "PruebaClaveAreaPreguntaDistractor.contarPorPadre", Long.class)
                        .setParameter("idPruebaClave", idPruebaClave)
                        .setParameter("idArea", idArea)
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