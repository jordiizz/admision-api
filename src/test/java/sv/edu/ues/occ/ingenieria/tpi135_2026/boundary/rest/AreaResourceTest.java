package sv.edu.ues.occ.ingenieria.tpi135_2026.boundary.rest;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.tpi135_2026.control.AreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.entity.Area;

public class AreaResourceTest {

    private UriInfo mockUriInfo;
    private AreaDAO mockAD;
    private AreaResource cut; // Class Under Test

    @BeforeEach
    public void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        mockAD = Mockito.mock(AreaDAO.class);

        Mockito.when(mockUriInfo.getAbsolutePathBuilder())
                .thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.path(Mockito.anyString()))
                .thenReturn(mockUriBuilder);
        Mockito.when(mockUriBuilder.build())
                .thenReturn(URI.create("http://localhost:8080/v1/area/1"));

        cut = new AreaResource();
        cut.areaDAO = mockAD; // Inyección manual del mock
    }

    @Test
    public void crearExitoso() {
        System.out.println("Ejecutando test: crearExitoso");
        Area nuevo = new Area();

        Mockito.doAnswer(invocation -> {
            Area area = invocation.getArgument(0);
            area.setIdArea(1);
            return null;
        }).when(mockAD).crear(nuevo);

        Response resultado = cut.crear(nuevo, mockUriInfo);

        assertAll(
                () -> assertEquals(201, resultado.getStatus()),
                () -> assertNotNull(resultado.getEntity())
        );

        Mockito.verify(mockAD).crear(nuevo);
    }

    @Test
    public void crearFallaSinId() {
        System.out.println("Ejecutando test: crearFallaSinId");
        Area nuevo = new Area();

        Mockito.doNothing().when(mockAD).crear(nuevo);

        Response resultado = cut.crear(nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockAD).crear(nuevo);
    }

    @Test
    public void crearConExcepcion() {
        System.out.println("Ejecutando test: crearConExcepcion");
        Area nuevo = new Area();

        Mockito.doThrow(new RuntimeException("Error en base de datos"))
                .when(mockAD).crear(nuevo);

        Response resultado = cut.crear(nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockAD).crear(nuevo);
    }

    @Test
    public void crearAreaNull() {
        System.out.println("Ejecutando test: crearAreaNull");
        Response resultado = cut.crear(null, mockUriInfo);

        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockAD);
    }

    @Test
    public void crearAreaConId() {
        System.out.println("Ejecutando test: crearAreaConId");
        Area areaConId = new Area();
        areaConId.setIdArea(100);

        Response resultado = cut.crear(areaConId, mockUriInfo);

        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockAD);
    }

    @Test
    public void eliminarExitoso() {
        System.out.println("Ejecutando test: eliminarExitoso");
        Integer id = 1;
        Area areaExistente = new Area(id);

        Mockito.when(mockAD.buscarPorId(id)).thenReturn(areaExistente);
        Mockito.doNothing().when(mockAD).eliminar(areaExistente);

        Response resultado = cut.eliminar(id);

        assertEquals(204, resultado.getStatus());
        Mockito.verify(mockAD).buscarPorId(id);
        Mockito.verify(mockAD).eliminar(areaExistente);
    }

    @Test
    public void eliminarNoEncontrado() {
        System.out.println("Ejecutando test: eliminarNoEncontrado");
        Integer id = 999;
        Mockito.when(mockAD.buscarPorId(id)).thenReturn(null);
        Response resultado = cut.eliminar(id);

        assertEquals(404, resultado.getStatus());
        Mockito.verify(mockAD).buscarPorId(id);
        Mockito.verify(mockAD, Mockito.never()).eliminar(Mockito.any());
    }

    @Test
    public void eliminarConExcepcion() {
        System.out.println("Ejecutando test: eliminarConExcepcion");
        Integer id = 1;
        Area areaExistente = new Area(id);
        Mockito.when(mockAD.buscarPorId(id)).thenReturn(areaExistente);
        Mockito.doThrow(new RuntimeException("Error en base de datos"))
                .when(mockAD).eliminar(areaExistente);
        Response resultado = cut.eliminar(id);
        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockAD).buscarPorId(id);
        Mockito.verify(mockAD).eliminar(areaExistente);
    }

    @Test
    public void eliminarIdNull(){
        System.out.println("Ejecutando test: eliminarIdNull");
        Response resultado = cut.eliminar(null);
        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockAD);
    }
}