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
            UUID idPruebaClaveAreaPregunta, int first, int max) {
        return em.createNamedQuery(
                "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
                PruebaClaveAreaPreguntaDistractor.class)
                .setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPadre(UUID idPruebaClaveAreaPregunta) {
        return em.createNamedQuery(
                "PruebaClaveAreaPreguntaDistractor.contarPorPadre", Long.class)
                .setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)
                .getSingleResult();
    }

    public PruebaClaveAreaPreguntaDistractor buscarPorIdYPadre(
            UUID id, UUID idPruebaClaveAreaPregunta) {
        List<PruebaClaveAreaPreguntaDistractor> resultados = em
                .createNamedQuery(
                        "PruebaClaveAreaPreguntaDistractor.buscarPorIdYPadre",
                        PruebaClaveAreaPreguntaDistractor.class)
                .setParameter("idPruebaClaveAreaPreguntaDistractor", id)
                .setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)
                .setMaxResults(1)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}