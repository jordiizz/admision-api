package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.aspirante.crear;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest.ResponseHeaders;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;

import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class CrearAspiranteBDD {

    static Client cliente;

    static WebTarget target;

    static Network red = Network.newNetwork();

    static MountableFile war = MountableFile.forHostPath(Paths.get("target/admision-api.war").toAbsolutePath());

    static GenericContainer postgres = new PostgreSQLContainer("postgres:16")
            .withDatabaseName("tpi135")
            .withInitScript("database.sql")
            .withPassword("abc123")
            .withUsername("postgres")
            .withNetwork(red)
            .withNetworkAliases("db")
            .withExposedPorts(5432)
            ;

    static GenericContainer liberty = new GenericContainer("openliberty-pg:10.26.0.0.2")
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

    static Aspirante nuevo;

    @Given("se tiene un servidor corriendo con la aplicación desplegada.")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Startables.deepStart(List.of(postgres,liberty)).join();
        Assertions.assertTrue(liberty.isRunning());
        cliente = ClientBuilder.newClient();
        target = cliente.target(String.format("http://localhost:%d/nuevoingreso/v1/aspirante", liberty.getMappedPort(9080)));
    }
    @When("puedo crear un aspirante")
    public void puedo_crear_un_aspirante() {
        System.out.println("Crear un aspirante");
        nuevo = new Aspirante();
        nuevo.setNombres("Chepe");
        nuevo.setApellidos("Rodriguez");
        nuevo.setCorreo("chepe@gmail.com");
        nuevo.setFechaNacimiento(java.time.LocalDate.of(2000, 1, 1));
        nuevo.setFechaCreacion(OffsetDateTime.now());
        
        int esperado = 201;
        Response respuesta = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        
        if (respuesta.getStatus() != 201) {
            System.out.println("ERROR DEL SERVIDOR: " + respuesta.readEntity(String.class));
            System.out.println("HEADER PROCESS_ERROR: " + respuesta.getHeaderString("process-error"));
        }
        
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("aspirante/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: " + id);
        nuevo.setIdAspirante(id);
    }


    @When("puedo asociarle a una opcion de carrera, por ejemplo {word}")
    public void puedo_asociarle_a_una_opcion_de_carrera_por_ejemplo_i20515(String codigoCarrera) {
        System.out.println("Asociar opcion de carrera al aspirante");
        Assertions.assertNotNull(codigoCarrera);
        AspiranteOpcion opcion = new AspiranteOpcion();

        opcion.setIdOpcion(codigoCarrera);
        // opcion.setAnio(2026)
        opcion.setIdAspirante(nuevo);
        opcion.setPrioridad(1);


        Response respuesta = target
                .path("{id}/opcion").resolveTemplate("id", nuevo.getIdAspirante())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(opcion));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID idOpcion = UUID.fromString(respuesta.getHeaderString("Location").substring(respuesta.getHeaderString("Location").lastIndexOf("/") + 1));
        Assertions.assertNotNull(idOpcion);
        System.out.println("ID Opcion: " + idOpcion);
    }
    @Then("puedo consultar el perfil del aspirante recien creado")
    public void puedo_consultar_el_perfil_del_aspirante_recien_creado() {
        System.out.println("consultando aspirante");
        Response respuesta = target
                .path("{id}")
                .resolveTemplate("id", nuevo.getIdAspirante())
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(200, respuesta.getStatus());
        Aspirante encontrado = respuesta.readEntity(Aspirante.class);
        Assertions.assertTrue(nuevo.getIdAspirante().compareTo(encontrado.getIdAspirante()) == 0);
    }
    @Then("verificar la opcionn de carrera a la que fue asociado.")
    public void verificar_la_opcionn_de_carrera_a_la_que_fue_asociado() {
        System.out.println("consultando opciones estudiante");
        int first = 0;
        int max = 50;
        int esperado = 200;
        int total_esperado = 1;
        Response respuesta = target
                .path("{id_aspirante}/opcion")
                .resolveTemplate("id_aspirante", nuevo.getIdAspirante())
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        Assertions.assertEquals(total_esperado, Integer.parseInt(respuesta.getHeaderString(ResponseHeaders.TOTAL_RECORDS.toString())));
        List<AspiranteOpcion> registros = respuesta.readEntity(new GenericType<List<AspiranteOpcion>>() {});
        for(AspiranteOpcion registro : registros) {
            System.out.println("Opcion consultada: " + registro.getIdOpcion());
        }
    }
}
