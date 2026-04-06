package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaClaveAreaResourceST extends AbstractIntegrationTest {

    @Override
    public String getResourceName() {
        return "prueba_clave";
    }

    private static class ContextoPruebaClaveArea {
        UUID idPrueba;
        UUID idPruebaClave;
        UUID idArea;
    }

    // Conexion SQL para crear prueba_clave porque no hay endpoint dedicado para crearla.
    private Connection abrirConexion() throws SQLException {
        String url = String.format("jdbc:postgresql://localhost:%d/tpi135", postgres.getMappedPort(5432));
        return DriverManager.getConnection(url, "postgres", "abc123");
    }

    // Raiz API comun para setup de entidades base.
    private WebTarget apiRoot() {
        return cliente.target(String.format("http://localhost:%d/nuevoingreso/v1", liberty.getMappedPort(9080)));
    }

    private UUID crearTipoPruebaPorApi() {
        TipoPrueba tipo = new TipoPrueba();
        tipo.setValor("TIPO-PCA-ST-" + UUID.randomUUID());
        tipo.setActivo(true);

        Response response = apiRoot().path("tipo_prueba")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tipo));

        Assertions.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    private UUID crearPruebaPorApi(UUID idTipoPrueba) {
        TipoPrueba tipo = new TipoPrueba();
        tipo.setIdTipoPrueba(idTipoPrueba);

        Prueba nuevaPrueba = new Prueba();
        nuevaPrueba.setNombre("PRUEBA-PCA-ST-" + UUID.randomUUID());
        nuevaPrueba.setIndicaciones("INDICACIONES ST PCA");
        nuevaPrueba.setPuntajeMaximo(new BigDecimal("10.00"));
        nuevaPrueba.setNotaAprobacion(new BigDecimal("6.00"));
        nuevaPrueba.setDuracion(90);
        nuevaPrueba.setFechaCreacion(OffsetDateTime.now().withNano(0));
        nuevaPrueba.setIdTipoPrueba(tipo);

        Response response = apiRoot().path("prueba")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevaPrueba));

        Assertions.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    private UUID crearAreaPorApi() {
        Area area = new Area();
        area.setNombre("AREA-PCA-ST-" + UUID.randomUUID());
        area.setActivo(true);

        Response response = apiRoot().path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(area));

        Assertions.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    private UUID insertarPruebaClave(Connection conexion, UUID idPrueba) throws SQLException {
        UUID idPruebaClave = UUID.randomUUID();
        String sql = "INSERT INTO prueba_clave (id_prueba_clave, nombre_clave, id_prueba) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, idPruebaClave);
            ps.setString(2, "CLAVE-ST-" + idPruebaClave.toString().substring(0, 8));
            ps.setObject(3, idPrueba);
            ps.executeUpdate();
        }
        return idPruebaClave;
    }

    // Crea contexto funcional para operar con /prueba_clave/{id_prueba_clave}/area.
    private ContextoPruebaClaveArea crearContexto(boolean incluirRelacion) throws SQLException {
        ContextoPruebaClaveArea contexto = new ContextoPruebaClaveArea();
        UUID idTipoPrueba = crearTipoPruebaPorApi();
        contexto.idPrueba = crearPruebaPorApi(idTipoPrueba);
        contexto.idArea = crearAreaPorApi();

        try (Connection conexion = abrirConexion()) {
            contexto.idPruebaClave = insertarPruebaClave(conexion, contexto.idPrueba);
        }

        if (incluirRelacion) {
            PruebaClaveArea relacion = new PruebaClaveArea();
            relacion.setIdPruebaClaveArea(contexto.idArea);

            Response crear = target.path(contexto.idPruebaClave.toString())
                    .path("area")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(relacion));
            Assertions.assertEquals(201, crear.getStatus());
        }

        return contexto;
    }

    @Order(1)
    @Test
    public void crearTest() throws SQLException {
        // Crea relacion prueba_clave-area con padre valido.
        System.out.println("crear en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(false);
        PruebaClaveArea nuevo = new PruebaClaveArea();
        nuevo.setIdPruebaClaveArea(contexto.idArea);

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
    }

    @Order(2)
    @Test
    public void crearParametrosInvalidosTest() throws SQLException {
        // Debe rechazar body invalido sin id de area.
        System.out.println("crearParametrosInvalidos en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(false);
        PruebaClaveArea nuevo = new PruebaClaveArea();

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(3)
    @Test
    public void crearDependenciaNoEncontradaTest() throws SQLException {
        // Debe responder 404 cuando prueba_clave o area no existen.
        System.out.println("crearDependenciaNoEncontrada en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(false);
        PruebaClaveArea nuevo = new PruebaClaveArea();
        nuevo.setIdPruebaClaveArea(contexto.idArea);

        Response respuesta = target.path(UUID.randomUUID().toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(404, respuesta.getStatus());
    }

    @Order(4)
    @Test
    public void buscarPorRangoTest() throws SQLException {
        // Lista relaciones por prueba_clave con total en header.
        System.out.println("buscarPorRango en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(true);

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .queryParam("first", 0)
                .queryParam("max", 50)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        List<PruebaClaveArea> registros = respuesta.readEntity(new GenericType<List<PruebaClaveArea>>() {});
        Assertions.assertNotNull(registros);
        Assertions.assertFalse(registros.isEmpty());
    }

    @Order(5)
    @Test
    public void buscarPorRangoParametrosInvalidosTest() throws SQLException {
        // Paginacion invalida debe responder 400.
        System.out.println("buscarPorRangoParametrosInvalidos en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(false);

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .queryParam("first", -1)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(400, respuesta.getStatus());
    }

    @Order(6)
    @Test
    public void buscarPorIdTest() throws SQLException {
        // Busca por clave compuesta existente.
        System.out.println("buscarPorId en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(true);

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .path(contexto.idArea.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, respuesta.getStatus());
        PruebaClaveArea encontrado = respuesta.readEntity(PruebaClaveArea.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(contexto.idArea, encontrado.getIdPruebaClaveArea());
    }

    @Order(7)
    @Test
    public void actualizarTest() throws SQLException {
        // Actualiza relacion existente manteniendo id_area del path.
        System.out.println("actualizar en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(true);

        PruebaClaveArea actualizar = new PruebaClaveArea();
        actualizar.setIdPruebaClaveArea(contexto.idArea);
        actualizar.setCantidad(2);
        actualizar.setPorcentaje(new BigDecimal("60.00"));

        Response respuesta = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .path(contexto.idArea.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(actualizar));

        Assertions.assertEquals(200, respuesta.getStatus());
    }

    @Order(8)
    @Test
    public void eliminarTest() throws SQLException {
        // Elimina relacion existente y valida que no se encuentre luego.
        System.out.println("eliminar en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(true);

        Response eliminar = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .path(contexto.idArea.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(204, eliminar.getStatus());

        Response buscar = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .path(contexto.idArea.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(404, buscar.getStatus());
    }

    @Order(9)
    @Test
    public void eliminarNoEncontradoTest() throws SQLException {
        // Debe responder 404 cuando la relacion no existe.
        System.out.println("eliminarNoEncontrado en PruebaClaveAreaResource");

        ContextoPruebaClaveArea contexto = crearContexto(false);

        Response eliminar = target.path(contexto.idPruebaClave.toString())
                .path("area")
                .path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(404, eliminar.getStatus());
    }
}
