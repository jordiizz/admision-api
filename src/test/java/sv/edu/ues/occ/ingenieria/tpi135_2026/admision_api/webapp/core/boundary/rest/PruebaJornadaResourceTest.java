package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaPK;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;

public class PruebaJornadaResourceTest {

    PruebaJornadaResource cut;
    Prueba p;
    Jornada j;
    PruebaDAO pDAO;
    JornadaDAO jDAO;
    PruebaJornadaDAO pJDAO;
    UriInfo uriInfo;
    UriBuilder uriBuilder;

    @BeforeEach
    public void setUp() {
        // Configurar el entorno dme prueba, como inicializar objetos necesarios
         // Clase bajo prueba

        cut = new PruebaJornadaResource();      

        pDAO = Mockito.mock(PruebaDAO.class);
        jDAO = Mockito.mock(JornadaDAO.class);
        pJDAO = Mockito.mock(PruebaJornadaDAO.class);

        cut.pDAO = pDAO;
        cut.jDAO = jDAO;
        cut.pJDAO = pJDAO;

        uriInfo = Mockito.mock(UriInfo.class);
        uriBuilder = Mockito.mock(UriBuilder.class);
         Mockito.when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
         Mockito.when(uriBuilder.path(Mockito.anyString())).thenReturn(uriBuilder);
    }

    @Test
    public void crear_PruebaJornada_Exitoso() {
        System.out.println("EjecutandoTest: crear_PruebaJornada_Exitoso");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        p = new Prueba(idPrueba);
        j = new Jornada(idJornada);

        Mockito.when(pDAO.buscarPorId(p.getIdPrueba())).thenReturn(p);
        Mockito.when(jDAO.buscarPorId(j.getIdJornada())).thenReturn(j);
        PruebaJornada pj = new PruebaJornada(p, j);
        Mockito.doNothing().when(pJDAO).crear(pj); // Simula la creación exitosa


        Response respuesta = cut.crear(idPrueba, idJornada, uriInfo);
        assertEquals(Response.Status.CREATED.getStatusCode(), respuesta.getStatus());
        // assertEquals("http://localhost/v1/prueba/" + prueba.getIdPrueba() + "/jornada/" + jornada.getIdJornada() , respuesta.getLocation().toString());
    }

    @Test
    public void crear_PruebaJornada_NotFound() {
        System.out.println("EjecutandoTest: crear_PruebaJornada_NotFound");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();

        Mockito.when(pDAO.buscarPorId(idPrueba)).thenReturn(null); // Simula que no se encuentra la prueba
        Mockito.when(jDAO.buscarPorId(idJornada)).thenReturn(null); // Simula que no se encuentra la jornada

        Response respuesta = cut.crear(idPrueba, idJornada, uriInfo);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void crear_PruebaJornada_BadRequest() {
        System.out.println("EjecutandoTest: crear_PruebaJornada_BadRequest");
        Response respuesta = cut.crear(null, null, uriInfo);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void crear_PruebaJornada_ErrorInterno() {
        System.out.println("EjecutandoTest: crear_PruebaJornada_ErrorInterno");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        p = new Prueba(idPrueba);
        j = new Jornada(idJornada);

        cut.jDAO = null;
        Response respuesta = cut.crear(idPrueba, idJornada, uriInfo);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void eliminar_PruebaJornada_Exitoso() {
        System.out.println("EjecutandoTest: eliminar_PruebaJornada_Exitoso");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        p = new Prueba(idPrueba);
        j = new Jornada(idJornada);
        PruebaJornada pj = new PruebaJornada(p, j);

        Mockito.when(pDAO.buscarPorId(idPrueba)).thenReturn(p);
        Mockito.when(jDAO.buscarPorId(idJornada)).thenReturn(j);
        Mockito.when(pJDAO.buscarPorId(Mockito.any(PruebaJornadaPK.class))).thenReturn(pj);
        Mockito.doNothing().when(pJDAO).eliminar(pj);

        Response respuesta = cut.eliminar(idPrueba, idJornada);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void eliminar_PruebaJornada_NotFound() {
        System.out.println("EjecutandoTest: eliminar_PruebaJornada_NotFound");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();

        Mockito.when(pDAO.buscarPorId(idPrueba)).thenReturn(null);
        Mockito.when(jDAO.buscarPorId(idJornada)).thenReturn(null);

        Response respuesta = cut.eliminar(idPrueba, idJornada);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), respuesta.getStatus());
    }

    @Test 
    public void eliminar_PruebaJornada_BadRequest() {
        System.out.println("EjecutandoTest: eliminar_PruebaJornada_BadRequest");
        Response respuesta = cut.eliminar(null, null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), respuesta.getStatus());
    }

     @Test
    public void eliminar_PruebaJornada_ErrorInterno() {
        System.out.println("EjecutandoTest: eliminar_PruebaJornada_ErrorInterno");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        p = new Prueba(idPrueba);
        j = new Jornada(idJornada);
        cut.jDAO = null;
        Response respuesta = cut.eliminar(idPrueba, idJornada);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void actualizar_PruebaJornada_Exitoso() {
        System.out.println("EjecutandoTest: actualizar_PruebaJornada_Exitoso");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        p = new Prueba(idPrueba);
        j = new Jornada(idJornada);
        PruebaJornada pj = new PruebaJornada(p, j);

        Mockito.when(pDAO.buscarPorId(idPrueba)).thenReturn(p);
        Mockito.when(jDAO.buscarPorId(idJornada)).thenReturn(j);
        Mockito.when(pJDAO.buscarPorId(Mockito.any(PruebaJornadaPK.class))).thenReturn(pj);

        Response respuesta = cut.actualizar(pj, idPrueba, idJornada);
        assertEquals(Response.Status.OK.getStatusCode(), respuesta.getStatus());
    }

     @Test
    public void actualizar_PruebaJornada_NotFound() {
        System.out.println("EjecutandoTest: actualizar_PruebaJornada_NotFound");
        UUID idPrueba = UUID.randomUUID();
        UUID idJornada = UUID.randomUUID();
        PruebaJornada pj = new PruebaJornada(new Prueba(idPrueba), new Jornada(idJornada));

        Mockito.when(pDAO.buscarPorId(idPrueba)).thenReturn(null);
        Mockito.when(jDAO.buscarPorId(idJornada)).thenReturn(null);
        Response respuesta = cut.actualizar(pj, idPrueba, idJornada);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), respuesta.getStatus());
    }

    @Test
    public void actualizar_PruebaJornada_BadRequest() {
        System.out.println("EjecutandoTest: actualizar_PruebaJornada_BadRequest");
        Response respuesta = cut.actualizar(null, null, null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), respuesta.getStatus());
    }
}