package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.opciones;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest.ResponseHeaders;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpcionesBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    static Aspirante nuevo;

    @Given("se tiene un servidor corriendo con la aplicación desplegada.")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        assertTrue(postgres.isRunning());
        assertTrue(liberty.isRunning());
    }
    @When("puedo crear un aspirante")
    public void puedo_crear_un_aspirante() {

        System.out.println("Crear un aspirante");
        nuevo = new Aspirante();
        nuevo.setNombres("Juan");
        nuevo.setApellidos("Rodriguez");
        nuevo.setCorreo("juan@gmail.com");
        nuevo.setFechaNacimiento(java.time.LocalDate.of(2000, 1, 1));
        nuevo.setFechaCreacion(OffsetDateTime.now());

        int esperado = 201;
        Response respuesta = target.path("aspirante").request(MediaType.APPLICATION_JSON)
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
    @When("puedo asociarle opciones de carrear por ejemplo {word} y {word}")
    public void puedo_asociarle_opciones_de_carrear_por_ejemplo_i30515_y_i30516(String codigoCarrera1, String codigoCarrera2) {
        System.out.println("Asociar opcion de carrera al aspirante");
        Assertions.assertNotNull(codigoCarrera1);
        AspiranteOpcion opcion = new AspiranteOpcion();

        opcion.setIdOpcion(codigoCarrera1);
        // opcion.setAnio(2026)
        opcion.setIdAspirante(nuevo);
        opcion.setPrioridad(1);


        Response respuesta = target.path("aspirante")
                .path("{id}/opcion").resolveTemplate("id", nuevo.getIdAspirante())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(opcion));

        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID idOpcion = UUID.fromString(respuesta.getHeaderString("Location").substring(respuesta.getHeaderString("Location").lastIndexOf("/") + 1));
        Assertions.assertNotNull(idOpcion);
        System.out.println("ID Opcion: " + idOpcion);

        // Asignamos segunda opcion
        System.out.println("Asociar segunda opcion de carrera al aspirante");
        Assertions.assertNotNull(codigoCarrera1);
        AspiranteOpcion opcion2 = new AspiranteOpcion();

        opcion2.setIdOpcion(codigoCarrera2);
        // opcion.setAnio(2026)
        opcion2.setIdAspirante(nuevo);
        opcion2.setPrioridad(2);


        Response respuesta2 = target.path("aspirante")
                .path("{id}/opcion").resolveTemplate("id", nuevo.getIdAspirante())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(opcion2));
        Assertions.assertEquals(201, respuesta2.getStatus());
        Assertions.assertTrue(respuesta2.getHeaders().containsKey("Location"));
        UUID idOpcion2 = UUID.fromString(respuesta2.getHeaderString("Location").substring(respuesta2.getHeaderString("Location").lastIndexOf("/") + 1));
        Assertions.assertNotNull(idOpcion2);
        System.out.println("ID Opcion: " + idOpcion2);

    }

    @Then("verificar las opciones de carrera asociadas al aspirante y su prioridad")
    public void verificar_las_opciones_de_carrera_asociadas_al_aspirante_y_su_prioridad() {
        System.out.println("consultando opciones estudiante");
        int first = 0;
        int max = 50;
        int esperado = 200;
        int total_esperado = 2;
        Response respuesta = target.path("aspirante")
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
