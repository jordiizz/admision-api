package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

public class AspiranteExamenResourceTest {

    private PruebaJornadaAulaAspiranteOpcionExamenDAO mockDAO;
    private AspiranteExamenResource cut;

    private UUID idAspirante;
    private UUID idPrueba;

    @BeforeEach
    public void setup() {
        mockDAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionExamenDAO.class);
        cut = new AspiranteExamenResource();
        cut.pruebaJornadaAulaAspiranteOpcionExamenDAO = mockDAO;

        idAspirante = UUID.randomUUID();
        idPrueba = UUID.randomUUID();
    }

    @Test
    public void actualizarResultadoExitosoTest() {
        System.out.println("Ejecutando test: actualizarResultadoExitosoTest en AspiranteExamenResource");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("8.75"));

        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen();
        existente.setResultado(new BigDecimal("5.00"));
        existente.setFechaResultado(null);

        Mockito.when(mockDAO.obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba)).thenReturn(existente);

        Response resultado = cut.actualizarResultado(idAspirante, idPrueba, entity);

        assertEquals(200, resultado.getStatus());
        assertEquals(0, existente.getResultado().compareTo(new BigDecimal("8.75")));
        assertNotNull(existente.getFechaResultado());
        assertEquals(existente, resultado.getEntity());
        Mockito.verify(mockDAO).actualizar(existente);
    }

    @Test
    public void actualizarResultadoParametrosInvalidosTest() {
        System.out.println("Ejecutando test: actualizarResultadoParametrosInvalidosTest en AspiranteExamenResource");
        PruebaJornadaAulaAspiranteOpcionExamen conResultado = new PruebaJornadaAulaAspiranteOpcionExamen();
        conResultado.setResultado(new BigDecimal("7.25"));

        PruebaJornadaAulaAspiranteOpcionExamen sinResultado = new PruebaJornadaAulaAspiranteOpcionExamen();

        assertEquals(400, cut.actualizarResultado(null, idPrueba, conResultado).getStatus());
        assertEquals(400, cut.actualizarResultado(idAspirante, null, conResultado).getStatus());
        assertEquals(400, cut.actualizarResultado(idAspirante, idPrueba, null).getStatus());
        assertEquals(400, cut.actualizarResultado(idAspirante, idPrueba, sinResultado).getStatus());

        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarResultadoNoEncontradoTest() {
        System.out.println("Ejecutando test: actualizarResultadoNoEncontradoTest en AspiranteExamenResource");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("6.00"));

        Mockito.when(mockDAO.obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba)).thenReturn(null);

        Response resultado = cut.actualizarResultado(idAspirante, idPrueba, entity);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarResultadoExcepcionAlBuscarTest() {
        System.out.println("Ejecutando test: actualizarResultadoExcepcionAlBuscarTest en AspiranteExamenResource");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("8.00"));

        Mockito.when(mockDAO.obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba))
                .thenThrow(new IllegalStateException("Error al buscar"));

        Response resultado = cut.actualizarResultado(idAspirante, idPrueba, entity);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarResultadoExcepcionAlActualizarTest() {
        System.out.println("Ejecutando test: actualizarResultadoExcepcionAlActualizarTest en AspiranteExamenResource");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("9.00"));

        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen();
        existente.setResultado(new BigDecimal("4.00"));
        existente.setFechaResultado(OffsetDateTime.now().minusDays(1));

        Mockito.when(mockDAO.obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba)).thenReturn(existente);
        Mockito.doThrow(new IllegalArgumentException("Error al actualizar")).when(mockDAO).actualizar(existente);

        Response resultado = cut.actualizarResultado(idAspirante, idPrueba, entity);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO).actualizar(existente);
    }
}
