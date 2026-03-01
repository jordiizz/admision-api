package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class DefaultDAO<T> implements DAOInterface<T> {

    final Class<T> entityClass;

    public DefaultDAO(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    /**
     * Almacena registros en el repositorio, el objeto a almacenar no puede ser nulo, de lo contrario se lanzará una IllegalArgumentException. Si el repositorio es nulo se lanzará una NullPointerException. Cualquier otra excepción se encapsulará en una IllegalStateException.
     * @param entity el objeto a almacenar
     * @throws IllegalArgumentException si el objeto a almacenar es nulo
     * @throws NullPointerException si el repositorio es nulo
     * @throws IllegalStateException si ocurre cualquier otra excepción
     */

    public void crear(T entity){
        if (entity != null){
            try {
                EntityManager em = this.getEntityManager();
                if (em != null){
                    em.persist(entity);
                    return;
                }
                throw new NullPointerException("El repositorio es nulo");
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        } else {
            throw new IllegalArgumentException("entity no puede ser nulo");
        }
    }

    /**
     * Elimina un registro del repositorio, el objeto a eliminar no puede ser nulo, de lo contrario se lanzará una IllegalArgumentException. Si el repositorio es nulo se lanzará una NullPointerException. Cualquier otra excepción se encapsulará en una IllegalStateException.
     * @param entity el objeto a eliminar
     * @throws IllegalArgumentException si el objeto a eliminar es nulo
     * @throws NullPointerException si el repositorio es nulo
     * @throws IllegalStateException si ocurre cualquier otra excepción
     */    
    public void eliminar(T entity){
        if (entity != null){
            try {
                EntityManager em = this.getEntityManager();
                if (em == null){
                    throw new NullPointerException("El repositorio es nulo");
                }
                if (em.contains(entity)){
                    em.remove(entity);
                }else {
                    em.remove(em.merge(entity));
                }
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        } else {
            throw new IllegalArgumentException("entity no puede ser nulo");
        }
    }

    /** 
     * Busca registros en el repositorio, los parámetros first y max no pueden ser negativos, de lo contrario se lanzará una IllegalArgumentException. Si el repositorio es nulo se lanzará una NullPointerException. Cualquier otra excepción se encapsulará en una IllegalStateException.
     * @param first el índice del primer registro a buscar
     * @param max el número máximo de registros a buscar
     * @return una lista de registros encontrados
     * @throws IllegalArgumentException si los parámetros first o max son negativos
     * @throws NullPointerException si el repositorio es nulo
     * @throws IllegalStateException si ocurre cualquier otra excepción
     */
    public List<T> buscarPorRango(int first, int max){
        if (first < 0 || max < 0){
            throw new IllegalArgumentException("Parametro no válido: first o max");
        }
        try {
            EntityManager em = this.getEntityManager();
            if (em != null){
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(entityClass);
                Root<T> root = cq.from(entityClass);
                cq.select(root);
                TypedQuery<T> query = em.createQuery(cq);
                query.setFirstResult(first);
                query.setMaxResults(max);
                return query.getResultList();
            }
            throw new NullPointerException("El repositorio es nulo");
        }catch (Exception ex){
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Cuenta el número total de registros en el repositorio. Si el repositorio es nulo se lanzará una NullPointerException. Cualquier otra excepción se encapsulará en una IllegalStateException.
     * @return el número total de registros en el repositorio
     * @throws NullPointerException si el repositorio es nulo
     * @throws IllegalStateException si ocurre cualquier otra excepción
     */
    public Long contar() throws IllegalStateException {
        EntityManager em = null;

        try {
            em = getEntityManager();

            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class); // Definir que queremos un resultado de tipo Long
            Root<T> raiz = cq.from(entityClass);
            cq.select(cb.count(raiz)); // Utilizar el método count

            TypedQuery<Long> query = em.createQuery(cq);
            return query.getSingleResult(); // Obtener el resultado único de la consulta

        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }
}
