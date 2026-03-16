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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

public class PruebaJornadaAulaAspiranteOpcionExamenDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.constructorTest");
        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();
        assertNotNull(cut);
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.getEntityManagerTest");
        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorPadreRangoTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorPadreRangoTest");
        UUID idPruebaJornadaAulaAspiranteOpcion = UUID.randomUUID();
        int first = 0;
        int max = 10;
        List<PruebaJornadaAulaAspiranteOpcionExamen> esperados = Arrays.asList(
                new PruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID()),
                new PruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID())
        );

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> cut.buscarPorPadreRango(idPruebaJornadaAulaAspiranteOpcion, first, max));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaJornadaAulaAspiranteOpcionExamen> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorPadre",
                PruebaJornadaAulaAspiranteOpcionExamen.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaJornadaAulaAspiranteOpcionExamen> resultado =
                cut.buscarPorPadreRango(idPruebaJornadaAulaAspiranteOpcion, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockQuery).setFirstResult(first);
        Mockito.verify(mockQuery).setMaxResults(max);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorPadreRangoTest - finalizado");
    }

    @Test
    public void contarPorPadreTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.contarPorPadreTest");
        UUID idPruebaJornadaAulaAspiranteOpcion = UUID.randomUUID();

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> cut.contarPorPadre(idPruebaJornadaAulaAspiranteOpcion));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.contarPorPadre",
                Long.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(3L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPadre(idPruebaJornadaAulaAspiranteOpcion);

        assertNotNull(resultado);
        assertEquals(3L, resultado);
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockQuery).getSingleResult();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.contarPorPadreTest - finalizado");
    }

    @Test
    public void buscarPorIdYPadreEncontradoTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorIdYPadreEncontradoTest");
        UUID id = UUID.randomUUID();
        UUID idPruebaJornadaAulaAspiranteOpcion = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen esperado = new PruebaJornadaAulaAspiranteOpcionExamen(id);

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> cut.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaJornadaAulaAspiranteOpcionExamen> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorIdYPadre",
                PruebaJornadaAulaAspiranteOpcionExamen.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcionExamen", id))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(esperado));

        cut.em = mockEM;
        PruebaJornadaAulaAspiranteOpcionExamen resultado =
                cut.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdPruebaJornadaAulaAspiranteOpcionExamen());
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcionExamen", id);
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorIdYPadreEncontradoTest - finalizado");
    }

    @Test
    public void buscarPorIdYPadreNoEncontradoTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorIdYPadreNoEncontradoTest");
        UUID id = UUID.randomUUID();
        UUID idPruebaJornadaAulaAspiranteOpcion = UUID.randomUUID();

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaJornadaAulaAspiranteOpcionExamen> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorIdYPadre",
                PruebaJornadaAulaAspiranteOpcionExamen.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcionExamen", id))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        cut.em = mockEM;
        PruebaJornadaAulaAspiranteOpcionExamen resultado =
                cut.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion);

        assertNull(resultado);
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcionExamen", id);
        Mockito.verify(mockQuery).setParameter("idPruebaJornadaAulaAspiranteOpcion",
                idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorIdYPadreNoEncontradoTest - finalizado");
    }
}
