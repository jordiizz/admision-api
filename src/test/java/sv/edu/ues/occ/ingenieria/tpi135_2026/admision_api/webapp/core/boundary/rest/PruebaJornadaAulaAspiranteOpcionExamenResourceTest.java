package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

public class PruebaJornadaAulaAspiranteOpcionExamenResourceTest {

    private UriInfo mockUriInfo;
    private PruebaJornadaAulaAspiranteOpcionExamenDAO mockDAO;
    private PruebaJornadaAulaAspiranteOpcionDAO mockPJAAODAO;
    private PruebaJornadaAulaAspiranteOpcionExamenResource cut;

    private UUID idPruebaJornadaAulaAspiranteOpcion;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionExamenDAO.class);
        mockPJAAODAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build())
                .thenReturn(URI.create(
                        "http://localhost:8080/v1/prueba-jornada-aula-aspirante-opcion/1/examen/1"));

        idPruebaJornadaAulaAspiranteOpcion = UUID.randomUUID();

        cut = new PruebaJornadaAulaAspiranteOpcionExamenResource();
        cut.pruebaJornadaAulaAspiranteOpcionExamenDAO = mockDAO;
        cut.pruebaJornadaAulaAspiranteOpcionDAO = mockPJAAODAO;
    }

    // ============================================================
    //  POST - crear
    // ============================================================

    @Test
    public void crearExitosoTest() {
        System.out.println("crearExitosoTest");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("9.50"));
        PruebaJornadaAulaAspiranteOpcion padre =
                new PruebaJornadaAulaAspiranteOpcion(idPruebaJornadaAulaAspiranteOpcion);
        Mockito.when(mockPJAAODAO.buscarPorId(idPruebaJornadaAulaAspiranteOpcion)).thenReturn(padre);
        Mockito.doNothing().when(mockDAO).crear(entity);

        Response resultado = cut.crear(idPruebaJornadaAulaAspiranteOpcion, entity, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockPJAAODAO).buscarPorId(idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearEntityNullTest() {
        System.out.println("crearEntityNullTest");
        Response resultado = cut.crear(idPruebaJornadaAulaAspiranteOpcion, null, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJAAODAO);
    }

    @Test
    public void crearResultadoNullTest() {
        System.out.println("crearResultadoNullTest");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        // resultado es null por defecto
        Response resultado = cut.crear(idPruebaJornadaAulaAspiranteOpcion, entity, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJAAODAO);
    }

    @Test
    public void crearIdPadreNullTest() {
        System.out.println("crearIdPadreNullTest");
        Response resultado = cut.crear(null, new PruebaJornadaAulaAspiranteOpcionExamen(), mockUriInfo);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJAAODAO);
    }

    @Test
    public void crearPadreNoEncontradoTest() {
        System.out.println("crearPadreNoEncontradoTest");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("8.00"));
        Mockito.when(mockPJAAODAO.buscarPorId(idPruebaJornadaAulaAspiranteOpcion)).thenReturn(null);

        Response resultado = cut.crear(idPruebaJornadaAulaAspiranteOpcion, entity, mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearConExcepcionTest() {
        System.out.println("crearConExcepcionTest");
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("7.00"));
        PruebaJornadaAulaAspiranteOpcion padre =
                new PruebaJornadaAulaAspiranteOpcion(idPruebaJornadaAulaAspiranteOpcion);
        Mockito.when(mockPJAAODAO.buscarPorId(idPruebaJornadaAulaAspiranteOpcion)).thenReturn(padre);
        Mockito.doThrow(new IllegalStateException("DB error")).when(mockDAO).crear(entity);

        Response resultado = cut.crear(idPruebaJornadaAulaAspiranteOpcion, entity, mockUriInfo);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  GET list - buscarPorRango
    // ============================================================

    @Test
    public void buscarPorRangoExitosoTest() {
        System.out.println("buscarPorRangoExitosoTest");
        List<PruebaJornadaAulaAspiranteOpcionExamen> registros =
                List.of(new PruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID()));
        Mockito.when(mockDAO.buscarPorPadreRango(idPruebaJornadaAulaAspiranteOpcion, 0, 50))
                .thenReturn(registros);
        Mockito.when(mockDAO.contarPorPadre(idPruebaJornadaAulaAspiranteOpcion)).thenReturn(1L);

        Response resultado = cut.buscarPorRango(idPruebaJornadaAulaAspiranteOpcion, 0, 50);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorPadreRango(idPruebaJornadaAulaAspiranteOpcion, 0, 50);
        Mockito.verify(mockDAO).contarPorPadre(idPruebaJornadaAulaAspiranteOpcion);
    }

    @Test
    public void buscarPorRangoIdPadreNullTest() {
        System.out.println("buscarPorRangoIdPadreNullTest");
        Response resultado = cut.buscarPorRango(null, 0, 50);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoFirstNegativoTest() {
        System.out.println("buscarPorRangoFirstNegativoTest");
        Response resultado = cut.buscarPorRango(idPruebaJornadaAulaAspiranteOpcion, -1, 10);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxCeroTest() {
        System.out.println("buscarPorRangoMaxCeroTest");
        Response resultado = cut.buscarPorRango(idPruebaJornadaAulaAspiranteOpcion, 0, 0);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxMayorACincuentaTest() {
        System.out.println("buscarPorRangoMaxMayorACincuentaTest");
        Response resultado = cut.buscarPorRango(idPruebaJornadaAulaAspiranteOpcion, 0, 51);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoConExcepcionTest() {
        System.out.println("buscarPorRangoConExcepcionTest");
        Mockito.when(mockDAO.buscarPorPadreRango(idPruebaJornadaAulaAspiranteOpcion, 0, 50))
                .thenThrow(new RuntimeException("DB error"));

        Response resultado = cut.buscarPorRango(idPruebaJornadaAulaAspiranteOpcion, 0, 50);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  GET {id} - buscarPorId
    // ============================================================

    @Test
    public void buscarPorIdExitosoTest() {
        System.out.println("buscarPorIdExitosoTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen encontrado =
                new PruebaJornadaAulaAspiranteOpcionExamen(id);
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(encontrado);

        Response resultado = cut.buscarPorId(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion);
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("buscarPorIdNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(null);

        Response resultado = cut.buscarPorId(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(404, resultado.getStatus());
    }

    @Test
    public void buscarPorIdNullTest() {
        System.out.println("buscarPorIdNullTest");
        Response resultado = cut.buscarPorId(idPruebaJornadaAulaAspiranteOpcion, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdIdPadreNullTest() {
        System.out.println("buscarPorIdIdPadreNullTest");
        Response resultado = cut.buscarPorId(null, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdConExcepcionTest() {
        System.out.println("buscarPorIdConExcepcionTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenThrow(new RuntimeException("DB error"));

        Response resultado = cut.buscarPorId(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  PUT - actualizar
    // ============================================================

    @Test
    public void actualizarExitosoTest() {
        System.out.println("actualizarExitosoTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(id);
        entity.setResultado(new BigDecimal("8.50"));

        PruebaJornadaAulaAspiranteOpcionExamen existente =
                new PruebaJornadaAulaAspiranteOpcionExamen(id);
        existente.setIdPruebaJornadaAulaAspiranteOpcion(
                new PruebaJornadaAulaAspiranteOpcion(idPruebaJornadaAulaAspiranteOpcion));

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(existente);
        Mockito.when(mockDAO.actualizar(entity)).thenReturn(entity);

        Response resultado = cut.actualizar(idPruebaJornadaAulaAspiranteOpcion, entity);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarEntityNullTest() {
        System.out.println("actualizarEntityNullTest");
        Response resultado = cut.actualizar(idPruebaJornadaAulaAspiranteOpcion, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarSinIdEnBodyTest() {
        System.out.println("actualizarSinIdEnBodyTest");
        Response resultado = cut.actualizar(idPruebaJornadaAulaAspiranteOpcion,
                new PruebaJornadaAulaAspiranteOpcionExamen());
        assertEquals(422, resultado.getStatus());
        assertEquals("El ID debe enviarse en el body",
                resultado.getHeaderString(ResponseHeaders.WRONG_PARAMETER.toString()));
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarIdPadreNullTest() {
        System.out.println("actualizarIdPadreNullTest");
        Response resultado = cut.actualizar(null,
                new PruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID()));
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarNoEncontradoTest() {
        System.out.println("actualizarNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(null);

        Response resultado = cut.actualizar(idPruebaJornadaAulaAspiranteOpcion,
                new PruebaJornadaAulaAspiranteOpcionExamen(id));

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarConExcepcionTest() {
        System.out.println("actualizarConExcepcionTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(id);

        PruebaJornadaAulaAspiranteOpcionExamen existente =
                new PruebaJornadaAulaAspiranteOpcionExamen(id);
        existente.setIdPruebaJornadaAulaAspiranteOpcion(
                new PruebaJornadaAulaAspiranteOpcion(idPruebaJornadaAulaAspiranteOpcion));

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(existente);
        Mockito.doThrow(new IllegalStateException("DB error")).when(mockDAO).actualizar(entity);

        Response resultado = cut.actualizar(idPruebaJornadaAulaAspiranteOpcion, entity);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  DELETE {id} - eliminar
    // ============================================================

    @Test
    public void eliminarExitosoTest() {
        System.out.println("eliminarExitosoTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen existente =
                new PruebaJornadaAulaAspiranteOpcionExamen(id);
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(existente);
        Mockito.doNothing().when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(204, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion);
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        System.out.println("eliminarNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(null);

        Response resultado = cut.eliminar(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarIdNullTest() {
        System.out.println("eliminarIdNullTest");
        Response resultado = cut.eliminar(idPruebaJornadaAulaAspiranteOpcion, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarIdPadreNullTest() {
        System.out.println("eliminarIdPadreNullTest");
        Response resultado = cut.eliminar(null, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarConExcepcionTest() {
        System.out.println("eliminarConExcepcionTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen existente =
                new PruebaJornadaAulaAspiranteOpcionExamen(id);
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaJornadaAulaAspiranteOpcion))
                .thenReturn(existente);
        Mockito.doThrow(new RuntimeException("DB error")).when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaJornadaAulaAspiranteOpcion, id);

        assertEquals(500, resultado.getStatus());
    }
}
