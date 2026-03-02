package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaAspiranteJornadaExamen;

public class PruebaAspiranteJornadaExamenDAO extends DefaultDAO<PruebaAspiranteJornadaExamen> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaAspiranteJornadaExamenDAO() {
        super(PruebaAspiranteJornadaExamen.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}