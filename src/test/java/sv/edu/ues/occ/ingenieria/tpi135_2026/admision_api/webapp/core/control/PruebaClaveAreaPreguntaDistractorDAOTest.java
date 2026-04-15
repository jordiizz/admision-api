package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaDistractor;

public class PruebaClaveAreaPreguntaDistractorDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaClaveAreaPreguntaDistractorDAOTest.constructorTest");
        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();
        assertNotNull(cut);
        System.out.println("PruebaClaveAreaPreguntaDistractorDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaClaveAreaPreguntaDistractorDAOTest.getEntityManagerTest");
        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaClaveAreaPreguntaDistractorDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorPadreRangoTest() {
        UUID idPruebaClave = UUID.randomUUID();
        UUID idArea = UUID.randomUUID();
        UUID idPregunta = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<PruebaClaveAreaPreguntaDistractor> esperados = Arrays.asList(
            new PruebaClaveAreaPreguntaDistractor(new PruebaClave(idPruebaClave), new Area(idArea), new Pregunta(idPregunta), new Distractor(UUID.randomUUID())),
            new PruebaClaveAreaPreguntaDistractor(new PruebaClave(idPruebaClave), new Area(idArea), new Pregunta(idPregunta), new Distractor(UUID.randomUUID()))
        );

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();

        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(null, idArea, idPregunta, first, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(idPruebaClave, null, idPregunta, first, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(idPruebaClave, idArea, null, first, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(idPruebaClave, idArea, idPregunta, -1, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(idPruebaClave, idArea, idPregunta, first, 0));

        assertThrows(IllegalStateException.class,
            () -> cut.buscarPorPadreRango(idPruebaClave, idArea, idPregunta, first, max));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
            PruebaClaveAreaPreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaClaveAreaPreguntaDistractor> resultado = cut.buscarPorPadreRango(idPruebaClave, idArea, idPregunta, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery).setParameter("idArea", idArea);
        Mockito.verify(mockQuery).setParameter("idPregunta", idPregunta);
        Mockito.verify(mockQuery).setFirstResult(first);
        Mockito.verify(mockQuery).setMaxResults(max);
        Mockito.verify(mockQuery).getResultList();
    }

    @Test
    public void contarPorPadreTest() {
        UUID idPruebaClave = UUID.randomUUID();
        UUID idArea = UUID.randomUUID();
        UUID idPregunta = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();

        assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(null, idArea, idPregunta));
        assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(idPruebaClave, null, idPregunta));
        assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(idPruebaClave, idArea, null));

        assertThrows(IllegalStateException.class,
            () -> cut.contarPorPadre(idPruebaClave, idArea, idPregunta));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.contarPorPadre",
            Long.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(3L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPadre(idPruebaClave, idArea, idPregunta);

        assertNotNull(resultado);
        assertEquals(3L, resultado);
        Mockito.verify(mockQuery).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery).setParameter("idArea", idArea);
        Mockito.verify(mockQuery).setParameter("idPregunta", idPregunta);
        Mockito.verify(mockQuery).getSingleResult();
    }

}