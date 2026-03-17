package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;

@Stateless
@LocalBean
public class AspiranteOpcionDAO extends DefaultDAO<AspiranteOpcion> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public AspiranteOpcionDAO() {
        super(AspiranteOpcion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<AspiranteOpcion> buscarPorAspiranteRango(UUID idAspirante, int first, int max) {
        if (idAspirante != null) {
            try {
                return em.createQuery("SELECT a FROM AspiranteOpcion a WHERE a.idAspirante.idAspirante = :idAspirante", AspiranteOpcion.class)
                        .setParameter("idAspirante", idAspirante)
                        .setFirstResult(first)
                        .setMaxResults(max)
                        .getResultList();
            } catch (Exception e) {
                // Log the exception
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    public Long contarPorAspirante(UUID idAspirante) {
        if (idAspirante != null) {
            try {
                return em.createQuery("SELECT COUNT(a) FROM AspiranteOpcion a WHERE a.idAspirante.idAspirante = :idAspirante", Long.class)
                        .setParameter("idAspirante", idAspirante)
                        .getSingleResult();
            } catch (Exception e) {
                // Log the exception
                return 0L;
            }
        }
        return 0L;
    }

    public AspiranteOpcion buscarPorIdYAspirante(UUID idAspiranteOpcion, UUID idAspirante) {
        if (idAspiranteOpcion != null && idAspirante != null) {
            try {
                return em.createQuery("SELECT a FROM AspiranteOpcion a WHERE a.idAspiranteOpcion = :idAspiranteOpcion AND a.idAspirante.idAspirante = :idAspirante", AspiranteOpcion.class)
                        .setParameter("idAspiranteOpcion", idAspiranteOpcion)
                        .setParameter("idAspirante", idAspirante)
                        .getSingleResult();
            } catch (Exception e) {
                // Log the exception
                return null;
            }
        }
        return null;
    }
}