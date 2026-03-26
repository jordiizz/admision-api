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

    // --- crear ---

    @Test
    public void crearExitosoTest() {
        System.out.println("Ejecutando test: crearExitosoTest en DistractorAreaResource");
        // Camino feliz: existe el distractor padre y se crea el vínculo.
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDD).buscarPorId(idDistractor);
        Mockito.verify(mockDAA).crear(nuevo);
    }

    @Test
    public void crearDistractorNoEncontradoTest() {
        System.out.println("Ejecutando test: crearDistractorNoEncontradoTest en DistractorAreaResource");
        // Si el padre no existe, debe responder 404.
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
        // Simula error interno al persistir.
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDD.buscarPorId(idDistractor)).thenReturn(new Distractor(idDistractor));
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).crear(nuevo);

        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).crear(nuevo);
    }

    @Test
    public void crearNullTest() {
        System.out.println("Ejecutando test: crearNullTest en DistractorAreaResource");
        // Body nulo debe fallar validación.
        Response resultado = cut.crear(idDistractor, null, mockUriInfo);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
        Mockito.verifyNoInteractions(mockDD);
    }

    @Test
    public void crearSinAreaTest() {
        System.out.println("Ejecutando test: crearSinAreaTest en DistractorAreaResource");
        // Sin idArea no se puede formar la PK compuesta.
        DistractorArea nuevo = new DistractorArea();
        Response resultado = cut.crear(idDistractor, nuevo, mockUriInfo);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
        Mockito.verifyNoInteractions(mockDD);
    }

    @Test
    public void crearIdDistractorNullTest() {
        System.out.println("Ejecutando test: crearIdDistractorNullTest en DistractorAreaResource");
        // Path param obligatorio.
        DistractorArea nuevo = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Response resultado = cut.crear(null, nuevo, mockUriInfo);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
        Mockito.verifyNoInteractions(mockDD);
    }

    // --- buscarPorRango ---

    @Test
    public void buscarPorRangoExitosoTest() {
        System.out.println("Ejecutando test: buscarPorRangoExitosoTest en DistractorAreaResource");
        // Retorna lista y cabecera con total.
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
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("Ejecutando test: buscarPorRangoParametrosInvalidosTest en DistractorAreaResource");
        // first negativo y max inválido.
        Response resultado = cut.buscarPorRango(idDistractor, -1, 0);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void buscarPorRangoIdDistractorNullTest() {
        System.out.println("Ejecutando test: buscarPorRangoIdDistractorNullTest en DistractorAreaResource");
        // Sin id del padre no hay consulta.
        Response resultado = cut.buscarPorRango(null, 0, 50);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void buscarPorRangoConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorRangoConExcepcionTest en DistractorAreaResource");
        // Propaga como 500 cuando falla DAO.
        Mockito.when(mockDAA.buscarPorDistractorRango(idDistractor, 0, 50))
                .thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorRango(idDistractor, 0, 50);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorDistractorRango(idDistractor, 0, 50);
    }

    // --- buscarPorId ---

    @Test
    public void buscarPorIdExitosoTest() {
        System.out.println("Ejecutando test: buscarPorIdExitosoTest en DistractorAreaResource");
        // Debe encontrar por PK compuesta.
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
        // Cuando no existe el vínculo devuelve 404.
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.buscarPorId(idDistractor, idArea);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorId(new DistractorAreaPK(idDistractor, idArea));
    }

    @Test
    public void buscarPorIdNullTest() {
        System.out.println("Ejecutando test: buscarPorIdNullTest en DistractorAreaResource");
        // idArea nulo, debe fallar validación.
        Response resultado = cut.buscarPorId(idDistractor, null);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void buscarPorIdConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorIdConExcepcionTest en DistractorAreaResource");
        // Si DAO lanza error, endpoint responde 500.
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea)))
                .thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorId(idDistractor, idArea);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).buscarPorId(new DistractorAreaPK(idDistractor, idArea));
    }

    // --- actualizar ---

    @Test
    public void actualizarExitosoTest() {
        System.out.println("Ejecutando test: actualizarExitosoTest en DistractorAreaResource");
        // Ignora IDs del body y usa los del path.
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
        // No actualiza si no existe el registro.
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.actualizar(idDistractor, idArea,
                new DistractorArea(new Distractor(idDistractor), new Area(idArea)));

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarNullTest() {
        System.out.println("Ejecutando test: actualizarNullTest en DistractorAreaResource");
        // Body nulo o IDs nulos => 400.
        Response resultado = cut.actualizar(idDistractor, idArea, null);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void actualizarConExcepcionTest() {
        System.out.println("Ejecutando test: actualizarConExcepcionTest en DistractorAreaResource");
        // Falla de persistencia al actualizar.
        DistractorArea body = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea)))
                .thenReturn(new DistractorArea(new Distractor(idDistractor), new Area(idArea)));
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).actualizar(body);

        Response resultado = cut.actualizar(idDistractor, idArea, body);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).actualizar(body);
    }

    // --- eliminar ---

    @Test
    public void eliminarExitosoTest() {
        System.out.println("Ejecutando test: eliminarExitosoTest en DistractorAreaResource");
        // Eliminación correcta cuando el registro existe.
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
        // Si no existe, devuelve 404.
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(null);

        Response resultado = cut.eliminar(idDistractor, idArea);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAA, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarNullTest() {
        System.out.println("Ejecutando test: eliminarNullTest en DistractorAreaResource");
        // idArea nulo -> request inválido.
        Response resultado = cut.eliminar(idDistractor, null);

        assertEquals(400, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAA);
    }

    @Test
    public void eliminarConExcepcionTest() {
        System.out.println("Ejecutando test: eliminarConExcepcionTest en DistractorAreaResource");
        // Error inesperado en eliminación.
        DistractorArea existente = new DistractorArea(new Distractor(idDistractor), new Area(idArea));
        Mockito.when(mockDAA.buscarPorId(new DistractorAreaPK(idDistractor, idArea))).thenReturn(existente);
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAA).eliminar(existente);

        Response resultado = cut.eliminar(idDistractor, idArea);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAA).eliminar(existente);
    }
}
