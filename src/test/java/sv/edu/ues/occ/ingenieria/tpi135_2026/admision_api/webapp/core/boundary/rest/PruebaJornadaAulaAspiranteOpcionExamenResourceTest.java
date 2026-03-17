package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private PruebaJornadaAulaAspiranteOpcionDAO mockPrubaOpcionDA0;
    private PruebaJornadaAulaAspiranteOpcionExamenResource cut;
    private UUID idPadre;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionExamenDAO.class);
        mockPrubaOpcionDA0 = Mockito.mock(PruebaJornadaAulaAspiranteOpcionDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build()).thenReturn(URI.create("http://localhost/1"));

        idPadre = UUID.randomUUID();
        cut = new PruebaJornadaAulaAspiranteOpcionExamenResource();
        cut.pruebaJornadaAulaAspiranteOpcionExamenDAO = mockDAO;
        cut.pruebaJornadaAulaAspiranteOpcionDAO = mockPrubaOpcionDA0;
    }

    // ============================================================
    //  POST - crear
    // ============================================================

    @Test
    public void crearExitosoTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("9.5"));

        Mockito.when(mockPrubaOpcionDA0.buscarPorId(idPadre)).thenReturn(new PruebaJornadaAulaAspiranteOpcion(idPadre));

        Response res = cut.crear(idPadre, entity, mockUriInfo);
        assertEquals(201, res.getStatus());
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearValidacionFallidaTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entityValida = new PruebaJornadaAulaAspiranteOpcionExamen();
        entityValida.setResultado(BigDecimal.TEN);

        PruebaJornadaAulaAspiranteOpcionExamen entitySinResultado = new PruebaJornadaAulaAspiranteOpcionExamen();

        // idPadre null
        assertEquals(422, cut.crear(null, entityValida, mockUriInfo).getStatus());
        // entity null
        assertEquals(422, cut.crear(idPadre, null, mockUriInfo).getStatus());
        // entity.getResultado() null
        assertEquals(422, cut.crear(idPadre, entitySinResultado, mockUriInfo).getStatus());
    }

    @Test
    public void crearPadreNoEncontradoTest() {
        Mockito.when(mockPrubaOpcionDA0.buscarPorId(idPadre)).thenReturn(null);
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(BigDecimal.TEN);

        Response res = cut.crear(idPadre, entity, mockUriInfo);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void crearExcepcionTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(BigDecimal.TEN);

        Mockito.when(mockPrubaOpcionDA0.buscarPorId(Mockito.any())).thenThrow(new RuntimeException("Error BD"));
        Response res = cut.crear(idPadre, entity, mockUriInfo);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  GET - buscarPorRango
    // ============================================================

    @Test
    public void buscarPorRangoExitosoTest() {
        Mockito.when(mockDAO.buscarPorPadreRango(idPadre, 0, 50)).thenReturn(List.of());
        Mockito.when(mockDAO.contarPorPadre(idPadre)).thenReturn(0L);

        Response res = cut.buscarPorRango(idPadre, 0, 50);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void buscarPorRangoValidacionFallidaTest() {
        // Evaluar cada OR por separado
        assertEquals(422, cut.buscarPorRango(null, 0, 50).getStatus()); // idPadre == null
        assertEquals(422, cut.buscarPorRango(idPadre, -1, 50).getStatus()); // first < 0
        assertEquals(422, cut.buscarPorRango(idPadre, 0, 0).getStatus()); // max <= 0
        assertEquals(422, cut.buscarPorRango(idPadre, 0, 51).getStatus()); // max > 50
    }

    @Test
    public void buscarPorRangoExcepcionTest() {
        Mockito.when(mockDAO.buscarPorPadreRango(Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.buscarPorRango(idPadre, 0, 50);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  GET {id} - buscarPorId
    // ============================================================

    @Test
    public void buscarPorIdExitosoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(new PruebaJornadaAulaAspiranteOpcionExamen(id));

        Response res = cut.buscarPorId(idPadre, id);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(null);

        Response res = cut.buscarPorId(idPadre, id);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void buscarPorIdValidacionFallidaTest() {
        UUID id = UUID.randomUUID();
        assertEquals(422, cut.buscarPorId(null, id).getStatus());
        assertEquals(422, cut.buscarPorId(idPadre, null).getStatus());
    }

    @Test
    public void buscarPorIdExcepcionTest() {
        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.buscarPorId(idPadre, UUID.randomUUID());
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  PUT - actualizar
    // ============================================================

    @Test
    public void actualizarExitosoTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(id);
        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen(id);

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(existente);

        Response res = cut.actualizar(idPadre, entity.getIdPruebaJornadaAulaAspiranteOpcionExamen(), entity);
        assertEquals(200, res.getStatus());
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarValidacionFallidaTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entityValida = new PruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID());
        PruebaJornadaAulaAspiranteOpcionExamen entitySinId = new PruebaJornadaAulaAspiranteOpcionExamen();

        assertEquals(422, cut.actualizar(null, null, entityValida).getStatus()); // idPadre null
        assertEquals(422, cut.actualizar(idPadre, UUID.randomUUID(), null).getStatus()); // entity null
        assertEquals(422, cut.actualizar(idPadre, null, entitySinId).getStatus()); // Sin ID en body
    }

    @Test
    public void actualizarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(null);

        Response res = cut.actualizar(idPadre, id, new PruebaJornadaAulaAspiranteOpcionExamen(id));
        assertEquals(404, res.getStatus());
    }

    @Test
    public void actualizarExcepcionTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(id);

        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.actualizar(idPadre, entity.getIdPruebaJornadaAulaAspiranteOpcionExamen(), entity);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  DELETE - eliminar
    // ============================================================

    @Test
    public void eliminarExitosoTest() {
        UUID id = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen(id);
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(existente);

        Response res = cut.eliminar(idPadre, id);
        assertEquals(204, res.getStatus());
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarValidacionFallidaTest() {
        UUID id = UUID.randomUUID();
        assertEquals(422, cut.eliminar(null, id).getStatus());
        assertEquals(422, cut.eliminar(idPadre, null).getStatus());
    }

    @Test
    public void eliminarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPadre)).thenReturn(null);

        Response res = cut.eliminar(idPadre, id);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void eliminarExcepcionTest() {
        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.eliminar(idPadre, UUID.randomUUID());
        assertEquals(500, res.getStatus());
    }
}