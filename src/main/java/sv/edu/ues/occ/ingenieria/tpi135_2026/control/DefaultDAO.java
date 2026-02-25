package sv.edu.ues.occ.ingenieria.tpi135_2026.control;

import jakarta.persistence.EntityManager;

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
        }
        throw new IllegalArgumentException("entity no puede ser nulo");
    }

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
}
