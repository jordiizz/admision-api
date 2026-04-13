package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;
import java.util.List;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;


@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoPruebaResourceST extends AbstractIntegrationTest {

    private final String RESOURCE_NAME = "tipo_prueba";

    TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID()); 
    TipoPrueba tipoPrueba2 = new TipoPrueba(UUID.randomUUID());
    UUID idTipoPrueba;

    @Override
    public String getResourceName() {
        return RESOURCE_NAME;
    }

    @BeforeEach
    public void setup() {
        tipoPrueba.setValor("NUEVO_INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);
        tipoPrueba2.setValor("NUEVO_INGRESO_UNIVERSITARIO_SEGUNDA_RONDA");
        tipoPrueba2.setActivo(true);
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Ejecutando Test E2E: testCrearTipoPruebaExitoso");
        Response respuesta = target.request(MediaType.APPLICATION_JSON).post(Entity.json(tipoPrueba));
        assertNotNull(respuesta);
        assertEquals(Response.Status.CREATED.getStatusCode(), respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey("Location"));
        idTipoPrueba = UUID.fromString(respuesta.getHeaderString("Location").split(RESOURCE_NAME + "/")[1]);
        assertNotNull(idTipoPrueba);
        Response respuesta2 = target.request(MediaType.APPLICATION_JSON).post(Entity.json(tipoPrueba2));
        assertNotNull(respuesta2);
        assertEquals(Response.Status.CREATED.getStatusCode(), respuesta2.getStatus());
        assertTrue(respuesta2.getHeaders().containsKey("Location"));
    }

    @Order(2)
    @Test
    public void testObtenerPorRangoExitoso(){
        System.out.println("Ejecutando Test E2E: testObtenerPorRangoExitoso");
        target = target.queryParam("first", 0).queryParam("max", 10);
        Response respuesta = target.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), respuesta.getStatus());
        List<TipoPrueba> tipoPruebas = respuesta.readEntity(List.class);
        assertNotNull(tipoPruebas);
        assertTrue(tipoPruebas.size() >= 2);
    }

    @Order(3)
    @Test
    public void testActualizarExitoso(){
        System.out.println("Ejecutando Test E2E: testActualizarTipoPruebaExitoso");
        target = target.path(idTipoPrueba.toString());
        tipoPrueba.setValor("NUEVO_INGRESO_UNIVERSITARIO_SEGUNDA_RONDA");
        Response respuesta = target.request(MediaType.APPLICATION_JSON).put(Entity.json(tipoPrueba));
        assertEquals(Response.Status.OK.getStatusCode(), respuesta.getStatus());
    }


    @Order(4)
    @Test
    public void testEliminarExitoso(){
        System.out.println("Ejecutando Test E2E: testEliminarTipoPruebaExitoso");
        Response respuesta = target.request(MediaType.APPLICATION_JSON).delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), respuesta.getStatus());    
    }
    

}
    
