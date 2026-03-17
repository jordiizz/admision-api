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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;

public class PruebaClaveAreaResourceTest {

    private UriInfo mockUriInfo;
    private PruebaClaveAreaDAO mockDAO;
    private PruebaClaveDAO mockPCD;
    private PruebaClaveAreaResource cut;

    private UUID idPruebaClave;

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockDAO = Mockito.mock(PruebaClaveAreaDAO.class);
        mockPCD = Mockito.mock(PruebaClaveDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build())
                .thenReturn(URI.create("http://localhost:8080/v1/prueba-clave/1/area/1"));

        idPruebaClave = UUID.randomUUID();

        cut = new PruebaClaveAreaResource();
        cut.pruebaClaveAreaDAO = mockDAO;
        cut.pruebaClaveDAO = mockPCD;
    }

    // --- crear ---

    @Test
    public void crearExitosoTest() {
        System.out.println("Ejecutando test: crearExitosoTest en PruebaClaveAreaResource");
        PruebaClave pruebaClave = new PruebaClave(idPruebaClave);
        PruebaClaveArea nueva = new PruebaClaveArea();
        Mockito.when(mockPCD.buscarPorId(idPruebaClave)).thenReturn(pruebaClave);
        Mockito.doNothing().when(mockDAO).crear(nueva);

        Response resultado = cut.crear(idPruebaClave, nueva, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockPCD).buscarPorId(idPruebaClave);
        Mockito.verify(mockDAO).crear(nueva);
    }

    @Test
    public void crearPruebaClaveNoEncontradaTest() {
        System.out.println("Ejecutando test: crearPruebaClaveNoEncontradaTest en PruebaClaveAreaResource");
        Mockito.when(mockPCD.buscarPorId(idPruebaClave)).thenReturn(null);

        Response resultado = cut.crear(idPruebaClave, new PruebaClaveArea(), mockUriInfo);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockPCD).buscarPorId(idPruebaClave);
        Mockito.verify(mockDAO, Mockito.never()).crear(Mockito.any());
    }

    @Test
    public void crearConExcepcionTest() {
        System.out.println("Ejecutando test: crearConExcepcionTest en PruebaClaveAreaResource");
        PruebaClave pruebaClave = new PruebaClave(idPruebaClave);
        PruebaClaveArea nueva = new PruebaClaveArea();
        Mockito.when(mockPCD.buscarPorId(idPruebaClave)).thenReturn(pruebaClave);
        Mockito.doThrow(new IllegalStateException("Error en base de datos")).when(mockDAO).crear(nueva);

        Response resultado = cut.crear(idPruebaClave, nueva, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockPCD).buscarPorId(idPruebaClave);
        Mockito.verify(mockDAO).crear(nueva);
    }

    @Test
    public void crearNullTest() {
        System.out.println("Ejecutando test: crearNullTest en PruebaClaveAreaResource");
        Response resultado = cut.crear(idPruebaClave, null, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
        Mockito.verifyNoInteractions(mockPCD);
    }

    @Test
    public void crearConIdTest() {
        System.out.println("Ejecutando test: crearConIdTest en PruebaClaveAreaResource");
        PruebaClaveArea nueva = new PruebaClaveArea(UUID.randomUUID());
        Mockito.when(mockPCD.buscarPorId(idPruebaClave)).thenReturn(new PruebaClave(idPruebaClave));

        Response resultado = cut.crear(idPruebaClave, nueva, mockUriInfo);

        assertEquals(201, resultado.getStatus());
        Mockito.verify(mockPCD).buscarPorId(idPruebaClave);
        Mockito.verify(mockDAO).crear(nueva);
    }

    @Test
    public void crearIdPruebaClaveNullTest() {
        System.out.println("Ejecutando test: crearIdPruebaClaveNullTest en PruebaClaveAreaResource");
        PruebaClaveArea nueva = new PruebaClaveArea();
        Response resultado = cut.crear(null, nueva, mockUriInfo);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
        Mockito.verifyNoInteractions(mockPCD);
    }

    // --- buscarPorRango ---

    @Test
    public void buscarPorRangoExitosoTest() {
        System.out.println("Ejecutando test: buscarPorRangoExitosoTest en PruebaClaveAreaResource");
        List<PruebaClaveArea> registros = List.of(new PruebaClaveArea(UUID.randomUUID()));
        Mockito.when(mockDAO.buscarPorPruebaClaveRango(idPruebaClave, 0, 50)).thenReturn(registros);
        Mockito.when(mockDAO.contarPorPruebaClave(idPruebaClave)).thenReturn(1L);

        Response resultado = cut.buscarPorRango(idPruebaClave, 0, 50);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorPruebaClaveRango(idPruebaClave, 0, 50);
        Mockito.verify(mockDAO).contarPorPruebaClave(idPruebaClave);
    }

    @Test
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("Ejecutando test: buscarPorRangoParametrosInvalidosTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorRango(idPruebaClave, -1, 0);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxCeroTest() {
        System.out.println("Ejecutando test: buscarPorRangoMaxCeroTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorRango(idPruebaClave, 0, 0);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoMaxMayorACincuentaTest() {
        System.out.println("Ejecutando test: buscarPorRangoMaxMayorACincuentaTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorRango(idPruebaClave, 0, 51);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoIdPruebaClaveNullTest() {
        System.out.println("Ejecutando test: buscarPorRangoIdPruebaClaveNullTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorRango(null, 0, 50);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorRangoConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorRangoConExcepcionTest en PruebaClaveAreaResource");
        Mockito.when(mockDAO.buscarPorPruebaClaveRango(idPruebaClave, 0, 50))
                .thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorRango(idPruebaClave, 0, 50);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorPruebaClaveRango(idPruebaClave, 0, 50);
    }

    // --- buscarPorId ---

    @Test
    public void buscarPorIdExitosoTest() {
        System.out.println("Ejecutando test: buscarPorIdExitosoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        PruebaClaveArea encontrado = new PruebaClaveArea(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(encontrado);

        Response resultado = cut.buscarPorId(idPruebaClave, id);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("Ejecutando test: buscarPorIdNoEncontradoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(null);

        Response resultado = cut.buscarPorId(idPruebaClave, id);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
    }

    @Test
    public void buscarPorIdConExcepcionTest() {
        System.out.println("Ejecutando test: buscarPorIdConExcepcionTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenThrow(new RuntimeException("Error en base de datos"));

        Response resultado = cut.buscarPorId(idPruebaClave, id);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
    }

    @Test
    public void buscarPorIdNullTest() {
        System.out.println("Ejecutando test: buscarPorIdNullTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorId(idPruebaClave, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void buscarPorIdIdPruebaClaveNullTest() {
        System.out.println("Ejecutando test: buscarPorIdIdPruebaClaveNullTest en PruebaClaveAreaResource");
        Response resultado = cut.buscarPorId(null, UUID.randomUUID());
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    // --- actualizar ---

    @Test
    public void actualizarExitosoTest() {
        System.out.println("Ejecutando test: actualizarExitosoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        PruebaClaveArea pruebaClaveArea = new PruebaClaveArea(id);
        PruebaClaveArea existente = new PruebaClaveArea(id);
        existente.setIdPruebaClave(new PruebaClave(idPruebaClave));
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(existente);
        Mockito.when(mockDAO.actualizar(pruebaClaveArea)).thenReturn(pruebaClaveArea);

        Response resultado = cut.actualizar(idPruebaClave, pruebaClaveArea.getIdPruebaClaveArea(), pruebaClaveArea);

        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
        assertEquals(id, pruebaClaveArea.getIdPruebaClaveArea());
        assertEquals(idPruebaClave, pruebaClaveArea.getIdPruebaClave().getIdPruebaClave());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO).actualizar(pruebaClaveArea);
    }

    @Test
    public void actualizarNoEncontradoTest() {
        System.out.println("Ejecutando test: actualizarNoEncontradoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(null);

        Response resultado = cut.actualizar(idPruebaClave, id, new PruebaClaveArea(id));

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO, Mockito.never()).actualizar(Mockito.any());
    }

    @Test
    public void actualizarConExcepcionTest() {
        System.out.println("Ejecutando test: actualizarConExcepcionTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        PruebaClaveArea pruebaClaveArea = new PruebaClaveArea(id);
        PruebaClaveArea existente = new PruebaClaveArea(id);
        existente.setIdPruebaClave(new PruebaClave(idPruebaClave));
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(existente);
        Mockito.doThrow(new IllegalStateException("Error en base de datos")).when(mockDAO).actualizar(pruebaClaveArea);

        Response resultado = cut.actualizar(idPruebaClave, pruebaClaveArea.getIdPruebaClaveArea(), pruebaClaveArea);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO).actualizar(pruebaClaveArea);
    }

    @Test
    public void actualizarNullTest() {
        System.out.println("Ejecutando test: actualizarNullTest en PruebaClaveAreaResource");
        Response resultado = cut.actualizar(idPruebaClave, UUID.randomUUID(), null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarSinIdEnBodyTest() {
        System.out.println("Ejecutando test: actualizarSinIdEnBodyTest en PruebaClaveAreaResource");
        Response resultado = cut.actualizar(idPruebaClave, null, new PruebaClaveArea());
        assertEquals(422, resultado.getStatus());
        assertEquals("El recurso no puede ser nulo y idPruebaClave no puede ser nulo",
                resultado.getHeaderString(ResponseHeaders.WRONG_PARAMETER.toString()));
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void actualizarIdPruebaClaveNullTest() {
        System.out.println("Ejecutando test: actualizarIdPruebaClaveNullTest en PruebaClaveAreaResource");
        Response resultado = cut.actualizar(null, UUID.randomUUID(), new PruebaClaveArea(UUID.randomUUID()));
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    // --- eliminar ---

    @Test
    public void eliminarExitosoTest() {
        System.out.println("Ejecutando test: eliminarExitosoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        PruebaClaveArea existente = new PruebaClaveArea(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(existente);
        Mockito.doNothing().when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaClave, id);

        assertEquals(204, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        System.out.println("Ejecutando test: eliminarNoEncontradoTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(null);

        Response resultado = cut.eliminar(idPruebaClave, id);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarConExcepcionTest() {
        System.out.println("Ejecutando test: eliminarConExcepcionTest en PruebaClaveAreaResource");
        UUID id = UUID.randomUUID();
        PruebaClaveArea existente = new PruebaClaveArea(id);
        Mockito.when(mockDAO.buscarPorIdYPruebaClave(id, idPruebaClave)).thenReturn(existente);
        Mockito.doThrow(new RuntimeException("Error en base de datos")).when(mockDAO).eliminar(existente);

        Response resultado = cut.eliminar(idPruebaClave, id);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockDAO).buscarPorIdYPruebaClave(id, idPruebaClave);
        Mockito.verify(mockDAO).eliminar(existente);
    }

    @Test
    public void eliminarIdNullTest() {
        System.out.println("Ejecutando test: eliminarIdNullTest en PruebaClaveAreaResource");
        Response resultado = cut.eliminar(idPruebaClave, null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }

    @Test
    public void eliminarIdPruebaClaveNullTest() {
        System.out.println("Ejecutando test: eliminarIdPruebaClaveNullTest en PruebaClaveAreaResource");
        Response resultado = cut.eliminar(null, UUID.randomUUID());
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockDAO);
    }
}
