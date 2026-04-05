package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;

@Stateless
@LocalBean
public class PruebaClaveDAO extends DefaultDAO<PruebaClave> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaClaveDAO() {
        super(PruebaClave.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PruebaClave> listarClavesPorPrueba(UUID idPrueba) {
        if(idPrueba != null) {
            try {
                return em.createNamedQuery("PruebaClave.findByIdPrueba", PruebaClave.class)
                         .setParameter("idPrueba", idPrueba)
                         .getResultList();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalArgumentException("idPrueba no puede ser nulo");
    }
}