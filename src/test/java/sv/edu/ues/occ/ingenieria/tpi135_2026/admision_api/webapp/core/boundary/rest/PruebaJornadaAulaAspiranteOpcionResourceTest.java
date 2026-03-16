package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaAulaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

public class PruebaJornadaAulaAspiranteOpcionResourceTest {

    private UriInfo mockUriInfo;
    private PruebaJornadaAulaAspiranteOpcionDAO mockDAO;
    private PruebaJornadaDAO mockPJDAO;
    private JornadaAulaDAO mockJADAO;
    private AspiranteOpcionDAO mockAODAO;
    private PruebaJornadaAulaAspiranteOpcionResource cut;

    private UUID idPruebaJornada;
    private UUID idJornadaAula;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionDAO.class);
        mockPJDAO = Mockito.mock(PruebaJornadaDAO.class);
        mockJADAO = Mockito.mock(JornadaAulaDAO.class);
        mockAODAO = Mockito.mock(AspiranteOpcionDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build())
                .thenReturn(URI.create("http://localhost:8080/v1/prueba-jornada/1/jornada-aula/1/aspirante-opcion/1"));

        idPruebaJornada = UUID.randomUUID();
        idJornadaAula = UUID.randomUUID();

        cut = new PruebaJornadaAulaAspiranteOpcionResource();
        cut.pruebaJornadaAulaAspiranteOpcionDAO = mockDAO;
        cut.pruebaJornadaDAO = mockPJDAO;
        cut.jornadaAulaDAO = mockJADAO;
        cut.aspiranteOpcionDAO = mockAODAO;
    }

    // ============================================================
    //  POST - crear
    // ============================================================

    @Test
    public void crearExitosoTest() {
        System.out.println("crearExitosoTest");
        UUID idAO = UUID.randomUUID();
        AspiranteOpcion ao = new AspiranteOpcion(idAO);
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(ao);

        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(new PruebaJornada(idPruebaJornada));
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(new JornadaAula(idJornadaAula));
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(ao);
        Mockito.doNothing().when(mockDAO).crear(entity);

        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockPJDAO).buscarPorId(idPruebaJornada);
        Mockito.verify(mockJADAO).buscarPorId(idJornadaAula);
        Mockito.verify(mockAODAO).buscarPorId(idAO);
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearEntityNullTest() {
        System.out.println("crearEntityNullTest");
        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, null, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJDAO, mockJADAO, mockAODAO);
    }

    @Test
    public void crearIdPruebaJornadaNullTest() {
        System.out.println("crearIdPruebaJornadaNullTest");
        Response resultado = cut.crear(null, idJornadaAula, new PruebaJornadaAulaAspiranteOpcion(), mockUriInfo);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJDAO, mockJADAO, mockAODAO);
    }

    @Test
    public void crearIdJornadaAulaNullTest() {
        System.out.println("crearIdJornadaAulaNullTest");
        Response resultado = cut.crear(idPruebaJornada, null, new PruebaJornadaAulaAspiranteOpcion(), mockUriInfo);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJDAO, mockJADAO, mockAODAO);
    }

    @Test
    public void crearSinIdAspiranteOpcionTest() {
        System.out.println("crearSinIdAspiranteOpcionTest");
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        // idAspiranteOpcion is null inside entity
        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJDAO, mockJADAO, mockAODAO);
    }

    @Test
    public void crearAspiranteOpcionConUUIDNuloTest() {
        System.out.println("crearAspiranteOpcionConUUIDNuloTest");
        // outer idAspiranteOpcion object is non-null but its UUID is null → 422
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion()); // UUID inside is null
        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO, mockPJDAO, mockJADAO, mockAODAO);
    }

    @Test
    public void crearPruebaJornadaNoEncontradaTest() {
        System.out.println("crearPruebaJornadaNoEncontradaTest");
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(null);

        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockPJDAO).buscarPorId(idPruebaJornada);
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearJornadaAulaNoEncontradaTest() {
        System.out.println("crearJornadaAulaNoEncontradaTest");
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(new PruebaJornada(idPruebaJornada));
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(null);

        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockPJDAO).buscarPorId(idPruebaJornada);
        Mockito.verify(mockJADAO).buscarPorId(idJornadaAula);
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearAspiranteOpcionNoEncontradaTest() {
        System.out.println("crearAspiranteOpcionNoEncontradaTest");
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(new PruebaJornada(idPruebaJornada));
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(new JornadaAula(idJornadaAula));
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(null);

        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockAODAO).buscarPorId(idAO);
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearConExcepcionTest() {
        System.out.println("crearConExcepcionTest");
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(new PruebaJornada(idPruebaJornada));
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(new JornadaAula(idJornadaAula));
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(new AspiranteOpcion(idAO));
        Mockito.doThrow(new IllegalStateException("DB error")).when(mockDAO).crear(entity);

        Response resultado = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  GET list - buscarPorRango
    // ============================================================

    @Test
    public void buscarPorRangoExitosoTest() {
        System.out.println("buscarPorRangoExitosoTest");
        List<PruebaJornadaAulaAspiranteOpcion> registros =
                List.of(new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID()));
        Mockito.when(mockDAO.buscarPorPruebaJornadaYJornadaAulaRango(idPruebaJornada, idJornadaAula, 0, 50))
                .thenReturn(registros);
        Mockito.when(mockDAO.contarPorPruebaJornadaYJornadaAula(idPruebaJornada, idJornadaAula)).thenReturn(1L);

        Response resultado = cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 50);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorPruebaJornadaYJornadaAulaRango(idPruebaJornada, idJornadaAula, 0, 50);
        Mockito.verify(mockDAO).contarPorPruebaJornadaYJornadaAula(idPruebaJornada, idJornadaAula);
    }

    @Test
    public void buscarPorRangoFirstNegativoTest() {
        System.out.println("buscarPorRangoFirstNegativoTest");
        Response resultado = cut.buscarPorRango(idPruebaJornada, idJornadaAula, -1, 10);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxCeroTest() {
        System.out.println("buscarPorRangoMaxCeroTest");
        Response resultado = cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 0);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxMayorACincuentaTest() {
        System.out.println("buscarPorRangoMaxMayorACincuentaTest");
        Response resultado = cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 51);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoIdPruebaJornadaNullTest() {
        System.out.println("buscarPorRangoIdPruebaJornadaNullTest");
        Response resultado = cut.buscarPorRango(null, idJornadaAula, 0, 50);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoIdJornadaAulaNullTest() {
        System.out.println("buscarPorRangoIdJornadaAulaNullTest");
        Response resultado = cut.buscarPorRango(idPruebaJornada, null, 0, 50);
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoConExcepcionTest() {
        System.out.println("buscarPorRangoConExcepcionTest");
        Mockito.when(mockDAO.buscarPorPruebaJornadaYJornadaAulaRango(idPruebaJornada, idJornadaAula, 0, 50))
                .thenThrow(new RuntimeException("DB error"));

        Response resultado = cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 50);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  GET {id} - buscarPorId
    // ============================================================

    @Test
    public void buscarPorIdExitosoTest() {
        System.out.println("buscarPorIdExitosoTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion encontrado = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(encontrado);

        Response resultado = cut.buscarPorId(idPruebaJornada, idJornadaAula, id);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula);
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("buscarPorIdNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(null);

        Response resultado = cut.buscarPorId(idPruebaJornada, idJornadaAula, id);

        assertEquals(404, resultado.getStatus());
    }

    @Test
    public void buscarPorIdNullTest() {
        System.out.println("buscarPorIdNullTest");
        Response resultado = cut.buscarPorId(idPruebaJornada, idJornadaAula, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdIdPruebaJornadaNullTest() {
        System.out.println("buscarPorIdIdPruebaJornadaNullTest");
        Response resultado = cut.buscarPorId(null, idJornadaAula, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdIdJornadaAulaNullTest() {
        System.out.println("buscarPorIdIdJornadaAulaNullTest");
        Response resultado = cut.buscarPorId(idPruebaJornada, null, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdConExcepcionTest() {
        System.out.println("buscarPorIdConExcepcionTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenThrow(new RuntimeException("DB error"));

        Response resultado = cut.buscarPorId(idPruebaJornada, idJornadaAula, id);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  PUT - actualizar
    // ============================================================

    @Test
    public void actualizarExitosoTest() {
        System.out.println("actualizarExitosoTest");
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));
        existente.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(new AspiranteOpcion(idAO));
        Mockito.when(mockDAO.actualizar(entity)).thenReturn(entity);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, entity);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula);
        Mockito.verify(mockAODAO).buscarPorId(idAO);
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarSinCambiarAspiranteOpcionTest() {
        System.out.println("actualizarSinCambiarAspiranteOpcionTest");
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        // entity sin idAspiranteOpcion → debe conservar el del existente

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));
        existente.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.when(mockDAO.actualizar(entity)).thenReturn(entity);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, entity);

        assertEquals(200, resultado.getStatus());
        assertEquals(existente.getIdAspiranteOpcion(), entity.getIdAspiranteOpcion());
        Mockito.verify(mockAODAO, Mockito.never()).buscarPorId(Mockito.any());
    }

    @Test
    public void actualizarNoEncontradoTest() {
        System.out.println("actualizarNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(null);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, new PruebaJornadaAulaAspiranteOpcion(id));

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarAspiranteOpcionNoEncontradaTest() {
        System.out.println("actualizarAspiranteOpcionNoEncontradaTest");
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(null);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, entity);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarEntityNullTest() {
        System.out.println("actualizarEntityNullTest");
        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarSinIdEnBodyTest() {
        System.out.println("actualizarSinIdEnBodyTest");
        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, new PruebaJornadaAulaAspiranteOpcion());
        assertEquals(422, resultado.getStatus());
        assertEquals("El ID debe enviarse en el body",
                resultado.getHeaderString(ResponseHeaders.WRONG_PARAMETER.toString()));
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarIdPruebaJornadaNullTest() {
        System.out.println("actualizarIdPruebaJornadaNullTest");
        Response resultado = cut.actualizar(null, idJornadaAula, new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID()));
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarIdJornadaAulaNullTest() {
        System.out.println("actualizarIdJornadaAulaNullTest");
        Response resultado = cut.actualizar(idPruebaJornada, null, new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID()));
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarAspiranteOpcionConUUIDNuloTest() {
        System.out.println("actualizarAspiranteOpcionConUUIDNuloTest");
        // outer idAspiranteOpcion non-null but its UUID is null → else branch → preserve existing
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        entity.setIdAspiranteOpcion(new AspiranteOpcion()); // UUID inside is null

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));
        existente.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.when(mockDAO.actualizar(entity)).thenReturn(entity);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, entity);

        assertEquals(200, resultado.getStatus());
        assertEquals(existente.getIdAspiranteOpcion(), entity.getIdAspiranteOpcion());
        Mockito.verify(mockAODAO, Mockito.never()).buscarPorId(Mockito.any());
    }

    @Test
    public void actualizarConExcepcionTest() {
        System.out.println("actualizarConExcepcionTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));
        existente.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.doThrow(new IllegalStateException("DB error")).when(mockDAO).actualizar(entity);

        Response resultado = cut.actualizar(idPruebaJornada, idJornadaAula, entity);

        assertEquals(500, resultado.getStatus());
    }

    // ============================================================
    //  DELETE {id} - eliminar
    // ============================================================

    @Test
    public void eliminarExitosoTest() {
        System.out.println("eliminarExitosoTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.doNothing().when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaJornada, idJornadaAula, id);

        assertEquals(204, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula);
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        System.out.println("eliminarNoEncontradoTest");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(null);

        Response resultado = cut.eliminar(idPruebaJornada, idJornadaAula, id);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarIdNullTest() {
        System.out.println("eliminarIdNullTest");
        Response resultado = cut.eliminar(idPruebaJornada, idJornadaAula, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarIdPruebaJornadaNullTest() {
        System.out.println("eliminarIdPruebaJornadaNullTest");
        Response resultado = cut.eliminar(null, idJornadaAula, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarIdJornadaAulaNullTest() {
        System.out.println("eliminarIdJornadaAulaNullTest");
        Response resultado = cut.eliminar(idPruebaJornada, null, UUID.randomUUID());
        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarConExcepcionTest() {
        System.out.println("eliminarConExcepcionTest");
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(existente);
        Mockito.doThrow(new RuntimeException("DB error")).when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaJornada, idJornadaAula, id);

        assertEquals(500, resultado.getStatus());
    }
}
