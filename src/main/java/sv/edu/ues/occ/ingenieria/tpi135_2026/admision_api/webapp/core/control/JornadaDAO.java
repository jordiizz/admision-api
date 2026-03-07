package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;

@Stateless
@LocalBean
public class JornadaDAO extends DefaultDAO<Jornada> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public JornadaDAO() {
        super(Jornada.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}