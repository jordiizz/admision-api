package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;

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
}