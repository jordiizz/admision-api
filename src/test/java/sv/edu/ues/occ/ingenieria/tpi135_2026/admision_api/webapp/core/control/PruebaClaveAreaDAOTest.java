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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;

public class PruebaClaveAreaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaClaveAreaDAOTest.constructorTest");
        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();
        assertNotNull(cut);
        System.out.println("PruebaClaveAreaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaClaveAreaDAOTest.getEntityManagerTest");
        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaClaveAreaDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorPruebaClaveRangoTest() {
        System.out.println("PruebaClaveAreaDAOTest.buscarPorPruebaClaveRangoTest");
        UUID idPruebaClave = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<PruebaClaveArea> esperados = Arrays.asList(
                new PruebaClaveArea(UUID.randomUUID()),
                new PruebaClaveArea(UUID.randomUUID())
        );

        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.buscarPorPruebaClaveRango(idPruebaClave, first, max));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveArea> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery("PruebaClaveArea.buscarPorPruebaClave", PruebaClaveArea.class))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaClaveArea> resultado = cut.buscarPorPruebaClaveRango(idPruebaClave, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery, Mockito.times(1)).setFirstResult(first);
        Mockito.verify(mockQuery, Mockito.times(1)).setMaxResults(max);
        Mockito.verify(mockQuery, Mockito.times(1)).getResultList();
        System.out.println("PruebaClaveAreaDAOTest.buscarPorPruebaClaveRangoTest - finalizado");
    }

    @Test
    public void contarPorPruebaClaveTest() {
        System.out.println("PruebaClaveAreaDAOTest.contarPorPruebaClaveTest");
        UUID idPruebaClave = UUID.randomUUID();

        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.contarPorPruebaClave(idPruebaClave));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery("PruebaClaveArea.contarPorPruebaClave", Long.class))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(3L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPruebaClave(idPruebaClave);

        assertNotNull(resultado);
        assertEquals(3L, resultado);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery, Mockito.times(1)).getSingleResult();
        System.out.println("PruebaClaveAreaDAOTest.contarPorPruebaClaveTest - finalizado");
    }

    @Test
    public void buscarPorIdYPruebaClaveEncontradoTest() {
        System.out.println("PruebaClaveAreaDAOTest.buscarPorIdYPruebaClaveEncontradoTest");
        UUID idPruebaClaveArea = UUID.randomUUID();
        UUID idPruebaClave = UUID.randomUUID();
        PruebaClaveArea esperado = new PruebaClaveArea(idPruebaClaveArea);

        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
            () -> cut.buscarPorIdYPruebaClave(idPruebaClaveArea, idPruebaClave));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveArea> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery("PruebaClaveArea.buscarPorIdYPruebaClave", PruebaClaveArea.class))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idPruebaClaveArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(esperado));

        cut.em = mockEM;
        PruebaClaveArea resultado = cut.buscarPorIdYPruebaClave(idPruebaClaveArea, idPruebaClave);

        assertNotNull(resultado);
        assertEquals(idPruebaClaveArea, resultado.getIdPruebaClaveArea());
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idArea", idPruebaClaveArea);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery, Mockito.times(1)).setMaxResults(1);
        Mockito.verify(mockQuery, Mockito.times(1)).getResultList();
        System.out.println("PruebaClaveAreaDAOTest.buscarPorIdYPruebaClaveEncontradoTest - finalizado");
    }

    @Test
    public void buscarPorIdYPruebaClaveNoEncontradoTest() {
        System.out.println("PruebaClaveAreaDAOTest.buscarPorIdYPruebaClaveNoEncontradoTest");
        UUID idPruebaClaveArea = UUID.randomUUID();
        UUID idPruebaClave = UUID.randomUUID();

        PruebaClaveAreaDAO cut = new PruebaClaveAreaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveArea> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery("PruebaClaveArea.buscarPorIdYPruebaClave", PruebaClaveArea.class))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idPruebaClaveArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        cut.em = mockEM;
        PruebaClaveArea resultado = cut.buscarPorIdYPruebaClave(idPruebaClaveArea, idPruebaClave);

        assertNull(resultado);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idArea", idPruebaClaveArea);
        Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idPruebaClave", idPruebaClave);
        Mockito.verify(mockQuery, Mockito.times(1)).setMaxResults(1);
        Mockito.verify(mockQuery, Mockito.times(1)).getResultList();
        System.out.println("PruebaClaveAreaDAOTest.buscarPorIdYPruebaClaveNoEncontradoTest - finalizado");
    }
}