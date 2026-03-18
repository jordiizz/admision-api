package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaDistractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaPK;

public class PruebaClaveAreaPreguntaDistractorResourceTest {

    private UriInfo mockUriInfo;
    private PruebaClaveAreaPreguntaDistractorDAO mockDAO;
    private PruebaClaveAreaPreguntaDAO mockPruebaClaveAreaPreguntaDAO;
    private DistractorDAO mockDistractorDAO;
    private PruebaClaveAreaPreguntaDistractorResource cut;
    private UUID idPruebaClave;
    private UUID idArea;
    private UUID idPregunta;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaClaveAreaPreguntaDistractorDAO.class);
        mockPruebaClaveAreaPreguntaDAO = Mockito.mock(PruebaClaveAreaPreguntaDAO.class);
        mockDistractorDAO = Mockito.mock(DistractorDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build()).thenReturn(URI.create("http://localhost/1"));

        idPruebaClave = UUID.randomUUID();
        idArea = UUID.randomUUID();
        idPregunta = UUID.randomUUID();
        cut = new PruebaClaveAreaPreguntaDistractorResource();
        cut.pruebaClaveAreaPreguntaDistractorDAO = mockDAO;
        cut.pruebaClaveAreaPreguntaDAO = mockPruebaClaveAreaPreguntaDAO;
        cut.distractorDAO = mockDistractorDAO;
    }

    @Test
    public void crearExitosoTest() {
        UUID idDistractor = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        Mockito.when(mockPruebaClaveAreaPreguntaDAO.buscarPorId(new PruebaClaveAreaPreguntaPK(idPruebaClave, idArea, idPregunta)))
            .thenReturn(new PruebaClaveAreaPregunta(idPruebaClave, idArea, idPregunta));
        Mockito.when(mockDistractorDAO.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response res = cut.crear(idPruebaClave, idArea, idPregunta, entity, mockUriInfo);
        assertEquals(201, res.getStatus());
        Mockito.verify(mockDAO).crear(entity);
    }

    @Test
    public void crearValidacionFallidaTest() {
        PruebaClaveAreaPreguntaDistractor entityValida = new PruebaClaveAreaPreguntaDistractor();
        entityValida.setIdDistractor(UUID.randomUUID());

        PruebaClaveAreaPreguntaDistractor entitySinDistractor = new PruebaClaveAreaPreguntaDistractor();

        assertEquals(422, cut.crear(null, idArea, idPregunta, entityValida, mockUriInfo).getStatus());
        assertEquals(422, cut.crear(idPruebaClave, idArea, idPregunta, null, mockUriInfo).getStatus());
        assertEquals(422, cut.crear(idPruebaClave, idArea, idPregunta, entitySinDistractor, mockUriInfo).getStatus());

        PruebaClaveAreaPreguntaDistractor entityDistractorSinId = new PruebaClaveAreaPreguntaDistractor();
        entityDistractorSinId.setIdDistractor(null);
        assertEquals(422, cut.crear(idPruebaClave, idArea, idPregunta, entityDistractorSinId, mockUriInfo).getStatus());
    }

    @Test
    public void crearPadreNoEncontradoTest() {
        UUID idDistractor = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        Mockito.when(mockPruebaClaveAreaPreguntaDAO.buscarPorId(new PruebaClaveAreaPreguntaPK(idPruebaClave, idArea, idPregunta))).thenReturn(null);

        Response res = cut.crear(idPruebaClave, idArea, idPregunta, entity, mockUriInfo);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void crearDistractorNoEncontradoTest() {
        UUID idDistractor = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        Mockito.when(mockPruebaClaveAreaPreguntaDAO.buscarPorId(new PruebaClaveAreaPreguntaPK(idPruebaClave, idArea, idPregunta)))
            .thenReturn(new PruebaClaveAreaPregunta(idPruebaClave, idArea, idPregunta));
        Mockito.when(mockDistractorDAO.buscarPorId(idDistractor)).thenReturn(null);

        Response res = cut.crear(idPruebaClave, idArea, idPregunta, entity, mockUriInfo);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void crearExcepcionTest() {
        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(UUID.randomUUID());

        Mockito.when(mockPruebaClaveAreaPreguntaDAO.buscarPorId(Mockito.any(PruebaClaveAreaPreguntaPK.class)))
                .thenThrow(new RuntimeException("Error BD"));
        Response res = cut.crear(idPruebaClave, idArea, idPregunta, entity, mockUriInfo);
        assertEquals(500, res.getStatus());
    }

    @Test
    public void buscarPorRangoExitosoTest() {
        Mockito.when(mockDAO.buscarPorPadreRango(idPruebaClave, idArea, idPregunta, 0, 50)).thenReturn(List.of());
        Mockito.when(mockDAO.contarPorPadre(idPruebaClave, idArea, idPregunta)).thenReturn(0L);

        Response res = cut.buscarPorRango(idPruebaClave, idArea, idPregunta, 0, 50);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void buscarPorRangoValidacionFallidaTest() {
        assertEquals(422, cut.buscarPorRango(null, idArea, idPregunta, 0, 50).getStatus());
        assertEquals(422, cut.buscarPorRango(idPruebaClave, idArea, idPregunta, -1, 50).getStatus());
        assertEquals(422, cut.buscarPorRango(idPruebaClave, idArea, idPregunta, 0, 0).getStatus());
        assertEquals(422, cut.buscarPorRango(idPruebaClave, idArea, idPregunta, 0, 51).getStatus());
    }

    @Test
    public void buscarPorRangoExcepcionTest() {
        Mockito.when(mockDAO.buscarPorPadreRango(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.buscarPorRango(idPruebaClave, idArea, idPregunta, 0, 50);
        assertEquals(500, res.getStatus());
    }

    @Test
    public void buscarPorIdExitosoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta))
            .thenReturn(new PruebaClaveAreaPreguntaDistractor(UUID.randomUUID(), UUID.randomUUID(), idPregunta, id));

        Response res = cut.buscarPorId(idPruebaClave, idArea, idPregunta, id);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(null);

        Response res = cut.buscarPorId(idPruebaClave, idArea, idPregunta, id);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void buscarPorIdValidacionFallidaTest() {
        UUID id = UUID.randomUUID();
        assertEquals(422, cut.buscarPorId(null, idArea, idPregunta, id).getStatus());
        assertEquals(422, cut.buscarPorId(idPruebaClave, idArea, idPregunta, null).getStatus());
    }

    @Test
    public void buscarPorIdExcepcionTest() {
        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.buscarPorId(idPruebaClave, idArea, idPregunta, UUID.randomUUID());
        assertEquals(500, res.getStatus());
    }

    @Test
    public void actualizarExitosoTest() {
        UUID id = UUID.randomUUID();
        UUID idDistractor = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        existente.setIdPruebaClave(UUID.randomUUID());
        existente.setIdArea(UUID.randomUUID());
        existente.setIdPregunta(idPregunta);
        existente.setIdDistractor(UUID.randomUUID());

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(existente);
        Mockito.when(mockDistractorDAO.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, entity);
        assertEquals(200, res.getStatus());
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarSustituyeIdBodyPorIdPathTest() {
        UUID idPath = UUID.randomUUID();
        UUID idDistractor = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        existente.setIdPruebaClave(UUID.randomUUID());
        existente.setIdArea(UUID.randomUUID());
        existente.setIdPregunta(idPregunta);
        existente.setIdDistractor(idPath);

        Mockito.when(mockDAO.buscarPorIdYPadre(idPath, idPruebaClave, idArea, idPregunta)).thenReturn(existente);
        Mockito.when(mockDistractorDAO.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, idPath, entity);

        assertEquals(200, res.getStatus());
        assertEquals(idDistractor, entity.getIdDistractor());
        Mockito.verify(mockDAO).actualizar(entity);
    }

    @Test
    public void actualizarPreservarDistractorTest() {
        UUID id = UUID.randomUUID();
        UUID idDistractorExistente = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(null);

        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        existente.setIdPruebaClave(UUID.randomUUID());
        existente.setIdArea(UUID.randomUUID());
        existente.setIdPregunta(idPregunta);
        existente.setIdDistractor(idDistractorExistente);

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(existente);

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, entity);
        assertEquals(200, res.getStatus());
        assertEquals(idDistractorExistente, entity.getIdDistractor());
        Mockito.verify(mockDAO).actualizar(entity);
        Mockito.verify(mockDistractorDAO, Mockito.never()).buscarPorId(Mockito.any());
    }

    @Test
    public void actualizarPreservarDistractorConIdInternoNuloTest() {
        UUID id = UUID.randomUUID();
        UUID idDistractorExistente = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(null);

        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        existente.setIdPruebaClave(UUID.randomUUID());
        existente.setIdArea(UUID.randomUUID());
        existente.setIdPregunta(idPregunta);
        existente.setIdDistractor(idDistractorExistente);

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(existente);

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, entity);
        assertEquals(200, res.getStatus());
        assertEquals(idDistractorExistente, entity.getIdDistractor());
        Mockito.verify(mockDAO).actualizar(entity);
        Mockito.verify(mockDistractorDAO, Mockito.never()).buscarPorId(Mockito.any());
    }

    @Test
    public void actualizarDistractorNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        UUID idDistractor = UUID.randomUUID();

        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();
        entity.setIdDistractor(idDistractor);

        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        existente.setIdPruebaClave(UUID.randomUUID());
        existente.setIdArea(UUID.randomUUID());
        existente.setIdPregunta(idPregunta);
        existente.setIdDistractor(UUID.randomUUID());

        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(existente);
        Mockito.when(mockDistractorDAO.buscarPorId(idDistractor)).thenReturn(null);

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, entity);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void actualizarValidacionFallidaTest() {
        PruebaClaveAreaPreguntaDistractor entityValida = new PruebaClaveAreaPreguntaDistractor();

        assertEquals(422, cut.actualizar(null, idArea, idPregunta, null, entityValida).getStatus());
        assertEquals(422, cut.actualizar(idPruebaClave, idArea, idPregunta, UUID.randomUUID(), null).getStatus());
        assertEquals(422, cut.actualizar(idPruebaClave, idArea, idPregunta, null, entityValida).getStatus());
    }

    @Test
    public void actualizarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(null);

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, new PruebaClaveAreaPreguntaDistractor());
        assertEquals(404, res.getStatus());
    }

    @Test
    public void actualizarExcepcionTest() {
        UUID id = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor entity = new PruebaClaveAreaPreguntaDistractor();

        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.actualizar(idPruebaClave, idArea, idPregunta, id, entity);
        assertEquals(500, res.getStatus());
    }

    @Test
    public void eliminarExitosoTest() {
        UUID id = UUID.randomUUID();
        PruebaClaveAreaPreguntaDistractor existente = new PruebaClaveAreaPreguntaDistractor();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(existente);

        Response res = cut.eliminar(idPruebaClave, idArea, idPregunta, id);
        assertEquals(204, res.getStatus());
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarValidacionFallidaTest() {
        UUID id = UUID.randomUUID();
        assertEquals(422, cut.eliminar(null, idArea, idPregunta, id).getStatus());
        assertEquals(422, cut.eliminar(idPruebaClave, idArea, idPregunta, null).getStatus());
    }

    @Test
    public void eliminarNoEncontradoTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPadre(id, idPruebaClave, idArea, idPregunta)).thenReturn(null);

        Response res = cut.eliminar(idPruebaClave, idArea, idPregunta, id);
        assertEquals(404, res.getStatus());
    }

    @Test
    public void eliminarExcepcionTest() {
        Mockito.when(mockDAO.buscarPorIdYPadre(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Error BD"));

        Response res = cut.eliminar(idPruebaClave, idArea, idPregunta, UUID.randomUUID());
        assertEquals(500, res.getStatus());
    }
}
