package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.pregunta.area;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaArea;

import java.util.UUID;

public class AsignarPreguntaAreaBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    Area area;
    Pregunta pregunta;

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }
    @Given("existe el área de conocimiento {string} con el ID {string}")
    public void existe_el_área_de_conocimiento_con_el_id(String nombre, String id) {

        int esperado = 200;

        Response respuesta = target.path("area")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        area = respuesta.readEntity(Area.class);
        Assertions.assertNotNull(area);
        Assertions.assertEquals(area.getIdArea().toString(), id);
        Assertions.assertEquals(area.getNombre(), nombre);
        System.out.println("Area: "+ area.getNombre());

    }
    @When("envío la petición para crear la pregunta {string}")
    public void envío_la_petición_para_crear_la_pregunta(String nombre) {
        System.out.println("Creando la prueba");

        int esperado = 201;
        pregunta = new Pregunta();
        pregunta.setValor(nombre);
        pregunta.setActivo(true);

        Response respuesta = target.path("pregunta")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pregunta));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("pregunta/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: " + id);
        pregunta.setIdPregunta(id);
    }
    @Then("asocicio la pregunta creada a el area")
    public void asocicio_la_pregunta_creada_a_el_area() {
        System.out.println("Asociando la pregunta creada a el area");

        int esperado = 201;

        Response respuesta = target.path("pregunta")
                .path(pregunta.getIdPregunta().toString())
                .path("area")
                .path(area.getIdArea().toString())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(null));
        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
    }
    @Then("al consultar verifico que se haya realizado la relacion correctamente")
    public void al_consultar_verifico_que_se_haya_realizado_la_relacion_correctamente() {
        System.out.println("Cosultando la relacion correctamente");

        int esperado = 200;

        Response respuesta = target.path("pregunta")
                .path(pregunta.getIdPregunta().toString())
                .path("area")
                .path(area.getIdArea().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        PreguntaArea preguntaArea = respuesta.readEntity(PreguntaArea.class);
        Assertions.assertNotNull(preguntaArea);
        Assertions.assertEquals(area.getIdArea(), preguntaArea.getIdArea().getIdArea());
        Assertions.assertEquals(pregunta.getIdPregunta(), preguntaArea.getIdPregunta().getIdPregunta());
        Assertions.assertEquals(area.getNombre(), preguntaArea.getIdArea().getNombre());
    }
}
