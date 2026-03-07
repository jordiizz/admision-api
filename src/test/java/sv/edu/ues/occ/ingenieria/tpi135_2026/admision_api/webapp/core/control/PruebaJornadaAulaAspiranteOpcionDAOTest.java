package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;

public class PruebaJornadaAulaAspiranteOpcionDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.constructorTest");
        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();
        assertNotNull(cut);
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.getEntityManagerTest");
        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.getEntityManagerTest - finalizado");
    }
}
