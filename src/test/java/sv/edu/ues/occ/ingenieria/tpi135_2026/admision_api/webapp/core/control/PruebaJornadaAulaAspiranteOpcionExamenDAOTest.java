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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;
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
                UUID idPrueba = UUID.randomUUID();
                UUID idJornada = UUID.randomUUID();
                String idAula = "A-01";
                UUID idAspiranteOpcion = UUID.randomUUID();
        int first = 0;
        int max = 10;
        PruebaJornadaAulaAspiranteOpcionExamen esperado1 = new PruebaJornadaAulaAspiranteOpcionExamen();
        esperado1.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));
        PruebaJornadaAulaAspiranteOpcionExamen esperado2 = new PruebaJornadaAulaAspiranteOpcionExamen();
        esperado2.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));
        List<PruebaJornadaAulaAspiranteOpcionExamen> esperados = Arrays.asList(
                esperado1,
                esperado2
        );

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();

        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(null, idJornada, idAula, idAspiranteOpcion, first, max));
        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(idPrueba, null, idAula, idAspiranteOpcion, first, max));
        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(idPrueba, idJornada, null, idAspiranteOpcion, first, max));
        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(idPrueba, idJornada, idAula, null, first, max));
        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(idPrueba, idJornada, idAula, idAspiranteOpcion, -1, max));
        assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(idPrueba, idJornada, idAula, idAspiranteOpcion, first, 0));

        assertThrows(IllegalStateException.class,
                () -> cut.buscarPorPadreRango(idPrueba, idJornada, idAula, idAspiranteOpcion, first, max));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaJornadaAulaAspiranteOpcionExamen> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.buscarPorPadre",
                PruebaJornadaAulaAspiranteOpcionExamen.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspiranteOpcion", idAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaJornadaAulaAspiranteOpcionExamen> resultado =
                cut.buscarPorPadreRango(idPrueba, idJornada, idAula, idAspiranteOpcion, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery).setParameter("idPrueba", idPrueba);
        Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).setParameter("idAspiranteOpcion", idAspiranteOpcion);
        Mockito.verify(mockQuery).setFirstResult(first);
        Mockito.verify(mockQuery).setMaxResults(max);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.buscarPorPadreRangoTest - finalizado");
    }

    @Test
    public void contarPorPadreTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.contarPorPadreTest");
                UUID idPrueba = UUID.randomUUID();
                UUID idJornada = UUID.randomUUID();
                String idAula = "A-01";
                UUID idAspiranteOpcion = UUID.randomUUID();

        PruebaJornadaAulaAspiranteOpcionExamenDAO cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();

        assertThrows(IllegalArgumentException.class,
                () -> cut.contarPorPadre(null, idJornada, idAula, idAspiranteOpcion));
        assertThrows(IllegalArgumentException.class,
                () -> cut.contarPorPadre(idPrueba, null, idAula, idAspiranteOpcion));
        assertThrows(IllegalArgumentException.class,
                () -> cut.contarPorPadre(idPrueba, idJornada, null, idAspiranteOpcion));
        assertThrows(IllegalArgumentException.class,
                () -> cut.contarPorPadre(idPrueba, idJornada, idAula, null));

        assertThrows(IllegalStateException.class,
                () -> cut.contarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion));

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcionExamen.contarPorPadre",
                Long.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspiranteOpcion", idAspiranteOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(3L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion);

        assertNotNull(resultado);
        assertEquals(3L, resultado);
        Mockito.verify(mockQuery).setParameter("idPrueba", idPrueba);
        Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).setParameter("idAspiranteOpcion", idAspiranteOpcion);
        Mockito.verify(mockQuery).getSingleResult();
        System.out.println("PruebaJornadaAulaAspiranteOpcionExamenDAOTest.contarPorPadreTest - finalizado");
    }
}
