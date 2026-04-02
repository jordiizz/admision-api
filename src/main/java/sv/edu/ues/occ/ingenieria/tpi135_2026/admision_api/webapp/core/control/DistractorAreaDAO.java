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

    public List<DistractorArea> buscarPorDistractorRango(UUID idDistractor, int first, int max) {
        return em.createNamedQuery(
            "DistractorArea.buscarPorDistractorRango",
            DistractorArea.class)
                .setParameter("idDistractor", idDistractor)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorDistractor(UUID idDistractor) {
        return em.createNamedQuery(
            "DistractorArea.contarPorDistractor",
            Long.class)
                .setParameter("idDistractor", idDistractor)
                .getSingleResult();
    }
}