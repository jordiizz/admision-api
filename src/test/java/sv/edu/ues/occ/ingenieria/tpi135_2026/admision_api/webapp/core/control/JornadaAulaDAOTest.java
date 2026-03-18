package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;

public class JornadaAulaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("JornadaAulaDAOTest.constructorTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        assertNotNull(cut);
        System.out.println("JornadaAulaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("JornadaAulaDAOTest.getEntityManagerTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("JornadaAulaDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void buscarPorJornadaYAulaSinResultadosTest() {
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaSinResultadosTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<JornadaAula> mockQuery = Mockito.mock(TypedQuery.class);

        UUID idJornada = UUID.randomUUID();
        String idAula = "A-01";

        cut.em = mockEM;
        Mockito.when(mockEM.createQuery(Mockito.anyString(), Mockito.eq(JornadaAula.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        JornadaAula resultado = cut.buscarPorJornadaYAula(idJornada, idAula);

        assertNull(resultado);
        Mockito.verify(mockEM).createQuery(Mockito.anyString(), Mockito.eq(JornadaAula.class));
        Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaSinResultadosTest - finalizado");
    }

    @Test
    public void buscarPorJornadaYAulaConResultadoTest() {
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaConResultadoTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<JornadaAula> mockQuery = Mockito.mock(TypedQuery.class);

        UUID idJornada = UUID.randomUUID();
        String idAula = "B-12";
        JornadaAula esperado = new JornadaAula(UUID.randomUUID());

        cut.em = mockEM;
        Mockito.when(mockEM.createQuery(Mockito.anyString(), Mockito.eq(JornadaAula.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(esperado));

        JornadaAula resultado = cut.buscarPorJornadaYAula(idJornada, idAula);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        Mockito.verify(mockEM).createQuery(Mockito.anyString(), Mockito.eq(JornadaAula.class));
        Mockito.verify(mockQuery).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery).setParameter("idAula", idAula);
        Mockito.verify(mockQuery).setMaxResults(1);
        Mockito.verify(mockQuery).getResultList();
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaConResultadoTest - finalizado");
    }
}
