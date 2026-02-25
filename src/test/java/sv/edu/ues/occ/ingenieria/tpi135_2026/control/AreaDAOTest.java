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
    
}
