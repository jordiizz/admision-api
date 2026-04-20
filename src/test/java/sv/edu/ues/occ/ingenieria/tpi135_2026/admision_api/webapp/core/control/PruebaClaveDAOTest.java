package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;

public class PruebaClaveDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaClaveDAOTest.constructorTest");
        PruebaClaveDAO cut = new PruebaClaveDAO();
        assertNotNull(cut);
        System.out.println("PruebaClaveDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaClaveDAOTest.getEntityManagerTest");
        PruebaClaveDAO cut = new PruebaClaveDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaClaveDAOTest.getEntityManagerTest - finalizado");
    }
    @Test
    public void testListarClavesPorPruebaExitoso() {
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaExitoso");

        PruebaClaveDAO cut = new PruebaClaveDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();
        List<PruebaClave> clavesEsperadas = List.of(
                new PruebaClave(),
                new PruebaClave()
        );

        TypedQuery<PruebaClave> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClave.findByIdPrueba", PruebaClave.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(clavesEsperadas);

        List<PruebaClave> resultado = cut.listarClavesPorPrueba(idPrueba);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(clavesEsperadas, resultado);
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaExitoso - finalizado");
    }

    @Test
    public void testListarClavesPorPruebaVacio() {
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaVacio");

        PruebaClaveDAO cut = new PruebaClaveDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();

        TypedQuery<PruebaClave> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClave.findByIdPrueba", PruebaClave.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        List<PruebaClave> resultado = cut.listarClavesPorPrueba(idPrueba);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaVacio - finalizado");
    }

    @Test
    public void testListarClavesPorPruebaIdNulo() {
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaIdNulo");

        PruebaClaveDAO cut = new PruebaClaveDAO();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.listarClavesPorPrueba(null));

        assertEquals("idPrueba no puede ser nulo", ex.getMessage());
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaIdNulo - finalizado");
    }

    @Test
    public void testListarClavesPorPruebaException() {
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaException");

        PruebaClaveDAO cut = new PruebaClaveDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();

        Mockito.when(mockEM.createNamedQuery("PruebaClave.findByIdPrueba", PruebaClave.class))
                .thenThrow(new RuntimeException("Error de persistencia"));

        assertThrows(IllegalStateException.class, () -> cut.listarClavesPorPrueba(idPrueba));
        System.out.println("PruebaDAOTest.testListarClavesPorPruebaException - finalizado");
    }
}