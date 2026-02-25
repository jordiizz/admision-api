package sv.edu.ues.occ.ingenieria.tpi135_2026.boundary.rest;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
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
    private UriBuilder mockUriBuilder;
    private AreaDAO mockAD;
    private AreaResource cut; // Class Under Test

    @BeforeEach
    void setup() {
        mockUriInfo = Mockito.mock(UriInfo.class);
        mockUriBuilder = Mockito.mock(UriBuilder.class);
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
    void crearExitoso() {
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
    void crearFallaSinId() {
        Area nuevo = new Area();

        Mockito.doNothing().when(mockAD).crear(nuevo);

        Response resultado = cut.crear(nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockAD).crear(nuevo);
    }

    @Test
    void crearConExcepcion() {
        Area nuevo = new Area();

        Mockito.doThrow(new RuntimeException("Error en base de datos"))
                .when(mockAD).crear(nuevo);

        Response resultado = cut.crear(nuevo, mockUriInfo);

        assertEquals(500, resultado.getStatus());
        Mockito.verify(mockAD).crear(nuevo);
    }

    @Test
    void crearAreaNull() {
        Response resultado = cut.crear(null, mockUriInfo);

        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockAD);
    }

    @Test
    void crearAreaConId() {
        Area areaConId = new Area();
        areaConId.setIdArea(100);

        Response resultado = cut.crear(areaConId, mockUriInfo);

        assertEquals(422, resultado.getStatus());
        Mockito.verifyNoInteractions(mockAD);
    }
}