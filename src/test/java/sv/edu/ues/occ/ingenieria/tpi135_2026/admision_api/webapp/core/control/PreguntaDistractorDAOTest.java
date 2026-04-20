package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaDistractor;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PreguntaDistractorDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PreguntaDistractorDAOTest.constructorTest");
        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();
        assertNotNull(cut);
        System.out.println("PreguntaDistractorDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PreguntaDistractorDAOTest.getEntityManagerTest");
        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PreguntaDistractorDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void testBuscarPorIdPreguntaExitoso() {
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaExitoso");

        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();;
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPregunta = UUID.randomUUID();
        List<PreguntaDistractor> distractoresEsperados = List.of(
                new PreguntaDistractor(),
                new PreguntaDistractor()
        );

        TypedQuery<PreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PreguntaDistractor.findByIdPregunta", PreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(distractoresEsperados);

        List<PreguntaDistractor> resultado = cut.buscarPorIdPregunta(idPregunta);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(distractoresEsperados, resultado);
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaExitoso - finalizado");
    }

    @Test
    public void testBuscarPorIdPreguntaVacio() {
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaVacio");

        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPregunta = UUID.randomUUID();

        TypedQuery<PreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PreguntaDistractor.findByIdPregunta", PreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        List<PreguntaDistractor> resultado = cut.buscarPorIdPregunta(idPregunta);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaVacio - finalizado");
    }

    @Test
    public void testBuscarPorIdPreguntaIdNulo() {
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaIdNulo");

        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorIdPregunta(null));

        assertEquals("El idPregunta no puede ser nulo", ex.getMessage());
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaIdNulo - finalizado");
    }

    @Test
    public void testBuscarPorIdPreguntaException() {
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaException");

        PreguntaDistractorDAO cut = new PreguntaDistractorDAO();;
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPregunta = UUID.randomUUID();

        Mockito.when(mockEM.createNamedQuery("PreguntaDistractor.findByIdPregunta", PreguntaDistractor.class))
                .thenThrow(new RuntimeException("Error en base de datos"));

        assertThrows(IllegalStateException.class, () -> cut.buscarPorIdPregunta(idPregunta));
        System.out.println("PruebaDAOTest.testBuscarPorIdPreguntaException - finalizado");
    }
}