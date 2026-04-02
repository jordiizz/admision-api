package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;

import java.util.List;
import java.util.UUID;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DistractorResourceST extends AbstractIntegrationTest{

    @Override
    public String getResourceName() {
        return "distractor";
    }

    @Order(1)
    @Test
    public void crearTest(){
        System.out.println("crearTest en DistractorResource");
        Distractor nuevo = new Distractor();
        nuevo.setValor("Distractor 1");
        int esperado = 201;
        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("distractor/")[1]);
        Assertions.assertNotNull(id);

    }

    @Order(4)
    @Test
    public void crearConIdInvalidoTest(){
        System.out.println("crearConIdInvalido en DistractorResource");

        // El id se crea en el resource
        int esperado = 400;

        Distractor nuevo = new Distractor(UUID.randomUUID());
        nuevo.setValor("Distractor 1");

        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperado, respuesta.getStatus());
    }

    @Order(3)
    @Test
    public void buscarPorRangoTest(){
        System.out.println("buscarPorRango en DistractorResource");
        int first = 0;
        int max = 50;
        int esperado = 200;
        Response respuesta = target.queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        List<Distractor> registros = respuesta.readEntity(new GenericType<List<Distractor>>() {});
        Assertions.assertNotNull(registros);
        // El esperado es 1 porque en el test anterior se creo un registro en la base de datos
        Assertions.assertEquals(1,  Integer.parseInt(respuesta.getHeaderString(ResponseHeaders.TOTAL_RECORDS.toString())));
        for(Distractor registro : registros){
            System.out.println(registro.getIdDistractor() + " " + registro.getValor());
        }
    }

    @Order(4)
    @Test
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("buscarPorRangoParametrosInvalidos en DistractorResource");

        Response respuesta = target.queryParam("first", -1)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(5)
    @Test
    public void buscarPorIdTest(){
        System.out.println("buscarPorId en DistractorResource");

        int esperadoBuscar = 200;
        int esperadoCrear = 201;

        // Crear el registro a buscar
        Distractor nuevo = new Distractor();
        nuevo.setValor("Distractor 2");

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperadoCrear, crear.getStatus());

        UUID id = UUID.fromString(crear.getHeaderString("Location").split("distractor/")[1]);

        // buscar el registro
        Response buscar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperadoBuscar, buscar.getStatus());
        Distractor encontrado = buscar.readEntity(Distractor.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(id, encontrado.getIdDistractor());
        Assertions.assertEquals(nuevo.getValor(), encontrado.getValor());
    }

    @Order(6)
    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("buscarPorIdNoEncontrado en DistractorResource");

        Response buscar = target.path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, buscar.getStatus());
    }

    @Order(7)
    @Test
    public void actualizarTest(){
        System.out.println("actualizarTest en DistractorResource");

        int esperadoActualizar = 200;
        int esperadoCrear = 201;

        // Crear el registro a actualizar
        Distractor nuevo = new Distractor();
        nuevo.setValor("Distractor 3");

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperadoCrear, crear.getStatus());

        UUID id = UUID.fromString(crear.getHeaderString("Location").split("distractor/")[1]);

        // Actualizar
        nuevo.setValor("Distractor Actualizado");
        Response respuest = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(nuevo));

        Assertions.assertEquals(esperadoActualizar, respuest.getStatus());
        Distractor actualizado = respuest.readEntity(Distractor.class);
        Assertions.assertEquals(nuevo.getValor(), actualizado.getValor());
        System.out.println("Actualizado: " + actualizado.getValor());
    }

    @Order(8)
    @Test
    public void eliminarTest(){
        System.out.println("eliminarTest en DistractorResource");
        int esperadoEliminar = 204; // not content
        int esperadoCrear = 201;
        int esperadoBuscar = 404;

        // Crear el registro a actualizar
        Distractor nuevo = new Distractor();
        nuevo.setValor("Distractor 4");

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperadoCrear, crear.getStatus());

        UUID id = UUID.fromString(crear.getHeaderString("Location").split("distractor/")[1]);

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
