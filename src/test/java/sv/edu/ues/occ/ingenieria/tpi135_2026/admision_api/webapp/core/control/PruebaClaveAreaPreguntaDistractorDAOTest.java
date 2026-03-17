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
        UUID idPruebaClaveAreaPregunta = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<PruebaClaveAreaPreguntaDistractor> esperados = Arrays.asList(
            new PruebaClaveAreaPreguntaDistractor(UUID.randomUUID()),
            new PruebaClaveAreaPreguntaDistractor(UUID.randomUUID())
        );

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.buscarPorPadreRango(idPruebaClaveAreaPregunta, first, max));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.buscarPorPadre",
            PruebaClaveAreaPreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaClaveAreaPreguntaDistractor> resultado = cut.buscarPorPadreRango(idPruebaClaveAreaPregunta, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta);
        Mockito.verify(mockQuery).setFirstResult(first);
        Mockito.verify(mockQuery).setMaxResults(max);
        Mockito.verify(mockQuery).getResultList();
        }

        @Test
        public void contarPorPadreTest() {
        UUID idPruebaClaveAreaPregunta = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.contarPorPadre(idPruebaClaveAreaPregunta));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.contarPorPadre",
            Long.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(3L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPadre(idPruebaClaveAreaPregunta);

        assertNotNull(resultado);
        assertEquals(3L, resultado);
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta);
        Mockito.verify(mockQuery).getSingleResult();
        }

        @Test
        public void buscarPorIdYPadreEncontradoTest() {
        UUID id = UUID.randomUUID();
        UUID idPruebaClaveAreaPregunta = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor esperado = new PruebaClaveAreaPreguntaDistractor(id);

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.buscarPorIdYPadre(id, idPruebaClaveAreaPregunta));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.buscarPorIdYPadre",
            PruebaClaveAreaPreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPreguntaDistractor", id)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(esperado));

        cut.em = mockEM;
        PruebaClaveAreaPreguntaDistractor resultado = cut.buscarPorIdYPadre(id, idPruebaClaveAreaPregunta);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdPruebaClaveAreaPreguntaDistractor());
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPreguntaDistractor", id);
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        }

        @Test
        public void buscarPorIdYPadreNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        UUID idPruebaClaveAreaPregunta = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractorDAO cut = new PruebaClaveAreaPreguntaDistractorDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPreguntaDistractor> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PruebaClaveAreaPreguntaDistractor.buscarPorIdYPadre",
            PruebaClaveAreaPreguntaDistractor.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPreguntaDistractor", id)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        cut.em = mockEM;
        PruebaClaveAreaPreguntaDistractor resultado = cut.buscarPorIdYPadre(id, idPruebaClaveAreaPregunta);

        assertNull(resultado);
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPreguntaDistractor", id);
        Mockito.verify(mockQuery).setParameter("idPruebaClaveAreaPregunta", idPruebaClaveAreaPregunta);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        }
}