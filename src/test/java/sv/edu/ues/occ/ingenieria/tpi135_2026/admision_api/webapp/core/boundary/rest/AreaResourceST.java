package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AreaResourceST extends AbstractIntegrationTest{

    private UUID idCreado;

    @Override
    public String getResourceName(){
        return "area";
    }

    @Order(1)
    @Test
    public void buscarPorRangoTest(){
        System.out.println("findRange");
        int first = 0;
        int max = 50;
        int esperado = 200;
        Response respuesta = target
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        int total = Integer.parseInt(respuesta.getHeaderString(ResponseHeaders.TOTAL_RECORDS.toString()));
        Assertions.assertTrue(total >= 0);
        List<Area> registros = respuesta.readEntity(new GenericType<List<Area>>() {});
        for(Area registro : registros) {
            System.out.printf(registro.getNombre());
        }
    }

    @Order(2)
    @Test
    public void crearTest(){
        System.out.println("crear");
        Area nuevo = new Area();
        nuevo.setNombre("MATEMATICA");
        int esperado = 201;
        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("area/")[1]);
        Assertions.assertNotNull(id);
        idCreado = id;
        System.out.print("ID: " + id);
    }

    @Order(3)
    @Test
    public void buscarPorIdTest(){
        System.out.println("buscarPorId");

        if (idCreado == null) {
            Area nuevo = new Area();
            nuevo.setNombre("AREA-BUSCAR-ST");
            Response crear = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(nuevo));
            Assertions.assertEquals(201, crear.getStatus());
            idCreado = UUID.fromString(crear.getHeaderString("Location").split("area/")[1]);
        }

        Response respuesta = target.path(idCreado.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(200, respuesta.getStatus());
        Area area = respuesta.readEntity(Area.class);
        Assertions.assertNotNull(area);
        Assertions.assertEquals(idCreado, area.getIdArea());
    }

    @Order(4)
    @Test
    public void buscarPorIdNoEncontradoTest(){
        System.out.println("buscarPorIdNoEncontrado");

        Response respuesta = target.path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, respuesta.getStatus());
    }

    @Order(5)
    @Test
    public void actualizar(){
        System.out.println("buscarPorIdNoEncontrado");
        Area area = new Area();
        area.setNombre("NOMBRE_ACTUALIZADO");
        Response respuesta = target.path(idCreado.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(area));
        Assertions.assertEquals(200, respuesta.getStatus());
    }

    @Order(6)
    @Test
    public void eliminar(){
        Response respuesta = target.path(idCreado.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(204, respuesta.getStatus());
    }
}
