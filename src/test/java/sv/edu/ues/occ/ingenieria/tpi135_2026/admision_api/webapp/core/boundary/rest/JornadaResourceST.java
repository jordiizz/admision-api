package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JornadaResourceST extends AbstractIntegrationTest{

    @Override
    public String getResourceName() {
        return "jornada";
    }

    @Order(1)
    @Test
    public void crearTest(){
        System.out.println("crearTest en JornadaResource");

        int esperado = 201;

        Jornada nuevo = new Jornada();
        nuevo.setNombre("Jornada 1");
        nuevo.setFechaInicio(OffsetDateTime.now());
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(1));

        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("jornada/")[1]);
        Assertions.assertNotNull(id);
        System.out.println(id);
    }

    @Order(2)
    @Test
    public void crearConIdInvalidoTest(){
        System.out.println("\ncrearConIdInvalido en JornadaResource");

        // El id se crea en el resource
        int esperado = 400;

        Jornada nuevo = new Jornada(UUID.randomUUID());
        nuevo.setNombre("Jornada 2");
        nuevo.setFechaInicio(OffsetDateTime.now());
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(1));

        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperado, respuesta.getStatus());
    }

    @Order(3)
    @Test
    public void buscarPorRangoTest(){
        System.out.println("\nbuscarPorRango en JornadaResource");
        int first = 0;
        int max = 50;
        int esperado = 200;

        Response respuesta = target.queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        List<Jornada> registros = respuesta.readEntity(new GenericType<List<Jornada>>() {});
        Assertions.assertNotNull(registros);
        Assertions.assertEquals(1, Integer.parseInt(respuesta.getHeaderString(ResponseHeaders.TOTAL_RECORDS.toString())));
        for (Jornada jornada : registros) {
            System.out.println(jornada);
        }
    }

    @Order(4)
    @Test
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("\nbuscarPorRangoParametrosInvalidos en JornadaResource");

        Response respuesta = target.queryParam("first", -1)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(5)
    @Test
    public void buscarPorIdTest(){
        System.out.println("\nbuscarPorId en JornadaResource");

        int esperadoBuscar = 200;
        int esperadoCrear = 201;

        // Crear el registro a buscar
        Jornada nuevo = new Jornada();
        nuevo.setNombre("Jornada 3");
        nuevo.setFechaInicio(OffsetDateTime.now());
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(1));

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperadoCrear, crear.getStatus());
        UUID id = UUID.fromString(crear.getHeaderString("Location").split("jornada/")[1]);

        // buscar el registro
        Response buscar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperadoBuscar, buscar.getStatus());
        Jornada encontrado = buscar.readEntity(Jornada.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(id, encontrado.getIdJornada());
        Assertions.assertEquals(nuevo.getNombre(), encontrado.getNombre());
    }

    @Order(6)
    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("\nbuscarPorIdNoEncontrado en JornadaResource");

        Response buscar = target.path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, buscar.getStatus());
    }

     @Order(7)
    @Test
    public void actualizarTest(){
         System.out.println("\nactualizar en JornadaResource");

         int esperadoActualizar = 200;
         int esperadoCrear = 201;

         // Crear el registro a actualizar
         Jornada nuevo = new Jornada();
         nuevo.setNombre("Jornada 4");
         nuevo.setFechaInicio(OffsetDateTime.now());
         nuevo.setFechaFin(OffsetDateTime.now().plusDays(1));

         Response crear = target.request(MediaType.APPLICATION_JSON)
                 .post(Entity.json(nuevo));

         Assertions.assertEquals(esperadoCrear, crear.getStatus());
         UUID id = UUID.fromString(crear.getHeaderString("Location").split("jornada/")[1]);

         // Actualizar
         nuevo.setNombre("Jornada Actualizada");
         Response respuesta = target.path(id.toString())
                 .request(MediaType.APPLICATION_JSON)
                 .put(Entity.json(nuevo));

         Assertions.assertEquals(esperadoActualizar, respuesta.getStatus());
         Jornada actualizado = respuesta.readEntity(Jornada.class);
         Assertions.assertNotNull(actualizado);
         Assertions.assertEquals(nuevo.getNombre(), actualizado.getNombre());
         System.out.println("Actualizado: "+actualizado);
    }

    @Order(8)
    @Test
    public void eliminarTest(){
        System.out.println("\neliminar en JornadaResource");

        int esperadoEliminar = 204; // not content
        int esperadoCrear = 201;
        int esperadoBuscar = 404;

        // Crear el registro a actualizar
        Jornada nuevo = new Jornada();
        nuevo.setNombre("Jornada 4");
        nuevo.setFechaInicio(OffsetDateTime.now());
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(1));

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperadoCrear, crear.getStatus());
        UUID id = UUID.fromString(crear.getHeaderString("Location").split("jornada/")[1]);

        // eliminar
        Response eliminar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(esperadoEliminar, eliminar.getStatus());

        Response buscar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperadoBuscar, buscar.getStatus());
    }


}
