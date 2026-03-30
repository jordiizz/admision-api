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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

public class PruebaJornadaAulaAspiranteOpcionDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.constructorTest");
        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();
        assertNotNull(cut);
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.getEntityManagerTest");
        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorPruebaJornadaYJornadaAulaRangoTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.buscarPorPruebaJornadaYJornadaAulaRangoTest");
                UUID idPrueba = UUID.randomUUID();
                UUID idJornada = UUID.randomUUID();
                String idAula = "A-01";
        int first = 0;
        int max = 10;
        List<PruebaJornadaAulaAspiranteOpcion> esperados = Arrays.asList(
                new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID()),
                new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID())
        );

        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> cut.buscarPorPruebaJornadaYJornadaAulaRango(idPrueba, idJornada, idAula, first, max));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<PruebaJornadaAulaAspiranteOpcion> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcion.buscarPorPruebaJornadaYJornadaAula",
                PruebaJornadaAulaAspiranteOpcion.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(first)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(max)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(esperados);

        cut.em = mockEM;
        List<PruebaJornadaAulaAspiranteOpcion> resultado =
                cut.buscarPorPruebaJornadaYJornadaAulaRango(idPrueba, idJornada, idAula, first, max);

        assertNotNull(resultado);
        assertEquals(esperados.size(), resultado.size());
        Mockito.verify(mockQuery).setParameter("idPrueba", idPrueba);
        Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).setFirstResult(first);
        Mockito.verify(mockQuery).setMaxResults(max);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.buscarPorPruebaJornadaYJornadaAulaRangoTest - finalizado");
    }

    @Test
    public void contarPorPruebaJornadaYJornadaAulaTest() {
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.contarPorPruebaJornadaYJornadaAulaTest");
                UUID idPrueba = UUID.randomUUID();
                UUID idJornada = UUID.randomUUID();
                String idAula = "A-01";

        PruebaJornadaAulaAspiranteOpcionDAO cut = new PruebaJornadaAulaAspiranteOpcionDAO();

        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> cut.contarPorPruebaJornadaYJornadaAula(idPrueba, idJornada, idAula));
        assertNotNull(npe);

        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(mockEM.createNamedQuery(
                "PruebaJornadaAulaAspiranteOpcion.contarPorPruebaJornadaYJornadaAula",
                Long.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPrueba", idPrueba)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(5L);

        cut.em = mockEM;
        Long resultado = cut.contarPorPruebaJornadaYJornadaAula(idPrueba, idJornada, idAula);

        assertNotNull(resultado);
        assertEquals(5L, resultado);
                Mockito.verify(mockQuery).setParameter("idPrueba", idPrueba);
                Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
                Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).getSingleResult();
        System.out.println("PruebaJornadaAulaAspiranteOpcionDAOTest.contarPorPruebaJornadaYJornadaAulaTest - finalizado");
    }

}
