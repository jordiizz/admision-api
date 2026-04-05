package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;

@Stateless
@LocalBean
public class PruebaClaveAreaPreguntaDAO extends DefaultDAO<PruebaClaveAreaPregunta> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaClaveAreaPreguntaDAO() {
        super(PruebaClaveAreaPregunta.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PruebaClaveAreaPregunta> buscarPorClaveYPregunta(UUID idPruebaClave, UUID idArea){
        if(idPruebaClave != null && idArea != null ){
            try {   
                return em.createNamedQuery("PruebaClaveAreaPregunta.findByClaveAndArea", PruebaClaveAreaPregunta.class)
                .setParameter("idPruebaClave", idPruebaClave)
                .setParameter("idArea", idArea)
                .getResultList();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalArgumentException();
    }
}