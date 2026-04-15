package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.aspirante.jornada;

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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

import java.time.OffsetDateTime;
import java.util.List;

public class AsignarAspiranteJornadaBDD extends AbstractBDD {

    @Before
    public void setup() {
        initClient();
    }

    AspiranteOpcion aspiranteOpcion;
    Aspirante aspirante;
    Jornada jornada;
    JornadaAula jornadaAula;
    Prueba prueba;
    int first= 0;
    int max = 50;

    @Given("se tiene un servidor corriendo con la aplicación desplegada.")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }

    @Given("existe un aspirante con una opción de carrera registrada.")
    public void existe_un_aspirante_con_una_opción_de_carrera_registrada() {
        System.out.println("Buscar Aspirante con opción de carrera registrada");
        int esperado = 200;

        // Obteniendo Aspirante
        Response respuesta = target.path("aspirante")
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<Aspirante> lista = respuesta.readEntity(new GenericType<List<Aspirante>>() {});
        Assertions.assertNotNull(lista);
        Assertions.assertFalse(lista.isEmpty());
        aspirante = lista.getFirst();
        System.out.println("ID: " + aspirante.getIdAspirante());

        Response resOpcion = target.path("aspirante")
                .path(aspirante.getIdAspirante().toString())
                .path("opcion")
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, resOpcion.getStatus());
        List<AspiranteOpcion> opciones = resOpcion.readEntity(new GenericType<List<AspiranteOpcion>>() {});
        Assertions.assertNotNull(opciones);
        Assertions.assertFalse(opciones.isEmpty());
        aspiranteOpcion = opciones.getFirst();
        System.out.println("Aspirante: " + aspiranteOpcion.getIdAspirante().getIdAspirante());
        System.out.println("Opcion: " + aspiranteOpcion.getIdAspiranteOpcion());
    }

    @Given("existe una prueba activa.")
    public void existe_una_prueba_activa() {
        System.out.println("Buscando prueba disponible");
        int esperado = 200;

        // Obteniendo Aspirante
        Response respuesta = target.path("prueba")
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<Prueba> lista = respuesta.readEntity(new GenericType<List<Prueba>>() {});
        Assertions.assertNotNull(lista);
        Assertions.assertFalse(lista.isEmpty());
        prueba = lista.getFirst();
        System.out.println("Id: "+ prueba.getIdPrueba() + " Nombre:" + prueba.getNombre());
    }

    @Given("esa prueba está asociada a una jornada.")
    public void esa_prueba_está_asociada_a_una_jornada() {
        System.out.println("Verificar que la prueba este asociada a una jornada");
        int esperado = 200;
        int max = 10;

        // Obteniendo Aspirante
        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
                .path("jornada")
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<Jornada> lista = respuesta.readEntity(new GenericType<List<Jornada>>() {});
        Assertions.assertNotNull(lista);
        Assertions.assertFalse(lista.isEmpty());
        jornada= lista.getFirst();
        System.out.println("jornada: "+  jornada.getIdJornada());

    }
    @Given("la jornada tiene un aula disponible.")
    public void la_jornada_tiene_un_aula_disponible() {

        int esperado = 200;

        Response resAula = target.path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .queryParam("first", first)
                .queryParam("max", max)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, resAula.getStatus());
        List<JornadaAula> jornadaAulas = resAula.readEntity(new GenericType<List<JornadaAula>>() {});
        Assertions.assertNotNull(jornadaAulas);
        Assertions.assertFalse(jornadaAulas.isEmpty());
        jornadaAula = jornadaAulas.getFirst();
        Assertions.assertEquals(jornadaAula.getIdJornada().getIdJornada(), jornada.getIdJornada());
        System.out.println("Aula: " + jornadaAula.getIdAula());
        System.out.println("Jornada: " + jornadaAula.getIdJornada().getIdJornada());
    }

    @When("asigno al aspirante a la prueba en dicha jornada y aula.")
    public void asigno_al_aspirante_a_la_prueba_en_dicha_jornada_y_aula() {
        System.out.println("Asignando aspirante a la prueba en dicha jornada y aula.");
        int esperado = 201;

        PruebaJornadaAulaAspiranteOpcion entidad = new PruebaJornadaAulaAspiranteOpcion();
        entidad.setFecha(OffsetDateTime.now().withNano(0));
        entidad.setIdAspiranteOpcion(aspiranteOpcion);

        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
                .path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .path(jornadaAula.getIdAula())
                .path("aspirante_opcion")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(entidad));

        // Imprimir el error si no es 201 para debuguear el 500
        if(respuesta.getStatus() != esperado){
            System.err.println("Error del servidor: " + respuesta.readEntity(String.class));
        }

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());

    }

    @Then("verificar que el aspirante está asignado a la jornada y al {string}.")
    public void verificar_que_el_aspirante_está_asignado_a_la_jornada_y_al(String string) {
        System.out.println("Obteniendo el restistro creado");

        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
                .path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .path(jornadaAula.getIdAula())
                .path("aspirante_opcion")
                .path(aspiranteOpcion.getIdAspiranteOpcion().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        PruebaJornadaAulaAspiranteOpcion entidad = respuesta.readEntity(PruebaJornadaAulaAspiranteOpcion.class);
        Assertions.assertNotNull(entidad);
        Assertions.assertEquals(entidad.getIdAula(), string);
        Assertions.assertEquals(entidad.getIdJornada().getIdJornada(), jornada.getIdJornada());
        Assertions.assertEquals(entidad.getIdPrueba().getIdPrueba(), prueba.getIdPrueba());
        Assertions.assertEquals(entidad.getIdAspiranteOpcion().getIdAspiranteOpcion(), aspiranteOpcion.getIdAspiranteOpcion());
        System.out.println("Aula: " + entidad.getIdAula());
        System.out.println("AspiranteOpcion: " + entidad.getIdAspiranteOpcion().getIdAspiranteOpcion());
    }

}
