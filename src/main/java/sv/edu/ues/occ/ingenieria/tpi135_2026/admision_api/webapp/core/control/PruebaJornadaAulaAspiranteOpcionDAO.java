package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

@Stateless
@LocalBean
public class PruebaJornadaAulaAspiranteOpcionDAO extends DefaultDAO<PruebaJornadaAulaAspiranteOpcion>
        implements Serializable {

    @PersistenceContext(unitName = "AdmisionPU")
    EntityManager em;

    public PruebaJornadaAulaAspiranteOpcionDAO() {
        super(PruebaJornadaAulaAspiranteOpcion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca registros por prueba-jornada-aula en un rango paginado.
     * @param idPrueba identificador de prueba
     * @param idJornada identificador de jornada
     * @param idAula identificador de aula
     * @param first indice inicial del rango
     * @param max cantidad maxima de registros
     * @return lista de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public List<PruebaJornadaAulaAspiranteOpcion> buscarPorPruebaJornadaYJornadaAulaRango(
            UUID idPrueba, UUID idJornada, String idAula, int first, int max) {
        if (idPrueba != null && idJornada != null && idAula != null && first >= 0 && max > 0) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                            "PruebaJornadaAulaAspiranteOpcion.buscarPorPruebaJornadaYJornadaAula",
                            PruebaJornadaAulaAspiranteOpcion.class)
                        .setParameter("idPrueba", idPrueba)
                        .setParameter("idJornada", idJornada)
                        .setParameter("idAula", idAula)
                            .setFirstResult(first)
                            .setMaxResults(max)
                            .getResultList();
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new IllegalArgumentException("Parámetros inválidos");
    }

    /**
     * Cuenta registros por prueba-jornada-aula.
     * @param idPrueba identificador de prueba
     * @param idJornada identificador de jornada
     * @param idAula identificador de aula
     * @return total de registros encontrados
     * @throws IllegalArgumentException si algun parametro es invalido
     * @throws IllegalStateException si ocurre un error al consultar
     */
    public Long contarPorPruebaJornadaYJornadaAula(UUID idPrueba, UUID idJornada, String idAula) {
        if (idPrueba != null && idJornada != null && idAula != null) {
            try {
                EntityManager entityManager = this.getEntityManager();
                if (entityManager != null) {
                    return entityManager.createNamedQuery(
                            "PruebaJornadaAulaAspiranteOpcion.contarPorPruebaJornadaYJornadaAula", Long.class)
                        .setParameter("idPrueba", idPrueba)
                        .setParameter("idJornada", idJornada)
                        .setParameter("idAula", idAula)
                            .getSingleResult();
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new IllegalArgumentException("Parámetros inválidos");
    }
}
