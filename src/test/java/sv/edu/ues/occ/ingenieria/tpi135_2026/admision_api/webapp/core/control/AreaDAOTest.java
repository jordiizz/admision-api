package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;

public class AreaDAOTest {

    protected List<Area> findResult ;

    public AreaDAOTest() {
        findResult = Arrays.asList(new Area[]{
                new Area(1), new Area(2), new Area(3)
        });
    }

    @Test
    public void crearTest() {
        System.out.println("AreaDAOTest.crearTest");
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        Area area = new Area();
        AreaDAO cut = new AreaDAO();
        assertThrows(IllegalArgumentException.class, ()->cut.crear(null));
        assertThrows(IllegalStateException.class, ()->cut.crear(area));
        cut.em = mockEM;
        cut.crear(area);
        //fail("Esta prueba falló");
        System.out.println("AreaDAOTest.crearTest");
    }
    @Test
    public void eliminarTest() {
        System.out.println("AreaDAOTest.eliminarTest");
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        Area eliminado = new Area(1);
        AreaDAO cut = new AreaDAO();
        assertThrows(IllegalArgumentException.class, ()->cut.eliminar(null));
        assertThrows(IllegalStateException.class, ()->cut.eliminar(eliminado));
        Mockito.when(mockEM.contains(eliminado)).thenReturn(true);
        cut.em = mockEM;
        cut.eliminar(eliminado);
        Mockito.verify(mockEM, Mockito.times(1)).remove(eliminado);
        Mockito.when(mockEM.contains(eliminado)).thenReturn(false);
        Mockito.when(mockEM.merge(eliminado)).thenReturn(eliminado);
        cut.em = mockEM;
        cut.eliminar(eliminado);
        System.out.println("AreaDAOTest.eliminarTest");
    }

    @Test
    public void buscarPorRango(){
        System.out.println("AreaDAOTest.buscarPorRango");
        int first = 0;
        int max = 1000;
        AreaDAO cut = new AreaDAO();
        assertThrows(IllegalArgumentException.class, ()->cut.buscarPorRango(-1,10));
        assertThrows(IllegalArgumentException.class, ()->cut.buscarPorRango(10,-1));
        assertThrows(IllegalStateException.class, ()->cut.buscarPorRango(first,max));
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Area> cqMock = Mockito.mock(CriteriaQuery.class);
        Root rootMock = Mockito.mock(Root.class);
        Mockito.when(cqMock.from(Area.class)).thenReturn(rootMock);
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        TypedQuery tqQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(tqQuery.getResultList()).thenReturn(findResult);
        Mockito.when(mockEM.createQuery(cqMock)).thenReturn(tqQuery);
        Mockito.when(cbMock.createQuery(Area.class)).thenReturn(cqMock);
        Mockito.when(mockEM.getCriteriaBuilder()).thenReturn(cbMock);
        cut.em = mockEM;
        List<Area> encontrados = cut.buscarPorRango(first,max);
        assertNotNull(encontrados);
        assertEquals(findResult.size(), encontrados.size());
        //fail("Esta prueba falló");
    }
}
