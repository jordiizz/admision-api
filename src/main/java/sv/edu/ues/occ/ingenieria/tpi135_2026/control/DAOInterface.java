package sv.edu.ues.occ.ingenieria.tpi135_2026.control;

public interface DAOInterface<T> {

    public void crear(T entity);

    public void eliminar(T entity);
}
