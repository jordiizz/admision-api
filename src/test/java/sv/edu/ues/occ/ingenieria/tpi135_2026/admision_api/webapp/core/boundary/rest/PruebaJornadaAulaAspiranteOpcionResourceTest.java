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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
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
        Mockito.when(mockUriBuilder.build()).thenReturn(URI.create("http://localhost/1"));

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
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        UUID idJornadaCompartida = UUID.randomUUID();
        PruebaJornada pj = new PruebaJornada(idPruebaJornada);
        pj.setIdJornada(new Jornada(idJornadaCompartida));
        JornadaAula ja = new JornadaAula(idJornadaAula);
        ja.setIdJornada(new Jornada(idJornadaCompartida));

        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(pj);
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(ja);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(new AspiranteOpcion(idAO));

        Response res = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(201, res.getStatus());
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearValidacionFallidaTest() {
        assertEquals(422, cut.crear(null, idJornadaAula, new PruebaJornadaAulaAspiranteOpcion(), mockUriInfo).getStatus());
        assertEquals(422, cut.crear(idPruebaJornada, null, new PruebaJornadaAulaAspiranteOpcion(), mockUriInfo).getStatus());

        assertEquals(422, cut.crear(idPruebaJornada, idJornadaAula, null, mockUriInfo).getStatus());

        // AspiranteOpcion nulo o con ID interno nulo
        PruebaJornadaAulaAspiranteOpcion entitySinAO = new PruebaJornadaAulaAspiranteOpcion();
        assertEquals(422, cut.crear(idPruebaJornada, idJornadaAula, entitySinAO, mockUriInfo).getStatus());

        PruebaJornadaAulaAspiranteOpcion entityConAOSinId = new PruebaJornadaAulaAspiranteOpcion();
        entityConAOSinId.setIdAspiranteOpcion(new AspiranteOpcion());
        assertEquals(422, cut.crear(idPruebaJornada, idJornadaAula, entityConAOSinId, mockUriInfo).getStatus());
    }

    @Test
    public void crearJornadasNoCoincidenODesconocidasTest() {
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));

        PruebaJornada pj = new PruebaJornada(idPruebaJornada);
        JornadaAula ja = new JornadaAula(idJornadaAula);

        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(pj);
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(ja);
        Mockito.when(mockAODAO.buscarPorId(Mockito.any())).thenReturn(new AspiranteOpcion());

        // Escenario 1: aula.getIdJornada() == null
        Response res1 = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(400, res1.getStatus());

        // Escenario 2: aula SÍ tiene jornada, pero prueba.getIdJornada() 
        ja.setIdJornada(new Jornada(UUID.randomUUID()));
        Response res2 = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(400, res2.getStatus());

        // Escenario 3: Ambas tienen jornada, pero los IDs son distintos
        pj.setIdJornada(new Jornada(UUID.randomUUID())); // pj ahora tiene, pero distinta a ja
        Response res3 = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);
        assertEquals(400, res3.getStatus());
    }

    @Test
    public void crearConflictoDePertenenciaJornadaTest() {
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        PruebaJornada pj = new PruebaJornada(idPruebaJornada);
        pj.setIdJornada(new Jornada(UUID.randomUUID()));

        JornadaAula ja = new JornadaAula(idJornadaAula);
        ja.setIdJornada(new Jornada(UUID.randomUUID()));

        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(pj);
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(ja);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(new AspiranteOpcion(idAO));

        Response res = cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo);

        assertEquals(400, res.getStatus());
        assertEquals("Conflicto de Jornada", res.getHeaderString(ResponseHeaders.WRONG_PARAMETER.toString()));
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearNoEncontradosTest() {
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));

        // 1. Falta prueba
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(null);
        assertEquals(404, cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo).getStatus());

        // 2. Falta aula (pero prueba sí está)
        Mockito.when(mockPJDAO.buscarPorId(idPruebaJornada)).thenReturn(new PruebaJornada());
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(null);
        assertEquals(404, cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo).getStatus());

        // 3. Falta aspirante (pero prueba y aula sí están)
        Mockito.when(mockJADAO.buscarPorId(idJornadaAula)).thenReturn(new JornadaAula());
        Mockito.when(mockAODAO.buscarPorId(Mockito.any())).thenReturn(null);
        assertEquals(404, cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo).getStatus());
    }

    @Test
    public void crearExcepcionTest() {
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion();
        entity.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));

        Mockito.when(mockPJDAO.buscarPorId(Mockito.any())).thenThrow(new RuntimeException("Error BD"));
        assertEquals(500, cut.crear(idPruebaJornada, idJornadaAula, entity, mockUriInfo).getStatus());
    }

    // ============================================================
    //  GET - buscarPorRango
    // ============================================================

    @Test
    public void buscarPorRangoExitosoTest() {
        Mockito.when(mockDAO.buscarPorPruebaJornadaYJornadaAulaRango(idPruebaJornada, idJornadaAula, 0, 50)).thenReturn(List.of());
        Mockito.when(mockDAO.contarPorPruebaJornadaYJornadaAula(idPruebaJornada, idJornadaAula)).thenReturn(0L);
        assertEquals(200, cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 50).getStatus());
    }

    @Test
    public void buscarPorRangoValidacionFallidaTest() {
        assertEquals(422, cut.buscarPorRango(null, idJornadaAula, 0, 50).getStatus()); // idPrueba nulo
        assertEquals(422, cut.buscarPorRango(idPruebaJornada, null, 0, 50).getStatus()); // idAula nulo
        assertEquals(422, cut.buscarPorRango(idPruebaJornada, idJornadaAula, -1, 50).getStatus()); // first negativo
        assertEquals(422, cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 0).getStatus()); // max cero
        assertEquals(422, cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 51).getStatus()); // max mayor a 50
    }

    @Test
    public void buscarPorRangoExcepcionTest() {
        Mockito.when(mockDAO.buscarPorPruebaJornadaYJornadaAulaRango(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Error"));
        assertEquals(500, cut.buscarPorRango(idPruebaJornada, idJornadaAula, 0, 50).getStatus());
    }

    // ============================================================
    //  GET {id} - buscarPorId
    // ============================================================

    @Test
    public void buscarPorIdExitosoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(new PruebaJornadaAulaAspiranteOpcion(id));
        assertEquals(200, cut.buscarPorId(idPruebaJornada, idJornadaAula, id).getStatus());
    }

    @Test
    public void buscarPorIdValidacionFallidaTest() {
        assertEquals(422, cut.buscarPorId(null, idJornadaAula, UUID.randomUUID()).getStatus());
        assertEquals(422, cut.buscarPorId(idPruebaJornada, null, UUID.randomUUID()).getStatus());
        assertEquals(422, cut.buscarPorId(idPruebaJornada, idJornadaAula, null).getStatus());
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula))
                .thenReturn(null);
        assertEquals(404, cut.buscarPorId(idPruebaJornada, idJornadaAula, id).getStatus());
    }

    @Test
    public void buscarPorIdExcepcionTest() {
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("DB Error"));
        assertEquals(500, cut.buscarPorId(idPruebaJornada, idJornadaAula, UUID.randomUUID()).getStatus());
    }

    // ============================================================
    //  PUT - actualizar
    // ============================================================

    @Test
    public void actualizarExitosoTest() {
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdPruebaJornada(new PruebaJornada(idPruebaJornada));
        existente.setIdJornadaAula(new JornadaAula(idJornadaAula));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(existente);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(new AspiranteOpcion(idAO));

        Response res = cut.actualizar(idPruebaJornada, idJornadaAula, entity.getIdPruebaJornadaAulaAspiranteOpcion(), entity);
        assertEquals(200, res.getStatus());
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarPreservarAspiranteOpcionTest() {
        UUID id = UUID.randomUUID();

        // Escenario 1: El body tiene AspiranteOpcion pero el ID interno es nulo
        PruebaJornadaAulaAspiranteOpcion entity1 = new PruebaJornadaAulaAspiranteOpcion(id);
        entity1.setIdAspiranteOpcion(new AspiranteOpcion());

        // Escenario 2: El body viene con AspiranteOpcion totalmente en nulo
        PruebaJornadaAulaAspiranteOpcion entity2 = new PruebaJornadaAulaAspiranteOpcion(id);
        entity2.setIdAspiranteOpcion(null);

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        existente.setIdAspiranteOpcion(new AspiranteOpcion(UUID.randomUUID()));

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(existente);

        // Prueba Escenario 1
        Response res1 = cut.actualizar(idPruebaJornada, idJornadaAula, entity1.getIdPruebaJornadaAulaAspiranteOpcion(), entity1);
        assertEquals(200, res1.getStatus());
        assertNotNull(entity1.getIdAspiranteOpcion().getIdAspiranteOpcion());

        // Prueba Escenario 2
        Response res2 = cut.actualizar(idPruebaJornada, idJornadaAula, entity2.getIdPruebaJornadaAulaAspiranteOpcion(), entity2);
        assertEquals(200, res2.getStatus());
        assertNotNull(entity2.getIdAspiranteOpcion().getIdAspiranteOpcion());
    }

    @Test
    public void actualizarValidacionFallidaTest() {
        assertEquals(422, cut.actualizar(null, idJornadaAula, UUID.randomUUID(), new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID())).getStatus());
        assertEquals(422, cut.actualizar(idPruebaJornada, null, UUID.randomUUID(), new PruebaJornadaAulaAspiranteOpcion(UUID.randomUUID())).getStatus());
        assertEquals(422, cut.actualizar(idPruebaJornada, idJornadaAula, UUID.randomUUID(), null).getStatus());
        assertEquals(422, cut.actualizar(idPruebaJornada, idJornadaAula, null, new PruebaJornadaAulaAspiranteOpcion()).getStatus());
    }

    @Test
    public void actualizarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(null);

        assertEquals(404, cut.actualizar(idPruebaJornada, idJornadaAula, entity.getIdPruebaJornadaAulaAspiranteOpcion(), entity).getStatus());
    }

    @Test
    public void actualizarAspiranteOpcionNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        UUID idAO = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        entity.setIdAspiranteOpcion(new AspiranteOpcion(idAO));

        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);

        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(existente);
        Mockito.when(mockAODAO.buscarPorId(idAO)).thenReturn(null); // Retorna nulo, detona el 404 interno

        assertEquals(404, cut.actualizar(idPruebaJornada, idJornadaAula, entity.getIdPruebaJornadaAulaAspiranteOpcion(), entity).getStatus());
    }

    @Test
    public void actualizarExcepcionTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion entity = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("DB Error"));
        assertEquals(500, cut.actualizar(idPruebaJornada, idJornadaAula, entity.getIdPruebaJornadaAulaAspiranteOpcion(), entity).getStatus());
    }

    // ============================================================
    //  DELETE - eliminar
    // ============================================================

    @Test
    public void eliminarExitosoTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcion existente = new PruebaJornadaAulaAspiranteOpcion(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(existente);

        assertEquals(204, cut.eliminar(idPruebaJornada, idJornadaAula, id).getStatus());
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarValidacionFallidaTest() {
        assertEquals(422, cut.eliminar(null, idJornadaAula, UUID.randomUUID()).getStatus());
        assertEquals(422, cut.eliminar(idPruebaJornada, null, UUID.randomUUID()).getStatus());
        assertEquals(422, cut.eliminar(idPruebaJornada, idJornadaAula, null).getStatus());
    }

    @Test
    public void eliminarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPruebaJornada, idJornadaAula)).thenReturn(null);
        assertEquals(404, cut.eliminar(idPruebaJornada, idJornadaAula, id).getStatus());
    }

    @Test
    public void eliminarExcepcionTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaJornadaYJornadaAula(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error"));
        assertEquals(500, cut.eliminar(idPruebaJornada, idJornadaAula, id).getStatus());
    }
}