package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;

public class PruebaJornadaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaJornadaDAOTest.constructorTest");
        PruebaJornadaDAO cut = new PruebaJornadaDAO();
        assertNotNull(cut);
        System.out.println("PruebaJornadaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaJornadaDAOTest.getEntityManagerTest");
        PruebaJornadaDAO cut = new PruebaJornadaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaJornadaDAOTest.getEntityManagerTest - finalizado");
    }
}
