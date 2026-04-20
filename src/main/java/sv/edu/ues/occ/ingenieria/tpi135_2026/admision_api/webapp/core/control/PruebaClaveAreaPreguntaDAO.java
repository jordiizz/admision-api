package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
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

    /**
     * Busca la lista de preguntas pertenencientes a un area y una clave
     * @param idPruebaClave
     * @param idArea
     * @return PruebaClaveAreaPregunta de toda el area de esa clave
     */
    public List<PruebaClaveAreaPregunta> buscarPorClaveYArea(UUID idPruebaClave, UUID idArea){
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

    /**
     * Valida si la suma de porcentajes de PruebaClaveAreaPregunta no excede el puntaje máximo de la Prueba
     * @param pruebaClave Clave de la prueba a validar
     * @param pruebaClaveAreaPregunta Registro a validar
     * @return true si la suma no excede el máximo, false en caso contrario
     * @throws IllegalArgumentException si los parámetros son null
     */
    public boolean validarPorcentajePrueba(PruebaClave pruebaClave, PruebaClaveAreaPregunta pruebaClaveAreaPregunta){
        if(pruebaClave == null || pruebaClaveAreaPregunta == null){
            throw new IllegalArgumentException("pruebaClave y pruebaClaveAreaPregunta no pueden ser null");
        }
        try {
            BigDecimal totalActual = em.createNamedQuery("PruebaClaveAreaPregunta.findTotalPorcentajeByPruebaClave", BigDecimal.class)
                    .setParameter("idPruebaClave", pruebaClave.getIdPruebaClave())
                    .getSingleResult();
            if(totalActual == null){
                totalActual = BigDecimal.ZERO;
            }
            BigDecimal totalNuevo = totalActual.add(pruebaClaveAreaPregunta.getPorcentaje());
            BigDecimal puntajeMaximo = pruebaClave.getIdPrueba().getPuntajeMaximo();

            return totalNuevo.compareTo(puntajeMaximo) <= 0;
        } catch (Exception e) {
            throw new IllegalStateException("Error validando porcentaje de prueba", e);
        }
    }
}