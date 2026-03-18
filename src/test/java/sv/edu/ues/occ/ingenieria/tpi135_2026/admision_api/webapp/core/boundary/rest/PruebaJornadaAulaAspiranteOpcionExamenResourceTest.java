package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

public class PruebaJornadaAulaAspiranteOpcionExamenResourceTest {

    private UriInfo mockUriInfo;
    private PruebaJornadaAulaAspiranteOpcionExamenDAO mockDAO;
    private PruebaJornadaAulaAspiranteOpcionDAO mockPrubaOpcionDA0;
    private PruebaClaveDAO mockPruebaClaveDAO;
    private PruebaJornadaAulaAspiranteOpcionExamenResource cut;
    private UUID idPrueba;
    private UUID idJornada;
    private String idAula;
    private UUID idAspiranteOpcion;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaJornadaAulaAspiranteOpcionExamenDAO.class);
        mockPrubaOpcionDA0 = Mockito.mock(PruebaJornadaAulaAspiranteOpcionDAO.class);
        mockPruebaClaveDAO = Mockito.mock(PruebaClaveDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build()).thenReturn(URI.create("http://localhost/1"));

        idPrueba = UUID.randomUUID();
        idJornada = UUID.randomUUID();
        idAula = "A-01";
        idAspiranteOpcion = UUID.randomUUID();
        cut = new PruebaJornadaAulaAspiranteOpcionExamenResource();
        cut.pruebaJornadaAulaAspiranteOpcionExamenDAO = mockDAO;
        cut.pruebaJornadaAulaAspiranteOpcionDAO = mockPrubaOpcionDA0;
        cut.pruebaClaveDAO = mockPruebaClaveDAO;
    }

    // ============================================================
    //  POST - crear
    // ============================================================

    @Test
    public void crearExitosoTest() {
        UUID idPruebaClave = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(new BigDecimal("9.5"));
        entity.setIdPruebaClave(idPruebaClave);

        Mockito.when(mockPrubaOpcionDA0.buscarPorIdYPruebaJornadaYJornadaAula(idAspiranteOpcion, idPrueba, idJornada, idAula))
                .thenReturn(new PruebaJornadaAulaAspiranteOpcion(idPrueba, idJornada, idAula, idAspiranteOpcion));
        Mockito.when(mockPruebaClaveDAO.buscarPorId(idPruebaClave)).thenReturn(new PruebaClave(idPruebaClave));

        Response res = cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, entity, mockUriInfo);
        assertEquals(201, res.getStatus());
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearValidacionFallidaTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entityValida = new PruebaJornadaAulaAspiranteOpcionExamen();
        entityValida.setResultado(BigDecimal.TEN);
        entityValida.setIdPruebaClave(UUID.randomUUID());

        PruebaJornadaAulaAspiranteOpcionExamen entitySinResultado = new PruebaJornadaAulaAspiranteOpcionExamen();
        entitySinResultado.setIdPruebaClave(UUID.randomUUID());

        assertEquals(422, cut.crear(null, idJornada, idAula, idAspiranteOpcion, entityValida, mockUriInfo).getStatus());
        // entity null
        assertEquals(422, cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, null, mockUriInfo).getStatus());
        // entity.getResultado() null
        assertEquals(422, cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, entitySinResultado, mockUriInfo).getStatus());
    }

    @Test
    public void crearPadreNoEncontradoTest() {
        Mockito.when(mockPrubaOpcionDA0.buscarPorIdYPruebaJornadaYJornadaAula(idAspiranteOpcion, idPrueba, idJornada, idAula)).thenReturn(null);
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(BigDecimal.TEN);
        entity.setIdPruebaClave(UUID.randomUUID());

        Response res = cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, entity, mockUriInfo);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void crearDuplicadoTest() {
        UUID idPruebaClave = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(BigDecimal.TEN);
        entity.setIdPruebaClave(idPruebaClave);

        PruebaJornadaAulaAspiranteOpcion padre = new PruebaJornadaAulaAspiranteOpcion(idPrueba, idJornada, idAula, idAspiranteOpcion);
        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);

        Mockito.when(mockPrubaOpcionDA0.buscarPorIdYPruebaJornadaYJornadaAula(idAspiranteOpcion, idPrueba, idJornada, idAula))
                .thenReturn(padre);
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(existente);

        Response res = cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, entity, mockUriInfo);
        assertEquals(409, res.getStatus());
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearExcepcionTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen();
        entity.setResultado(BigDecimal.TEN);
        entity.setIdPruebaClave(UUID.randomUUID());

        Mockito.when(mockPrubaOpcionDA0.buscarPorIdYPruebaJornadaYJornadaAula(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));
        Response res = cut.crear(idPrueba, idJornada, idAula, idAspiranteOpcion, entity, mockUriInfo);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  GET - buscarPorPadre
    // ============================================================

    @Test
    public void buscarPorPadreExitosoTest() {
        PruebaJornadaAulaAspiranteOpcionExamen encontrado = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(encontrado);

        Response res = cut.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void buscarPorPadreNoEncontradoTest() {
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(null);

        Response res = cut.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void buscarPorPadreValidacionFallidaTest() {
        assertEquals(422, cut.buscarPorPadre(null, idJornada, idAula, idAspiranteOpcion).getStatus());
    }

    @Test
    public void buscarPorPadreExcepcionTest() {
        Mockito.when(mockDAO.buscarPorPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  PUT - actualizar
    // ============================================================

    @Test
    public void actualizarExitosoTest() {
        UUID idPruebaClave = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        entity.setIdPruebaClave(idPruebaClave);
        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        existente.setIdPruebaClave(idPruebaClave);

        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(existente);
        Mockito.when(mockPruebaClaveDAO.buscarPorId(idPruebaClave)).thenReturn(new PruebaClave(idPruebaClave));

        Response res = cut.actualizar(idPrueba, idJornada, idAula, idAspiranteOpcion, entity);
        assertEquals(200, res.getStatus());
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarValidacionFallidaTest() {
        PruebaJornadaAulaAspiranteOpcionExamen entityValida = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        PruebaJornadaAulaAspiranteOpcionExamen entitySinId = new PruebaJornadaAulaAspiranteOpcionExamen();

        assertEquals(422, cut.actualizar(null, idJornada, idAula, idAspiranteOpcion, entityValida).getStatus());
        assertEquals(422, cut.actualizar(idPrueba, idJornada, idAula, idAspiranteOpcion, null).getStatus());
        assertEquals(404, cut.actualizar(idPrueba, idJornada, idAula, idAspiranteOpcion, entitySinId).getStatus());
    }

    @Test
    public void actualizarNoEncontradoTest() {
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(null);

        Response res = cut.actualizar(idPrueba, idJornada, idAula, idAspiranteOpcion, new PruebaJornadaAulaAspiranteOpcionExamen());
        assertEquals(404, res.getStatus());
    }

    @Test
    public void actualizarExcepcionTest() {
        UUID idPruebaClave = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen entity = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        entity.setIdPruebaClave(idPruebaClave);

        Mockito.when(mockDAO.buscarPorPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.actualizar(idPrueba, idJornada, idAula, idAspiranteOpcion, entity);
        assertEquals(500, res.getStatus());
    }

    // ============================================================
    //  DELETE - eliminar
    // ============================================================

    @Test
    public void eliminarExitosoTest() {
        UUID idPruebaClave = UUID.randomUUID();
        PruebaJornadaAulaAspiranteOpcionExamen existente = new PruebaJornadaAulaAspiranteOpcionExamen(idPrueba, idJornada, idAula, idAspiranteOpcion);
        existente.setIdPruebaClave(idPruebaClave);
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(existente);

        Response res = cut.eliminar(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(204, res.getStatus());
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarValidacionFallidaTest() {
        assertEquals(422, cut.eliminar(null, idJornada, idAula, idAspiranteOpcion).getStatus());
    }

    @Test
    public void eliminarNoEncontradoTest() {
        Mockito.when(mockDAO.buscarPorPadre(idPrueba, idJornada, idAula, idAspiranteOpcion)).thenReturn(null);

        Response res = cut.eliminar(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void eliminarExcepcionTest() {
        Mockito.when(mockDAO.buscarPorPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.eliminar(idPrueba, idJornada, idAula, idAspiranteOpcion);
        assertEquals(500, res.getStatus());
    }
}