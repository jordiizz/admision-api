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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;

public class DistractorAreaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("DistractorAreaDAOTest.constructorTest");
        DistractorAreaDAO cut = new DistractorAreaDAO();
        assertNotNull(cut);
        System.out.println("DistractorAreaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("DistractorAreaDAOTest.getEntityManagerTest");
        DistractorAreaDAO cut = new DistractorAreaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("DistractorAreaDAOTest.getEntityManagerTest - finalizado");
    }

        @Test
        public void buscarPorDistractorRangoTest() {
        System.out.println("DistractorAreaDAOTest.buscarPorDistractorRangoTest");
        UUID idDistractor = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<DistractorArea> esperados = Arrays.asList(
            new DistractorArea(new Distractor(idDistractor), new Area(UUID.randomUUID())),
            new DistractorArea(new Distractor(idDistractor), new Area(UUID.randomUUID()))
        );

        DistractorAreaDAO cut = new DistractorAreaDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.buscarPorDistractorRango(idDistractor, first, max));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<DistractorArea> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createQuery(
            "SELECT d FROM DistractorArea d WHERE d.idDistractor.idDistractor = :idDistractor ORDER BY d.idArea.idArea",
            DistractorArea.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idDistractor", idDistractor)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<DistractorArea> resultado = cut.buscarPorDistractorRango(idDistractor, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idDistractor", idDistractor);
        Mockito.verify(mockQuery, Mockito.times(1)).setFirstResult(first);
        Mockito.verify(mockQuery, Mockito.times(1)).setMaxResults(max);
        Mockito.verify(mockQuery, Mockito.times(1)).getResultList();
        System.out.println("DistractorAreaDAOTest.buscarPorDistractorRangoTest - finalizado");
        }

        @Test
        public void contarPorDistractorTest() {
        System.out.println("DistractorAreaDAOTest.contarPorDistractorTest");
        UUID idDistractor = UUID.randomUUID();

        DistractorAreaDAO cut = new DistractorAreaDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.contarPorDistractor(idDistractor));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createQuery(
            "SELECT COUNT(d) FROM DistractorArea d WHERE d.idDistractor.idDistractor = :idDistractor",
            Long.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idDistractor", idDistractor)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(2L);

        cut.em = mockEM;
        Long resultado = cut.contarPorDistractor(idDistractor);

        assertNotNull(resultado);
        assertEquals(2L, resultado);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idDistractor", idDistractor);
        Mockito.verify(mockQuery, Mockito.times(1)).getSingleResult();
        System.out.println("DistractorAreaDAOTest.contarPorDistractorTest - finalizado");
        }
}