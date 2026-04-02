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

    public List<PreguntaArea> buscarPorPreguntaRango(UUID idPregunta, int first, int max) {
        return em.createNamedQuery(
            "PreguntaArea.buscarPorPreguntaRango",
            PreguntaArea.class)
                .setParameter("idPregunta", idPregunta)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPregunta(UUID idPregunta) {
        return em.createNamedQuery(
            "PreguntaArea.contarPorPregunta",
            Long.class)
                .setParameter("idPregunta", idPregunta)
                .getSingleResult();
    }
}