package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;

@Stateless
@LocalBean
public class PruebaClaveAreaDAO extends DefaultDAO<PruebaClaveArea> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaClaveAreaDAO() {
        super(PruebaClaveArea.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca registros de PruebaClaveArea por id de PruebaClave en un rango paginado.
     * @param idPruebaClave identificador de la prueba clave
     * @param first indice inicial del rango
     * @param max cantidad maxima de registros
     * @return lista de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public List<PruebaClaveArea> buscarPorPruebaClaveRango(UUID idPruebaClave, int first, int max) {
        if (idPruebaClave != null && first >= 0 && max > 0) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery("PruebaClaveArea.buscarPorPruebaClave", PruebaClaveArea.class)
                            .setParameter("idPruebaClave", idPruebaClave)
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
     * Cuenta registros de PruebaClaveArea por id de PruebaClave.
     * @param idPruebaClave identificador de la prueba clave
     * @return total de registros encontrados
     * @throws IllegalArgumentException si el parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public Long contarPorPruebaClave(UUID idPruebaClave) {
        if (idPruebaClave != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery("PruebaClaveArea.contarPorPruebaClave", Long.class)
                            .setParameter("idPruebaClave", idPruebaClave)
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