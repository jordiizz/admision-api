package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaPK;

@ExtendWith(MockitoExtension.class)
class PruebaClaveAreaPreguntaResourceTest {

    @Mock
    private PreguntaDAO pDAO;

    @Mock
    private AreaDAO aDAO;

    @Mock
    private PruebaClaveDAO pCDAO;

    @Mock
    private PruebaClaveAreaPreguntaDAO pCAPDAO;

    @InjectMocks
    private PruebaClaveAreaPreguntaResource resource;

    private UUID idPruebaClave;
    private UUID idArea;
    private UUID idPregunta;

    @BeforeEach
    void setUp() {
        idPruebaClave = UUID.randomUUID();
        idArea = UUID.randomUUID();
        idPregunta = UUID.randomUUID();
    }

    @Nested
    @DisplayName("Pruebas para el método crear")
    class CrearTests {

        @Test
        @DisplayName("Debe crear exitosamente y retornar 201")
        void crear_Exitoso() {
            // Arrange
            Pregunta p = new Pregunta();
            p.setIdPregunta(idPregunta);
            Area a = new Area();
            a.setIdArea(idArea);
            PruebaClave pC = new PruebaClave();
            pC.setIdPruebaClave(idPruebaClave);

            when(pDAO.buscarPorId(idPregunta)).thenReturn(p);
            when(aDAO.buscarPorId(idArea)).thenReturn(a);
            when(pCDAO.buscarPorId(idPruebaClave)).thenReturn(pC);

            // Act
            Response response = resource.crear(idPruebaClave, idArea, idPregunta);

            // Assert
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            verify(pCAPDAO).crear(any(PruebaClaveAreaPregunta.class));
        }

        @Test
        @DisplayName("Debe retornar 404 si alguna entidad no existe")
        void crear_EntidadNoEncontrada() {
            // Simulamos que la pregunta no existe
            when(pDAO.buscarPorId(idPregunta)).thenReturn(null);

            Response response = resource.crear(idPruebaClave, idArea, idPregunta);

            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
            verify(pCAPDAO, never()).crear(any());
        }

        @Test
        @DisplayName("Debe retornar 400 si algún parámetro es nulo")
        void crear_ParametroNulo() {
            Response response = resource.crear(null, idArea, idPregunta);

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }

        @Test
        @DisplayName("Debe retornar 500 si ocurre una excepción")
        void crear_Excepcion() {
            when(pDAO.buscarPorId(idPregunta)).thenThrow(new RuntimeException("Error de BD"));

            Response response = resource.crear(idPruebaClave, idArea, idPregunta);

            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            assertEquals("Error de BD", response.getEntity());
        }
    }

    @Nested
    @DisplayName("Pruebas para el método eliminar")
    class EliminarTests {

        @Test
        @DisplayName("Debe eliminar exitosamente y retornar 204")
        void eliminar_Exitoso() {
            PruebaClaveAreaPregunta pcap = new PruebaClaveAreaPregunta();
            when(pCAPDAO.buscarPorId(any(PruebaClaveAreaPreguntaPK.class))).thenReturn(pcap);

            Response response = resource.eliminar(idPruebaClave, idArea, idPregunta);

            assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
            verify(pCAPDAO).eliminar(pcap);
        }

        @Test
        @DisplayName("Debe retornar 404 si no encuentra el registro a eliminar")
        void eliminar_NoEncontrado() {
            when(pCAPDAO.buscarPorId(any(PruebaClaveAreaPreguntaPK.class))).thenReturn(null);

            Response response = resource.eliminar(idPruebaClave, idArea, idPregunta);

            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
            verify(pCAPDAO, never()).eliminar(any());
        }

        @Test
        @DisplayName("Debe retornar 400 si algún parámetro es nulo")
        void eliminar_ParametroNulo() {
            Response response = resource.eliminar(idPruebaClave, null, idPregunta);

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }

        @Test
        @DisplayName("Debe retornar 500 si ocurre una excepción")
        void eliminar_Excepcion() {
            when(pCAPDAO.buscarPorId(any(PruebaClaveAreaPreguntaPK.class)))
                    .thenThrow(new RuntimeException("Error al eliminar"));

            Response response = resource.eliminar(idPruebaClave, idArea, idPregunta);

            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        }
    }

    @Nested
    @DisplayName("Pruebas para el método listar")
    class ListarTests {

        @Test
        @DisplayName("Debe retornar la lista y 200")
        void listar_Exitoso() {
            List<PruebaClaveAreaPregunta> lista = Arrays.asList(new PruebaClaveAreaPregunta());
            when(pCAPDAO.buscarPorClaveYPregunta(idPruebaClave, idArea)).thenReturn(lista);

            Response response = resource.listar(idPruebaClave, idArea);

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(lista, response.getEntity());
        }

        @Test
        @DisplayName("Debe retornar 404 si la lista es nula")
        void listar_Nulo() {
            when(pCAPDAO.buscarPorClaveYPregunta(idPruebaClave, idArea)).thenReturn(null);

            Response response = resource.listar(idPruebaClave, idArea);

            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }

        @Test
        @DisplayName("Debe retornar 400 si algún parámetro es nulo")
        void listar_ParametroNulo() {
            Response response = resource.listar(null, idArea);

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }

        @Test
        @DisplayName("Debe retornar 500 si ocurre una excepción")
        void listar_Excepcion() {
            when(pCAPDAO.buscarPorClaveYPregunta(idPruebaClave, idArea))
                    .thenThrow(new RuntimeException("Error de consulta"));

            Response response = resource.listar(idPruebaClave, idArea);

            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        }
    }
}
