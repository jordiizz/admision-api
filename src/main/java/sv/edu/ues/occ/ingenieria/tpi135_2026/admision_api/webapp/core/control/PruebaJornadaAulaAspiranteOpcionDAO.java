package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

@Stateless
@LocalBean
public class PruebaJornadaAulaAspiranteOpcionDAO extends DefaultDAO<PruebaJornadaAulaAspiranteOpcion>
        implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaJornadaAulaAspiranteOpcionDAO() {
        super(PruebaJornadaAulaAspiranteOpcion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PruebaJornadaAulaAspiranteOpcion> buscarPorPruebaJornadaYJornadaAulaRango(
            UUID idPruebaJornada, UUID idJornadaAula, int first, int max) {
        return em.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcion.buscarPorPruebaJornadaYJornadaAula",
                PruebaJornadaAulaAspiranteOpcion.class)
                .setParameter("idPruebaJornada", idPruebaJornada)
                .setParameter("idJornadaAula", idJornadaAula)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPorPruebaJornadaYJornadaAula(UUID idPruebaJornada, UUID idJornadaAula) {
        return em.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcion.contarPorPruebaJornadaYJornadaAula", Long.class)
                .setParameter("idPruebaJornada", idPruebaJornada)
                .setParameter("idJornadaAula", idJornadaAula)
                .getSingleResult();
    }

    public PruebaJornadaAulaAspiranteOpcion buscarPorIdYPruebaJornadaYJornadaAula(
            UUID id, UUID idPruebaJornada, UUID idJornadaAula) {
        List<PruebaJornadaAulaAspiranteOpcion> resultados = em
                .createNamedQuery(
                        "PruebaJornadaAulaAspiranteOpcion.buscarPorIdYPruebaJornadaYJornadaAula",
                        PruebaJornadaAulaAspiranteOpcion.class)
                .setParameter("idPruebaJornadaAulaAspiranteOpcion", id)
                .setParameter("idPruebaJornada", idPruebaJornada)
                .setParameter("idJornadaAula", idJornadaAula)
                .setMaxResults(1)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}
