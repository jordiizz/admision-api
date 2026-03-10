package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AreaResourceIT {

    static Client cliente;

    static WebTarget target;

    static Network red = Network.newNetwork();

    static MountableFile war = MountableFile.forHostPath(Paths.get("target/admision-api.war").toAbsolutePath());

    @Container
    static GenericContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("tpi135")
            .withInitScript("database.sql")
            .withPassword("abc123")
            .withUsername("postgres")
            .withNetwork(red)
            .withNetworkAliases("db")
            .withExposedPorts(5432)
            ;

    @Container
    static GenericContainer liberty = new GenericContainer("openliberty-pg:10.24.0.0.8")
            .withEnv("PGSERVER", "db")
            .withEnv("PGPORT", "5432")
            .withEnv("PGDBNAME", "tpi135")
            .withEnv("PGUSER", "postgres")
            .withEnv("PGPASSWORD", "abc123")
            .dependsOn(postgres) // que no arranque si no ha arrancado la base de datos
            .withNetwork(red)
            .withCopyFileToContainer(war, "/home/usuario/wlp/usr/servers/app/dropins/nuevoingreso.war")
            .withExposedPorts(9080)
            ;

    @BeforeAll
    public void inicializar(){
        Assertions.assertTrue(liberty.isRunning());
        cliente = ClientBuilder.newClient();
        target = cliente.target(String.format("http://localhost:%d/nuevoingreso/v1/area", liberty.getMappedPort(9080)));
    }

    @Order(1)
    @Test
    public void buscarPorRangoTest(){
        System.out.println("findRange");
        int first = 0;
        int max = 50;
        int esperado = 200;
        int total_esperado = 0;
        Response respuesta = target
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Total_Records"));
        Assertions.assertEquals(total_esperado, Integer.parseInt(respuesta.getHeaderString("Total_Records")));
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
        // Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        // UUID id= UUID.fromString(respuesta.getHeaderString("Location").split("area/")[1]);
        //Assertions.assertNotNull(id);
        //System.out.print("ID: "+ id);
    }
}
