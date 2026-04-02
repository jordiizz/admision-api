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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaArea;

public class PreguntaAreaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PreguntaAreaDAOTest.constructorTest");
        PreguntaAreaDAO cut = new PreguntaAreaDAO();
        assertNotNull(cut);
        System.out.println("PreguntaAreaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PreguntaAreaDAOTest.getEntityManagerTest");
        PreguntaAreaDAO cut = new PreguntaAreaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PreguntaAreaDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorPreguntaRangoTest() {
        System.out.println("PreguntaAreaDAOTest.buscarPorPreguntaRangoTest");
        UUID idPregunta = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<PreguntaArea> esperados = Arrays.asList(
            new PreguntaArea(new Pregunta(idPregunta), new Area(UUID.randomUUID())),
            new PreguntaArea(new Pregunta(idPregunta), new Area(UUID.randomUUID()))
        );

        PreguntaAreaDAO cut = new PreguntaAreaDAO();

        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPreguntaRango(null, first, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPreguntaRango(idPregunta, -1, max));
        assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPreguntaRango(idPregunta, first, 0));

        assertThrows(IllegalStateException.class,
            () -> cut.buscarPorPreguntaRango(idPregunta, first, max));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PreguntaArea> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PreguntaArea.buscarPorPreguntaRango",
            PreguntaArea.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PreguntaArea> resultado = cut.buscarPorPreguntaRango(idPregunta, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPregunta", idPregunta);
        Mockito.verify(mockQuery, Mockito.times(1)).setFirstResult(first);
        Mockito.verify(mockQuery, Mockito.times(1)).setMaxResults(max);
        Mockito.verify(mockQuery, Mockito.times(1)).getResultList();
        System.out.println("PreguntaAreaDAOTest.buscarPorPreguntaRangoTest - finalizado");
    }

    @Test
    public void contarPorPreguntaTest() {
        System.out.println("PreguntaAreaDAOTest.contarPorPreguntaTest");
        UUID idPregunta = UUID.randomUUID();

        PreguntaAreaDAO cut = new PreguntaAreaDAO();

        assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPregunta(null));

        assertThrows(IllegalStateException.class,
            () -> cut.contarPorPregunta(idPregunta));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
            "PreguntaArea.contarPorPregunta",
            Long.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPregunta", idPregunta)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(2L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPregunta(idPregunta);

        assertNotNull(resultado);
        assertEquals(2L, resultado);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPregunta", idPregunta);
        Mockito.verify(mockQuery, Mockito.times(1)).getSingleResult();
        System.out.println("PreguntaAreaDAOTest.contarPorPreguntaTest - finalizado");
    }
}