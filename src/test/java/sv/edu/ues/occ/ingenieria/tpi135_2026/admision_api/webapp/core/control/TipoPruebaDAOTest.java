package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;

public class TipoPruebaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("TipoPruebaDAOTest.constructorTest");
        TipoPruebaDAO cut = new TipoPruebaDAO();
        assertNotNull(cut);
        System.out.println("TipoPruebaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("TipoPruebaDAOTest.getEntityManagerTest");
        TipoPruebaDAO cut = new TipoPruebaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("TipoPruebaDAOTest.getEntityManagerTest - finalizado");
    }
}