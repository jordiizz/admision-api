package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaDistractor;

@Stateless
@LocalBean
public class PreguntaDistractorDAO extends DefaultDAO<PreguntaDistractor> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PreguntaDistractorDAO() {
        super(PreguntaDistractor.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    /**
     * Busca los distractores asociados a una pregunta
     * @param idPregunta
     * @return
     */
    public List<PreguntaDistractor> buscarPorIdPregunta(UUID idPregunta){
        if(idPregunta != null){
            try {
                return em.createNamedQuery("PreguntaDistractor.findByIdPregunta", PreguntaDistractor.class)
                .setParameter("idPregunta", idPregunta)
                .getResultList();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalArgumentException("El idPregunta no puede ser nulo");
    }

}