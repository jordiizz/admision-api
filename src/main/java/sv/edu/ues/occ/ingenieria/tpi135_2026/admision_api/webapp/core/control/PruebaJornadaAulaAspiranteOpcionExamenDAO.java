package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

@Stateless
@LocalBean
public class PruebaJornadaAulaAspiranteOpcionExamenDAO
        extends DefaultDAO<PruebaJornadaAulaAspiranteOpcionExamen> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaJornadaAulaAspiranteOpcionExamenDAO() {
        super(PruebaJornadaAulaAspiranteOpcionExamen.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PruebaJornadaAulaAspiranteOpcionExamen> buscarPorPadreRango(
            UUID idPruebaJornadaAulaAspiranteOpcion, int first, int max) {
        return em.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorPadre",
                PruebaJornadaAulaAspiranteOpcionExamen.class)
                .setParameter("idPruebaJornadaAulaAspiranteOpcion", idPruebaJornadaAulaAspiranteOpcion)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPadre(UUID idPruebaJornadaAulaAspiranteOpcion) {
        return em.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.contarPorPadre", Long.class)
                .setParameter("idPruebaJornadaAulaAspiranteOpcion", idPruebaJornadaAulaAspiranteOpcion)
                .getSingleResult();
    }

    public PruebaJornadaAulaAspiranteOpcionExamen buscarPorIdYPadre(
            UUID id, UUID idPruebaJornadaAulaAspiranteOpcion) {
        List<PruebaJornadaAulaAspiranteOpcionExamen> resultados = em
                .createNamedQuery(
                        "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorIdYPadre",
                        PruebaJornadaAulaAspiranteOpcionExamen.class)
                .setParameter("idPruebaJornadaAulaAspiranteOpcionExamen", id)
                .setParameter("idPruebaJornadaAulaAspiranteOpcion", idPruebaJornadaAulaAspiranteOpcion)
                .setMaxResults(1)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}

