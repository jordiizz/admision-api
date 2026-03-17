package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;

@ExtendWith(MockitoExtension.class)
public class AspiranteOpcionDAOTest {

    @Mock
    EntityManager em;

    @InjectMocks
    AspiranteOpcionDAO dao;

    @Test
    public void getEntityManagerTest() {
        System.out.println("Ejecutando test: getEntityManagerTest en AspiranteOpcionDAO");
        assertEquals(em, dao.getEntityManager());
    }

    @Test
    public void buscarPorAspiranteRangoTest() {
        System.out.println("Ejecutando test: buscarPorAspiranteRangoTest en AspiranteOpcionDAO");
        UUID idAspirante = UUID.randomUUID();
        AspiranteOpcion opcion = new AspiranteOpcion();
        List<AspiranteOpcion> listaEsperada = Collections.singletonList(opcion);

        // Branch: idAspirante null
        List<AspiranteOpcion> resultadoNulo = dao.buscarPorAspiranteRango(null, 0, 10);
        assertTrue(resultadoNulo.isEmpty());

        // Branch: Success
        TypedQuery<AspiranteOpcion> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(AspiranteOpcion.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setFirstResult(Mockito.anyInt())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setMaxResults(Mockito.anyInt())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(listaEsperada);

        List<AspiranteOpcion> resultadoExitoso = dao.buscarPorAspiranteRango(idAspirante, 0, 10);
        assertEquals(listaEsperada, resultadoExitoso);

        // Branch: Exception
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(AspiranteOpcion.class))).thenThrow(new RuntimeException("Error"));
        List<AspiranteOpcion> resultadoExcepcion = dao.buscarPorAspiranteRango(idAspirante, 0, 10);
        assertTrue(resultadoExcepcion.isEmpty());
    }

    @Test
    public void contarPorAspiranteTest() {
        System.out.println("Ejecutando test: contarPorAspiranteTest en AspiranteOpcionDAO");
        UUID idAspirante = UUID.randomUUID();

        // Branch: idAspirante null
        Long resultadoNulo = dao.contarPorAspirante(null);
        assertEquals(0L, resultadoNulo);

        // Branch: Success
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(5L);

        Long resultadoExitoso = dao.contarPorAspirante(idAspirante);
        assertEquals(5L, resultadoExitoso);

        // Branch: Exception
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenThrow(new RuntimeException("Error"));
        Long resultadoExcepcion = dao.contarPorAspirante(idAspirante);
        assertEquals(0L, resultadoExcepcion);
    }

    @Test
    public void buscarPorIdYAspiranteTest() {
        System.out.println("Ejecutando test: buscarPorIdYAspiranteTest en AspiranteOpcionDAO");
        UUID idAspirante = UUID.randomUUID();
        UUID idOpcion = UUID.randomUUID();
        
        // Branch: idAspirante or idOpcion null
        assertNull(dao.buscarPorIdYAspirante(null, UUID.randomUUID()));
        assertNull(dao.buscarPorIdYAspirante(UUID.randomUUID(), null));

        // Branch: Success
        AspiranteOpcion opcionEsperada = new AspiranteOpcion();
        TypedQuery<AspiranteOpcion> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(AspiranteOpcion.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspiranteOpcion", idOpcion)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspirante", idAspirante)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(opcionEsperada);

        AspiranteOpcion resultadoExitoso = dao.buscarPorIdYAspirante(idOpcion, idAspirante);
        assertEquals(opcionEsperada, resultadoExitoso);

        // Branch: Exception
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(AspiranteOpcion.class))).thenThrow(new RuntimeException("Error"));
        assertNull(dao.buscarPorIdYAspirante(idOpcion, idAspirante));
    }
}