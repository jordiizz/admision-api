package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

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

    /**
     * Lista las jornadas asociadas a una prueba específica.
     *
     * @param idPrueba UUID de la prueba.
     * @param first límite inferior
     * @param max máximo número de resultados.
     * @return lista de jornadas.
     */
    public List<Jornada> listarPorIdPrueba(UUID idPrueba, int first, int max) {
        try {
            if(idPrueba != null && first >= 0 && max > 0){
            return em.createNamedQuery("Jornada.findByIdPrueba", Jornada.class)
                            .setParameter("idPrueba", idPrueba)
                            .setFirstResult(first)
                            .setMaxResults(max)
                            .getResultList();
        }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
      
        throw new IllegalArgumentException("Parámetros inválidos");
    }
}