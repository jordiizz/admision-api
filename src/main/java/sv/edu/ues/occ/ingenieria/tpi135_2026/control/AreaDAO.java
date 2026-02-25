package sv.edu.ues.occ.ingenieria.tpi135_2026.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.entity.Area;

import java.io.Serializable;

public class AreaDAO extends DefaultDAO<Area> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public AreaDAO() {
        super(Area.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
