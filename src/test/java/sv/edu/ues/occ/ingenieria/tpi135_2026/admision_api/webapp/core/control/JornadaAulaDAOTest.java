package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

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
    public void buscarPorJornadaYAulaTest() {
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<JornadaAula> mockQuery = Mockito.mock(TypedQuery.class);

        UUID idJornada = UUID.randomUUID();
        String idAula = "A-01";
        JornadaAula esperado = new JornadaAula(UUID.randomUUID());

        // Validaciones de entrada y repositorio no inicializado.
        assertThrows(IllegalArgumentException.class, () -> cut.buscarPorJornadaYAula(null, idAula));
        assertThrows(IllegalArgumentException.class, () -> cut.buscarPorJornadaYAula(idJornada, null));
        assertThrows(IllegalStateException.class, () -> cut.buscarPorJornadaYAula(idJornada, idAula));

        // Configuracion comun del query para los escenarios con EntityManager disponible.
        cut.em = mockEM;
        Mockito.when(mockEM.createNamedQuery("JornadaAula.buscarPorJornadaYAula", JornadaAula.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAula", idAula)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(1)).thenReturn(mockQuery);

        // Escenario 1: no hay coincidencias, se espera null.
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        JornadaAula resultado = cut.buscarPorJornadaYAula(idJornada, idAula);

        assertNull(resultado);

        // Escenario 2: hay coincidencia, se retorna el primer elemento.
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(esperado));
        JornadaAula conResultado = cut.buscarPorJornadaYAula(idJornada, idAula);
        assertNotNull(conResultado);
        assertEquals(esperado, conResultado);

        // Verifica que ambos escenarios ejecutaron la misma cadena de consulta.
        Mockito.verify(mockEM, Mockito.times(2)).createNamedQuery("JornadaAula.buscarPorJornadaYAula", JornadaAula.class);
        Mockito.verify(mockQuery, Mockito.times(2)).setParameter("idJornada", idJornada);
        Mockito.verify(mockQuery, Mockito.times(2)).setParameter("idAula", idAula);
        Mockito.verify(mockQuery, Mockito.times(2)).setMaxResults(1);
        Mockito.verify(mockQuery, Mockito.times(2)).getResultList();
        System.out.println("JornadaAulaDAOTest.buscarPorJornadaYAulaTest - finalizado");
    }

    @Test
    public void listarPorJornadaTest() {
        System.out.println("JornadaAulaDAOTest.listarPorJornadaTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<JornadaAula> mockQuery = Mockito.mock(TypedQuery.class);

        UUID idJornada = UUID.randomUUID();
        JornadaAula jornadaAula = new JornadaAula(UUID.randomUUID());
        List<JornadaAula> listaEsperada = List.of(jornadaAula);

        // Validaciones: parámetro nulo y sin EntityManager
        assertThrows(IllegalArgumentException.class, () -> cut.listarPorJornada(null));
        assertThrows(IllegalStateException.class, () -> cut.listarPorJornada(idJornada));

        // Configurar mock
        cut.em = mockEM;
        Mockito.when(mockEM.createNamedQuery("JornadaAula.findByIdJornada", JornadaAula.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(listaEsperada);

        // Act
        List<JornadaAula> resultado = cut.listarPorJornada(idJornada);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(jornadaAula, resultado.get(0));
        System.out.println("JornadaAulaDAOTest.listarPorJornadaTest - finalizado");
    }

    @Test
    public void listarPorJornadaVacioTest() {
        System.out.println("JornadaAulaDAOTest.listarPorJornadaVacioTest");
        JornadaAulaDAO cut = new JornadaAulaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<JornadaAula> mockQuery = Mockito.mock(TypedQuery.class);

        UUID idJornada = UUID.randomUUID();

        cut.em = mockEM;
        Mockito.when(mockEM.createNamedQuery("JornadaAula.findByIdJornada", JornadaAula.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idJornada", idJornada)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        // Act
        List<JornadaAula> resultado = cut.listarPorJornada(idJornada);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("JornadaAulaDAOTest.listarPorJornadaVacioTest - finalizado");
    }

}
