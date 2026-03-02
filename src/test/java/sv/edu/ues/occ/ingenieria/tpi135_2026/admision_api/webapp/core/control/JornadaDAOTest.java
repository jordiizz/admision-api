package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;

public class JornadaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("JornadaDAOTest.constructorTest");
        JornadaDAO cut = new JornadaDAO();
        assertNotNull(cut);
        System.out.println("JornadaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("JornadaDAOTest.getEntityManagerTest");
        JornadaDAO cut = new JornadaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("JornadaDAOTest.getEntityManagerTest - finalizado");
    }
}