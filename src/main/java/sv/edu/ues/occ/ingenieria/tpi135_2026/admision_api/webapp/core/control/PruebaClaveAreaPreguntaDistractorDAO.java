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

    public List<PruebaClaveAreaPreguntaDistractor> buscarPorPadreRango(
            UUID idPruebaClave, UUID idArea, UUID idPregunta, int first, int max) {
        return em.createNamedQuery(
                "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
                PruebaClaveAreaPreguntaDistractor.class)
            .setParameter("idPruebaClave", idPruebaClave)
            .setParameter("idArea", idArea)
            .setParameter("idPregunta", idPregunta)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPadre(UUID idPruebaClave, UUID idArea, UUID idPregunta) {
        return em.createNamedQuery(
                "PruebaClaveAreaPreguntaDistractor.contarPorPadre", Long.class)
            .setParameter("idPruebaClave", idPruebaClave)
            .setParameter("idArea", idArea)
            .setParameter("idPregunta", idPregunta)
                .getSingleResult();
    }
}