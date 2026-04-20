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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;

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

    @Test
    public void testListarPorIdPruebaExitoso() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaExitoso");

        // Arrange
        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<Jornada> jornadasEsperadas = List.of(
            new Jornada(UUID.randomUUID()),
            new Jornada(UUID.randomUUID())
        );

        @SuppressWarnings("unchecked")
        TypedQuery<Jornada> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Jornada.findByIdPrueba", Jornada.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(jornadasEsperadas);

        List<Jornada> resultado = cut.listarPorIdPrueba(idPrueba, first, max);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(jornadasEsperadas, resultado);
        System.out.println("JornadaDAOTest.testListarPorIdPruebaExitoso - finalizado");
    }

    @Test
    public void testListarPorIdPruebaVacio() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaVacio");

        // Arrange
        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();
        int first = 0;
        int max = 10;

        @SuppressWarnings("unchecked")
        TypedQuery<Jornada> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Jornada.findByIdPrueba", Jornada.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Jornada> resultado = cut.listarPorIdPrueba(idPrueba, first, max);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("JornadaDAOTest.testListarPorIdPruebaVacio - finalizado");
    }

    @Test
    public void testListarPorIdPruebaIdPruebaNull() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaIdPruebaNull");

        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.listarPorIdPrueba(null, 0, 10));

        assertEquals("Parámetros inválidos", ex.getMessage());
        System.out.println("JornadaDAOTest.testListarPorIdPruebaIdPruebaNull - finalizado");
    }

    @Test
    public void testListarPorIdPruebaFirstNegativo() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaFirstNegativo");

        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.listarPorIdPrueba(idPrueba, -1, 10));

        assertEquals("Parámetros inválidos", ex.getMessage());
        System.out.println("JornadaDAOTest.testListarPorIdPruebaFirstNegativo - finalizado");
    }

    @Test
    public void testListarPorIdPruebaMaxCero() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaMaxCero");

        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.listarPorIdPrueba(idPrueba, 0, 0));

        assertEquals("Parámetros inválidos", ex.getMessage());
        System.out.println("JornadaDAOTest.testListarPorIdPruebaMaxCero - finalizado");
    }

    @Test
    public void testListarPorIdPruebaMaxNegativo() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaMaxNegativo");

        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.listarPorIdPrueba(idPrueba, 0, -5));

        assertEquals("Parámetros inválidos", ex.getMessage());
        System.out.println("JornadaDAOTest.testListarPorIdPruebaMaxNegativo - finalizado");
    }

    @Test
    public void testListarPorIdPruebaExcepcionEnQuery() {
        System.out.println("JornadaDAOTest.testListarPorIdPruebaExcepcionEnQuery");

        JornadaDAO cut = new JornadaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPrueba = UUID.randomUUID();
        int first = 0;
        int max = 10;

        @SuppressWarnings("unchecked")
        TypedQuery<Jornada> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Jornada.findByIdPrueba", Jornada.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenThrow(new RuntimeException("Error en BD"));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cut.listarPorIdPrueba(idPrueba, first, max));

        assertNotNull(ex);
        System.out.println("JornadaDAOTest.testListarPorIdPruebaExcepcionEnQuery - finalizado");
    }

}