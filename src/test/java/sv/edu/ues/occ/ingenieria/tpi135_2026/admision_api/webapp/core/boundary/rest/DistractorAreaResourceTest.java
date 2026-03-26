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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorAreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorAreaPK;

public class DistractorAreaResourceTest {

    private UriInfo mockUriInfo;
    private DistractorAreaDAO mockDAA;
    private DistractorDAO mockDD;
    private DistractorAreaResource cut;

    private UUID idDistractor;
    private UUID idArea;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAA = Mockito.mock(DistractorAreaDAO.class);
        mockDD = Mockito.mock(DistractorDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build())
                .thenReturn(URI.create("http://localhost:8080/v1/distractor/1/area/1"));

        idDistractor = UUID.randomUUID();
        idArea = UUID.randomUUID();

        cut = new DistractorAreaResource();
        cut.distractorAreaDAO = mockDAA;
        cut.distractorDAO = mockDD;
    }

    @Test
    public void crearExitosoTest() {
        System.out.println("Ejecutando test: crearExitosoTest en DistractorAreaResource");
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDD).buscarPorId(idDistractor);
        Mockito.verify(mockDAA).crear(nuevo);
    }

    @Test
    public void crearConAreaSinIdInternoTest() {
        System.out.println("Ejecutando test: crearConAreaSinIdInternoTest en DistractorAreaResource");
        DistractorArea nuevo = new DistractorArea();
        nuevo.setIdArea(new Area());
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        Mockito.verify(mockDAA).crear(nuevo);
    }

    @Test
    public void crearDistractorNoEncontradoTest() {
        System.out.println("Ejecutando test: crearDistractorNoEncontradoTest en DistractorAreaResource");
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(null);

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDD).buscarPorId(idDistractor);
        Mockito.verify(mockDAA, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearConExcepcionTest() {
        System.out.println("Ejecutando test: crearConExcepcionTest en DistractorAreaResource");
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).crear(nuevo);

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).crear(nuevo);
    }

    @Test
    public void crearBadRequestTest() {
        System.out.println("Ejecutando test: crearBadRequestTest en DistractorAreaResource");
        assertEquals(400, cut.crear(idDistractor, null, mockUriInfo).getStatus());
        assertEquals(400, cut.crear(idDistractor, new DistractorArea(), mockUriInfo).getStatus());
        assertEquals(400,
                cut.crear(null, new DistractorArea(new Distractor(idDistractor), new Area(idArea)), mockUriInfo).getStatus());
        Mockito.verifyNoInteractions(mockDAA);
        Mockito.verifyNoInteractions(mockDD);
    }

    @Test
    public void buscarPorRangoExitosoTest() {
        System.out.println("Ejecutando test: buscarPorRangoExitosoTest en DistractorAreaResource");
        List<DistractorArea> registros = List.of(
                new DistractorArea(new Distractor(idDistractor), new Area(idArea)));
        Mockito.when(mockDAA.buscarPorDistractorRango(idDistractor, 0, 50)).thenReturn(registros);
        Mockito.when(mockDAA.contarPorDistractor(idDistractor)).thenReturn(1L);

        Response resultado = cut.buscarPorRango(idDistractor, 0, 50);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        assertEquals("1", resultado.getHeaderString(ResponseHeaders.TOTAL_RECORDS.toString()));
        Mockito.verify(mockDAA).buscarPorDistractorRango(idDistractor, 0, 50);
        Mockito.verify(mockDAA).contarPorDistractor(idDistractor);
    }

    @Test
    public void buscarPorRangoConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorRangoConExcepcionTest en DistractorAreaResource");
        Mockito.when(mockDAA.buscarPorDistractorRango(idDistractor, 0, 50))
                .thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorRango(idDistractor, 0, 50);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorDistractorRango(idDistractor, 0, 50);
    }

    @Test
    public void buscarPorRangoBadRequestTest() {
        System.out.println("Ejecutando test: buscarPorRangoBadRequestTest en DistractorAreaResource");
        assertEquals(400, cut.buscarPorRango(idDistractor, -1, 1).getStatus());
        assertEquals(400, cut.buscarPorRango(idDistractor, 0, 0).getStatus());
        assertEquals(400, cut.buscarPorRango(idDistractor, 0, 51).getStatus());
        assertEquals(400, cut.buscarPorRango(null, 0, 50).getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void buscarPorIdExitosoTest() {
        System.out.println("Ejecutando test: buscarPorIdExitosoTest en DistractorAreaResource");
        DistractorArea encontrado = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(encontrado);

        Response resultado = cut.buscarPorId(idDistractor, idArea);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAA).buscarPorId(new DistractorAreaPK(idDistractor, idArea));
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("Ejecutando test: buscarPorIdNoEncontradoTest en DistractorAreaResource");
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.buscarPorId(idDistractor, idArea);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorId(new DistractorAreaPK(idDistractor, idArea));
    }

    @Test
    public void buscarPorIdConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorIdConExcepcionTest en DistractorAreaResource");
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea)))
                .thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorId(idDistractor, idArea);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorId(new DistractorAreaPK(idDistractor, idArea));
    }

    @Test
    public void buscarPorIdBadRequestTest() {
        System.out.println("Ejecutando test: buscarPorIdBadRequestTest en DistractorAreaResource");
        assertEquals(400, cut.buscarPorId(idDistractor, null).getStatus());
        assertEquals(400, cut.buscarPorId(null, idArea).getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void actualizarExitosoTest() {
        System.out.println("Ejecutando test: actualizarExitosoTest en DistractorAreaResource");
        UUID otroDistractor = UUID.randomUUID();
        UUID otraArea = UUID.randomUUID();
        DistractorArea body = new DistractorArea(new Distractor(otroDistractor), new Area(otraArea));
        DistractorArea existente = new DistractorArea(new Distractor(idDistractor), new Area(idArea));

        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(existente);
        Mockito.when(mockDAA.actualizar(body)).thenReturn(body);

        Response resultado = cut.actualizar(idDistractor, idArea, body);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        assertEquals(idDistractor, body.getIdDistractor().getIdDistractor());
        assertEquals(idArea, body.getIdArea().getIdArea());
        Mockito.verify(mockDAA).actualizar(body);
    }

    @Test
    public void actualizarNoEncontradoTest() {
        System.out.println("Ejecutando test: actualizarNoEncontradoTest en DistractorAreaResource");
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.actualizar(idDistractor, idArea,
                new DistractorArea(new Distractor(idDistractor), new Area(idArea)));

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarConExcepcionTest() {
        System.out.println("Ejecutando test: actualizarConExcepcionTest en DistractorAreaResource");
        DistractorArea body = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea)))
                .thenReturn(new DistractorArea(new Distractor(idDistractor), new Area(idArea)));
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).actualizar(body);

        Response resultado = cut.actualizar(idDistractor, idArea, body);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).actualizar(body);
    }

    @Test
    public void actualizarBadRequestTest() {
        System.out.println("Ejecutando test: actualizarBadRequestTest en DistractorAreaResource");
        DistractorArea body = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        assertEquals(400, cut.actualizar(idDistractor, idArea, null).getStatus());
        assertEquals(400, cut.actualizar(null, idArea, body).getStatus());
        assertEquals(400, cut.actualizar(idDistractor, null, body).getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void eliminarExitosoTest() {
        System.out.println("Ejecutando test: eliminarExitosoTest en DistractorAreaResource");
        DistractorArea existente = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(existente);
        Mockito.doNothing().when(mockDAA).eliminar(existente);

        Response resultado = cut.eliminar(idDistractor, idArea);

        assertEquals(204, resultado.getStatus());
        Mockito.verify(mockDAA).eliminar(existente);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        System.out.println("Ejecutando test: eliminarNoEncontradoTest en DistractorAreaResource");
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.eliminar(idDistractor, idArea);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarConExcepcionTest() {
        System.out.println("Ejecutando test: eliminarConExcepcionTest en DistractorAreaResource");
        DistractorArea existente = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(existente);
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).eliminar(existente);

        Response resultado = cut.eliminar(idDistractor, idArea);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).eliminar(existente);
    }

    @Test
    public void eliminarBadRequestTest() {
        System.out.println("Ejecutando test: eliminarBadRequestTest en DistractorAreaResource");
        assertEquals(400, cut.eliminar(idDistractor, null).getStatus());
        assertEquals(400, cut.eliminar(null, idArea).getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }
}
