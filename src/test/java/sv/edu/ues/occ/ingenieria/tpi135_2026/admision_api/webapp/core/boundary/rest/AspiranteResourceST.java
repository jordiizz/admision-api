package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AspiranteResourceST extends AbstractIntegrationTest {

    @Override
    public String getResourceName() {
        return "aspirante";
    }

    @Order(1)
    @Test
    public void buscarPorRangoTest() {
        System.out.println("buscarPorRango en AspiranteResource");

        Response respuesta = target.queryParam("first", 0)
                .queryParam("max", 50)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        List<Aspirante> registros = respuesta.readEntity(new GenericType<List<Aspirante>>() {});
        Assertions.assertNotNull(registros);
    }

    @Order(2)
    @Test
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("buscarPorRangoParametrosInvalidos en AspiranteResource");

        Response respuesta = target.queryParam("first", -1)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(3)
    @Test
    public void crearTest() {
        System.out.println("crear en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
    }

    @Order(4)
    @Test
    public void crearConIdInvalidoTest() {
        System.out.println("crearConIdInvalido en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setIdAspirante(UUID.randomUUID());
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(5)
    @Test
    public void buscarPorIdTest() {
        System.out.println("buscarPorId en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2001, 2, 2));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        Assertions.assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Response buscar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, buscar.getStatus());
        Aspirante encontrado = buscar.readEntity(Aspirante.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(id, encontrado.getIdAspirante());
    }

    @Order(6)
    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("buscarPorIdNoEncontrado en AspiranteResource");

        Response buscar = target.path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, buscar.getStatus());
    }

    @Order(7)
    @Test
    public void actualizarTest() {
        System.out.println("actualizar en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2002, 3, 3));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        Assertions.assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Aspirante actualizar = new Aspirante();
        actualizar.setNombres("NOMBRES-ACTUALIZADO");
        actualizar.setApellidos("APELLIDOS-ACTUALIZADO");
        actualizar.setFechaNacimiento(LocalDate.of(2002, 3, 3));
        actualizar.setDocumentoIdentidad("DOC-ACT-ST");
        actualizar.setCorreo("actualizado@test.com");
        actualizar.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(actualizar));

        Assertions.assertEquals(200, respuesta.getStatus());
    }

    @Order(8)
    @Test
    public void eliminarTest() {
        System.out.println("eliminar en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2003, 4, 4));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        Assertions.assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Response eliminar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(204, eliminar.getStatus());

        Response buscar = target.path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, buscar.getStatus());
    }
}
