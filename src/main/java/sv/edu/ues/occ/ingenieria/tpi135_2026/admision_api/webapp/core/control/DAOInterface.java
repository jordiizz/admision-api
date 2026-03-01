package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.List;

public interface DAOInterface<T> {

    public void crear(T entity);

    public void eliminar(T entity);

    public List<T> buscarPorRango(int first, int max);
    
    public Long contar();
}
