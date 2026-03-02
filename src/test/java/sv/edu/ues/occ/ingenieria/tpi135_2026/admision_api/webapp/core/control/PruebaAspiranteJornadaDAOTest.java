package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;

public class PruebaAspiranteJornadaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaAspiranteJornadaDAOTest.constructorTest");
        PruebaAspiranteJornadaDAO cut = new PruebaAspiranteJornadaDAO();
        assertNotNull(cut);
        System.out.println("PruebaAspiranteJornadaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaAspiranteJornadaDAOTest.getEntityManagerTest");
        PruebaAspiranteJornadaDAO cut = new PruebaAspiranteJornadaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaAspiranteJornadaDAOTest.getEntityManagerTest - finalizado");
    }
}