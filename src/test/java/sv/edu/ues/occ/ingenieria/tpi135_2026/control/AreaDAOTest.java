package sv.edu.ues.occ.ingenieria.tpi135_2026.control;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import sv.edu.ues.occ.ingenieria.tpi135_2026.entity.Area;

public class AreaDAOTest {

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
}
