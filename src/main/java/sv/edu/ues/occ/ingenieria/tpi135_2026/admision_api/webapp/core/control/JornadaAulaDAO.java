package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;

@Stateless
@LocalBean
public class JornadaAulaDAO extends DefaultDAO<JornadaAula> implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public JornadaAulaDAO() {
        super(JornadaAula.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public JornadaAula buscarPorJornadaYAula(UUID idJornada, String idAula) {
        List<JornadaAula> resultados = em
                .createNamedQuery("JornadaAula.buscarPorJornadaYAula", JornadaAula.class)
                .setParameter("idJornada", idJornada)
                .setParameter("idAula", idAula)
                .setMaxResults(1)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}
