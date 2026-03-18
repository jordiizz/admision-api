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

    public List<PruebaClaveArea> buscarPorPruebaClaveRango(UUID idPruebaClave, int first, int max) {
        return em.createNamedQuery("PruebaClaveArea.buscarPorPruebaClave", PruebaClaveArea.class)
                .setParameter("idPruebaClave", idPruebaClave)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPruebaClave(UUID idPruebaClave) {
        return em.createNamedQuery("PruebaClaveArea.contarPorPruebaClave", Long.class)
                .setParameter("idPruebaClave", idPruebaClave)
                .getSingleResult();
    }

    public PruebaClaveArea buscarPorIdYPruebaClave(UUID idArea, UUID idPruebaClave) {
        List<PruebaClaveArea> resultados = em
                .createNamedQuery("PruebaClaveArea.buscarPorIdYPruebaClave", PruebaClaveArea.class)
                .setParameter("idArea", idArea)
                .setParameter("idPruebaClave", idPruebaClave)
                .setMaxResults(1)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}